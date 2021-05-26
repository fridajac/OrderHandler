package client;

import server.AbstractKitchenServer;
import shared.*;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class OrderClient extends AbstractOrderClient {

    private AbstractKitchenServer abstractKitchenServer;
    private Order order = new Order();
    private GenericRestaurantForm form;

    public OrderClient(AbstractKitchenServer abstractKitchenServer) {
        this.abstractKitchenServer = abstractKitchenServer;
    }

    public void addItemToOrder(OrderItem item) {
        order.addOrderItem(item);
    }

    public void removeItemToOrder(OrderItem item) {
        order.removeOrderItem(item);
    }

    @Override
    public void setGUI(GenericRestaurantForm genericRestaurantForm) {
        this.form = genericRestaurantForm;
    }

    @Override
    public void submitOrder() {
        Thread submitThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    CompletableFuture<KitchenStatus> currentStatus = abstractKitchenServer.receiveOrder(order);
                    order.setSent(true);
                    KitchenStatus kitchenStatus = currentStatus.get();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            form.setStatus(kitchenStatus.text);
                        }
                    });

                    OrderClient.this.startPollingServer(order.getOrderID());
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        submitThread.start();
    }

    @Override
    protected void startPollingServer(String orderId) {
        Thread pollingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                TimerTask task = new TimerTask() {
                    public void run() {
                        boolean continuePolling = true;
                        while (continuePolling) {
                            try {
                                CompletableFuture<OrderStatus> currentStatus = abstractKitchenServer.checkStatus(orderId);
                                OrderStatus status = currentStatus.get();
                                if (status == OrderStatus.Ready) {
                                    order.setDone(true);
                                    pickUpOrder();
                                    continuePolling = false;
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                Timer timer = new Timer("PollingTimer");
                timer.scheduleAtFixedRate(task, 3000, 1000);
            }
        });
        pollingThread.start();
    }

    @Override
    protected void pickUpOrder() {
        String orderId = order.getOrderID();
        Thread pickUpThread = new Thread(() -> {
            try {
                CompletableFuture<KitchenStatus> order = abstractKitchenServer.serveOrder(orderId);
                KitchenStatus status = order.get();
                SwingUtilities.invokeLater(new Runnable() {
                                               @Override
                                               public void run() {
                                                   form.setStatus(status.text);
                                               }
                });


                        System.out.println("mottagit färdig mat");
            }
            catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        pickUpThread.start();
    }
}

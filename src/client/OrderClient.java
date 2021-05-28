package client;

import server.AbstractKitchenServer;
import shared.*;

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

    public void removeTtemFromOrderIndex(int valueToDelete) {
        order.removeOrderItemIndex(valueToDelete);
    }


    @Override
    public void setGUI(GenericRestaurantForm genericRestaurantForm) {
        this.form = genericRestaurantForm;
    }

    @Override
    public void submitOrder() {
        Thread submitThread = new Thread(new Runnable() { //async by creating a new thread
            KitchenStatus status;

            @Override
            public void run() {
                KitchenStatus status;
                try {
                    form.setStatus(KitchenStatus.Sent);
                    System.out.println("Now we send new order to server");
                    CompletableFuture<KitchenStatus> completableFuture = abstractKitchenServer.receiveOrder(order);
                    status = completableFuture.get();
                    form.setStatus(status);
                    order.setSent(true);
                    if (status == KitchenStatus.Received) {
                        startPollingServer(order.getOrderID());
                    }
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        submitThread.start();
    }

    @Override
    protected void startPollingServer(String orderId) {
        TimerTask task = new TimerTask() {
            boolean continuePolling = true;
            boolean displayCookingStatus = true;
            public void run() {
                while (continuePolling) {
                    System.out.println("Polling for status");
                    try {
                        CompletableFuture<OrderStatus> currentStatus = abstractKitchenServer.checkStatus(orderId);
                        OrderStatus status = currentStatus.get();
                        if (status == OrderStatus.BeingPrepared && displayCookingStatus) {
                            form.setStatus(KitchenStatus.Cooking);
                            displayCookingStatus = false;
                        }
                        else if (status == OrderStatus.Ready) {
                            order.setDone(true);
                            pickUpOrder();
                            System.out.println("Picking up order");
                            continuePolling = false;
                        }
                    }
                    catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Timer timer = new Timer("PollingTimer");
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    @Override
    protected void pickUpOrder() {
        String orderId = order.getOrderID();
        Thread pickUpThread = new Thread(() -> {
            try {
                CompletableFuture<KitchenStatus> kitchenStatus = abstractKitchenServer.serveOrder(orderId);
                KitchenStatus status = kitchenStatus.get();
                form.setStatus(status);
                System.out.println("Food is received, we did it!!");
            }
            catch (InterruptedException | ExecutionException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        pickUpThread.start();
    }
}

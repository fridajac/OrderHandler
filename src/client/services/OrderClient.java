package client.services;

import client.view.GenericRestaurantForm;
import server.AbstractKitchenServer;
import shared.KitchenStatus;
import shared.Order;
import shared.OrderStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Represent a self-service app
 * Allows a user to assemble an order, submit it to the KitchenSever and output status of the order.
 * Send a request to KitchenServer through the network, GUI should stay responsive through async method calls.
 * For setting up a periodic task, you may use Timer.scheduleAtFiedRate togehter with TimerTask.
 */

public class OrderClient extends AbstractOrderClient {

    private AbstractKitchenServer abstractKitchenServer;
    private Order order = new Order();
    private GenericRestaurantForm form;

    public OrderClient(AbstractKitchenServer abstractKitchenServer) {
        this.abstractKitchenServer = abstractKitchenServer;
    }

    @Override
    public void setForm(GenericRestaurantForm genericRestaurantForm) {
        this.form = genericRestaurantForm;
    }

    @Override
    public void submitOrder() {
        Thread submitThread = new Thread(() -> {
            try {
                CompletableFuture<KitchenStatus> currentStatus = abstractKitchenServer.receiveOrder(order);
                order.setSent(true);
                KitchenStatus kitchenStatus = currentStatus.get();
                form.setStatus(kitchenStatus.text);
                startPollingServer(order.getOrderID());
            }
            catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        submitThread.start();
    }

    @Override
    protected void startPollingServer(String orderId) {
        Thread pollingThread = new Thread(() -> {
            TimerTask task = new TimerTask() {
                public void run() {
                    System.out.println("polling for status");
                    try {
                        CompletableFuture<OrderStatus> currentStatus = abstractKitchenServer.checkStatus(orderId);
                        OrderStatus status = currentStatus.get();
                        form.setStatus(status.text);
                        if (status == OrderStatus.Ready) {
                            order.setDone(true);
                            pickUpOrder();
                        }
                    }
                    catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            };
            Timer timer = new Timer("Timer");
            timer.scheduleAtFixedRate(task, 1000, 1000);
        });
        pollingThread.start();
    }

    @Override
    protected void pickUpOrder() {
        if (order.isDone()) {
            Thread pickUpThread = new Thread(() -> {
           /* try {
                //CompletableFuture<OrderStatus> order = abstractKitchenServer.serveOrder(order.getOrderID());
            }
            //catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }*/
                //Start an asynchronous request to {@link AbstractKitchenServer#serveOrder(String)}
            });
            pickUpThread.start();
        }
    }
}

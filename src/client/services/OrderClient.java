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

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private AbstractKitchenServer abstractKitchenServer;
    private Order order = new Order();
    private GenericRestaurantForm form;

    public OrderClient(String ipAddress, int port, AbstractKitchenServer abstractKitchenServer) throws IOException {
        socket = new Socket(ipAddress, port);
        oos = new ObjectOutputStream(socket.getOutputStream());
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
                oos.writeObject(order);
                oos.flush();
                startPollingServer(order.getOrderID());
                ois = new ObjectInputStream(socket.getInputStream());
                CompletableFuture<KitchenStatus> currentStatus =(CompletableFuture<KitchenStatus>) ois.readObject();
                form.setStatus(currentStatus.toString());
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e) {
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
                    if (status == OrderStatus.Ready) {
                        pickUpOrder();
                        Thread.interrupted();
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

package server;

import client.GenericRestaurantForm;
import shared.KitchenStatus;
import shared.Order;
import shared.OrderStatus;
import shared.Randomizer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class KitchenServer extends AbstractKitchenServer {

    private ExecutorService receivingThreadPool;
    private ExecutorService cookingThreadPool;
    private Map<String, Order> orderMap = new HashMap<>();
    private GenericRestaurantForm form;

    public KitchenServer() {
        receivingThreadPool = Executors.newFixedThreadPool(3);
        cookingThreadPool = Executors.newFixedThreadPool(3);
    }

    @Override
    public void setGUI(GenericRestaurantForm genericRestaurantForm) {
        this.form = form;
    }

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
        Thread.sleep(Randomizer.getRandom());
        orderMap.put(order.getOrderID(), order); //saves the order to map
        CompletableFuture<KitchenStatus> completableFuture = new CompletableFuture<KitchenStatus>();
        receivingThreadPool.submit(() -> {
            completableFuture.complete(KitchenStatus.Received);
            order.setStatus(OrderStatus.Received);
            System.out.println("now we will send the " + order.getOrderID() + " to the kitchen with thread " + Thread.currentThread().getName());
            cook(order);
        });
        Thread.sleep(Randomizer.getRandom());
        return completableFuture;
    }

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        CompletableFuture<OrderStatus> status = new CompletableFuture<OrderStatus>();
        status.complete(orderMap.get(orderID).getStatus()); //sets the value to current status
        Thread.sleep(Randomizer.getRandom());
        return status;
    }

    /**
     * Method is called from client when OrderStatus is "Ready"
     */
    @Override
    public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {
        orderMap.remove(orderID);
        CompletableFuture<KitchenStatus> status = new CompletableFuture<KitchenStatus>();
        status.complete(KitchenStatus.Served);
        Thread.sleep(Randomizer.getRandom());
        System.out.println("server returning cooked meal " + orderID + " to client");
        return status;
    }

    @Override
    protected void cook(Order order) {
        System.out.println(order.getOrderID() + " has arrived in cooking method");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cookingThreadPool.submit(new CookingTask(order)); //task that changes status of order
            }
        });
    }
}


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

    public KitchenServer() {
        receivingThreadPool = Executors.newFixedThreadPool(3);
        cookingThreadPool = Executors.newFixedThreadPool(3);
    }

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
        CompletableFuture<KitchenStatus> completableFuture = new CompletableFuture<KitchenStatus>();
        receivingThreadPool.submit(() -> {
            if (order == null || order.getOrderList().size() == 0) {
                order.setStatus(OrderStatus.NotFound);
                completableFuture.complete(KitchenStatus.Rejected);
            }
            else {
                orderMap.put(order.getOrderID(), order); //saves the order to map
                order.setStatus(OrderStatus.Received);
                completableFuture.complete(KitchenStatus.Received);
                cook(order);
            }
        });
        Thread.sleep(Randomizer.getRandom());
        return completableFuture;
    }

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        CompletableFuture<OrderStatus> status = new CompletableFuture<OrderStatus>();
        Order order = orderMap.get(orderID);
        OrderStatus orderStatus = order.getStatus();
        status.complete(orderStatus);
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
        System.out.println("server returning cooked meal to client");
        return status;
    }

    @Override
    protected void cook(Order order) {
        try {
            Thread.sleep(Randomizer.getRandom());
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cookingThreadPool.submit(new CookingTask(order)); //task that changes status of order until ready
            }
        });
    }
}
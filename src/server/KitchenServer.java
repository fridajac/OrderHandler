package server;

import shared.KitchenStatus;
import shared.Order;
import shared.OrderStatus;
import shared.Randomizer;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Server that handles an order and changes status of it until it's ready
 */
public class KitchenServer extends AbstractKitchenServer {

    private ExecutorService receivingThreadPool; //thread pool handling new orders
    private ExecutorService cookingThreadPool; //thread pool handling cooking task
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
                completableFuture.completeAsync(() -> KitchenStatus.Rejected);
            }
            else {
                orderMap.put(order.getOrderID(), order); //saves the order to map
                order.setStatus(OrderStatus.Received);
                completableFuture.completeAsync(() -> KitchenStatus.Received);
                cook(order);
            }
        });
        Thread.sleep(Randomizer.getRandom());
        return completableFuture;
    }

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        Order order = orderMap.get(orderID);
        OrderStatus orderStatus = order.getStatus(); //get current order status
        CompletableFuture<OrderStatus> status = CompletableFuture.supplyAsync(() -> orderStatus); //status could be ready, received or being prepared
        Thread.sleep(Randomizer.getRandom());
        return status;
    }

    @Override
    public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {
        orderMap.get(orderID).setStatus(OrderStatus.Served); //changes status of current order to served
        orderMap.remove(orderID);
        CompletableFuture<KitchenStatus> status = CompletableFuture.supplyAsync(() -> KitchenStatus.Served);
        Thread.sleep(Randomizer.getRandom());
        System.out.println("Server returning cooked meal to client");
        return status; //return status to client
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
                cookingThreadPool.submit(new CookingTask(order)); //dummy task that changes status of order until ready
            }
        });
    }
}
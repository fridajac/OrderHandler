package server;

import client.view.GenericRestaurantForm;
import shared.KitchenStatus;
import shared.Order;
import shared.OrderStatus;
import shared.Randomizer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KitchenServer extends AbstractKitchenServer {

    ExecutorService threadPool;
    private Map<String, Order> orderMap =  new HashMap<>();
    CompletableFuture<KitchenStatus> completableFutureKitchenStatus;
    private GenericRestaurantForm form;

    public KitchenServer() {
        completableFutureKitchenStatus = new CompletableFuture<>();
        threadPool =  Executors.newFixedThreadPool(3);
    }

    @Override
    public void setGUI(GenericRestaurantForm genericRestaurantForm) {
        this.form = form;
    }

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
            orderMap.put(order.getOrderID(), order); //saves the order to map
        System.out.println(orderMap.size());
            order.setStatus(OrderStatus.Received);
            Thread.sleep(Randomizer.getRandom());
            threadPool.submit(() -> {
                completableFutureKitchenStatus.complete(KitchenStatus.Received);
                form.setStatus(KitchenStatus.Received.text);
                order.setStatus(OrderStatus.Received);
                cook(order);
            });
        return completableFutureKitchenStatus; //return kitchen status TODO could return "rejected", when?
    }

    @Override
    public CompletableFuture<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        Order order = orderMap.get(orderID);
        OrderStatus orderStatus = order.getStatus();
        CompletableFuture<OrderStatus> completableFutureOrderStatus = new CompletableFuture<>();
        completableFutureOrderStatus.complete(orderStatus);
        return completableFutureOrderStatus;
    }

    @Override
    public CompletableFuture<KitchenStatus> serveOrder(String orderID) throws InterruptedException {
        Thread.sleep(Randomizer.getRandom());
        completableFutureKitchenStatus.complete(KitchenStatus.Served);
        form.setStatus(KitchenStatus.Served.text);
        //orderMap.remove(orderID);
        return completableFutureKitchenStatus;
    }

    @Override
    protected void cook(Order order) {
        try {
            Thread.sleep(Randomizer.getRandom());
            completableFutureKitchenStatus.complete(KitchenStatus.Cooking);
            form.setStatus(KitchenStatus.Cooking.text);
            order.setStatus(OrderStatus.BeingPrepared);
            Thread.sleep(Randomizer.getRandom());
            order.setStatus(OrderStatus.Ready);
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}

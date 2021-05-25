package server;

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

    ExecutorService threadPool = Executors.newFixedThreadPool(3);
    private Map<String, Order> orderMap = new HashMap<>();
    CompletableFuture<KitchenStatus> completableFutureKitchenStatus = new CompletableFuture<>();

    @Override
    public CompletableFuture<KitchenStatus> receiveOrder(Order order) throws InterruptedException {
        if(order == null) {
            completableFutureKitchenStatus.complete(KitchenStatus.NotFound);
            order.setStatus(OrderStatus.NotFound);
        }
        else {
            orderMap.put(order.getOrderID(), order); //saves the order to map
            order.setStatus(OrderStatus.Received);
            Thread.sleep(Randomizer.getRandom());
            threadPool.submit(() -> {
                completableFutureKitchenStatus.complete(KitchenStatus.Received);
                order.setStatus(OrderStatus.Received);
                cook(order);
            });
        }
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
        return completableFutureKitchenStatus;
    }

    @Override
    protected void cook(Order order) {
        try {
            Thread.sleep(Randomizer.getRandom());
            completableFutureKitchenStatus.complete(KitchenStatus.Cooking);
            order.setStatus(OrderStatus.BeingPrepared);
            Thread.sleep(Randomizer.getRandom());
            order.setStatus(OrderStatus.Ready);
            serveOrder(order.getOrderID());
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
//
//    @Override
//    public void receiveOrder(Order order) {
//        //CookingTask cookingTask = new CookingTask(GetOrderTask()); //submit a dummy task
//
//        Future<Status> future = kitchenThreadPool.submit(cookingTask);
//        //Status status = future.get();
//        cook(cookingTask);
//    }
//
//    @Override
//    public void cook(CookingTask cookingTask) {
//        cookingThreadPool.submit(cookingTask);
//    }
//
//    @Override
//    public void checkStatus(String string) {
//
//    }
//
//    @Override
//    public void serveOrder(String string) {
//
//    }

}

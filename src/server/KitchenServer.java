package server;

import shared.Order;
import shared.OrderStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Receives a new order and should init the cooking process.
 * Use a threadpool to represent the kitchen and submit a dummy task that
 * just sleeps and modifies the status of an order.
 * Use a seprate thread pool for cooking in the Kitchen Server.
 * Add random sleep intervals to KitchenServer methods to represent network delay.
 * You may use a CompletableFuture object to set the return value of async with the complete()-method.
 * Use newFixedThreadPool(). myPool.submit(task) to submit a task for execution.
 */
public class KitchenServer extends AbstractKitchenServer implements Runnable {

    private ServerSocket serverSocket;
    private Thread thread = new Thread(this); //thread that listen to new socket
    private boolean serverRunning;
    ExecutorService kitchenThreadPool = Executors.newFixedThreadPool(3); //represent kitchen
    ExecutorService cookingThreadPool = Executors.newFixedThreadPool(3); //represent cooking

    public KitchenServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverRunning = true;
            thread.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void shutdownServer() {
        serverRunning = false;
    }

    @Override
    public void run() {
        Socket socket = null;
        Order order = null;
        while (serverRunning) {
            try {
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                try {
                    order = (Order) ois.readObject();
                    receiveOrder(order);
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                //init cooking process
                //receiveOrder(order);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    socket.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public Future<OrderStatus> receiveOrder(Order order) throws InterruptedException {
        return null;
    }

    @Override
    public Future<OrderStatus> checkStatus(String orderID) throws InterruptedException {
        return null;

       /**
     * Note that the methods should sleep for a random duration before it returns a status.
     * This is to simulate an actual server-call that might operate slowly.
     */
    }

    @Override
    public Future<OrderStatus> serveOrder(String orderID) throws InterruptedException {
        return null;
    }

    @Override
    protected void cook(Order order) {

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

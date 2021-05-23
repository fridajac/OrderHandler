import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Receives a new order and should init the cooking process.
 * Use a threadpool to represent the kitchen and submit a dummy task that
 * just sleeps and modifies the status of an order.
 * Use a seprate thread pool for cooking in the Kitchen Server.
 * Add random sleep intervals to KitchenServer methods to represent network delay.
 * You may use a CompletableFuture object to set the return value of async with the complete()-method.
 * Use newFixedThreadPool(). myPool.submit(task) to submit a task for execution.
 */
public class KitchenServer implements IKitchenServer, Runnable {

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
    public void receiveOrder(Order order) {
        CookingTask cookingTask = new CookingTask(order); //submit a dummy task
        kitchenThreadPool.submit(cookingTask);
        cook(cookingTask);
    }

    @Override
    public void cook(CookingTask cookingTask) {
        cookingThreadPool.submit(cookingTask);
    }

    @Override
    public void checkStatus(String string) {

    }

    @Override
    public void serveOrder(String string) {

    }

    @Override
    public void run() {
        Socket socket = null;
        Order order = null;
        while(serverRunning) {
            try {
                socket = serverSocket.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                try {
                    order = (Order) ois.readObject();
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                //init cooking process
                receiveOrder(order);
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
}

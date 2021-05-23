import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    private Thread thread = new Thread(this);
    private boolean serverRunning;

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
        Order order = null;
        while(serverRunning) {
            try {
                Socket socket = serverSocket.accept();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                try {
                    order = (Order) ois.readObject();
                }
                catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                ArrayList<OrderItem> orderList = order.getOrder();
                for(OrderItem item : orderList) {
                    System.out.println(item.toString());
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

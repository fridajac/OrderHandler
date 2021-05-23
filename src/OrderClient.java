import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represent a self-service app
 * Allows a user to assemble an order, submit it to the KitchenSever and output status of the order.
 * Send a request to KitchenServer through the network, GUI should stay responsive through async method calls.
 * For setting up a periodic task, you may use Timer.scheduleAtFiedRate togehter with TimerTask.
 */

public class OrderClient implements IOrderClient {

    private String ipAddress;
    private int port;

    public OrderClient(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public void submitOrder(Order order) {
        try {
            Socket socket = new Socket(ipAddress, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject(order);
            oos.flush();
            Status status = (Status)ois.readObject();
            order.setStatus(status);
            startPollingServer();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startPollingServer() {
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("polling for status");
                try {
                    Socket socket = new Socket(ipAddress, port);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    //oos.writeObject(request);
                    Order order = (Order)ois.readObject();
                    if(order.getStatus() == Status.READY) {
                        pickUpOrder();
                    }
                }
                catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer("Timer");
        timer.scheduleAtFixedRate(task, 500, 1000);
    }

    @Override
    public void pickUpOrder() {
        //request order from server (serveOrder)
        //get order returned
        try {
            Socket socket = new Socket(ipAddress, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //oos.writeObject(request);
            Order order = (Order)ois.readObject();
            //print out time and status in GUI.
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
    }
}

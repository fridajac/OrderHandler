package client.services;

import client.services.AbstractOrderClient;
import server.AbstractKitchenServer;
import shared.Order;
import shared.OrderStatus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Represent a self-service app
 * Allows a user to assemble an order, submit it to the KitchenSever and output status of the order.
 * Send a request to KitchenServer through the network, GUI should stay responsive through async method calls.
 * For setting up a periodic task, you may use Timer.scheduleAtFiedRate togehter with TimerTask.
 */

public class OrderClient extends AbstractOrderClient {

    private String ipAddress;
    private int port;

    public OrderClient(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public void submitOrder() {
        Thread submitThread = new Thread(() -> {
            try {
                Socket socket = new Socket(ipAddress, port);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                System.out.println("hejhopp");
                oos.writeObject(super.order);
                oos.flush();
                System.out.println(super.order.getNamesFromOrderList());
            }
            catch (UnknownHostException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
        submitThread.start();
        System.out.println("frida");
        /**
         * Start a new task with a periodic timer {@link #pollingTimer}
         * to ask a server periodically about the order status {@link AbstractKitchenServer#checkStatus(String)}.
         *
         * Call {@link #pickUpOrder()} when status is {@link OrderStatus#Ready} and stop the {@link #pollingTimer}.
         */
    }

    @Override
    protected void startPollingServer(String orderId) {

    }

    @Override
    protected void pickUpOrder() {

    }
//
//    @Override
//    public void submitOrder(Order order) {
//        try {
//            Socket socket = new Socket(ipAddress, port);
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            oos.writeObject(order);
//            oos.flush();
//            Status status = (Status)ois.readObject();
//            order.setStatus(status);
//            startPollingServer();
//        }
//        catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void startPollingServer() {
//        TimerTask task = new TimerTask() {
//            public void run() {
//                System.out.println("polling for status");
//                try {
//                    Socket socket = new Socket(ipAddress, port);
//                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                    //oos.writeObject(request);
//                    Order order = (Order)ois.readObject();
//                    if(order.getStatus() == Status.READY) {
//                        pickUpOrder();
//                    }
//                }
//                catch (IOException | ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        Timer timer = new Timer("Timer");
//        timer.scheduleAtFixedRate(task, 500, 1000);
//    }
//
//    @Override
//    public void pickUpOrder() {
//        //request order from server (serveOrder)
//        //get order returned
//        try {
//            Socket socket = new Socket(ipAddress, port);
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            //oos.writeObject(request);
//            Order order = (Order)ois.readObject();
//            //print out time and status in GUI.
//        }
//        catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
}
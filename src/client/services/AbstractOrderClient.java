package client.services;

import server.AbstractKitchenServer;
import shared.Order;
import shared.OrderItem;
import shared.OrderStatus;

import java.util.Timer;

public abstract class AbstractOrderClient {
    public Order order = new Order();
    private AbstractKitchenServer kitchenServer;
    private Timer pollingTimer;

    public void addItemToOrder(OrderItem item) {
        order.addOrderItem(item);
    }

    public void removeItemFromOrder(OrderItem item) {
        order.removeOrderItem(item);
    }

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#receiveOrder(Order)}
     * Also start {@link #startPollingServer(String)}
     */
    abstract public void submitOrder();

    /**
     * Start a new task with a periodic timer {@link #pollingTimer}
     * to ask a server periodically about the order status {@link AbstractKitchenServer#checkStatus(String)}.
     *
     * Call {@link #pickUpOrder()} when status is {@link OrderStatus#Ready} and stop the {@link #pollingTimer}.
     */
    abstract protected void startPollingServer(String orderId);

    /**
     * Start an asynchronous request to {@link AbstractKitchenServer#serveOrder(String)}
     */
    abstract protected void pickUpOrder();
}
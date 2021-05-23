public abstract class AbstractOrderClient {

    abstract void submitOrder(Order order);
    abstract void startPollingServer();
    abstract void pickUpOrder();
}

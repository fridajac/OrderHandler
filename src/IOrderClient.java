public interface IOrderClient {

    void submitOrder(Order order);
    void startPollingServer();
    void pickUpOrder();
}

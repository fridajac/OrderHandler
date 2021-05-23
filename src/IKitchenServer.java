public interface IKitchenServer {

    void shutdownServer();
    void receiveOrder(Order order);
    void cook(Order order);
    void checkStatus(String string);
    void serveOrder(String string);
}

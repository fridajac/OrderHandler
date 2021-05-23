public interface IKitchenServer {

    void shutdownServer();
    void receiveOrder(Order order);
    void cook(CookingTask cookingTask);
    void checkStatus(String string);
    void serveOrder(String string);
}

public abstract class AbstractKitchenServer implements Runnable {

    abstract void shutdownServer();
    abstract void receiveOrder(Order order);
    abstract void cook(Order order);
    abstract void checkStatus(String string);
    abstract void serveOrder(String string);

}

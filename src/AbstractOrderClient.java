public abstract class AbstractOrderClient {

    abstract void sendRequest(Order order);
    abstract void pollForStatus();
}

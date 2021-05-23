/**
 * Dummy task that sleeps and modifies the status of the order
 */
public class CookingTask implements Runnable {

    private Order order;
    private Status status;

    public CookingTask(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Randomizer.getRandom()); //random delay
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        order.setStatus(status);
    }
}

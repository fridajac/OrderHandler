package server;

import shared.Order;

import java.util.concurrent.Callable;

/**
 * Dummy task that sleeps and modifies the status of the order
 */
public class CookingTask implements Callable {

    private Order order;
    private Status status;

    public CookingTask(Order order) {
        this.order = order;
    }

    @Override
    public Status call() {
        try {
            Thread.sleep(Randomizer.getRandom()); //random delay
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return Status.ACCEPTED;
    }
}

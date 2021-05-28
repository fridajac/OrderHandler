package server;

import shared.Order;
import shared.OrderStatus;
import shared.Randomizer;

/**
 * Dummy task that sleeps for a random range of seconds and then changes status of an order
 */
public class CookingTask implements Runnable {

    private Order order;

    /**
     * Constructor that takes in the specific order being handled
     * @param order the current order
     */
    public CookingTask(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Randomizer.getRandom());
            System.out.println("inside cooking method with order");
            order.setStatus(OrderStatus.BeingPrepared);
            System.out.println("changes status of order to being prepared ");
            Thread.sleep(Randomizer.getRandom()*3); //sleep longer
            order.setStatus(OrderStatus.Ready);
            System.out.println("changes status of order to ready");
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}

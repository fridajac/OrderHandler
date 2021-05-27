package server;

import shared.Order;
import shared.OrderStatus;
import shared.Randomizer;

public class CookingTask implements Runnable {

    private Order order;

    public CookingTask(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        try {
            System.out.println("inside cooking method with order " + order.getOrderID());
            Thread.sleep(Randomizer.getRandom());
            order.setStatus(OrderStatus.BeingPrepared);
            System.out.println("changes status of" + order.getOrderID() + " to being prepared ");
            Thread.sleep(Randomizer.getRandom());
            order.setStatus(OrderStatus.Ready);
            System.out.println("changes status of" + order.getOrderID() + " to ready");
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}

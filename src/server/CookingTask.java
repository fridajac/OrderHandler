package server;

import shared.KitchenStatus;
import shared.Order;
import shared.OrderStatus;
import shared.Randomizer;

import java.util.concurrent.Callable;

public class CookingTask implements Runnable {

    private Order order;

    public CookingTask(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Randomizer.getRandom());
            order.setStatus(OrderStatus.Received);
            order.setStatus(OrderStatus.BeingPrepared);
            Thread.sleep(Randomizer.getRandom());
            order.setStatus(OrderStatus.Ready);
        }
        catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}

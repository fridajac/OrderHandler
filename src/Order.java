import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private ArrayList<OrderItem> orderList = new ArrayList<OrderItem>();

    public void addItemToOrder(OrderItem newOrderItem) {
        orderList.add(newOrderItem);
    }

    public void removeItemInOrder(OrderItem orderItemToRemove) {
        orderList.remove(orderItemToRemove);
    }

    public ArrayList<OrderItem> getOrder() {
        return orderList;
    }
}

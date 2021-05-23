import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private ArrayList<OrderItem> orderList;
    private OrderStatus orderStatus;

    public Order() {
        this.orderList = new ArrayList<OrderItem>();
        this.orderStatus = new OrderStatus(java.time.LocalTime.now(), "New");
    }

    public void setOrderStatus(String status) {
        orderStatus.setStatus(status);
        orderStatus.setTime(java.time.LocalTime.now());
    }

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

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

    private ArrayList<OrderItem> items;
    private Status status;

    public Order() {
        this.items = new ArrayList<OrderItem>();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void addItemToOrder(OrderItem newOrderItem) {
        items.add(newOrderItem);
    }

    public void removeItemInOrder(OrderItem orderItemToRemove) {
        items.remove(orderItemToRemove);
    }

    public ArrayList<OrderItem> getOrder() {
        return items;
    }
}

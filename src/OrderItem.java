import java.io.Serializable;

public class OrderItem implements Serializable {

    private String name;
    private String description;
    private int price;

    public OrderItem(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}

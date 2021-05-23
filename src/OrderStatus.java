import java.time.LocalTime;

public class OrderStatus {

    private LocalTime time;
    private String status;

    public OrderStatus(LocalTime time, String status) {
        this.time = time;
        this.status = status;
    }

    @Override
    public String toString() {
        return time + " Order " +status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}

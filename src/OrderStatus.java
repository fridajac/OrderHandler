import java.time.LocalTime;

public class OrderStatus {

    private LocalTime time;
    private Status status;

    public OrderStatus() {
        time = java.time.LocalTime.now();
        status = Status.submitted;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return time + " Order " +status;
    }
}

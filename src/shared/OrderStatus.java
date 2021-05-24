package shared;

public enum OrderStatus {
    Received("order received"),
    Rejected("order rejected"),
    NotFound("order not found"),
    Served("order served"),
    Cooking("order is being cooked"),
    NotSent("order is not sent"),
    Ready("order is ready"),
    BeingPrepared("order is being prepared");

    public final String text;

    private OrderStatus(String name){
        this.text = name;
    }
}


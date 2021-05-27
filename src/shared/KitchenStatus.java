package shared;

public enum KitchenStatus {

    Sent ("Order: sent to kitchen"),
    Received("Order: received"),
    Rejected("Order: rejected"),
    NotFound("Order: not found"),
    Served("Order: served"),
    Cooking("Order: is being prepared");

    public final String text;

    private KitchenStatus(String name){
        this.text = name;
    }
}

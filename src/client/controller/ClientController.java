package client.controller;

import client.services.AbstractOrderClient;
import client.ItemType;
import shared.OrderItem;

public class ClientController {

    private AbstractOrderClient orderClient;

    public ClientController(AbstractOrderClient orderClient) {
        this.orderClient = orderClient;
    }

    public void addItemToOrder(ItemType itemType) {
        switch(itemType) {
            case ITEM1:
                OrderItem item1 = new OrderItem("Sandwich", "Bread, meat, cheese, salat, vegetables, sauce",23);
                orderClient.addItemToOrder(item1);
                break;
            case ITEM2:
                OrderItem item2 = new OrderItem("Borscht", "Beetroot, cabbage, potato, beef",84);
                orderClient.addItemToOrder(item2);
                break;
            case ITEM3:
                OrderItem item3 = new OrderItem("Coffee", "Hot, black, good",18);
                orderClient.addItemToOrder(item3);
                break;
        }
    }

    public void removeItemsFromOrder(ItemType itemType) {
        switch(itemType) {
            case ITEM1:
                OrderItem item1 = new OrderItem("Sandwich", "Bread, meat, cheese, salat, vegetables, sauce",23);
                orderClient.removeItemFromOrder(item1);
                break;
            case ITEM2:
                OrderItem item2 = new OrderItem("Borscht", "Beetroot, cabbage, potato, beef",84);
                orderClient.removeItemFromOrder(item2);
                break;
            case ITEM3:
                OrderItem item3 = new OrderItem("Coffee", "Hot, black, good",18);
                orderClient.removeItemFromOrder(item3);
                break;
        }
    }

    public void submitOrder() {
        orderClient.submitOrder();
    }
}

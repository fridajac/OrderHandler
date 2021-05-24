package client;

import client.OrderClient;
import shared.Order;

public class clientController {

    private OrderClient orderClient;

    public clientController(OrderClient orderClient) {
        this.orderClient = orderClient;
    }
}

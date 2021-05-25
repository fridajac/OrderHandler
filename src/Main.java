import client.services.AbstractOrderClient;
import client.view.GenericRestaurantForm;
import client.services.OrderClient;
import server.AbstractKitchenServer;
import server.KitchenServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AbstractKitchenServer kitchenServer = new KitchenServer(2555);
        AbstractOrderClient orderClient = new OrderClient("localhost", 2555, kitchenServer);
        GenericRestaurantForm restaurant = new GenericRestaurantForm(orderClient);
        restaurant.Start();
//        MainForm form = new MainForm();
    }
}

import client.AbstractOrderClient;
import client.GenericRestaurantForm;
import client.OrderClient;
import server.AbstractKitchenServer;
import server.KitchenServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        AbstractKitchenServer kitchenServer = new KitchenServer();
        AbstractOrderClient orderClient = new OrderClient(kitchenServer);
        GenericRestaurantForm restaurant = new GenericRestaurantForm(orderClient, kitchenServer);
        restaurant.Start();
//        MainForm form = new MainForm();
    }
}

import client.services.AbstractOrderClient;
import client.controller.ClientController;
import client.view.GenericRestaurantForm;
import client.services.OrderClient;
import server.AbstractKitchenServer;
import server.KitchenServer;

public class Main {

    public static void main(String[] args) {
        AbstractOrderClient orderClient = new OrderClient("localhost", 2555);
        AbstractKitchenServer kitchenServer = new KitchenServer(2555);
        ClientController clientController = new ClientController(orderClient);
        GenericRestaurantForm restaurant = new GenericRestaurantForm(clientController);
        restaurant.Start();
//        MainForm form = new MainForm();
    }
}

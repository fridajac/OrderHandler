import client.AbstractOrderClient;
import client.ClientController;
import client.GenericRestaurantForm;
import client.OrderClient;

public class Main {

    public static void main(String[] args) {
        AbstractOrderClient orderClient = new OrderClient("localhost", 2555);
        ClientController clientController = new ClientController(orderClient);
        GenericRestaurantForm restaurant = new GenericRestaurantForm(clientController);
        restaurant.Start();
//        MainForm form = new MainForm();
    }
}

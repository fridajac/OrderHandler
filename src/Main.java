public class Main {

    public static void main(String[] args) {
        KitchenServer kitchenServer = new KitchenServer(2555);
        OrderClient orderClient = new OrderClient("localhost", 2555);
        Controller controller = new Controller(orderClient);
    }
}

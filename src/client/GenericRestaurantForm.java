package client;

import server.AbstractKitchenServer;
import shared.OrderItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI-class
 */
public class GenericRestaurantForm {

    private JFrame frame;            // The Main window
    private AbstractOrderClient orderClient;
    private AbstractKitchenServer kitchenServer;

    JLabel labelMenu;               // Label for menu section
    JLabel labelOrder;              // Label for Order section
    JLabel labelStatus;             // Label for Status section

    JPanel menuItem1;               // panel for the first menu item
    JLabel menuItem1Name;           // label with the menu item name
    JLabel menuItem1Descr;          // label with menu item description
    JLabel menuItem1Cost;           // label with the cost for the menu item
    JButton menuItem1Button;        // button to add menu item to the cart

    JPanel menuItem2;               // panel for the first menu item
    JLabel menuItem2Name;           // label with the menu item name
    JLabel menuItem2Descr;          // label with menu item description
    JLabel menuItem2Cost;           // label with the cost for the menu item
    JButton menuItem2Button;        // button to add menu item to the cart

    JPanel menuItem3;               // panel for the first menu item
    JLabel menuItem3Name;           // label with the menu item name
    JLabel menuItem3Descr;          // label with menu item description
    JLabel menuItem3Cost;           // label with the cost for the menu item
    JButton menuItem3Button;        // button to add menu item to the cart

    DefaultListModel<String> orderCartModel;     // Stores a list of string that is displayed at orderCartArea
    JList<String> orderCartArea;                 // Displays current order
    JButton orderRemoveButton;                   // Button to remove an item from the order
    JButton orderSubmitButton;                   // Button to submit the order to KitchenServer

    DefaultListModel<String> orderStatusModel;   // Stores a list of string that is displayed at orderStatusArea
    JList<String> orderStatusArea;               // To display status of the submitted order

    public GenericRestaurantForm(AbstractOrderClient orderClient, AbstractKitchenServer kitchenServer) {
        this.orderClient = orderClient;
        this.kitchenServer = kitchenServer;
        orderClient.setGUI(this);
        kitchenServer.setGUI(this);
    }

    /**
     * Starts the application
     */
    public void Start() {
        frame = new JFrame();
        frame.setBounds(0, 0, 900, 482);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Generic Restaurant");
        InitializeGUI();                    // Fill in components
        frame.setVisible(true);
        frame.setResizable(true);            // Prevent user from change size
        frame.setLocationRelativeTo(null);    // Start middle screen
        initListeners();
    }

    private void InitializeGUI() {
        labelMenu = new JLabel("Menu");
        labelMenu.setBounds(10, 10, 128, 13);
        frame.add(labelMenu);

        //**********************
        //*** Menu item 1 *****
        //*********************
        menuItem1 = new JPanel();
        menuItem1.setBounds(10, 35, 300, 100);
        menuItem1.setBorder(BorderFactory.createLineBorder(Color.black));
        menuItem1.setLayout(null);
        frame.add(menuItem1);

        menuItem1Name = new JLabel("Sandwich");
        menuItem1Name.setBounds(5, 5, 100, 10);
        menuItem1.add(menuItem1Name);

        menuItem1Descr = new JLabel("Bread, meat, cheese, salad, vegetables, sauce");
        menuItem1Descr.setFont(new Font("Verdana", Font.PLAIN, 11));
        menuItem1Descr.setBounds(5, 20, 300, 10);
        menuItem1.add(menuItem1Descr);

        menuItem1Cost = new JLabel("23kr");
        menuItem1Cost.setFont(new Font("Dialog", Font.BOLD, 11));
        menuItem1Cost.setBounds(5, 60, 100, 10);
        menuItem1.add(menuItem1Cost);

        menuItem1Button = new JButton();
        menuItem1Button.setBounds(180, 50, 100, 30);
        menuItem1Button.setText("add");
        menuItem1.add(menuItem1Button);

        //**********************
        //*** Menu item 2 *****
        //*********************
        menuItem2 = new JPanel();
        menuItem2.setBounds(10, 150, 300, 100);
        menuItem2.setBorder(BorderFactory.createLineBorder(Color.black));
        menuItem2.setLayout(null);
        frame.add(menuItem2);

        menuItem2Name = new JLabel("Borscht");
        menuItem2Name.setBounds(5, 5, 100, 10);
        menuItem2.add(menuItem2Name);

        menuItem2Descr = new JLabel("Beetroot, cabbage, potato, beef");
        menuItem2Descr.setFont(new Font("Verdana", Font.PLAIN, 11));
        menuItem2Descr.setBounds(5, 20, 300, 10);
        menuItem2.add(menuItem2Descr);

        menuItem2Cost = new JLabel("84kr");
        menuItem2Cost.setFont(new Font("Dialog", Font.BOLD, 11));
        menuItem2Cost.setBounds(5, 60, 100, 10);
        menuItem2.add(menuItem2Cost);

        menuItem2Button = new JButton();
        menuItem2Button.setBounds(180, 50, 100, 30);
        menuItem2Button.setText("add");
        menuItem2.add(menuItem2Button);

        //**********************
        //*** Menu item 3 *****
        //*********************
        menuItem3 = new JPanel();
        menuItem3.setBounds(10, 265, 300, 100);
        menuItem3.setBorder(BorderFactory.createLineBorder(Color.black));
        menuItem3.setLayout(null);
        frame.add(menuItem3);

        menuItem3Name = new JLabel("Coffee");
        menuItem3Name.setBounds(5, 5, 100, 10);
        menuItem3.add(menuItem3Name);

        menuItem3Descr = new JLabel("Hot, black, good");
        menuItem3Descr.setFont(new Font("Verdana", Font.PLAIN, 11));
        menuItem3Descr.setBounds(5, 20, 300, 10);
        menuItem3.add(menuItem3Descr);

        menuItem3Cost = new JLabel("18kr");
        menuItem3Cost.setFont(new Font("Dialog", Font.BOLD, 11));
        menuItem3Cost.setBounds(5, 60, 100, 10);
        menuItem3.add(menuItem3Cost);

        menuItem3Button = new JButton();
        menuItem3Button.setBounds(180, 50, 100, 30);
        menuItem3Button.setText("add");
        menuItem3.add(menuItem3Button);

        //*********************
        //*** Order cart  *****
        //*********************
        labelOrder = new JLabel("Order");
        labelOrder.setBounds(340, 10, 128, 13);
        frame.add(labelOrder);

        orderCartModel = new DefaultListModel<String>();
        orderCartArea = new JList<String>(orderCartModel);
        orderCartArea.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        orderCartArea.setBounds(340, 35, 250, 250);
        orderCartArea.setBorder(BorderFactory.createLineBorder(Color.black));
        //orderCartModel.addElement("Sandwich");
        //orderCartModel.addElement("Coffee");
        frame.add(orderCartArea);

        orderRemoveButton = new JButton();
        orderRemoveButton.setBounds(340, 300, 100, 30);
        orderRemoveButton.setText("remove");
        frame.add(orderRemoveButton);

        orderSubmitButton = new JButton();
        orderSubmitButton.setBounds(490, 300, 100, 30);
        orderSubmitButton.setText("order!");
        frame.add(orderSubmitButton);

        //*********************
        //***** Status  *******
        //*********************
        labelStatus = new JLabel("Status");
        labelStatus.setBounds(620, 10, 128, 13);
        frame.add(labelStatus);

        orderStatusModel = new DefaultListModel<String>();
        orderStatusArea = new JList<String>(orderStatusModel);
        orderStatusArea.setBounds(620, 35, 250, 335);
        orderStatusArea.setBorder(BorderFactory.createLineBorder(Color.black));
        //orderStatusModel.addElement("19:02:03 Order submitted");
        //orderStatusModel.addElement("19:02:05 Order accepted");
        frame.add(orderStatusArea);
    }

    private void initListeners() {
        ButtonListener listener = new ButtonListener();
        menuItem1Button.addActionListener(listener);
        menuItem2Button.addActionListener(listener);
        menuItem3Button.addActionListener(listener);
        orderRemoveButton.addActionListener(listener);
        orderSubmitButton.addActionListener(listener);
    }

    public void setStatus(String status) {
        //TODO ska ange korrekt klockslag
        orderStatusModel.addElement("19:02:03 " + status);
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menuItem1Button) {
                OrderItem item1 = new OrderItem("Sandwich", "Bread, meat, cheese, salat, vegetables, sauce", 23);
                orderClient.addItemToOrder(item1);
                orderCartModel.addElement("Sandwich");
            }
            if (e.getSource() == menuItem2Button) {
                OrderItem item2 = new OrderItem("Borscht", "Beetroot, cabbage, potato, beef", 84);
                orderClient.addItemToOrder(item2);
                orderCartModel.addElement("Borscht");
            }
            if (e.getSource() == menuItem3Button) {
                OrderItem item3 = new OrderItem("Coffee", "Hot, black, good", 18);
                orderClient.addItemToOrder(item3);
                orderCartModel.addElement("Coffee");
            }
            if (e.getSource() == orderRemoveButton) {
                String valueToDelete = orderCartArea.getSelectedValue();
                orderCartModel.removeElement(valueToDelete);
                //orderClient.removeItemToOrder(valueToDelete); TODO item ska även raderas från ordern
            }
            if (e.getSource() == orderSubmitButton) {
                orderClient.submitOrder();
            }
        }
    }
}
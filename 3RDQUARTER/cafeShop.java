package MyFirstGUIPackage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;

public class cafeShop {

    private JFrame mainFrame;
    private DefaultTableModel orderModel;
    private JTable orderTable;
    private JLabel subtotalLabel;
    private double subtotal = 0.0;
    private DecimalFormat money = new DecimalFormat("#,##0.00");

    private final String[][] coffeeMenu = {
        {"Espresso", "50"},
        {"Americano", "55"},
        {"Latte", "65"},
        {"Cappuccino", "70"},
        {"Mocha", "75"}
    };

    private final String[][] burgerMenu = {
        {"Cheeseburger", "80"},
        {"Bacon Burger", "90"},
        {"Double Burger", "120"},
        {"Veggie Burger", "70"}
    };

    private final String[][] burgerAddons = {
        {"Extra Cheese", "15"},
        {"Bacon", "25"},
        {"Fried Egg", "20"},
        {"Onion Rings", "15"}
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                cafeShop window = new cafeShop();
                window.mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public cafeShop() {
        initialize();
    }

    private void initialize() {
        mainFrame = new JFrame("RVG Café");
        mainFrame.setBounds(100, 100, 800, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));

        JPanel header = new JPanel(new GridLayout(2, 1));
        JLabel title = new JLabel("RVG Café", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        JLabel subtitle = new JLabel("Welcome to our shop!", SwingConstants.CENTER);
        subtitle.setFont(new Font("Serif", Font.PLAIN, 18));
        header.add(title);
        header.add(subtitle);
        mainFrame.add(header, BorderLayout.NORTH);

        orderModel = new DefaultTableModel(new Object[]{"Item", "Price", "Quantity", "Total"}, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        orderTable = new JTable(orderModel);
        JScrollPane scroll = new JScrollPane(orderTable);
        mainFrame.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton coffeeBtn = new JButton("Coffee Drinks (View Products)");
        JButton burgerBtn = new JButton("Burgers (View Products)");
        JButton removeBtn = new JButton("Remove Selected");
        JButton checkoutBtn = new JButton("Checkout");
        subtotalLabel = new JLabel("Subtotal: ₱0.00");
        subtotalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(coffeeBtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(burgerBtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(removeBtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(checkoutBtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(subtotalLabel);

        mainFrame.add(buttonPanel, BorderLayout.EAST);

        coffeeBtn.addActionListener(e -> showMenu(coffeeMenu, "Coffee Menu", false));
        burgerBtn.addActionListener(e -> showMenu(burgerMenu, "Burger Menu", true));
        removeBtn.addActionListener(e -> removeSelectedItem());
        checkoutBtn.addActionListener(e -> openPaymentWindow());
    }

    private void showMenu(String[][] menu, String title, boolean hasAddons) {
        JFrame menuFrame = new JFrame(title);
        menuFrame.setSize(400, hasAddons ? 400 : 300);
        menuFrame.setLayout(new BorderLayout(10, 10));

        int rows = menu.length;
        if (hasAddons) rows *= 2;

        JPanel itemPanel = new JPanel(new GridLayout(rows, 3, 10, 10));
        JCheckBox[] checkBoxes = new JCheckBox[menu.length];
        JSpinner[] qtySpinners = new JSpinner[menu.length];

        for (int i = 0; i < menu.length; i++) {
            checkBoxes[i] = new JCheckBox(menu[i][0] + " - ₱" + menu[i][1]);
            qtySpinners[i] = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
            itemPanel.add(checkBoxes[i]);
            itemPanel.add(new JLabel("Qty:"));
            itemPanel.add(qtySpinners[i]);

            if (hasAddons) {
                JPanel addonPanel = new JPanel(new GridLayout(burgerAddons.length, 2));
                JCheckBox[] addonChecks = new JCheckBox[burgerAddons.length];
                for (int j = 0; j < burgerAddons.length; j++) {
                    addonChecks[j] = new JCheckBox(burgerAddons[j][0] + " - ₱" + burgerAddons[j][1]);
                    addonPanel.add(addonChecks[j]);
                }
                itemPanel.add(addonPanel);
            }
        }

        JButton addToOrder = new JButton("Add to Order");
        menuFrame.add(new JScrollPane(itemPanel), BorderLayout.CENTER);
        menuFrame.add(addToOrder, BorderLayout.SOUTH);

        addToOrder.addActionListener(ev -> {
            for (int i = 0; i < menu.length; i++) {
                if (checkBoxes[i].isSelected()) {
                    String name = menu[i][0];
                    double price = Double.parseDouble(menu[i][1]);
                    int qty = (Integer) qtySpinners[i].getValue();
                    double totalPrice = price * qty;

                    if (hasAddons) {
                        Component addonComp = itemPanel.getComponent(i * 3 + 2);
                        if (addonComp instanceof JPanel) {
                            JPanel addonPanel = (JPanel) addonComp;
                            for (int j = 0; j < burgerAddons.length; j++) {
                                JCheckBox addonCheck = (JCheckBox) addonPanel.getComponent(j);
                                if (addonCheck.isSelected()) {
                                    totalPrice += Double.parseDouble(burgerAddons[j][1]) * qty;
                                    name += " + " + burgerAddons[j][0];
                                }
                            }
                        }
                    }

                    orderModel.addRow(new Object[]{name, String.format("₱%.2f", price), qty, String.format("₱%.2f", totalPrice)});
                    subtotal += totalPrice;
                }
            }

            updateSubtotal();
            menuFrame.dispose();
        });

        menuFrame.setLocationRelativeTo(mainFrame);
        menuFrame.setVisible(true);
    }

    private void removeSelectedItem() {
        int row = orderTable.getSelectedRow();
        if (row >= 0) {
            String totalStr = ((String) orderModel.getValueAt(row, 3)).replace("₱", "");
            subtotal -= Double.parseDouble(totalStr);
            orderModel.removeRow(row);
            updateSubtotal();
        }
    }

    private void updateSubtotal() {
        subtotalLabel.setText("Subtotal: ₱" + money.format(subtotal));
    }

    private void openPaymentWindow() {
        if (orderModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(mainFrame, "No items in order.");
            return;
        }

        JFrame payFrame = new JFrame("Payment");
        payFrame.setSize(450, 420);
        payFrame.setLayout(new GridLayout(8, 2, 10, 10));
        payFrame.setLocationRelativeTo(mainFrame);

        JLabel subtotalLbl = new JLabel("Subtotal:");
        JLabel subtotalVal = new JLabel("₱" + money.format(subtotal));
        JLabel totalLbl = new JLabel("Total to Pay:");
        JLabel totalVal = new JLabel("₱" + money.format(subtotal));
        JLabel paymentLbl = new JLabel("Enter Payment:");
        JTextField paymentField = new JTextField();

        // ❤️ Added: ORDER TYPE RADIO BUTTONS
        JLabel orderTypeLbl = new JLabel("Order Type:");
        JRadioButton dineIn = new JRadioButton("Dine In");
        JRadioButton takeOut = new JRadioButton("Take Out");
        ButtonGroup orderTypeGroup = new ButtonGroup();
        orderTypeGroup.add(dineIn);
        orderTypeGroup.add(takeOut);

        // ❤️ Added: PAYMENT METHOD COMBO BOX
        JLabel methodLbl = new JLabel("Payment Method:");
        String[] methods = {"Cash", "Bank App", "Digital Bank (GCash, PayMaya, etc)"};
        JComboBox<String> methodBox = new JComboBox<>(methods);

        JButton processBtn = new JButton("Process Payment");
        JButton cancelBtn = new JButton("Cancel");

        payFrame.add(subtotalLbl); payFrame.add(subtotalVal);
        payFrame.add(totalLbl); payFrame.add(totalVal);
        payFrame.add(paymentLbl); payFrame.add(paymentField);

        // Insert Radio Buttons
        payFrame.add(orderTypeLbl); 
        payFrame.add(dineIn);
        payFrame.add(new JLabel("")); 
        payFrame.add(takeOut);

        // Insert Combo Box
        payFrame.add(methodLbl);
        payFrame.add(methodBox);

        payFrame.add(processBtn); 
        payFrame.add(cancelBtn);

        processBtn.addActionListener(e -> {
            try {
                double payment = Double.parseDouble(paymentField.getText());
                if (payment < subtotal) {
                    JOptionPane.showMessageDialog(payFrame, "Insufficient amount.");
                    return;
                }

                double change = payment - subtotal;

                StringBuilder receipt = new StringBuilder();
                receipt.append("\tRVG Café Receipt\n");
                receipt.append("----------------------------------\n");

                for (int i = 0; i < orderModel.getRowCount(); i++) {
                    String item = (String) orderModel.getValueAt(i, 0);
                    int qty = (Integer) orderModel.getValueAt(i, 2);
                    String totalStr = (String) orderModel.getValueAt(i, 3);
                    receipt.append(String.format("%d x %-15s %8s\n", qty, item, totalStr));
                }

                receipt.append("----------------------------------\n");

                receipt.append("Order Type: " 
                    + (dineIn.isSelected() ? "Dine In" : takeOut.isSelected() ? "Take Out" : "Not Specified")
                    + "\n");

                receipt.append("Payment Method: " + methodBox.getSelectedItem() + "\n");
                receipt.append("----------------------------------\n");

                receipt.append(String.format("Total: ₱%s\n", money.format(subtotal)));
                receipt.append(String.format("Paid: ₱%s\n", money.format(payment)));
                receipt.append(String.format("Change: ₱%s\n", money.format(change)));

                JTextArea receiptArea = new JTextArea(receipt.toString());
                receiptArea.setEditable(false);
                receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                JFrame receiptFrame = new JFrame("Receipt");
                receiptFrame.setSize(400, 500);
                receiptFrame.setLayout(new BorderLayout());
                receiptFrame.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

                JButton closeBtn = new JButton("Close");
                closeBtn.addActionListener(ev -> receiptFrame.dispose());
                JPanel btnPanel = new JPanel();
                btnPanel.add(closeBtn);
                receiptFrame.add(btnPanel, BorderLayout.SOUTH);

                receiptFrame.setLocationRelativeTo(mainFrame);
                receiptFrame.setVisible(true);

                orderModel.setRowCount(0);
                subtotal = 0.0;
                updateSubtotal();
                payFrame.dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(payFrame, "Enter a valid numeric payment amount.");
            }
        });

        cancelBtn.addActionListener(e -> payFrame.dispose());

        payFrame.setVisible(true);
    }
}

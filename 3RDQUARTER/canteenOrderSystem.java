package MyFirstGUIPackage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;

public class canteenOrderSystem {

    private JFrame mainFrame;
    private JComboBox<String> itemMenu;
    private JSpinner qtySpinner;
    private DefaultTableModel cartModel;
    private JTable cartTable;
    private JLabel subtotalLabel;

    private double subtotal = 0.0;
    private DecimalFormat money = new DecimalFormat("#,##0.00");

    private final String[] items = {
        "Select Item", "Burger - 50", "Spaghetti - 60", "Sandwich - 40",
        "Fried Rice & Egg - 55", "Hotdog - 35", "Siomai Rice - 45",
        "Milk Tea - 65", "Iced Coffee - 55", "Water - 20", "Soft Drink - 30"
    };

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                canteenOrderSystem window = new canteenOrderSystem();
                window.mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public canteenOrderSystem() {
        initialize();
    }

    private void initialize() {
        mainFrame = new JFrame("Canteen POS - Ordering");
        mainFrame.setBounds(100, 100, 900, 500);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        itemMenu = new JComboBox<>(items);
        qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        JButton addBtn = new JButton("Add Item");
        JButton removeBtn = new JButton("Remove Selected");
        JButton clearBtn = new JButton("Clear Cart");
        JButton checkoutBtn = new JButton("Checkout");

        topPanel.add(new JLabel("Item:"));
        topPanel.add(itemMenu);
        topPanel.add(new JLabel("Quantity:"));
        topPanel.add(qtySpinner);
        topPanel.add(addBtn);
        topPanel.add(removeBtn);
        topPanel.add(clearBtn);
        topPanel.add(checkoutBtn);

        mainFrame.add(topPanel, BorderLayout.NORTH);

        cartModel = new DefaultTableModel(new Object[] {"Item", "Price", "Qty", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        cartTable = new JTable(cartModel);
        cartTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(cartTable);
        mainFrame.add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        subtotalLabel = new JLabel("Subtotal: ₱0.00");
        bottomPanel.add(subtotalLabel);
        mainFrame.add(bottomPanel, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addItemToCart());
        removeBtn.addActionListener(e -> removeSelectedItem());
        clearBtn.addActionListener(e -> clearCart());
        checkoutBtn.addActionListener(e -> openPaymentWindow());
    }

    private double parsePrice(String itemText) {
        try {
            String[] parts = itemText.split(" - ");
            return Double.parseDouble(parts[1]);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private void addItemToCart() {
        int index = itemMenu.getSelectedIndex();
        if (index <= 0) {
            JOptionPane.showMessageDialog(mainFrame, "Please select an item to add.", "No Item Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String selected = (String) itemMenu.getSelectedItem();
        String name = selected.split(" - ")[0];
        double price = parsePrice(selected);
        int qty = (Integer) qtySpinner.getValue();
        double total = price * qty;
        cartModel.addRow(new Object[] {name, String.format("₱%s", money.format(price)), qty, String.format("₱%s", money.format(total))});
        subtotal += total;
        updateSubtotalLabel();
    }

    private void removeSelectedItem() {
        int row = cartTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(mainFrame, "Select a row to remove.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String totalStr = ((String) cartModel.getValueAt(row, 3)).replace("₱", "").replace(",", "");
        try {
            double val = Double.parseDouble(totalStr);
            subtotal -= val;
        } catch (Exception e) {}
        cartModel.removeRow(row);
        updateSubtotalLabel();
    }

    private void clearCart() {
        if (cartModel.getRowCount() == 0) return;
        int confirm = JOptionPane.showConfirmDialog(mainFrame, "Clear all items from cart?", "Confirm Clear", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            cartModel.setRowCount(0);
            subtotal = 0.0;
            updateSubtotalLabel();
        }
    }

    private void updateSubtotalLabel() {
        subtotalLabel.setText("Subtotal: ₱" + money.format(subtotal));
    }

    private void openPaymentWindow() {
        if (cartModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(mainFrame, "Cart is empty. Add items before checkout.", "Empty Cart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFrame payFrame = new JFrame("Payment - POS");
        payFrame.setSize(420, 260);
        payFrame.setLocationRelativeTo(mainFrame);

        JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
        JLabel subtotalLbl = new JLabel("Subtotal:");
        JLabel subtotalVal = new JLabel("₱" + money.format(subtotal));
        JLabel discountLbl = new JLabel("Discount (ID Type):");
        JComboBox<String> discountBox = new JComboBox<>(new String[] {"None", "Student (10%)", "Teacher (5%)", "School Personnel (3%)"});
        JLabel discountAmtLbl = new JLabel("Discount Amount:");
        JLabel discountVal = new JLabel("₱0.00");
        JLabel totalLbl = new JLabel("Total to Pay:");
        JLabel totalVal = new JLabel("₱" + money.format(subtotal));
        JLabel payLbl = new JLabel("Enter Payment:");
        JTextField payField = new JTextField();

        panel.add(subtotalLbl); panel.add(subtotalVal);
        panel.add(discountLbl); panel.add(discountBox);
        panel.add(discountAmtLbl); panel.add(discountVal);
        panel.add(totalLbl); panel.add(totalVal);
        panel.add(payLbl); panel.add(payField);

        JButton processBtn = new JButton("Process Payment");
        JButton cancelBtn = new JButton("Cancel");
        panel.add(processBtn); panel.add(cancelBtn);

        payFrame.add(panel);

        discountBox.addActionListener(e -> {
            double rate = 0;
            String sel = (String) discountBox.getSelectedItem();
            if (sel.startsWith("Student")) rate = 0.10;
            else if (sel.startsWith("Teacher")) rate = 0.05;
            else if (sel.startsWith("School Personnel")) rate = 0.03;
            double discAmt = subtotal * rate;
            discountVal.setText("₱" + money.format(discAmt));
            totalVal.setText("₱" + money.format(subtotal - discAmt));
        });

        processBtn.addActionListener(e -> {
            try {
                double rate = 0;
                String sel = (String) discountBox.getSelectedItem();
                if (sel.startsWith("Student")) rate = 0.10;
                else if (sel.startsWith("Teacher")) rate = 0.05;
                else if (sel.startsWith("School Personnel")) rate = 0.03;
                double discAmt = subtotal * rate;
                double totalPay = subtotal - discAmt;
                double paid = Double.parseDouble(payField.getText());
                if (paid < totalPay) {
                    JOptionPane.showMessageDialog(payFrame, "Insufficient payment.");
                    return;
                }
                double change = paid - totalPay;
                StringBuilder receipt = new StringBuilder();
                receipt.append("\tCANTEEN RECEIPT\n");
                receipt.append("--------------------------------------------\n");
                for (int r = 0; r < cartModel.getRowCount(); r++) {
                    String item = (String) cartModel.getValueAt(r, 0);
                    String price = (String) cartModel.getValueAt(r, 1);
                    int q = (Integer) cartModel.getValueAt(r, 2);
                    String tot = (String) cartModel.getValueAt(r, 3);
                    receipt.append(String.format("%d x %-15s %8s\n", q, item, tot));
                }
                receipt.append("--------------------------------------------\n");
                receipt.append(String.format("Subtotal: %14s\n", "₱" + money.format(subtotal)));
                receipt.append(String.format("Discount (%s): %7s\n", sel, "₱" + money.format(discAmt)));
                receipt.append(String.format("Total to Pay: %9s\n", "₱" + money.format(totalPay)));
                receipt.append(String.format("Paid: %17s\n", "₱" + money.format(paid)));
                receipt.append(String.format("Change: %14s\n", "₱" + money.format(change)));
                receipt.append("\nThank you for your purchase!\n");

                showReceiptWindow(receipt.toString());
                cartModel.setRowCount(0);
                subtotal = 0.0;
                updateSubtotalLabel();
                payFrame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(payFrame, "Invalid payment input.");
            }
        });

        cancelBtn.addActionListener(e -> payFrame.dispose());

        payFrame.setVisible(true);
    }

    private void showReceiptWindow(String receiptText) {
        JFrame r = new JFrame("Receipt");
        r.setSize(420, 520);
        r.setLayout(new BorderLayout());
        JTextArea area = new JTextArea(receiptText);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        r.add(new JScrollPane(area), BorderLayout.CENTER);
        JPanel btn = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton close = new JButton("Close");
        btn.add(close);
        r.add(btn, BorderLayout.SOUTH);
        close.addActionListener(e -> r.dispose());
        r.setLocationRelativeTo(mainFrame);
        r.setVisible(true);
    }
}

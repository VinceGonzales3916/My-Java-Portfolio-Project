package MyFirstGUIPackage;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class myFirstWindow {

    private JFrame frmmyFirstGui;
    private JButton Log_in;
    private JTextField userNameField;
    private JPasswordField passwordField;
    
    private String username = "admin";
    private String password = "1234";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    myFirstWindow window = new myFirstWindow();
                    window.frmMyFirstGui.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public myFirstWindow() {
        initialize();
        createEvent();
    }

    private void initialize() {
        frmMyFirstGui = new JFrame()
        frmMyFirstGui.setTitle("My First GUI");
        frmMyFirstGui.setBounds(750, 450, 500, 300);
        frmMyFirstGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmMyFirstGui.getContentPane().setLayout(null);

        Log_in = new JButton("Log In");
        Log_in.setBounds(191, 139, 104, 23);
        frmMyFirstGui.getContentPane().add(Log_in);

        userNameField = new JTextField();
        userNameField.setText("Enter your username here");
        userNameField.setBounds(162, 61, 154, 20);
        frmMyFirstGui.getContentPane().add(userNameField);
        userNameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setText("Enter your password here"); // optional placeholder
        passwordField.setBounds(162, 108, 154, 20);
        frmMyFirstGui.getContentPane().add(passwordField);

        JLabel lblNewLabel = new JLabel("Username:");
        lblNewLabel.setBounds(85, 64, 74, 14);
        frmMyFirstGui.getContentPane().add(lblNewLabel);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(85, 111, 74, 14);
        frmMyFirstGui.getContentPane().add(lblPassword);
    }

    private void createEvent() {
        Log_in.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String enteredUsername = userNameField.getText();
                String enteredPassword = new String(passwordField.getPassword());

                if (enteredUsername.isEmpty() || enteredPassword.isEmpty()
                        || enteredUsername.equals("Enter your username here")
                        || enteredPassword.equals("Enter your password here")) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                    JOptionPane.showMessageDialog(null, "Welcome!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Clear placeholder on click
        userNameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (userNameField.getText().equals("Enter your username here")) {
                    userNameField.setText("");
                }
            }
        });

        passwordField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (new String(passwordField.getPassword()).equals("Enter your password here")) {
                    passwordField.setText("");
                }
            }
        });
    }
}

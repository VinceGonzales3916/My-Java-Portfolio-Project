package MyFirstGUIPackage;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class myFirstWindow {

    private JFrame mainFrame;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    
    private String correctUsername = "admin";
    private String correctPassword = "1234";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                myFirstWindow window = new myFirstWindow();
                window.mainFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public myFirstWindow() {
        initialize();
        setupEvents();
    }

    private void initialize() {
        mainFrame = new JFrame();
        mainFrame.setTitle("My First GUI");
        mainFrame.setBounds(750, 450, 500, 300);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(null);

        loginButton = new JButton("Log In");
        loginButton.setBounds(191, 139, 104, 23);
        mainFrame.getContentPane().add(loginButton);

        usernameField = new JTextField();
        usernameField.setText("Enter your username here");
        usernameField.setBounds(162, 61, 154, 20);
        mainFrame.getContentPane().add(usernameField);
        usernameField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setText("Enter your password here");
        passwordField.setBounds(162, 108, 154, 20);
        mainFrame.getContentPane().add(passwordField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(85, 64, 74, 14);
        mainFrame.getContentPane().add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(85, 111, 74, 14);
        mainFrame.getContentPane().add(passwordLabel);
    }

    private void setupEvents() {
        loginButton.addActionListener(e -> {
            String enteredUsername = usernameField.getText();
            String enteredPassword = new String(passwordField.getPassword());

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()
                    || enteredUsername.equals("Enter your username here")
                    || enteredPassword.equals("Enter your password here")) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (enteredUsername.equals(correctUsername) && enteredPassword.equals(correctPassword)) {
                JOptionPane.showMessageDialog(null, "Welcome!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clear placeholder on click
        usernameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (usernameField.getText().equals("Enter your username here")) {
                    usernameField.setText("");
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

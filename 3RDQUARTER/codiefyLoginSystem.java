package MyFirstGUIPackage;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class myLoginSystem {

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Font customFont;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            myLoginSystem window = new myLoginSystem();
            window.frame.setVisible(true);
        });
    }

    public myLoginSystem() {
        loadCustomFont();
        initialize();
    }

    // ----------------------------------------
    // LOAD CUSTOM FONT (TTF)
    // ----------------------------------------
    private void loadCustomFont() {
        try {
            File fontFile = new File("MyFont.ttf"); 
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            customFont = font.deriveFont(16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (Exception e) {
            customFont = new Font("Segoe UI", Font.PLAIN, 16); // fallback
        }
    }

    // ----------------------------------------
    // CREATE CUSTOM ROUNDED TEXT FIELD
    // ----------------------------------------
    class RoundedTextField extends JTextField {
        private int radius = 20;

        public RoundedTextField(int columns) {
            super(columns);
            setOpaque(false);
            setBorder(new EmptyBorder(8, 10, 8, 10));
            setFont(customFont);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    class RoundedPasswordField extends JPasswordField {
        private int radius = 20;

        public RoundedPasswordField(int columns) {
            super(columns);
            setOpaque(false);
            setBorder(new EmptyBorder(8, 10, 8, 10));
            setFont(customFont);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
    }

    // ----------------------------------------
    // GRADIENT BACKGROUND PANEL
    // ----------------------------------------
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color top = new Color(33, 147, 176);
            Color bottom = new Color(109, 213, 237);

            GradientPaint gp = new GradientPaint(0, 0, top, 0, getHeight(), bottom);
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // ----------------------------------------
    // MAIN UI
    // ----------------------------------------
    private void initialize() {
        frame = new JFrame("Modern Login System");
        frame.setSize(450, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        GradientPanel bgPanel = new GradientPanel();
        bgPanel.setLayout(new GridBagLayout());
        frame.setContentPane(bgPanel);

        JPanel card = new JPanel();
        card.setBackground(new Color(255, 255, 255, 220));
        card.setBorder(new EmptyBorder(25, 25, 25, 25));
        card.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Login");
        title.setFont(customFont.deriveFont(22f));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        card.add(title, gbc);
        gbc.gridwidth = 1;

        JLabel userLbl = new JLabel("Username:");
        userLbl.setFont(customFont);
        gbc.gridx = 0; gbc.gridy = 1;
        card.add(userLbl, gbc);

        usernameField = new RoundedTextField(15);
        gbc.gridx = 1;
        card.add(usernameField, gbc);

        JLabel passLbl = new JLabel("Password:");
        passLbl.setFont(customFont);
        gbc.gridx = 0; gbc.gridy = 2;
        card.add(passLbl, gbc);

        passwordField = new RoundedPasswordField(15);
        gbc.gridx = 1;
        card.add(passwordField, gbc);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(customFont);
        loginBtn.setBackground(new Color(52, 152, 219));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);

        gbc.gridx = 0; 
        gbc.gridy = 3; 
        gbc.gridwidth = 2;
        card.add(loginBtn, gbc);

        bgPanel.add(card);

        // ----------------------------------------
        // SMART PLACEHOLDER TEXT
        // ----------------------------------------
        String usernamePlaceholder = "Enter your username here";
        String passwordPlaceholder = "Enter your password here";

        // Username field
        usernameField.setText(usernamePlaceholder);
        usernameField.setForeground(Color.GRAY);
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals(usernamePlaceholder)) {
                    usernameField.setText("");
                    usernameField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText(usernamePlaceholder);
                    usernameField.setForeground(Color.GRAY);
                }
            }
        });

        // Password field
        passwordField.setEchoChar((char)0); // show placeholder initially
        passwordField.setText(passwordPlaceholder);
        passwordField.setForeground(Color.GRAY);

        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String passText = new String(passwordField.getPassword());
                if (passText.equals(passwordPlaceholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.BLACK);
                    passwordField.setEchoChar('•'); // hide input
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                String passText = new String(passwordField.getPassword());
                if (passText.isEmpty()) {
                    passwordField.setText(passwordPlaceholder);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char)0); // show placeholder
                }
            }
        });

        // ----------------------------------------
        // LOGIN LOGIC
        // ----------------------------------------
        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            if (user.isEmpty() || user.equals(usernamePlaceholder) ||
                pass.isEmpty() || pass.equals(passwordPlaceholder)) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (user.equals("admin") && pass.equals("1234")) {
                JOptionPane.showMessageDialog(frame, "Login Successful!");
            } else {
                JOptionPane.showMessageDialog(frame, "Username or password is incorrect, please try again!", "Invalid Login!", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

package MyFirstGUIPackage;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class BMI {

    private JFrame frame;
    private JTextField txtHeight;
    private JTextField txtWeight;
    private JLabel lblResult;
    private JButton btnCalculate;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    BMI window = new BMI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BMI() {
        initialize();
        createEvent();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblHeight = new JLabel("Height (cm):");
        lblHeight.setBounds(50, 40, 120, 25);
        frame.getContentPane().add(lblHeight);

        txtHeight = new JTextField();
        txtHeight.setBounds(180, 40, 150, 25);
        frame.getContentPane().add(txtHeight);

        JLabel lblWeight = new JLabel("Weight (kg):");
        lblWeight.setBounds(50, 80, 120, 25);
        frame.getContentPane().add(lblWeight);

        txtWeight = new JTextField();
        txtWeight.setBounds(180, 80, 150, 25);
        frame.getContentPane().add(txtWeight);

        btnCalculate = new JButton("Calculate BMI");
        btnCalculate.setBounds(150, 120, 150, 30);
        frame.getContentPane().add(btnCalculate);

        lblResult = new JLabel("BMI Result:");
        lblResult.setBounds(50, 170, 350, 25);
        frame.getContentPane().add(lblResult);
    }

    private void createEvent() {
        btnCalculate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Check for empty fields
                if (txtHeight.getText().isEmpty() || txtWeight.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Please fill in all fields.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int heightCm = Integer.parseInt(txtHeight.getText());
                    double weightKg = Double.parseDouble(txtWeight.getText());

                    // Check for valid values
                    if (heightCm <= 0 || weightKg <= 0) {
                        JOptionPane.showMessageDialog(frame,
                                "Height and weight must be greater than zero.",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Convert height to meters
                    double heightMeters = heightCm / 100.0;

                    // Calculate BMI
                    double bmi = weightKg / (heightMeters * heightMeters);
                    String comment;

                    if (bmi < 18.5) {
                        comment = "Underweight";
                    } else if (bmi < 25) {
                        comment = "Normal weight";
                    } else if (bmi < 30) {
                        comment = "Overweight";
                    } else {
                        comment = "Obese";
                    }

                    lblResult.setText(String.format("BMI: %.2f (%s)", bmi, comment));

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Please enter valid numbers only.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

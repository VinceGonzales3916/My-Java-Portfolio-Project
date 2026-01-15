package MyFirstGUIPackage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class numberGame {

    private JFrame frame;
    private JTextField guessField;
    private JButton checkButton;
    private JLabel resultLabel;
    private JComboBox<String> difficultyBox;

    private int randomNumber;
    private int maxNumber;
    private int remainingGuesses;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                numberGame window = new numberGame();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public numberGame() {
        initialize();
        createEvents();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Guess the Number");
        frame.getContentPane().setBackground(new Color(220, 220, 220));
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Title
        JLabel title = new JLabel("Guess the Number", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(50, 10, 400, 40);
        frame.getContentPane().add(title);

        // Difficulty Label
        JLabel difficultyLabel = new JLabel("Select Difficulty:");
        difficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        difficultyLabel.setBounds(150, 55, 200, 20);
        frame.getContentPane().add(difficultyLabel);

        // Difficulty ComboBox
        difficultyBox = new JComboBox<>(new String[] {
                "Easy (1-25)",
                "Normal (1-50)",
                "Advanced (1-75)",
                "Hard (1-100)"
        });
        difficultyBox.setBounds(170, 75, 160, 25);
        frame.getContentPane().add(difficultyBox);

        // Enter guess label
        JLabel enterLabel = new JLabel("Enter your Guess:");
        enterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enterLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        enterLabel.setBounds(139, 105, 200, 30);
        frame.getContentPane().add(enterLabel);

        // Guess text field
        guessField = new JTextField("Put Your Guess Here");
        guessField.setHorizontalAlignment(SwingConstants.CENTER);
        guessField.setFont(new Font("Arial", Font.PLAIN, 15));
        guessField.setForeground(Color.GRAY);
        guessField.setBounds(139, 135, 200, 30);
        frame.getContentPane().add(guessField);

        // Check button
        checkButton = new JButton("Check the Guess");
        checkButton.setFont(new Font("Arial", Font.PLAIN, 16));
        checkButton.setBounds(149, 175, 180, 40);
        frame.getContentPane().add(checkButton);

        // Result label
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resultLabel.setBounds(50, 225, 400, 40);
        frame.getContentPane().add(resultLabel);

        // Initialize game
        setDifficulty();
    }

    // Sets the random number and max guesses based on difficulty
    private void setDifficulty() {
        String difficulty = (String) difficultyBox.getSelectedItem();

        if (difficulty.startsWith("Easy")) {
            maxNumber = 25;
            remainingGuesses = 5;
        } else if (difficulty.startsWith("Normal")) {
            maxNumber = 50;
            remainingGuesses = 7;
        } else if (difficulty.startsWith("Advanced")) {
            maxNumber = 75;
            remainingGuesses = 9;
        } else { // Hard
            maxNumber = 100;
            remainingGuesses = 10;
        }

        randomNumber = (int) (Math.random() * maxNumber) + 1;
        resultLabel.setText("Guesses left: " + remainingGuesses);
    }

    private void createEvents() {

        // Focus events for guessField
        guessField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (guessField.getText().equals("Put Your Guess Here")) {
                    guessField.setText("");
                    guessField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (guessField.getText().isEmpty()) {
                    guessField.setText("Put Your Guess Here");
                    guessField.setForeground(Color.GRAY);
                }
            }
        });

        // Difficulty change event
        difficultyBox.addActionListener(e -> setDifficulty());

        // Check button event
        checkButton.addActionListener(e -> handleGuess());
    }

    // Handles the guess logic
    private void handleGuess() {
        String userText = guessField.getText();

        if (userText.equals("Put Your Guess Here") || userText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a number!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int guess = Integer.parseInt(userText);

            if (guess < 1 || guess > maxNumber) {
                JOptionPane.showMessageDialog(frame,
                        "Enter a number between 1 and " + maxNumber,
                        "Out of Range",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            remainingGuesses--;

            if (guess == randomNumber) {
                JOptionPane.showMessageDialog(frame, "Correct! You guessed it!", "Success", JOptionPane.INFORMATION_MESSAGE);
                setDifficulty(); // restart game
            } else {
                String hint = (guess < randomNumber) ? "Too low!" : "Too high!";
                JOptionPane.showMessageDialog(frame, hint, "Hint", JOptionPane.INFORMATION_MESSAGE);

                if (remainingGuesses == 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Game over! The number was " + randomNumber,
                            "Game Over",
                            JOptionPane.INFORMATION_MESSAGE);
                    setDifficulty(); // restart game
                }
            }

            resultLabel.setText("Guesses left: " + remainingGuesses);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input! Enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

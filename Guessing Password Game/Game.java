import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    private Password password;
    private JFrame frame;
    private JLabel timerLabel;
    private JTextArea informationArea;
    private JButton[] digitButtons;
    private JButton enterButton;
    private JTextField guessField;
    private Timer timer;
    private int remainingGuesses;
    private String latestGuess;

    public Game() {
        password = new Password(); // Generate password untuk ditebak
        remainingGuesses = 10;
        latestGuess = "";

        // Membuat frame
        frame = new JFrame("3 Digits Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        // Membuat label timer
        timerLabel = new JLabel("Time remaining: 2:00");
        frame.add(timerLabel, BorderLayout.NORTH);

        // Membuat information area untuk password yang ditebak
        informationArea = new JTextArea();
        informationArea.setEditable(false);
        JScrollPane informationScrollPane = new JScrollPane(informationArea);
        frame.add(informationScrollPane, BorderLayout.CENTER);

        // Membuat panel untuk tombol digit
        JPanel buttonPanel = new JPanel(new GridLayout(4, 3));
        digitButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            digitButtons[i] = new JButton(Integer.toString(i));
            int digit = i;
            digitButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    guessField.setText(guessField.getText() + digit);
                    latestGuess = guessField.getText();
                    informationArea.setText("Latest Entered Password: \n" + latestGuess);
                }
            });
            buttonPanel.add(digitButtons[i]);
        }
        frame.add(buttonPanel, BorderLayout.WEST);

        // Membuat panel untuk field menebak dan button enter
        JPanel inputPanel = new JPanel(new BorderLayout());
        guessField = new JTextField();
        guessField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });
        inputPanel.add(guessField, BorderLayout.CENTER);
        enterButton = new JButton("Enter Guess");
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });
        inputPanel.add(enterButton, BorderLayout.SOUTH);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Membuat timer
        timer = new Timer(1000, new ActionListener() {
            int remainingTime = 2 * 60;

            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                int minutes = remainingTime / 60;
                int seconds = remainingTime % 60;
                timerLabel.setText("Time remaining: " + minutes + ":" + String.format("%02d", seconds));

                if (remainingTime <= 0) {
                    timer.stop();
                    disableInput();
                    JOptionPane.showMessageDialog(frame, "Time's up! The correct password was: " + password);
                }
            }
        });
    }

    // Method untuk memulai game
    public void startGame() {
        frame.setVisible(true);
        timer.start();
        informationArea.setText("Latest Entered Password: \n" + latestGuess);
        JOptionPane.showMessageDialog(frame, "Welcome to the 3 Digits Password game!\nGuess the correct password within 2 minutes. You have 10 chances.");
    }

    // Method untuk memproses tebakan user
    private void processGuess() {
        String guess = guessField.getText();
        guessField.setText("");
        latestGuess = "";

        // Conditional jika tebakan benar dan tebakan salah
        if (password.isPasswordGuessed(guess)) {
            timer.stop();
            disableInput();
            JOptionPane.showMessageDialog(frame, "Congratulations! You guessed the password correctly.");
        } else {
            String feedback = password.checkGuess(guess);
            informationArea.append("\nIncorrect password. " + feedback);

            remainingGuesses--;
            if (remainingGuesses <= 0) {
                timer.stop();
                disableInput();
                JOptionPane.showMessageDialog(frame, "Sorry, you ran out of guesses. The correct password was: " + password);
            }
        }
    }

    // Method untuk mendisable input program jika program selesai
    private void disableInput() {
        for (int i = 0; i < 10; i++) {
            digitButtons[i].setEnabled(false);
        }
        guessField.setEnabled(false);
        enterButton.setEnabled(false);
    }
}
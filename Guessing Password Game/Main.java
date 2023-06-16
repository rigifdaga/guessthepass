import javax.swing.*;

// Main class untuk memulai game
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                game.startGame();
            }
        });
    }
}

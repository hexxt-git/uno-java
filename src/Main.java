import player.Player;
import display.GameDisplay;
import display.InputListener;
import game.GameManager;

// Main entry point for the Uno game application
// Sets up the game display, input listener, and game manager
// Then starts the game with configured players
class Main {
    public static void main(String[] Args) {
        GameDisplay gameDisplay = new GameDisplay();
        InputListener inputListener = InputListener.getInstance();
        GameManager manager = new GameManager(gameDisplay, inputListener);
        Player[] players = manager.setupGame();
        manager.startGame(players);
    }
}

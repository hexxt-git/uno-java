import player.BotPlayer;
import player.HumanPlayer;
import player.Player;

import display.GameDisplay;
import display.InputListener;
import display.Point;
import game.GameManager;
import game.Uno;

class Main {
    public static void main(String[] Args) {
        GameDisplay gameDisplay = new GameDisplay();
        InputListener inputListener = InputListener.getInstance();
        GameManager manager = new GameManager(gameDisplay,inputListener);
        Player[] players = manager.setupGame();
        manager.startGame(players);
        
    }
}

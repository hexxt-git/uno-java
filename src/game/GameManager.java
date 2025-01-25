package game;

import display.GameDisplay;
import display.InputListener;
import display.Point;
import player.BotPlayer;
import player.HumanPlayer;
import player.Player;

public class GameManager {
	  GameDisplay gameDisplay;
	  InputListener inputListener;
	
	public GameManager(GameDisplay gameDisplay,InputListener inputListener) {
		this.gameDisplay = gameDisplay;
		this.inputListener = inputListener;
	}
	
	public Player[] setupGame() {
        Point[] dimensions = { new Point(0, 0) };

        Thread displayThread = new Thread(() -> {
            while (true) {
                Point newDimensions = gameDisplay.getDimensions();
                if (!newDimensions.equals(dimensions[0])) {
                    dimensions[0] = newDimensions;
                    gameDisplay.render();
                }
            }
        });

        displayThread.start();
        gameDisplay.render();

        gameDisplay.log("Enter the number of players (2-4):");
        int numPlayers = 0;
        char ch = inputListener.getInput();
        numPlayers = ch - '0';
        gameDisplay.log("Number of players entered: " + numPlayers);
        while (numPlayers < 2 || numPlayers > 4) {
            gameDisplay.log("Invalid number of players. Please enter a number between 2 and 4:");
            ch = inputListener.getInput();
            numPlayers = ch - '0';
            gameDisplay.log("Number of players entered: " + numPlayers);
        }

        Player[] players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            gameDisplay.log("Is player " + (i + 1) + " a bot? (h/b):");
            ch = inputListener.getInput();
            gameDisplay.log("Player " + (i + 1) + " is " + (ch == 'h' ? "human" : "bot") + ": " + ch);
            while (ch != 'h' && ch != 'b') {
                gameDisplay.log("Invalid input. Please enter 'h' or 'b':");
                ch = inputListener.getInput();
                gameDisplay.log("Player " + (i + 1) + " is " + (ch == 'h' ? "human" : "bot") + ": " + ch);
            }
            gameDisplay.log("Input Player " + (i + 1) + "'s name:");
            String name = inputListener.getString();
            gameDisplay.log("Player " + (i + 1) + "'s name entered: " + name);
            if (ch == 'b') {
                players[i] = new BotPlayer(name);
            } else {
                players[i] = new HumanPlayer(name, inputListener);
            }
        }

        return players;
    }
	
    public void startGame(Player[] players) {
        Uno game = new Uno(players, gameDisplay, inputListener);
        game.start();
    }
}

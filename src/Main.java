import player.BotPlayer;
import player.HumanPlayer;
import player.Player;

import display.GameDisplay;
import display.InputListener;
import display.Point;
import game.Uno;

class Main {
    public static void main(String[] Args) {
        GameDisplay gameDisplay = new GameDisplay();

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

        InputListener inputListener = InputListener.getInstance();

        Player[] players = {
                new BotPlayer("Alice"),
                new BotPlayer("Bob"),
                new BotPlayer("Charlie"),
                new HumanPlayer("You", inputListener)
        };
        Uno game = new Uno(players, gameDisplay, inputListener);
        game.start();
    
    
    }
}

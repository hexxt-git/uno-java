import player.BotPlayer;
import player.Player;
import cards.NumberCard;
import constants.Color;
import display.Display;
import game.Uno;

class Main {
    public static void main(String[] Args) {
        // Player[] players = {
        // new BotPlayer("Alice"),
        // new BotPlayer("Bob"),
        // new BotPlayer("Charlie"),
        // new BotPlayer("Diana")
        // };
        // Uno game = new Uno(players);
        // game.start();
        Display display = new Display();

        display.writeln(
                "lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
        display.write("hello card: ");
        display.writeln(new NumberCard(Color.Green, 4).toString());
        display.writeln("hello world");
        display.out();
        display.readInput();
        while (true) {
            display.tick();
        }

    }
}

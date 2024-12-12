import player.HumanPlayer;
import player.Player;

class UnoGame {
    Player[] players;
    Deck deck;
    CardStack placedCards; // operate as a queue
    boolean direction;
    int turn; // index of current

    public UnoGame(Player[] players) {
        this.players = players;
        this.deck = new Deck();
        this.placedCards = new CardStack();
        this.direction = true;
        this.turn = 0;
    }

    public void start() {
        // this is a test
        System.out.println("New deck:");
        System.out.println(deck);
        System.out.println(placedCards);
        deck.shuffle();
        System.out.println("\nShuffled deck");
        System.out.println(deck);

        System.out.println(placedCards);
        placedCards.push(deck.draw());
        System.out.println(placedCards);
        placedCards.push(deck.draw());
        System.out.println(placedCards);
        placedCards.push(deck.draw());
        System.out.println(placedCards);

        System.out.println("\nAfter draw:");
        System.out.println(deck);
        System.out.println(placedCards);

        deck.resetAndKeepTop(placedCards);

        System.out.println("\nAfter reset:");
        System.out.println(deck);
        System.out.println(placedCards);

        turn = (turn + (direction ? 1 : -1)) % players.length;
    }

    public static void main(String[] Args) {
        Player[] players = {
                new HumanPlayer("Alice"),
                new HumanPlayer("Bob"),
                new HumanPlayer("Charlie"),
                new HumanPlayer("Diana")
        };
        UnoGame game = new UnoGame(players);
        game.start();
    }
}
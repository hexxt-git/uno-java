import player.HumanPlayer;
import player.Player;
import cards.Card;

class UnoGame {
    Player[] players;
    Deck deck;
    Card[] placedCards; // operate as a queue
    boolean direction;
    int turn; // index of current

    public UnoGame(Player[] players) {
        this.players = players;
        this.deck = new Deck();
        this.placedCards = new Card[108];
        this.direction = true;
    }

    public void start() {
        System.out.println("New deck");
        System.out.println(deck);
        deck.shuffle();
        System.out.println("\nShuffled deck");
        System.out.println(deck);
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
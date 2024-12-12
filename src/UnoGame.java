import player.BotPlayer;
import player.Player;
import cards.Card;

class UnoGame {
    Player[] players;
    Deck deck;
    CardStack placedCards;
    boolean direction;
    int currentTurn;

    public UnoGame(Player[] players) {
        this.players = players;
        this.deck = new Deck();
        this.placedCards = new CardStack();
        this.direction = true;
        this.currentTurn = 0;
    }

    public void start() {
        deck.shuffle();
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.draw(deck.draw(placedCards));
            }
            System.out.println(player);
        }

        for (int i = 0; i < 1000; i++) { // TODO: TEMP CODE
            Card play = players[currentTurn].play(placedCards.top());
            if (play == null) {
                Card drawnCard = deck.draw(placedCards);
                players[currentTurn].draw(drawnCard);
                System.out.println(players[currentTurn].getName() + " draws " + drawnCard);
            } else {
                placedCards.push(play);
                System.out.println(players[currentTurn].getName() + " plays " + play + " on " + placedCards.top());
            }

            if (players[currentTurn].getHand().size() == 0) {
                System.out.println(players[currentTurn].getName() + " wins!");
                break;
            }
            currentTurn = (currentTurn + (direction ? 1 : -1)) % players.length;
        }

    }

    public static void main(String[] Args) {
        Player[] players = {
                new BotPlayer("Alice"),
                new BotPlayer("Bob"),
                new BotPlayer("Charlie"),
                new BotPlayer("Diana")
        };
        UnoGame game = new UnoGame(players);
        game.start();
    }
}
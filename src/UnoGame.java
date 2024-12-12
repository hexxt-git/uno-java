import player.BotPlayer;
import player.Player;
import cards.ActionCard;
import cards.Card;
import constants.Color;

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
        System.out.println("");

        while (true) {
            if (playTurn()) {
                break;
            }
        }
    }

    private boolean playTurn() {
        Card top = placedCards.top();
        Card play = players[currentTurn].play(top);

        if (play == null) {
            Card drawnCard = deck.draw(placedCards);
            players[currentTurn].draw(drawnCard);
            System.out.println(players[currentTurn].getName() + " draws " + drawnCard);
        } else {
            if (!play.isValidPlay(top)) {
                throw new RuntimeException("Invalid play: " + play + " on " + top);
            }
            placedCards.push(play);
            System.out.println(players[currentTurn].getName() + " plays " + play + " on " + top);

            if (play.getColor() == Color.Wild) {
                Color chosenColor = players[currentTurn].chooseColor();
                play.setColor(chosenColor);
                System.out.println(players[currentTurn].getName() + " chooses " + chosenColor);
            }
            if (play instanceof ActionCard) {
                handleActionCard((ActionCard) play);
            }
        }

        if (players[currentTurn].getHand().size() == 0) {
            System.out.println(players[currentTurn].getName() + " wins!");
            return true;
        }
        currentTurn = next(currentTurn);
        return false;
    }

    private void handleActionCard(ActionCard actionCard) {
        switch (actionCard.action) {
            case Skip:
                System.out.println(players[currentTurn].getName() + " plays Skip. "
                        + players[next(currentTurn)].getName() + " is skipped.");
                currentTurn = next(currentTurn);
                break;
            case Reverse:
                System.out.println(players[currentTurn].getName() + " plays Reverse. Direction is now "
                        + (direction ? "clockwise." : "counterclockwise."));
                direction = !direction;
                break;
            case Draw2:
                System.out.println(players[currentTurn].getName() + " plays Draw2. "
                        + players[next(currentTurn)].getName() + " draws 2 cards.");
                for (int j = 0; j < 2; j++) {
                    Card drawnCard = deck.draw(placedCards);
                    players[next(currentTurn)].draw(drawnCard);
                    System.out.println(players[next(currentTurn)].getName() + " draws " + drawnCard);
                }
                currentTurn = next(currentTurn);
                break;
            case Draw4:
                System.out.println(players[currentTurn].getName() + " plays Draw4. "
                        + players[next(currentTurn)].getName() + " draws 4 cards.");
                for (int j = 0; j < 4; j++) {
                    Card drawnCard = deck.draw(placedCards);
                    players[next(currentTurn)].draw(drawnCard);
                    System.out.println(players[next(currentTurn)].getName() + " draws " + drawnCard);
                }
                currentTurn = next(currentTurn);
                break;
            default:
                break;
        }
    }

    private int next(int index) {
        return (index + (direction ? 1 : -1) + players.length) % players.length;
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
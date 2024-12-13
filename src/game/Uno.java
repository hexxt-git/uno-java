package game;

import player.Player;
import cards.ActionCard;
import cards.Card;
import constants.Color;

public class Uno {
    Player[] players;
    Deck deck;
    CardStack placedCards;
    boolean direction;
    int currentTurn;

    public Uno(Player[] players) {
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
        }

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
        } else {
            if (!play.isValidPlay(top)) {
                throw new RuntimeException("Invalid play: " + play + " on " + top);
            }
            placedCards.push(play);

            if (play.getColor() == Color.Wild) {
                Color chosenColor = players[currentTurn].chooseColor();
                play.setColor(chosenColor);
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
                currentTurn = next(currentTurn);
                break;
            case Reverse:
                direction = !direction;
                break;
            case Draw2:
                for (int j = 0; j < 2; j++) {
                    Card drawnCard = deck.draw(placedCards);
                    players[next(currentTurn)].draw(drawnCard);
                }
                currentTurn = next(currentTurn);
                break;
            case Draw4:
                for (int j = 0; j < 4; j++) {
                    Card drawnCard = deck.draw(placedCards);
                    players[next(currentTurn)].draw(drawnCard);
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
}
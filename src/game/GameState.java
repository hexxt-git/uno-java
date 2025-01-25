package game;

import cards.Card;
import display.GameDisplay;
import player.Player;

// Encapsulates the current state of the game
// Provides methods to modify game state safely
public class GameState {
    private Player[] players;
    private Deck deck;
    private CardStack placedCards;
    private boolean direction;      // true = clockwise, false = counter-clockwise
    private int currentTurn;
    private GameDisplay display;
    private boolean gameOver;

    public GameState(Player[] players, Deck deck, CardStack placedCards, GameDisplay display) {
        this.players = players;
        this.deck = deck;
        this.placedCards = placedCards;
        this.display = display;
        this.direction = true;
        this.currentTurn = 0;
        this.gameOver = false;
    }

    public Player getCurrentPlayer() {
        return players[currentTurn];
    }

    public Player getNextPlayer() {
        return players[getNextPlayerIndex()];
    }

    public void skipNextPlayer() {
        currentTurn = getNextPlayerIndex();
    }

    public void reverseDirection() {
        direction = !direction;
    }

    public Card drawCard() {
        return deck.draw(placedCards);
    }

    public void playCard(Card card) {
        placedCards.push(card);
    }

    private int getNextPlayerIndex() {
        return (currentTurn + (direction ? 1 : -1) + players.length) % players.length;
    }

    public void advanceTurn() {
        currentTurn = getNextPlayerIndex();
    }

    public GameDisplay getDisplay() {
        return display;
    }

    public Card getTopCard() {
        return placedCards.top();
    }

    public void checkWinCondition() {
        for (Player player : players) {
            if (player.getHand().isEmpty()) {
                gameOver = true;
                display.showGameOver(player);
                break;
            }
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public Player[] getPlayers() {
        return players;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int getCurrentTurnIndex() {
        return currentTurn;
    }

    // ... other getters and state management methods as needed
} 
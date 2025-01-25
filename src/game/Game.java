package game;

import player.Player;
import player.HumanPlayer;
import cards.ActionCard;
import cards.Card;
import constants.Color;
import display.GameDisplay;
import leaderboard.leaderboardcl;

public class Game {
    private Player[] players;
    private Deck deck;
    private CardStack placedCards;
    private boolean direction; // true = clockwise, false = counter-clockwise
    private int currentTurn;
    private GameDisplay display;
    private boolean gameOver;
    private leaderboardcl leaderboard;

    public Game(Player[] players, GameDisplay display) {
        this.players = players;
        this.deck = new Deck();
        this.placedCards = new CardStack();
        this.direction = true;
        this.currentTurn = 0;
        this.gameOver = false;
        this.display = display;
        this.leaderboard = new leaderboardcl();
    }

    public void start() {
        initializeGame();
        gameLoop();
    }

    private void initializeGame() {
        deck.shuffle();
        display.setDeck(deck);
        display.log("Deck shuffled.");

        // Deal initial cards
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.draw(drawCard());
            }
            display.log(player.getName() + " drew 7 cards.");
            leaderboard.createScore(0, player.getName());
        }

        updatePlayerDisplay();
    }

    private void gameLoop() {
        while (!gameOver) {
            playTurn();
            checkWinCondition();
            if (gameOver) {
                leaderboard.printLeaderboard();
            }
            display.setTableTop(getTopCard().toString());
        }
    }

    private void playTurn() {
        Card topCard = getTopCard();
        if (topCard == null) {
            Card newCard = drawCard();
            playCard(newCard);
            display.setDeck(deck);
            display.log("Placed a new card on the table.");
            return;
        }

        display.setTableTop(topCard.toString());
        display.showAvailableActions(getCurrentPlayer(), topCard);
        updatePlayerDisplay();

        Card playedCard = getCurrentPlayer().play(topCard);

        if (playedCard == null) {
            handleCardDraw();
        } else {
            handleCardPlay(playedCard);
        }

        advanceTurn();
    }

    private void handleCardDraw() {
        Player currentPlayer = getCurrentPlayer();
        Card drawnCard = drawCard();
        currentPlayer.draw(drawnCard);
        leaderboard.updateScore(-10, currentPlayer.getName());
        display.setDeck(deck);
        display.logCardDrawn(currentPlayer, 1);
    }

    private void handleCardPlay(Card playedCard) {
        if (!playedCard.isValidPlay(getTopCard())) {
            display.alert("Invalid play: " + playedCard + " on " + getTopCard());
            throw new RuntimeException("Invalid play: " + playedCard + " on " + getTopCard());
        }

        playCard(playedCard);
        leaderboard.updateScore(5, getCurrentPlayer().getName());

        if (playedCard.getColor() == Color.Wild) {
            handleWildCard(playedCard);
        }

        if (playedCard instanceof ActionCard) {
            ((ActionCard) playedCard).applyEffect(this);
            leaderboard.updateScore(10, getCurrentPlayer().getName());
        }
    }

    private void handleWildCard(Card wildCard) {
        Player currentPlayer = getCurrentPlayer();
        if (currentPlayer instanceof HumanPlayer) {
            display.showColorSelection(currentPlayer);
        }
        Color chosenColor = currentPlayer.chooseColor();
        wildCard.setColor(chosenColor);
        display.logCardPlayed(currentPlayer, wildCard, chosenColor);
    }

    private void updatePlayerDisplay() {
        display.updatePlayerList(players, currentTurn);
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

    public GameDisplay getDisplay() {
        return display;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
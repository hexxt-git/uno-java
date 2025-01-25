package game;

import player.Player;
import player.HumanPlayer;
import cards.ActionCard;
import cards.Card;
import constants.Color;
import display.GameDisplay;
import leaderboard.leaderboardcl;

// Main game logic for Uno
// Handles turn management and game rules
public class Game {
    private GameState state;
    private leaderboardcl leaderboard;

    public Game(Player[] players, GameDisplay display) {
        Deck deck = new Deck();
        CardStack placedCards = new CardStack();
        this.state = new GameState(players, deck, placedCards, display);
        this.leaderboard = new leaderboardcl();
    }

    public void start() {
        initializeGame();
        gameLoop();
    }

    private void initializeGame() {
        state.getDeck().shuffle();
        state.getDisplay().setDeck(state.getDeck());
        state.getDisplay().log("Deck shuffled.");

        // Deal initial cards
        for (Player player : state.getPlayers()) {
            for (int i = 0; i < 7; i++) {
                player.draw(state.drawCard());
            }
            state.getDisplay().log(player.getName() + " drew 7 cards.");
            leaderboard.createScore(0, player.getName());
        }

        updatePlayerDisplay();
    }

    private void gameLoop() {
        while (!state.isGameOver()) {
            playTurn();
            state.checkWinCondition();
            if (state.isGameOver()) {
                leaderboard.printLeaderboard();
            }
            state.getDisplay().setTableTop(state.getTopCard().toString());
        }
    }

    private void playTurn() {
        Card topCard = state.getTopCard();
        if (topCard == null) {
            Card newCard = state.drawCard();
            state.playCard(newCard);
            state.getDisplay().setDeck(state.getDeck());
            state.getDisplay().log("Placed a new card on the table.");
            return;
        }

        state.getDisplay().setTableTop(topCard.toString());
        state.getDisplay().showAvailableActions(state.getCurrentPlayer(), topCard);
        updatePlayerDisplay();

        Card playedCard = state.getCurrentPlayer().play(topCard);

        if (playedCard == null) {
            handleCardDraw();
        } else {
            handleCardPlay(playedCard);
        }

        state.advanceTurn();
    }

    private void handleCardDraw() {
        Player currentPlayer = state.getCurrentPlayer();
        Card drawnCard = state.drawCard();
        currentPlayer.draw(drawnCard);
        leaderboard.updateScore(-10, currentPlayer.getName());
        state.getDisplay().setDeck(state.getDeck());
        state.getDisplay().logCardDrawn(currentPlayer, 1);
    }

    private void handleCardPlay(Card playedCard) {
        if (!playedCard.isValidPlay(state.getTopCard())) {
            state.getDisplay().alert("Invalid play: " + playedCard + " on " + state.getTopCard());
            throw new RuntimeException("Invalid play: " + playedCard + " on " + state.getTopCard());
        }

        state.playCard(playedCard);
        leaderboard.updateScore(5, state.getCurrentPlayer().getName());

        if (playedCard.getColor() == Color.Wild) {
            handleWildCard(playedCard);
        }

        if (playedCard instanceof ActionCard) {
            ((ActionCard) playedCard).applyEffect(state);
            leaderboard.updateScore(10, state.getCurrentPlayer().getName());
        }
    }

    private void handleWildCard(Card wildCard) {
        Player currentPlayer = state.getCurrentPlayer();
        if (currentPlayer instanceof HumanPlayer) {
            state.getDisplay().showColorSelection(currentPlayer);
        }
        Color chosenColor = currentPlayer.chooseColor();
        wildCard.setColor(chosenColor);
        state.getDisplay().logCardPlayed(currentPlayer, wildCard, chosenColor);
    }

    private void updatePlayerDisplay() {
        state.getDisplay().updatePlayerList(state.getPlayers(), state.getCurrentTurnIndex());
    }
}
package game;

import player.HumanPlayer;
import player.Player;

import java.util.List;

import cards.ActionCard;
import cards.Card;
import constants.Color;
import constants.ConsoleColor;
import display.GameDisplay;
import display.InputListener;
import leaderboard.leaderboardcl;

public class Uno {
    Player[] players;
    Deck deck;
    CardStack placedCards;
    boolean direction;
    int currentTurn;
    GameDisplay display;
    InputListener inputListener;
    boolean gameOver;
    leaderboardcl leaderboard = new leaderboardcl();



    public Uno(Player[] players, GameDisplay display, InputListener inputListener) {
        this.players = players;
        this.deck = new Deck();
        this.placedCards = new CardStack();
        this.direction = true;
        this.currentTurn = 0;
        this.display = display;
        this.inputListener = inputListener;
        this.gameOver = false;

        

    }

    public void start() {
        
        deck.shuffle();
        display.setDeck(deck);
        display.log("Deck shuffled.");
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                player.draw(deck.draw(placedCards));
            }
            display.log(player.getName() + " drew 7 cards.");
        }

        String[] playerNames = new String[players.length];

        for (int i = 0; i < players.length; i++) {
            leaderboard.createScore(0, players[i].getName());
            if (i == currentTurn) {
                playerNames[i] = "> " + players[i].getName();
            } else {
                playerNames[i] = players[i].getName();
            }
        }

        display.setPlayers(playerNames);

        while (!gameOver) {

            playTurn();

            Player winner = null;
            for (Player player : players) {
                if (player.getHand().size() == 0) {
                    winner = player;
                    break;
                }
            }
            if (winner != null) {
                gameOver = true;
                display.alert("\n" + winner.getName() + " has won the game!\n\n\n\n[CTRL+C] to Exit");
                leaderboard.printLeaderboard();
                display.log(winner.getName() + " has won the game!");
            }
            
            display.setTableTop(placedCards.top().toString());
        }

    }

    private void playTurn() {
        Card top = placedCards.top();
        if (top == null) {
            placedCards.push(deck.draw(placedCards));
            display.setDeck(deck);
            display.log("Placed a new card on the table.");
            return;
        }

        display.setTableTop(top.toString());

        if (players[currentTurn] instanceof HumanPlayer) {
            StringBuilder actions = new StringBuilder();
            actions.append("Enter - Draw card\n");

            List<Card> hand = players[currentTurn].getHand();
            for (int i = 0; i < hand.size(); i++) {
                char actionKey = (char) ('a' + i - 9);
                if (hand.get(i).isValidPlay(top)) {
                    if (i < 9) {
                        actions.append(String.format("%d - Play %s\n", i + 1, hand.get(i)));
                    } else {
                        actions.append(String.format("%c - Play %s\n", actionKey, hand.get(i)));
                    }
                } else {
                    actions.append(String.format("%sx - Play \033[0m%s\n", ConsoleColor.GRAY.code, hand.get(i)));
                }
            }

            display.setAvailableInputs(actions.toString(), players[currentTurn].getName());
        } else {
            display.clearAvailableInputs();
        }

        String[] playerNames = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            if (i == currentTurn) {
                playerNames[i] = "> " + players[i].getName() + " (" + players[i].getHand().size() + ")";
            } else {
                playerNames[i] = players[i].getName() + " (" + players[i].getHand().size() + ")";
            }
        }

        display.setPlayers(playerNames);

        Card play = players[currentTurn].play(top);

        if (play == null) {

            Card drawnCard = deck.draw(placedCards);
            players[currentTurn].draw(drawnCard);
            leaderboard.updateScore(-10, players[currentTurn].getName());
            display.setDeck(deck);
            display.log(players[currentTurn].getName() + " drew a card.");
        } else {
            if (!play.isValidPlay(top)) {
                display.alert("Invalid play: " + play + " on " + top);
                throw new RuntimeException("Invalid play: " + play + " on " + top);
            }
            placedCards.push(play);
            leaderboard.updateScore(5, players[currentTurn].getName());
            display.log(players[currentTurn].getName() + " played " + play + ".");

            if (play.getColor() == Color.Wild) {
                if (players[currentTurn] instanceof HumanPlayer) {
                    display.setAvailableInputs("R - Red\nG - Green\nB - Blue\nY - Yellow",
                            players[currentTurn].getName());
                }
                Color chosenColor = players[currentTurn].chooseColor();
                play.setColor(chosenColor);
                display.log(players[currentTurn].getName() + " chose color " + chosenColor.consoleColor + chosenColor
                        + "\033[0m.");
            }
            if (play instanceof ActionCard) {
                handleActionCard((ActionCard) play);
                leaderboard.updateScore(10, players[currentTurn].getName());

            }
        }

        currentTurn = next(currentTurn);
    }

    private void handleActionCard(ActionCard actionCard) {
        switch (actionCard.action) {
            case Skip:
                currentTurn = next(currentTurn);
                display.log(players[currentTurn].getName() + " was skipped.");
                break;
            case Reverse:
                direction = !direction;
                display.log("Direction reversed.");
                break;
            case Draw2:
                for (int j = 0; j < 2; j++) {
                    Card drawnCard = deck.draw(placedCards);
                    players[next(currentTurn)].draw(drawnCard);
                }
                display.setDeck(deck);
                display.log(players[next(currentTurn)].getName() + " drew 2 cards.");
                currentTurn = next(currentTurn);
                break;
            case Draw4:
                for (int j = 0; j < 4; j++) {
                    Card drawnCard = deck.draw(placedCards);
                    players[next(currentTurn)].draw(drawnCard);
                }
                display.setDeck(deck);
                display.log(players[next(currentTurn)].getName() + " drew 4 cards.");
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
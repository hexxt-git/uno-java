package display;

import java.util.ArrayList;
import java.util.List;

import constants.BorderStyle;
import constants.ChildAlignment;
import constants.ConsoleColor;
import constants.TextAlignment;
import constants.Color;

import game.Deck;
import player.Player;
import cards.Card;
import player.HumanPlayer;

// Main game UI that composes all display components
// Manages the game board, player info, and message areas

public class GameDisplay extends Display {
    private ParentComponent root;
    private TextComponent players;
    private TextComponent tableTop;
    private TextComponent deck;
    private TextComponent availableInputs;
    private TextComponent logs;

    public GameDisplay() {
        root = new ParentComponent().setTitle("Uno Game").setBorderStyle(BorderStyle.Double);
        root.setMaxH(60);

        hideCursor();

        ParentComponent div1 = new ParentComponent().setPadding(0, 0, 1, 0)
                .setBorderStyle(BorderStyle.None, BorderStyle.None, BorderStyle.Single, BorderStyle.None)
                .setAlignment(ChildAlignment.Horizontal);

        players = new TextComponent("").setTextAlignment(TextAlignment.Center);

        ParentComponent playersParent = new ParentComponent()
                .setBorderStyle(BorderStyle.None, BorderStyle.Single, BorderStyle.None, BorderStyle.None)
                .setBorderColor(ConsoleColor.YELLOW).addChild(players).setPadding(0);
        playersParent.setBiasSelf(1);

        ParentComponent table = new ParentComponent().setPadding(0);

        tableTop = new TextComponent("Table Top:\n   [Empty]");
        deck = new TextComponent("Deck (108 cards):\n   [Hidden]");


        table.addChild(tableTop).addChild(deck);        
        div1.addChild(playersParent).addChild(table);
        

        ParentComponent div2 = new ParentComponent().setPadding(0, 0, 1, 0)
                .setBorderStyle(BorderStyle.None, BorderStyle.None, BorderStyle.Single, BorderStyle.None)
                .setAlignment(ChildAlignment.Horizontal);

        availableInputs = new TextComponent("");
        div2.addChild(availableInputs).setPadding(1, 0, 1, 1);

        ParentComponent div3 = new ParentComponent().setPadding(0, 0, 1, 0)
                .setBorderStyle(BorderStyle.None, BorderStyle.None, BorderStyle.None, BorderStyle.None)
                .setAlignment(ChildAlignment.Horizontal).setPadding(1, 0);

        logs = new TextComponent("");
        div3.addChild(logs);

        root.addChild(div1).addChild(div2).addChild(div3);
    }

    // Updates the display when game state changes
    public void setPlayers(String[] playerNames) {
        StringBuilder sb = new StringBuilder("Players: \n\n");
        for (String name : playerNames) {
            sb.append(name).append("\n");
        }
        players.setInnerText(sb.toString());
        render();
    }

    public void setTableTop(String top) {
        tableTop.setInnerText("Table Top:\n" + top);
        render();
    }

    public void setDeck(Deck cardDeck) {
        deck.setInnerText("Deck (" + cardDeck.size() + " cards):\n[Hidden]");
        render();
    }

    public void setAvailableInputs(String inputs, String playerName) {
        availableInputs.setInnerText("  Available Inputs for \u001B[4m" + playerName + "\u001B[0m: \n\n" + inputs);
        render();
    }

    public void clearAvailableInputs() {
        availableInputs.setInnerText("No Inputs Available");
        render();
    }

    private List<String> logEntries = new ArrayList<>();

    public void log(String entry) {
        if (logEntries.size() >= 20) {
            logEntries.remove(logEntries.size() - 1);
        }
        logEntries.add(0, entry);
        StringBuilder sb = new StringBuilder("  Logs:\n\n");
        for (String logEntry : logEntries) {
            sb.append(logEntry).append("\n");
        }
        logs.setInnerText(sb.toString());
        render();
    }

    // Shows floating alert messages
    // Will be used for important game events
    public void alert(String message) {
        FloatComponent alertBox = new FloatComponent();

        alertBox.setTargetX(root.w / 2 - 15);
        alertBox.setTargetY(root.h / 3 - 5);
        alertBox.setTargetW(30);
        alertBox.setTargetH(10);
        alertBox.setClear(true);

        alertBox.setTitle("alert").setBorderStyle(BorderStyle.Single)
                .setBorderColor(ConsoleColor.BLUE).setPadding(1, 1, 1, 1);

        alertBox.addChild(new TextComponent(message).setTextAlignment(TextAlignment.Center));

        root.addChild(alertBox);
        render();
    }

    public void render() {
        this.clear();
        root.fullScreen(this);
        root.render(this);
    }

    // Formats and updates player list with current turn indicator and hand sizes
    public void updatePlayerList(Player[] players, int currentTurn) {
        String[] playerNames = new String[players.length];
        for (int i = 0; i < players.length; i++) {
            if (i == currentTurn) {
                playerNames[i] = "> " + players[i].getName() + " (" + players[i].getHand().size() + ")";
            } else {
                playerNames[i] = players[i].getName() + " (" + players[i].getHand().size() + ")";
            }
        }
        setPlayers(playerNames);
    }

    // Formats and displays available actions for human player
    public void showAvailableActions(Player currentPlayer, Card topCard) {
        if (!(currentPlayer instanceof HumanPlayer)) {
            clearAvailableInputs();
            return;
        }

        StringBuilder actions = new StringBuilder();
        actions.append("Enter - Draw card\n");

        List<Card> hand = currentPlayer.getHand();
        for (int i = 0; i < hand.size(); i++) {
            char actionKey = (char) ('a' + i - 9);
            if (hand.get(i).isValidPlay(topCard)) {
                if (i < 9) {
                    actions.append(String.format("%d - Play %s\n", i + 1, hand.get(i)));
                } else {
                    actions.append(String.format("%c - Play %s\n", actionKey, hand.get(i)));
                }
            } else {
                actions.append(String.format("%sx - Play \033[0m%s\n", ConsoleColor.GRAY.code, hand.get(i)));
            }
        }

        setAvailableInputs(actions.toString(), currentPlayer.getName());
    }

    // Shows color selection options for wild cards
    public void showColorSelection(Player player) {
        if (player instanceof HumanPlayer) {
            setAvailableInputs("R - Red\nG - Green\nB - Blue\nY - Yellow", player.getName());
        }
    }

    // Shows game over message with winner
    public void showGameOver(Player winner) {
        alert("\n" + winner.getName() + " has won the game!\n\n\n\n[CTRL+C] to Exit");
        log(winner.getName() + " has won the game!");
    }

    // Logs a card being played with color info
    public void logCardPlayed(Player player, Card card, Color chosenColor) {
        if (chosenColor != null) {
            log(player.getName() + " chose color " + chosenColor.consoleColor + chosenColor + "\033[0m.");
        }
        log(player.getName() + " played " + card + ".");
    }

    // Logs cards being drawn
    public void logCardDrawn(Player player, int count) {
        if (count == 1) {
            log(player.getName() + " drew a card.");
        } else {
            log(player.getName() + " drew " + count + " cards.");
        }
    }

    // Logs action card effects
    public void logActionEffect(String playerName, String effect) {
        log(playerName + " " + effect);
    }
}

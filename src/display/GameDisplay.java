package display;

import java.util.ArrayList;
import java.util.List;

import constants.BorderStyle;
import constants.ChildAlignment;
import constants.ConsoleColor;
import constants.TextAlignment;

import game.Deck;

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
        div2.addChild(availableInputs).setPadding(1, 0);

        ParentComponent div3 = new ParentComponent().setPadding(0, 0, 1, 0)
                .setBorderStyle(BorderStyle.None, BorderStyle.None, BorderStyle.None, BorderStyle.None)
                .setAlignment(ChildAlignment.Horizontal).setPadding(1, 0);

        logs = new TextComponent("");
        div3.addChild(logs);

        root.addChild(div1).addChild(div2).addChild(div3);
    }

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
        availableInputs.setInnerText("  Available Inputs for " + playerName + ": \n\n" + inputs);
        render();
    }

    public void clearAvailableInputs() {
        availableInputs.setInnerText("No Inputs Available");
        render();
    }

    private List<String> logEntries = new ArrayList<>();

    public void log(String entry) {
        if (logEntries.size() >= 15) {
            logEntries.remove(0);
        }
        logEntries.add(entry);
        StringBuilder sb = new StringBuilder("  Logs:\n\n");
        for (String logEntry : logEntries) {
            sb.append(logEntry).append("\n");
        }
        logs.setInnerText(sb.toString());
        render();
    }

    public void error(String message) {
        FloatComponent errorBox = new FloatComponent();

        errorBox.setTargetX(4);
        errorBox.setTargetY(4);
        errorBox.setTargetW(20);
        errorBox.setTargetH(10);
        errorBox.setClear(true);

        errorBox.setTitle("Error").setBorderStyle(BorderStyle.Single)
                .setBorderColor(ConsoleColor.RED).setPadding(1, 1, 1, 1);

        errorBox.addChild(new TextComponent(message).setTextAlignment(TextAlignment.Center));

        root.addChild(errorBox);
        render();
    }

    public void render() {
        this.clear();
        root.fullScreen(this);
        root.render(this);
    }
}

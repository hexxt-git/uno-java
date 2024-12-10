package cards;

import constants.Color;

public class Card {
    private Color color;

    public Card(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public String toString() {
        return String.format("%s[%s Blank]\033[0m", getColor().consoleColor, getColor());
    }
}
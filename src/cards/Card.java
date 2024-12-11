package cards;

import constants.Color;

public class Card {
    private Color color;
    private Color currentColor;

    public Card(Color color) {
        this.color = color;
        this.currentColor = color;
    }

    public void resetColor() {
        this.currentColor = this.color;
    }

    public void setColor(Color color) {
        this.currentColor = color;
    }

    public Color getColor() {
        return this.currentColor;
    }

    public boolean isValidPlay(Card other) {
        if (this.getColor() == Color.Wild)
            return true;
        return this.getColor() == other.getColor();
    }

    public String toString() {
        return String.format("%s[%s Blank]\033[0m", getColor().consoleColor, getColor());
    }
}
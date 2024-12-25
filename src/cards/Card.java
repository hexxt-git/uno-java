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
        if (other == null)
            return true;
        // placing anything when wild card is on top is an exception and shouldn't occur
        // in the game
        if (this.getColor() == Color.Wild || other.getColor() == Color.Wild)
            return true;
        return this.getColor() == other.getColor();
    }

    public String toString() {
        return String.format("%s[%s Blank]\033[0m", getColor().consoleColor, getColor());
    }

    @Override // generated by IDE
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (color != other.color)
            return false;
        if (currentColor != other.currentColor)
            return false;
        return true;
    }
}
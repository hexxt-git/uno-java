package cards;

import constants.Color;

public class NumberCard extends Card {
    private int number;

    public NumberCard(Color color, int number) {
        super(color);
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public boolean isValidPlay(Card other) {
        if (this.getColor() == Color.Wild)
            return true;
        if (other instanceof NumberCard) {
            NumberCard otherNumberCard = (NumberCard) other;
            return this.getColor() == other.getColor() || this.getNumber() == otherNumberCard.getNumber();
        }
        return this.getColor() == other.getColor();
    }

    public String toString() {
        return String.format("%s[%s %s]\033[0m", getColor().consoleColor, getColor(), number);
    }
}
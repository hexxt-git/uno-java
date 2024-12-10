package cards;

import constants.Color;

public class NumberCard extends Card {
    public int number;

    public NumberCard(Color color, int number) {
        super(color);
        this.number = number;
    }

    public String toString() {
        return String.format("%s[%s %s]\033[0m", getColor().consoleColor, getColor(), number);
    }
}
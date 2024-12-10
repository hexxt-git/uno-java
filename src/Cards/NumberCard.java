package Cards;

public class NumberCard extends Card {
    public int number;

    public NumberCard(Color color, int number) {
        super(color);
        this.number = number;

    }

    public String toString() {
        return String.format("[%s %s]", color, number);
    }
}
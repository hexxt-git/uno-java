package Cards;

public class Card {
    public Color color;

    public Card(Color color) {
        this.color = color;
    }

    public String toString() {
        return String.format("[%s Blank]", color);
    }
}
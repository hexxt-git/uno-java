package Cards;

public class ActionCard extends Card {
    public Action action;

    public ActionCard(Color color, Action action) {
        super(color);
        this.action = action;
    }

    public String toString() {
        return String.format("[%s %s]", color, action);
    }
}

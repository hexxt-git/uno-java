package cards;

import constants.Action;
import constants.Color;

public class ActionCard extends Card {
    public Action action;

    public ActionCard(Color color, Action action) {
        super(color);
        this.action = action;
    }

    public String toString() {
        return String.format("%s[%s %s]\033[0m", getColor().consoleColor, getColor(), action);
    }
}

package cards;

import constants.Action;
import constants.Color;
import game.GameState;

// Base class for all action cards (Skip, Reverse, Draw2, Draw4)
// Each action card type implements its own effect
public abstract class ActionCard extends Card {
    // The action this card performs when played
    protected Action action;

    public ActionCard(Color color, Action action) {
        super(color);
        this.action = action;
    }

    // Abstract method that each action card must implement
    // Defines how the card affects game state
    public abstract void applyEffect(GameState state);

    @Override
    public String toString() {
        return getColor().consoleColor + action + "\033[0m";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        ActionCard other = (ActionCard) obj;
        return action == other.action;
    }
}

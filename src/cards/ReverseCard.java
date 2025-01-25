package cards;

import constants.Action;
import constants.Color;
import game.GameState;

// Reverses the direction of play
public class ReverseCard extends ActionCard {
    public ReverseCard(Color color) {
        super(color, Action.Reverse);
    }

    @Override
    public void applyEffect(GameState state) {
        state.reverseDirection();
        state.getDisplay().logActionEffect("", "Direction reversed.");
    }
} 
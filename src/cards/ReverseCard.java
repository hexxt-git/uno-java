package cards;

import constants.Action;
import constants.Color;
import game.Game;

// Reverses the direction of play
public class ReverseCard extends ActionCard {
    public ReverseCard(Color color) {
        super(color, Action.Reverse);
    }

    @Override
    public void applyEffect(Game game) {
        game.reverseDirection();
        game.getDisplay().logActionEffect("", "Direction reversed.");
    }
} 
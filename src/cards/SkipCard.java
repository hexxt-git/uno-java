package cards;

import constants.Action;
import constants.Color;
import game.GameState;

// Skips the next player's turn
public class SkipCard extends ActionCard {
    public SkipCard(Color color) {
        super(color, Action.Skip);
    }

    @Override
    public void applyEffect(GameState state) {
        state.skipNextPlayer();
        state.getDisplay().logActionEffect(state.getNextPlayer().getName(), "was skipped.");
    }
} 
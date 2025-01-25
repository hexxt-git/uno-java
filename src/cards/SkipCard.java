package cards;

import constants.Action;
import constants.Color;
import game.Game;
import player.Player;

// Skips the next player's turn
public class SkipCard extends ActionCard {
    public SkipCard(Color color) {
        super(color, Action.Skip);
    }

    @Override
    public void applyEffect(Game game) {
        Player nextPlayer = game.getNextPlayer();
        game.skipNextPlayer();
        game.getDisplay().logActionEffect(nextPlayer.getName(), "was skipped.");
    }
}
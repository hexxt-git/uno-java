package cards;

import constants.Action;
import constants.Color;
import game.GameState;
import player.Player;

// Forces next player to draw 2 cards
public class DrawTwoCard extends ActionCard {
    public DrawTwoCard(Color color) {
        super(color, Action.Draw2);
    }

    @Override
    public void applyEffect(GameState state) {
        Player nextPlayer = state.getNextPlayer();
        for (int i = 0; i < 2; i++) {
            nextPlayer.draw(state.drawCard());
        }
        state.skipNextPlayer();
        state.getDisplay().logCardDrawn(nextPlayer, 2);
    }
} 
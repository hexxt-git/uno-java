package cards;

import constants.Action;
import constants.Color;
import game.GameState;
import player.Player;

// Wild card that forces next player to draw 4 cards
public class DrawFourCard extends ActionCard {
    public DrawFourCard() {
        super(Color.Wild, Action.Draw4);
    }

    @Override
    public void applyEffect(GameState state) {
        Player nextPlayer = state.getNextPlayer();
        for (int i = 0; i < 4; i++) {
            nextPlayer.draw(state.drawCard());
        }
        state.skipNextPlayer();
        state.getDisplay().logCardDrawn(nextPlayer, 4);
    }
} 
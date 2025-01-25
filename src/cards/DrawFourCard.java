package cards;

import constants.Action;
import constants.Color;
import game.Game;
import player.Player;

// Wild card that forces next player to draw 4 cards
public class DrawFourCard extends ActionCard {
    public DrawFourCard() {
        super(Color.Wild, Action.Draw4);
    }

    @Override
    public void applyEffect(Game game) {
        Player nextPlayer = game.getNextPlayer();
        for (int i = 0; i < 4; i++) {
            nextPlayer.draw(game.drawCard());
        }
        game.skipNextPlayer();
        game.getDisplay().logCardDrawn(nextPlayer, 4);
    }
} 
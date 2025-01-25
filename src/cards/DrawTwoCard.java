package cards;

import constants.Action;
import constants.Color;
import game.Game;
import player.Player;

// Forces next player to draw 2 cards
public class DrawTwoCard extends ActionCard {
    public DrawTwoCard(Color color) {
        super(color, Action.Draw2);
    }

    @Override
    public void applyEffect(Game game) {
        Player nextPlayer = game.getNextPlayer();
        for (int i = 0; i < 2; i++) {
            nextPlayer.draw(game.drawCard());
        }
        game.skipNextPlayer();
        game.getDisplay().logCardDrawn(nextPlayer, 2);
    }
}
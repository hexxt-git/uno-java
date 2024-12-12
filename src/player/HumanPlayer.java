package player;

import cards.Card;
import constants.Color;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    public Card play(Card topCard) {
        // TODO: Read user input and return the card
        playCard(null);
        return null;
    }

    public Color chooseColor() {
        return Color.Red; // TODO: Read user input and return the color
    }
}

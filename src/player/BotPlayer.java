package player;

import cards.Card;
import constants.Color;

public class BotPlayer extends Player {
    public BotPlayer(String name) {
        super(name);
    }

    public Card play(Card topCard) {
        for (Card card : getHand()) {
            if (card.isValidPlay(topCard)) {
                playCard(card);
                return card;
            }
        }
        return null;
    }

    public Color chooseColor() {
        return Color.values()[(int) (Math.random() * 4)];
    }

}
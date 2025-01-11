package player;

import cards.Card;
import constants.Color;

public class BotPlayer extends Player {
    public BotPlayer(String name) {
        super(name);
    }

    public Card play(Card topCard) {
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
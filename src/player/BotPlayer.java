package player;

import cards.Card;

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

}
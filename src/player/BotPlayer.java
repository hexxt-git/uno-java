package player;

import cards.Card;

public class BotPlayer extends Player {
    public BotPlayer(String name) {
        super(name);
    }

    public Card play(Card topCard) {
        // TODO: evaluate each play with score and play the best one
        for (Card card : getHand()) {
            if (card.isValidPlay(topCard)) {
                PlayCard(card);
                return card;
            }
        }
        return null;
    }

}
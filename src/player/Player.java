package player;

import cards.Card;

public abstract class Player {
    private String name;
    private Card[] hand;

    public Player(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public String getName() {
        return name;
    }

    public abstract Card play(Card topCard);

    public final void PlayCard(Card card) {
        for (int i = 0; i < hand.length; i++) {
            if (hand[i].equals(card)) {
                hand[i] = null;
                break;
            }
        }
        throw new IllegalArgumentException("Card not found in hand");
    }
}

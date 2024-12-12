package player;

import cards.Card;
import constants.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    private String name;
    private List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public final void draw(Card card) {
        hand.add(card);
    }

    public abstract Card play(Card topCard);
    public abstract Color chooseColor();

    protected final Card playCard(Card card) {
        if (hand.remove(card)) {
            return card;
        }

        throw new IllegalArgumentException("Card not found in hand");
    }

    public String toString() {
        return String.format("%s: %s", name, hand);
    }
}

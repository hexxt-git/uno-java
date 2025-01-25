package player;

import cards.Card;
import constants.Color;

import java.util.ArrayList;
import java.util.List;

// Abstract base class for all player types (human and bot)
// Handles common player functionality like hand management

public abstract class Player {
    private String name;
    private List<Card> hand;  // Cards currently held by player

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

    // Core gameplay methods that must be implemented by subclasses
    public abstract Card play(Card topCard);      // Choose and play a card
    public abstract Color chooseColor();          // Choose color for wild cards

    // Protected helper to safely remove and return a played card
    // Used by subclasses to handle card playing logic
    protected final Card playCard(Card card) {
        if (hand.remove(card)) {
            return card;
        }
        throw new IllegalArgumentException(String.format("Card not found in hand for player %s: %s", name, card));
    }

    public String toString() {
        return String.format("%s: %s", name, hand);
    }
}

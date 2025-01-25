package game;

import cards.*;
import constants.Color;

// Specialized CardStack that represents the Uno deck
// Handles deck initialization and reshuffling

public class Deck extends CardStack {
    // Creates a standard 108-card Uno deck
    public Deck() {
        super();
        // For each color (Red, Blue, Green, Yellow):
        for (Color color : Color.values()) {
            if (color == Color.Wild) {
                // Add Wild cards:
                // - Four Blank Wild cards
                // - Four Wild Draw4 cards
                for (int i = 0; i < 4; i++) {
                    push(new Card(Color.Wild));
                    push(new DrawFourCard());
                }
            } else {
                // Add number cards:
                // - One '0' card
                // - Two of each number 1-9
                for (int i = 0; i < 10; i++) {
                    push(new NumberCard(color, i));
                    if (i != 0) {
                        push(new NumberCard(color, i));
                    }
                }
                // Add action cards:
                // Two of each action card per color
                for (int i = 0; i < 2; i++) {
                    push(new SkipCard(color));
                    push(new ReverseCard(color));
                    push(new DrawTwoCard(color));
                }
            }
        }
    }

    // Draws a card, reshuffling if needed
    // Used by GameManager for card draws
    public Card draw(CardStack placedCards) {
        if (top() == null) {
            rest(placedCards);
        }
        return pop();
    }

    public void rest(CardStack placedCards) {
        Card topCard = placedCards.pop();
        while (placedCards.top() != null) {
            Card card = placedCards.pop();
            card.resetColor();
            push(card);
        }
        placedCards.push(topCard);
    }

    @Override
    public String toString() {
        return "Deck [" + super.toString() + "]";
    }
}

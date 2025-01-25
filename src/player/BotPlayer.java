package player;

import cards.Card;
import constants.Color;

// AI player that makes simple but valid moves
// Will be enhanced with smarter strategy in the future

public class BotPlayer extends Player {
    public BotPlayer(String name) {
        super(name);
    }

    // Simulates "thinking" time and finds first valid card
    // Will be updated with strategic card selection
    @Override
    public Card play(Card topCard) {
        try {
            Thread.sleep(750);  // Artificial delay for better UX
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

    // Randomly selects a color for wild cards
    // Will be updated to choose based on hand contents
    @Override
    public Color chooseColor() {
        return Color.values()[(int) (Math.random() * 4)];
    }

}
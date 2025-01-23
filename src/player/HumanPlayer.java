package player;

import cards.Card;
import constants.Color;
import display.InputListener;

public class HumanPlayer extends Player {
    private InputListener inputListener;
    private int turnCount = 0;
    private int score =0;

    public HumanPlayer(String name, InputListener inputListener) {
        super(name);
        this.inputListener = inputListener;
    }
    public int  getTurnCount() {
        return turnCount;
    }
    public int  getScore() {
        return score;
    }

    @Override
    public Card play(Card topCard) {
        while (true) {
            char input = inputListener.getInput();

            if (input == '\n' || input == '\r') {
                return null; // Draw card
            }

            int cardIndex;
            if (Character.isDigit(input)) {
                cardIndex = Character.getNumericValue(input) - 1;
            } else {
                cardIndex = input - 'a' + 9;
            }

            if (cardIndex >= 0 && cardIndex < getHand().size()) {
                Card selectedCard = getHand().get(cardIndex);
                if (selectedCard.isValidPlay(topCard)) {
                    // score += selectedCard.getValue();
                    // turnCount++;
                    return playCard(selectedCard);

                }
            }
        }
    }

    public Color chooseColor() {
        while (true) {
            char input = inputListener.getInput();
            switch (Character.toUpperCase(input)) {
                case 'R':
                    return Color.Red;
                case 'G':
                    return Color.Green;
                case 'B':
                    return Color.Blue;
                case 'Y':
                    return Color.Yellow;
                default:
                    System.out.println("Invalid choice. Please choose again.");
            }
        }
    }
}

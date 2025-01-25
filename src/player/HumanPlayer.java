package player;

import cards.Card;
import constants.Color;
import display.InputListener;

// Represents a human player that takes input from the terminal
// Handles input validation and turn management

public class HumanPlayer extends Player {
    private InputListener inputListener;
    private int turnCount = 0;  // Tracks number of turns taken
   

    public HumanPlayer(String name, InputListener inputListener) {
        super(name);
        this.inputListener = inputListener;
    }
    public int  getTurnCount() {
        return turnCount;
    }

    // Waits for valid card selection from player
    // Returns null if player chooses to draw instead
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
                    
                     turnCount++;
                    return playCard(selectedCard);

                }
            }
        }
    }

    // Gets color choice for wild cards
    // Will be enhanced with better input validation
    @Override
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

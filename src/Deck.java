import cards.ActionCard;
import cards.Card;
import cards.NumberCard;
import constants.Color;
import constants.Action;

class Deck extends CardStack {

    public Deck() {
        super();
        for (Color color : Color.values()) {
            if (color == Color.Wild)
                continue;
            for (int i = 0; i < 10; i++) {
                push(new NumberCard(color, i));
                if (i != 0) {
                    push(new NumberCard(color, i));
                }
            }
            for (Action action : Action.values()) {
                if (action == Action.Draw4)
                    continue;
                push(new ActionCard(color, action));
                push(new ActionCard(color, action));
            }
        }
        for (int i = 0; i < 4; i++) {
            push(new Card(Color.Wild));
            push(new ActionCard(Color.Wild, Action.Draw4));
        }
    }

    public Card draw(CardStack placedCards) {
        if (top() == null) {
            resetAndKeepTop(placedCards);
        }
        return pop();
    }

    public void resetAndKeepTop(CardStack placedCards) {
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

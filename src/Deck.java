import cards.ActionCard;
import cards.Card;
import cards.NumberCard;
import constants.Color;
import constants.Action;

class Deck {
    private CardStack cards;

    public Deck() {
        this.cards = new CardStack();
        for (Color color : Color.values()) {
            if (color == Color.Wild)
                continue;
            for (int i = 0; i < 10; i++) {
                cards.push(new NumberCard(color, i));
                if (i != 0) {
                    cards.push(new NumberCard(color, i));
                }
            }
            for (Action action : Action.values()) {
                if (action == Action.Draw4)
                    continue;
                cards.push(new ActionCard(color, action));
                cards.push(new ActionCard(color, action));
            }
        }
        for (int i = 0; i < 4; i++) {
            cards.push(new Card(Color.Wild));
            cards.push(new ActionCard(Color.Wild, Action.Draw4));
        }

    }

    public Card draw(CardStack placedCards) {
        if (cards.top() == null) {
            resetAndKeepTop(placedCards);
        }
        return cards.pop();
    }

    public void reset(CardStack placedCards) {
        while (placedCards.top() != null) {
            cards.push(placedCards.pop());
        }
    }

    public void resetAndKeepTop(CardStack placedCards) {
        Card topCard = placedCards.pop();
        while (placedCards.top() != null) {
            cards.push(placedCards.pop());
        }
        placedCards.push(topCard);
    }

    public void shuffle() {
        cards.shuffle();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Deck [");
        sb.append(cards);
        sb.append("]");
        return sb.toString();
    }
}

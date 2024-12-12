import cards.Card;
import java.util.ArrayList;
import java.util.List;

public class CardStack {
    private List<Card> cards;

    public CardStack() {
        this.cards = new ArrayList<Card>();
    }

    public void push(Card card) {
        if (card != null) {
            cards.add(card);
        }
    }

    public Card top() {
        if (cards.size() > 0) {
            return cards.get(cards.size() - 1);
        }
        return null;
    }

    public Card pop() {
        if (cards.size() > 0) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    public void shuffle() {
        for (int i = 0; i < cards.size(); i++) {
            int j = (int) (Math.random() * (cards.size() - i) + i);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("CardStack (%d) [", cards.size()));
        for (int i = 0; i < cards.size(); i++) {
            sb.append(cards.get(i));
            if (i < cards.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}

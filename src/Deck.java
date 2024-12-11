import cards.ActionCard;
import cards.Card;
import cards.NumberCard;
import constants.Color;
import constants.Action;
import java.util.Random;

class Deck {
    private Card[] cards;

    public Deck() {
        this.cards = new Card[108];
        int index = 0;
        for (Color color : Color.values()) {
            if (color == Color.Wild)
                continue;
            for (int i = 0; i < 10; i++) {
                cards[index++] = new NumberCard(color, i);
                if (i != 0) {
                    cards[index++] = new NumberCard(color, i);
                }
            }
            for (Action action : Action.values()) {
                if (action == Action.Draw4)
                    continue;
                cards[index++] = new ActionCard(color, action);
                cards[index++] = new ActionCard(color, action);
            }
        }
        for (int i = 0; i < 4; i++) {
            cards[index++] = new Card(Color.Wild);
            cards[index++] = new ActionCard(Color.Wild, Action.Draw4);
        }

    }

    public void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < cards.length - 1; i++) {
            int j = rand.nextInt(cards.length - i) + i;
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Deck [");
        for (int i = 0; i < cards.length; i++) {
            sb.append(cards[i].toString());
            if (i < cards.length - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

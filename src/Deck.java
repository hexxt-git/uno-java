import Cards.Card;

class Deck {
    private Card[] cards;

    public Deck(Card[] cards) {
        this.cards = cards;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }
}

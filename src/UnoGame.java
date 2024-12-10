import Cards.Card;
import Cards.NumberCard;
import Cards.Color;

class UnoGame {
    public static void main(String[] Args) {
        Card testCard = new NumberCard(Color.Red, 10);
        System.out.println(testCard);
    }
}
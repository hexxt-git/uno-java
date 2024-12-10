import cards.ActionCard;
import cards.Card;
import cards.NumberCard;
import constants.Action;
import constants.Color;

class UnoGame {
    Player[] players;

    public static void main(String[] Args) {
        Card testCard = new NumberCard(Color.Red, 10);
        Card testCard2 = new ActionCard(Color.Blue, Action.Reverse);
        
        System.out.println(testCard);
        System.out.println(testCard2);

    }
}
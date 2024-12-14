import player.BotPlayer;
import player.Player;
import cards.NumberCard;
import constants.ChildAlignment;
import constants.Color;
import constants.TextAlignment;
import display.Component;
import display.Display;
import display.ParentComponent;
import display.Point;
import display.TextComponent;
import game.Uno;

class Main {
    public static void main(String[] Args) {
        // Player[] players = {
        // new BotPlayer("Alice"),
        // new BotPlayer("Bob"),
        // new BotPlayer("Charlie"),
        // new BotPlayer("Diana")
        // };
        // Uno game = new Uno(players);
        // game.start();

        Display display = new Display();
        display.hideCursor();
        ParentComponent body = new ParentComponent().setTitle("Uno Game");

        ParentComponent div = new ParentComponent();
        ParentComponent div1 = new ParentComponent().setPadding(1);
        TextComponent paragraph = new TextComponent(
                "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Officia, fugit et. Eligendi totam ad nemo deleniti quas! Velit quibusdam natus et inventore! Fugiat reiciendis voluptate reprehenderit voluptatem dolores. Et, sit!\nHello world");
        div1.addChild(paragraph);
        ParentComponent div2 = new ParentComponent();
        ParentComponent sub = new ParentComponent();
        ParentComponent sub2 = new ParentComponent();
        sub2.addChild(new TextComponent("test test test..."));
        sub.addChild(
                new TextComponent("\ntext here\nshould align\nto the\ncenter").setTextAlignment(TextAlignment.Center));
        div2.setPadding(1);
        div2.setAlignment(ChildAlignment.Horizontal);
        div2.addChild(sub);
        div2.addChild(sub2);
        body.addChild(div);
        body.addChild(div1);
        body.addChild(div2);

        display.clear();
        body.fullScreen(display);
        body.render(display);

        Point dimensions = display.getDimensions();
        while (true) {
            Point newDimensions = display.getDimensions();
            if (!dimensions.equals(newDimensions)) {
                body.fullScreen(display);
                display.clear();
                body.render(display);
                dimensions = newDimensions;
            }
        }

    }
}

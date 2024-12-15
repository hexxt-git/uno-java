import player.BotPlayer;
import player.Player;
import cards.NumberCard;
import constants.BorderStyle;
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
        ParentComponent body = new ParentComponent().setTitle("Uno Game").setBorderStyle(BorderStyle.Double);

        ParentComponent div1 = new ParentComponent()
                .addChild(new ParentComponent().addChild(new TextComponent("Div 1, 1"))
                        .setBorderStyle(BorderStyle.None))
                .addChild(new ParentComponent().addChild(new TextComponent("Div 1, 2"))
                        .setBorderStyle(BorderStyle.None, BorderStyle.None, BorderStyle.None, BorderStyle.Single)
                        .setPadding(1, 1, 1, 2))
                .setPadding(0)
                .setBorderStyle(BorderStyle.None)
                .setAlignment(ChildAlignment.Horizontal);
        div1.setBiasSelf(2);

        ParentComponent div2 = new ParentComponent().addChild(new TextComponent(
                "lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."))
                .setPadding(4, 2)
                .setBorderStyle(BorderStyle.Rounded, BorderStyle.None, BorderStyle.Rounded, BorderStyle.Rounded)
                .setAlignment(ChildAlignment.Vertical);

        ParentComponent div3 = new ParentComponent().addChild(new TextComponent(
                "\nDiv 3, text align center, text here should align to the center...\nnew lines are supported")
                .setTextAlignment(TextAlignment.Center));

        body.addChild(div1).addChild(div2).addChild(div3);

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

import player.BotPlayer;
import player.Player;
import cards.NumberCard;
import constants.BorderStyle;
import constants.ChildAlignment;
import constants.Color;
import constants.ConsoleColor;
import constants.TextAlignment;
import display.Component;
import display.Display;
import display.FloatComponent;
import display.InputListener;
import display.InputListener;
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
        InputListener input_reader = InputListener.getInstance();

        Display display = new Display();
        display.hideCursor();
        ParentComponent body = new ParentComponent().setTitle("Uno Game").setBorderStyle(BorderStyle.Double);
        body.setMaxH(40);

        Component window = new FloatComponent().setPosition(-4, 16, 25,
                8).setBorderStyle(BorderStyle.Dashed)
                .setTitle("Window")
                .addChild(new TextComponent(
                        "this window should be floating in the right, 16 row down. this should cover the text under it"))
                .setClear(true)
                .setPadding(2)
                .setBorderColor(ConsoleColor.GREEN_BOLD);

        ParentComponent div1 = new ParentComponent()
                .addChild(
                        new ParentComponent()
                                .addChild(new TextComponent("Div 1.1: has border on the right only"))
                                .setBorderStyle(BorderStyle.None, BorderStyle.Single, BorderStyle.None,
                                        BorderStyle.None)
                                .setBorderColor(ConsoleColor.YELLOW)
                                .setBiasSelf(2))
                .addChild(new ParentComponent().addChild(new TextComponent(
                        "Div 1.2\n\nDiv 1 has 2 children, no border and no padding. it has self bias 2. its children are biased 2/5"))
                        .setPadding(2, 1).setBorderStyle(BorderStyle.None).setBiasSelf(5))
                .setPadding(0)
                .setBorderStyle(BorderStyle.None)
                .setAlignment(ChildAlignment.Horizontal)
                .setSpace(0);
        div1.setBiasSelf(2);

        ParentComponent div2 = new ParentComponent().addChild(new TextComponent(
                "lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\nthis component has: 4 padding Horizontal, 2 padding veritcal, double border Horizontal and rounded border Vertical"))
                .setPadding(4, 2)
                .setBorderStyle(BorderStyle.Double, BorderStyle.Rounded)
                .setAlignment(ChildAlignment.Vertical);

        ParentComponent div3 = new ParentComponent().setClear(true);

        TextComponent paragraph = new TextComponent("").setTextAlignment(TextAlignment.Center);
        div3.addChild(paragraph);

        body.addChild(div1).addChild(div2).addChild(div3);
        body.addChild(window);

        display.clear();
        body.fullScreen(display);
        body.render(display);

        Point[] dimensions = { display.getDimensions() };
        Thread inputThread = new Thread(() -> {
            while (true) {
                char c = input_reader.getInput();
                if (c == 127) {
                    String text = paragraph.getInnerText();
                    if (!text.isEmpty()) {
                        paragraph.setInnerText(text.substring(0, text.length() - 1));
                    }
                } else {
                    paragraph.setInnerText(paragraph.getInnerText() + c);
                }
                // display.clear();
                div3.render(display);
            }
        });
        inputThread.start();

        Thread displayThread = new Thread(() -> {
            while (true) {
                Point newDimensions = display.getDimensions();
                if (!newDimensions.equals(dimensions[0])) {
                    body.fullScreen(display);
                    display.clear();
                    body.render(display);
                    dimensions[0] = newDimensions;
                }
            }
        });
        displayThread.start();

        // input_reader.close();
    }
}

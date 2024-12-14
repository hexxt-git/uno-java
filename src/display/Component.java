package display;

public abstract class Component {
    // todo: rename to row and col and setup setters and getters
    protected int x = 0;
    protected int y = 0;
    protected int w = 0;
    protected int h = 0;

    public abstract void render(Display display);

    public void fullScreen(Display display) {
        Point dimensions = display.getDimensions();
        x = 1;
        y = 1;
        w = dimensions.x;
        h = dimensions.y;
    }
}

package display;

public abstract class Component {
    // todo: rename to row and col and setup setters and getters
    protected int x = 0;
    protected int y = 0;
    protected int w = 0;
    protected int h = 0;
    private int maxW = -1;
    private int maxH = -1;
    private int biasSelf = 1;

    public int getMaxW() {
        return maxW;
    }

    public Component setMaxW(int maxW) {
        this.maxW = maxW;
        return this;
    }

    public int getMaxH() {
        return maxH;
    }

    public Component setMaxH(int maxH) {
        this.maxH = maxH;
        return this;
    }

    public int getBiasSelf() {
        return biasSelf;
    }

    public Component setBiasSelf(int biasSelf) {
        this.biasSelf = biasSelf;
        return this;
    }

    public abstract void render(Display display);

    public void fullScreen(Display display) {
        Point dimensions = display.getDimensions();
        x = 1;
        y = 1;
        if (maxW != -1) {
            w = Math.min(dimensions.x, maxW);
        } else {
            w = dimensions.x;
        }
        if (maxH != -1) {
            h = Math.min(dimensions.y - 1, maxH);
        } else {
            h = dimensions.y - 1;
        }
        
    }
}

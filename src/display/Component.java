package display;

// Base class for all UI components in the display system
// Handles positioning, dimensions and size constraints

public abstract class Component {
    // todo: rename to row and col and setup setters and getters
    protected int x = 0;  // Column position 
    protected int y = 0;  // Row position
    protected int w = 0;  // Width
    protected int h = 0;  // Height
    
    // Maximum dimensions - used to prevent components from growing too large
    private int maxW = -1;
    private int maxH = -1;

    // Used by layout system to determine relative sizes
    // Will be used for responsive layouts
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

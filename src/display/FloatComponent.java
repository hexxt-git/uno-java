package display;

public class FloatComponent extends ParentComponent {
    private int targetX = 0;
    private int targetY = 0;
    private int targetW = 0;
    private int targetH = 0;

    public void render(Display display) {
        if (targetW == 0 || targetH == 0) {
            return;
        }
        int x = targetX;
        int y = targetY;
        int w = targetW;
        int h = targetH;
        int displayWidth = display.getDimensions().x;
        int displayHeight = display.getDimensions().y;

        if (x < 0) {
            x = displayWidth + x - w;
        }
        if (y < 0) {
            y = displayHeight + y - h;
        }
        if (x + w > displayWidth) {
            x = displayWidth - w;
        }
        if (y + h > displayHeight) {
            y = displayHeight - h;
        }
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        super.render(display);
    }

    public FloatComponent setPosition(int targetX, int targetY, int targetW, int targetH) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetW = targetW;
        this.targetH = targetH;
        return this;
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public int getTargetW() {
        return targetW;
    }

    public int getTargetH() {
        return targetH;
    }

    public FloatComponent setTargetX(int targetX) {
        this.targetX = targetX;
        return this;
    }

    public FloatComponent setTargetY(int targetY) {
        this.targetY = targetY;
        return this;
    }

    public FloatComponent setTargetW(int targetW) {
        this.targetW = targetW;
        return this;
    }

    public FloatComponent setTargetH(int targetH) {
        this.targetH = targetH;
        return this;
    }
}

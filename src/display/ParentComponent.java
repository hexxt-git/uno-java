package display;

import java.util.ArrayList;
import java.util.List;

import constants.BorderStyle;
import constants.ChildAlignment;

public class ParentComponent extends Component {
    private List<Component> children = new ArrayList<>();
    private ChildAlignment alignment = ChildAlignment.Vertical;
    private BorderStyle[] borderStyles = { BorderStyle.Rounded, BorderStyle.Rounded, BorderStyle.Rounded,
            BorderStyle.Rounded }; // top, right, bottom, left
    private int[] padding = { 1, 1, 1, 1 }; // top, right, bottom, left
    private int space = 1;
    private String title = "";

    private void border(Display display) {

        display.moveCursor(x, y);
        if (title.length() == 0 || title == null) {
            display.print(borderStyles[0].topHorizontal.repeat(w));
        } else {
            display.print(
                    borderStyles[0].topHorizontal.repeat(Math.max(0,
                            (int) Math.floor((w - title.length()) / 2.0) -
                                    borderStyles[0].topLeft.length())));
            display.print(borderStyles[0].titleLeft);
            display.print(title);
            display.print(borderStyles[0].titleRight);
            display.print(
                    borderStyles[0].topHorizontal.repeat(Math.max(0,
                            (int) Math.ceil((w - title.length()) / 2.0 - borderStyles[0].titleRight.length()))));
        }
        display.moveCursor(x, y + h);
        display.print(borderStyles[2].bottomHorizontal.repeat(w));
        for (int y1 = 0; y1 <= h; y1++) {
            display.moveCursor(x, y + y1);
            display.print(borderStyles[3].leftVertical);
            display.moveCursor(x + w - 1, y + y1);
            display.print(borderStyles[1].rightVertical);
        }
        if (borderStyles[0] != BorderStyle.None && borderStyles[3] != BorderStyle.None) {
            display.moveCursor(x, y);
            display.print(borderStyles[0].topLeft);
        }
        if (borderStyles[0] != BorderStyle.None && borderStyles[1] != BorderStyle.None) {
            display.moveCursor(x + w - borderStyles[0].topRight.length(), y);
            display.print(borderStyles[0].topRight);
        }
        if (borderStyles[2] != BorderStyle.None && borderStyles[3] != BorderStyle.None) {
            display.moveCursor(x, y + h);
            display.print(borderStyles[2].bottomLeft);
        }
        if (borderStyles[2] != BorderStyle.None && borderStyles[1] != BorderStyle.None) {
            display.moveCursor(x + w - borderStyles[2].bottomRight.length(), y + h);
            display.print(borderStyles[2].bottomRight);
        }
    }

    private void alignVertically(List<Component> children) {
        int offset_y = 0;
        int totalBias = children.stream().mapToInt(Component::getBiasSelf).sum();
        int totalSpace = space * (children.size() - 1);
        int availableHeight = h - padding[0] - padding[2] - totalSpace;
        for (int i = 0; i < children.size(); i++) {
            Component child = children.get(i);
            int biasSelf = child.getBiasSelf();

            child.x = x + padding[3];
            child.w = w - padding[1] - padding[3];

            child.h = availableHeight * biasSelf / totalBias;
            if (i < availableHeight * biasSelf % totalBias)
                child.h += 1;

            child.y = y + padding[0] + offset_y;
            offset_y += child.h + space;
        }
    }

    private void alignHorizontally(List<Component> children) {
        int offset_x = 0;
        int totalBias = children.stream().mapToInt(Component::getBiasSelf).sum();
        int totalSpace = space * (children.size() - 1);
        int availableWidth = w - padding[1] - padding[3] - totalSpace;
        for (int i = 0; i < children.size(); i++) {
            Component child = children.get(i);
            int biasSelf = child.getBiasSelf();

            child.y = y + padding[0];
            child.h = h - padding[0] - padding[2];

            child.w = availableWidth * biasSelf / totalBias;
            if (i < availableWidth * biasSelf % totalBias)
                child.w += 1;

            child.x = x + padding[3] + offset_x;
            offset_x += child.w + space;
        }
    }

    public void render(Display display) {
        border(display);
        switch (alignment) {
            case Vertical:
                alignVertically(children);
                break;
            case Horizontal:
                alignHorizontally(children);
                break;
        }
        for (Component child : children) {
            child.render(display);
        }
    }

    public List<Component> getChildren() {
        return children;
    }

    public ParentComponent setChildren(List<Component> children) {
        this.children = children;
        return this;
    }

    public ParentComponent addChild(Component child) {
        children.add(child);
        return this;
    }

    public ChildAlignment getAlignment() {
        return alignment;
    }

    public ParentComponent setAlignment(ChildAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    public ParentComponent setPadding(int padding) {
        this.padding = new int[] { padding, padding, padding, padding };
        return this;
    }

    public ParentComponent setPadding(int horizontal, int vertical) {
        this.padding = new int[] { vertical, horizontal, vertical, horizontal };
        return this;
    }

    public ParentComponent setPadding(int top, int right, int bottom, int left) {
        this.padding = new int[] { top, right, bottom, left };
        return this;
    }

    public int[] getPadding() {
        return padding;
    }

    public BorderStyle[] getBorderStyle() {
        return borderStyles;
    }

    public ParentComponent setBorderStyle(BorderStyle border_style) {
        this.borderStyles = new BorderStyle[] { border_style, border_style, border_style, border_style };
        return this;
    }

    public ParentComponent setBorderStyle(BorderStyle horizontal, BorderStyle vertical) {
        this.borderStyles = new BorderStyle[] { vertical, horizontal, vertical, horizontal };
        return this;
    }

    public ParentComponent setBorderStyle(BorderStyle top, BorderStyle right, BorderStyle bottom, BorderStyle left) {
        this.borderStyles = new BorderStyle[] { top, right, bottom, left };
        return this;
    }

    public int getSpace() {
        return space;
    }

    public ParentComponent setSpace(int space) {
        this.space = space;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ParentComponent setTitle(String title) {
        this.title = title;
        return this;
    }
}

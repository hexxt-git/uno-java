package display;

import java.util.ArrayList;
import java.util.List;
import constants.ChildAlignment;

public class ParentComponent extends Component {
    private List<Component> children = new ArrayList<>();
    private ChildAlignment alignment = ChildAlignment.Vertical;
    private boolean border = true;
    private int padding = 1;
    private int space = 1;
    private String title = "";

    private void border(Display display) {
        display.moveCursor(x, y);
        display.print("╔");
        if (title.length() == 0 || title == null) {
            display.print("═".repeat(Math.max(0, w - 2)));
        } else {
            display.print("═".repeat(Math.max(0, (int) Math.floor((w - title.length() - 6) / 2.0))));
            display.print("╣ ");
            display.print(title);
            display.print(" ╠");
            display.print("═".repeat(Math.max(0, (int) Math.ceil((w - title.length() - 6) / 2.0))));
        }
        display.print("╗");
        for (int y1 = 1; y1 < h; y1++) {
            display.moveCursor(x, y + y1);
            display.print("║");
            display.moveCursor(x + w - 1, y + y1);
            display.print("║");
        }
        display.moveCursor(x, y + h);
        display.print("╚");
        display.print("═".repeat(Math.max(0, w - 2)));
        display.print("╝");
    }

    private void alignVertically(List<Component> children) {
        int offset_y = 0;
        for (int i = 0; i < children.size(); i++) {
            Component child = children.get(i);

            child.x = x + padding;
            child.w = w - padding * 2;

            child.h = (h - padding * 2) / children.size() - space;
            if (i <= (h - padding * 2) % children.size())
                child.h += 1;

            child.y = y + padding + offset_y;
            offset_y += child.h + space;
        }
    }

    private void alignHorizontally(List<Component> children) {
        int offset_x = 0;
        for (int i = 0; i < children.size(); i++) {
            Component child = children.get(i);

            child.y = y + padding;
            child.h = h - padding * 2;

            child.w = (w - padding * 2) / children.size() - space;
            if (i <= (w - padding * 2) % children.size())
                child.w += 1;

            child.x = x + padding + offset_x;
            offset_x += child.w + space;

        }
    }

    public void render(Display display) {
        if (border)
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

    public Component addChild(Component child) {
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

    public boolean isBorder() {
        return border;
    }

    public ParentComponent setBorder(boolean border) {
        this.border = border;
        return this;
    }

    public int getPadding() {
        return padding;
    }

    public ParentComponent setPadding(int padding) {
        this.padding = padding;
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

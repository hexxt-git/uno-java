package display;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

import constants.ConsoleColor;

public class Display {
    private static final Stack<ConsoleColor> colorStack = new Stack<>();
    private static ConsoleColor currentColor = ConsoleColor.RESET;

    public void print(Object obj) {
        System.out.print(obj);
    }

    public void print(String format, Object... args) {
        System.out.print(String.format(format, args));
    }

    public void flush() {
        // documentation said we need this every time
        System.out.flush();
    }

    public void clear() {
        print("\033[H\033[2J");
        flush();
    }

    public void hideCursor() {
        print("\033[?25l");
        flush();
    }

    public void showCursor() {
        print("\033[?25h");
        flush();
    }

    public void moveCursor(int x, int y) {
        print(String.format("\033[%d;%dH", y, x));
        flush();
    }

    public void setColor(ConsoleColor color) {
        if (color == ConsoleColor.RESET)
            return;
        colorStack.push(currentColor); // Save the current color
        currentColor = color;
        print(color.code);
    }

    public void revertColor() {
        if (!colorStack.isEmpty()) {
            currentColor = colorStack.pop();
            print(currentColor.code);
        } else {
            resetColor();
        }
    }

    public void resetColor() {
        currentColor = ConsoleColor.RESET;
        print(ConsoleColor.RESET.code);
    }

    public Point getDimensions() {
        Point dimensions = new Point(80, 24); // Default fallback values

        try {

            String[] cmd = { "/bin/sh", "-c", "stty size </dev/tty" };
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();

            if (output != null) {
                String[] sizes = output.split(" ");
                if (sizes.length == 2) {
                    dimensions.y = Integer.parseInt(sizes[0]);
                    dimensions.x = Integer.parseInt(sizes[1]);
                }
            }
        } catch (Exception e) {

        }

        return dimensions;
    }
}
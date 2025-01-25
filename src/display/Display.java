package display;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

import constants.ConsoleColor;

// Handles low-level console display operations
// Manages cursor movement, colors, and terminal dimensions

public class Display {
    // Stack to track nested color states
    private static final Stack<ConsoleColor> colorStack = new Stack<>();
    private static ConsoleColor currentColor = ConsoleColor.RESET;

    // Basic print methods for text output
    public void print(Object obj) {
        System.out.print(obj);
    }

    public void print(String format, Object... args) {
        System.out.print(String.format(format, args));
    }

    // Ensures output is immediately displayed
    // Required for proper terminal rendering
    public void flush() {
        System.out.flush();
    }

    // Screen clearing and cursor management methods
    public void clear() {
        print("\033[H\033[2J"); // ANSI escape code to clear screen
        flush();
    }

    public void hideCursor() {
        print("\033[?25l"); // ANSI escape code to hide cursor
        flush();
    }

    public void showCursor() {
        print("\033[?25h"); // ANSI escape code to show cursor
        flush();
    }

    // Moves cursor to specific position
    // Used for precise text placement
    public void moveCursor(int x, int y) {
        print(String.format("\033[%d;%dH", y, x));
        flush();
    }

    // Color management methods for nested color regions
    public void setColor(ConsoleColor color) {
        if (color == ConsoleColor.RESET)
            return;
        colorStack.push(currentColor);
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

    // Gets terminal dimensions using system commands
    // Will be enhanced to handle terminal resizing events
    public Point getDimensions() {
        Point dimensions = new Point(80, 24); // Default fallback values

        try {
            // Get terminal size using stty command
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
            // Silently fall back to default dimensions
        }

        return dimensions;
    }
}
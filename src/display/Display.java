package display;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Vector2D {
    int x;
    int y;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}

public class Display {
    List<String> displayValues = new ArrayList<>();
    Vector2D prevDimensions = new Vector2D(0, 0);

    protected void print(Object obj) {
        System.out.print(obj);
    }

    protected void print(String format, Object... args) {
        System.out.print(String.format(format, args));
    }

    protected void flush() {
        // documentation said we need this every time
        System.out.flush();
    }

    public void write(String value) {
        String[] lines = value.split("\n");
        if (displayValues.size() == 0) {
            displayValues.add("");
        }
        for (String line : lines) {
            displayValues.set(displayValues.size() - 1, displayValues.get(displayValues.size() - 1) + line);
        }
    }

    public void writeln(String value) {
        this.write(value);
        displayValues.add("");
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

    public void moveCursor(int row, int col) {
        print(String.format("\033[%d;%dH", col, row));
        flush();
    }

    public Vector2D getDimensions() {
        Vector2D dimensions = new Vector2D(80, 24); // Default fallback values

        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("unix") || os.contains("linux") || os.contains("mac") || os.contains("bsd")) {
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
            }
        } catch (Exception e) {

        }

        return dimensions;
    }

    public void outline(String title, Vector2D dimensions) {
        moveCursor(1, 1);
        print("╔");
        print("═".repeat(Math.max(0, (dimensions.x - title.length()) / 2 - 3)));
        if (title.length() == 0 || title == null) {
            print("═".repeat(4));
        } else {
            print("╣ ");
            print(title);
            print(" ╠");
        }
        print("═".repeat(Math.max(0, (int) Math.ceil((dimensions.x - title.length()) / 2.0) - 3)));
        print("╗");
        for (int y = 2; y < Math.min(dimensions.y, 25); y++) {
            moveCursor(0, y);
            print("║");
            moveCursor(dimensions.x, y);
            print("║");
        }
        moveCursor(0, Math.min(dimensions.y, 25));
        print("╚");
        print("═".repeat(Math.max(0, dimensions.x - 2)));
        print("╝");
    }

    public void out() {
        this.hideCursor();
        this.clear();
        Vector2D dimensions = getDimensions();
        this.outline("Uno Game", dimensions);

        int padding = 3;
        int col = padding - 1;
        int maxRowWidth = dimensions.x - padding * 2;
        for (String displayValue : displayValues) {
            String[] words = displayValue.split(" ");
            StringBuilder line = new StringBuilder();
            int currentRowWidth = 0;
            int i = 0;
            while (i < words.length) {
                // skip ansi special characters
                int wordLength = words[i].replaceAll("\\033\\[[0-9;]*[a-zA-Z]", "").length();
                if (currentRowWidth + wordLength + 1 > maxRowWidth) {
                    moveCursor(padding, col++);
                    print(line.toString());
                    line = new StringBuilder();
                    currentRowWidth = 0;
                }
                if (line.length() > 0) {
                    line.append(" ");
                    currentRowWidth++;
                }
                line.append(words[i]);
                currentRowWidth += wordLength;
                i++;
            }
            if (line.length() > 0) {
                moveCursor(padding, col++);
                print(line.toString());
            }
        }
        moveCursor(0, Math.min(dimensions.y, 25) + 1);
        // print(displayValues);
    }

    public void readInput() {
        // turn this into some sort of event listener
        new Thread(() -> {
            try {
                // Set terminal to raw mode
                String[] cmd = { "/bin/sh", "-c", "stty raw -echo < /dev/tty" };
                Runtime.getRuntime().exec(cmd).waitFor();

                // Read input one character at a time
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                int ch;
                while (true) {
                    if ((ch = reader.read()) == -1)
                        continue;
                    if (ch == 3) {
                        System.exit(0); // Exit on Ctrl+C
                    }
                    if ((char) ch == '\n' || (char) ch == '\r') {
                        writeln("");
                    } else {
                        write(Character.toString((char) ch));
                    }
                    out();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // Restore terminal to normal mode
                    String[] cmd = { "/bin/sh", "-c", "stty sane < /dev/tty" };
                    Runtime.getRuntime().exec(cmd).waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void tick() {
        Vector2D dimensions = getDimensions();
        if (prevDimensions.x != dimensions.x || prevDimensions.y != dimensions.y) {
            this.out();
            prevDimensions = dimensions;
        }
    }
}
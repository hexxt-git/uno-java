package display;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Display {
    // boolean reading = false;

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

    public Point getDimensions() {
        Point dimensions = new Point(80, 24); // Default fallback values

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

    /*
     * 
     * public void readInput() {
     * 
     * if (reading)
     * return;
     * else
     * reading = true;
     * // todo: turn this into some sort of event listener
     * // todo: use proper singleton class for this
     * new Thread(() -> {
     * try {
     * // Set terminal to raw mode
     * String[] cmd = { "/bin/sh", "-c", "stty raw -echo < /dev/tty" };
     * Runtime.getRuntime().exec(cmd).waitFor();
     * 
     * // Read input one character at a time
     * BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
     * int ch;
     * while (true) {
     * if ((ch = reader.read()) == -1)
     * continue;
     * if (ch == 3) {
     * System.exit(0); // Ctrl+C
     * }
     * if ((char) ch == '\n' || (char) ch == '\r') {
     * writeln(""); // new line
     * } else {
     * write(Character.toString((char) ch));
     * }
     * out();
     * }
     * } catch (Exception e) {
     * e.printStackTrace();
     * } finally {
     * try {
     * // Restore terminal to normal mode
     * String[] cmd = { "/bin/sh", "-c", "stty sane < /dev/tty" };
     * Runtime.getRuntime().exec(cmd).waitFor();
     * } catch (Exception e) {
     * e.printStackTrace();
     * }
     * }
     * }).start();
     * }
     */
}
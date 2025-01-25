package display;

import java.io.BufferedReader;
import java.io.InputStreamReader;

// Singleton class that handles raw terminal input
// Provides both character and string-based input methods

public class InputListener {
    private static InputListener instance = null;
    private static boolean reading = false;
    private char inputBuffer = 0;

    // Sets terminal to raw mode for immediate character input
    // Used for real-time game controls
    private InputListener() {
    }

    public static synchronized InputListener getInstance() {
        if (instance == null) {
            instance = new InputListener();
            instance.start();
        }
        return instance;
    }

    // Blocks until a character is available and returns it
    // Used for single-key game controls
    public synchronized char getInput() {
        while (inputBuffer == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        char ch = inputBuffer;
        inputBuffer = 0;
        return ch;
    }

    // Blocks until a full line of text is entered
    // Used for player names and other text input
    public synchronized String getString() {
        StringBuilder sb = new StringBuilder();
        char ch = 0;
        while (ch != '\r') {
            while (inputBuffer == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ch = inputBuffer;
            inputBuffer = 0;
            if (ch == '\r') {
                break;
            }
            sb.append(ch);
        }

        return sb.toString();
    }

    // Thread-safe method to store incoming character
    // Used by input thread to pass characters to main thread
    private synchronized void input(char ch) {
        inputBuffer = ch;
        notifyAll();
    }

    // Sets terminal to raw mode for immediate character input
    // Used for real-time game controls
    private void start() {
        synchronized (InputListener.class) {
            if (reading) {
                return;
            }
            reading = true;
        }

        new Thread(() -> {
            try {
                // Set terminal to raw mode
                String[] cmd = { "/bin/sh", "-c", "stty raw -echo < /dev/tty" };
                Runtime.getRuntime().exec(cmd).waitFor();

                // Read input one character at a time
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                int ch;
                while (reading) {
                    if ((ch = reader.read()) == -1)
                        continue;
                    if (ch == 3) {
                        System.out.print("\033[H\033[2J");
                        System.out.print("\033[H\033[2J");
                        System.out.println("Exiting...");
                        System.exit(0); // Ctrl+C
                    }
                    input((char) ch);
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

    // Restores terminal to normal mode on shutdown
    // Will be used for clean program termination
    public void close() {
        synchronized (InputListener.class) {
            reading = false;
        }
    }
}

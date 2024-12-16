package display;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InputListener {
    private static InputListener instance = null;
    private static boolean reading = false;
    private char inputBuffer = 0;

    private InputListener() {
    }

    public static synchronized InputListener getInstance() {
        if (instance == null) {
            instance = new InputListener();
            instance.start();
        }
        return instance;
    }

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

    private synchronized void input(char ch) {
        inputBuffer = ch;
        notifyAll();
    }

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

    public void close() {
        synchronized (InputListener.class) {
            reading = false;
        }
    }
}

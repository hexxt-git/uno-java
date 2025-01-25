package constants;

// Defines card colors and their ANSI terminal representations
// Used throughout the game for card display
public enum Color {
    Red("\033[0;31m"),
    Green("\033[0;32m"),
    Blue("\033[0;34m"),
    Yellow("\033[0;33m"),
    Wild("\033[0;35m");

    public final String consoleColor;

    Color(String consoleColor) {
        this.consoleColor = consoleColor;
    }
}

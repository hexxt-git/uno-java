package constants;

// Comprehensive set of ANSI terminal color codes
// Used for all colored text output in the game
public enum ConsoleColor {
    RESET("\033[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m"),
    GRAY("\u001B[90m"),
    BLACK_BOLD("\u001B[1;30m"),
    RED_BOLD("\u001B[1;31m"),
    GREEN_BOLD("\u001B[1;32m"),
    YELLOW_BOLD("\u001B[1;33m"),
    BLUE_BOLD("\u001B[1;34m"),
    PURPLE_BOLD("\u001B[1;35m"),
    CYAN_BOLD("\u001B[1;36m"),
    WHITE_BOLD("\u001B[1;37m"),
    GRAY_BOLD("\u001B[1;90m"),
    BLACK_BACKGROUND("\u001B[40m"),
    RED_BACKGROUND("\u001B[41m"),
    GREEN_BACKGROUND("\u001B[42m"),
    YELLOW_BACKGROUND("\u001B[43m"),
    BLUE_BACKGROUND("\u001B[44m"),
    PURPLE_BACKGROUND("\u001B[45m"),
    CYAN_BACKGROUND("\u001B[46m"),
    WHITE_BACKGROUND("\u001B[47m"),
    GRAY_BACKGROUND("\u001B[100m");

    public final String code;

    ConsoleColor(String code) {
        this.code = code;
    }
}

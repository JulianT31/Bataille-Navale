package tools;

public enum Couleur {
    ANSI_RESET("\u001B[0m"),
    ANSI_BLACK("\u001B[30m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[32m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_CYAN("\u001B[36m"),
    ANSI_WHITE("\u001B[37m"),
    ANSI_BRIGHT_BLACK("\u001b[30;1m"),
    ANSI_BRIGHT_RED("\u001b[31;1m"),
    ANSI_BRIGHT_GREEN("\u001b[32;1m"),
    ANSI_BRIGHT_YELLOW("\u001b[33;1m"),
    ANSI_BRIGHT_BLUE("\u001b[34;1m"),
    ANSI_BRIGHT_MAGENTA("\u001b[35;1m"),
    ANSI_BRIGHT_CYAN("\u001b[36;1m"),
    ANSI_BRIGHT_WHITE("\u001b[37;1m");

    private String value;

    Couleur(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

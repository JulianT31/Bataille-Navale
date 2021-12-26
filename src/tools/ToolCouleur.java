package tools;

public class ToolCouleur {
    public static void printCouleur(String message, Couleur couleur) {
        System.out.print(couleur.getValue() + message + Couleur.ANSI_RESET.getValue());
    }

    public static void printCouleur(String message) {
        System.out.print(message);
    }
}

package tools;

/**
 * Classe ToolCouleur qui est une toolbos pour l'affichage des couleurs dans la console
 * @author Julian TRANI 1A SRI
 */
public class ToolCouleur {
    /**
     * Permet d'afficher un message en couleur dans la console
     * @param message le string à afficher
     * @param couleur la couleur ANSI
     */
    public static void printCouleur(String message, Couleur couleur) {
        System.out.print(couleur.getValue() + message + Couleur.ANSI_RESET.getValue());
    }

    public static void printCouleur(String message) {
        System.out.print(message);
    }
}

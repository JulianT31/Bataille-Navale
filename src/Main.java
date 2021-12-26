import exceptions.JeuException;
import manager.Jeu;

public class Main {
    public static void main(String[] args) {
        try {
            Jeu jeu = new Jeu(4);
            jeu.jouer();
        } catch (JeuException e) {
            e.printStackTrace();
        }
    }
}

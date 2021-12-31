package joueur;

/**
 * Interface Ia qui hérite aussi de Joueur et qui implemente la méthode tirageAleatoire()
 * afin de faire jouer l'ordinateur de facon aléatoire
 */
public interface Ia extends Joueur {
    void tirageAleatoire();
}

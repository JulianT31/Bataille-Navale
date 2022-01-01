package plateau;

import joueur.Joueur;
import tools.Couleur;
import tools.ToolCouleur;

/**
 * Classe Plateau
 *
 * @author Julian TRANI 1A SRI
 */
public class Plateau {
    // Attributs
    private int taille;
    private CasePlateau[][] matricePlateau;

    // Constructeurs
    public Plateau(int myTaille) {
        this.taille = myTaille;
        this.matricePlateau = new CasePlateau[myTaille][myTaille];

        for (int i = 0; i < myTaille; i++) {
            for (int j = 0; j < myTaille; j++) {
                this.matricePlateau[i][j] = new CasePlateau();
            }
        }
    }

    // Getter
    public int getMyTaille() {
        return taille;
    }

    /**
     * Retourne la CasePlateau à partir des coordonnées si elle existe
     *
     * @param x  : coordonées x de la grille
     * @param y: coordonées y de la grille
     * @return la CasePlateau si les coords sont valides sinon null
     */
    public CasePlateau getCasePlateau(int x, int y) {
        if (x >= 0 && y >= 0 && x < this.taille && y < this.taille) {
            return this.matricePlateau[x][y];
        }
        return null;
    }

    /**
     * Affichage le plateau et les bateaux du joueur qui joue en couleur (si le paramètre n'est pas null)
     *
     * @param joueur : (NULLABLE !) le joueur qui est entrain de jouer
     */
    public void affiche(Joueur joueur) {
        System.out.print("      ");
        // Affichage des coordonnées
        for (int i = 0; i < this.taille; i++) {
            System.out.print(i + "   ");
        }

        // Affichage de la bordure haute ("tirets")
        System.out.print("\n   ");
        for (int i = 0; i < (this.taille * 2) * 2 / 3 + 1; i++) {
            System.out.print(" = ");
        }

        System.out.print("\n");
        for (int i = 0; i < this.taille; i++) { // COLONNE
            // Affichage selon la coordonnée pour avoir la même taille
            if (i + 1 < 10) {
                System.out.print(" " + i);
                System.out.print(" | ");
            } else {
                System.out.print(i);
                System.out.print(" | ");
            }

            for (int j = 0; j < this.taille; j++) { // LIGNE
                CasePlateau caseActuel = this.matricePlateau[i][j];

                // Si la case ne contient pas de navire
                if (caseActuel.toString().contains("~")) {
                    ToolCouleur.printCouleur(caseActuel + " ", Couleur.ANSI_BLUE);
                } else {
                    // Si on a passé  une équipe en param
                    if (joueur != null) {
                        caseActuel.affiche(joueur);
                        System.out.print(" ");
                    } else {
                        caseActuel.affiche(null);
                        System.out.print(" ");
                    }
                }

            }
            System.out.print("|\n");
        }

        // Affichage de la bordure basse
        System.out.print("   ");
        for (int i = 0; i < (this.taille * 2) * 2 / 3 + 1; i++) {
            System.out.print(" = ");
        }
        System.out.println();
    }
}

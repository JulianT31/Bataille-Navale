package plateau;

import joueur.Joueur;
import tools.Couleur;
import tools.ToolCouleur;

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

    public CasePlateau getCasePlateau(int x, int y) {
        if (x >= 0 && y >= 0 && x < this.taille && y < this.taille) {
            return this.matricePlateau[x][y];
        }
        return null;
    }

    public int nombreBateauPlateau() {
        int cpt = 0;
        for (int i = 0; i < this.taille; i++) {
            for (int j = 0; j < this.taille; j++) {
                if (!this.getCasePlateau(i, j).toString().contains("~")) {
                    cpt++;
                }
            }
        }
        return cpt;
    }

    public void affiche(Joueur joueur) {
//        System.out.println("================");
//        System.out.println("  Plateau : ");
//        System.out.println("================");

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
        for (int i = 0; i < this.taille; i++) {
            // Affichage selon la coordonnée pour avoir la même taille
            if (i + 1 < 10) {
                System.out.print(" " + i);
//                ToolCouleur.printCouleur(" " + i, Couleur.ANSI_WHITE);
                System.out.print(" | ");
            } else {
                System.out.print(i);
//                ToolCouleur.printCouleur(String.valueOf(i), Couleur.ANSI_WHITE);
                System.out.print(" | ");
            }

            for (int j = 0; j < this.taille; j++) {
                // Si la case ne contient pas de navire
                if (this.matricePlateau[i][j].toString().contains("~")) {
                    ToolCouleur.printCouleur(this.matricePlateau[i][j].toString() + " ", Couleur.ANSI_BLUE);
                } else {
                    // Si on a passé  une équipe en param
                    if (joueur != null) {
                        // Vérification que l'ID de l'équipe correspond à celui du navire
                        if (this.matricePlateau[i][j].isEstOccupeeSurface()) {
                            if (joueur.getId() == this.matricePlateau[i][j].getOccupantSurface().getNumEq()) {
                                // Affichage couleur
                                ToolCouleur.printCouleur(this.matricePlateau[i][j].toString() + " ", Couleur.ANSI_YELLOW);
                            } else {
                                System.out.print(this.matricePlateau[i][j].toString() + " ");
                            }

                        } else if (this.matricePlateau[i][j].isEstOccupeeProfondeur()) {
                            if (joueur.getId() == this.matricePlateau[i][j].getOccupantProfondeur().getNumEq()) {
                                // Affichage couleur
                                ToolCouleur.printCouleur(this.matricePlateau[i][j].toString() + " ", Couleur.ANSI_YELLOW);
                            } else {
                                System.out.print(this.matricePlateau[i][j].toString() + " ");
                            }
                        }
                    } else {
                        System.out.print(this.matricePlateau[i][j].toString() + " ");
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

    @Override
    public String toString() {
        String res = "      ";

        // Affichage des coordonnées
        for (int i = 1; i <= this.taille; i++) {
            res += i + "   ";
        }

        // Affichage de la bordure haute ("tirets")
        res += "\n   ";
        for (int i = 0; i < (this.taille * 2) * 2 / 3 + 1; i++) {
            res += " _ ";
        }

        res += "\n";
        for (int i = 0; i < this.taille; i++) {
            // Affichage selon la coordonnée pour avoir la même taille
            if (i + 1 < 10) {
                res += " " + (i + 1) + " | ";
            } else {
                res += (i + 1) + " | ";
            }

            for (int j = 0; j < this.taille; j++) {
                res += this.matricePlateau[i][j].toString() + " ";
            }
            res += "|\n";
        }

        // Affichage de la bordure basse
        res += "   ";
        for (int i = 0; i < (this.taille * 2) * 2 / 3 + 1; i++) {
            res += " _ ";
        }
        return res;
    }
}

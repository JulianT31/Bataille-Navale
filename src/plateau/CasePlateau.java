package plateau;

import joueur.Joueur;
import navire.Navire;
import navire.NavireProfondeur;
import navire.SousMarin;
import tools.Couleur;
import tools.ToolCouleur;

/**
 * Classe CasePlateau, qui gère une case de la matrice de jeu
 *
 * @author Julian TRANI 1A SRI
 */
public class CasePlateau {
    // Attributs
    private Navire occupant;
    private NavireProfondeur sousmarin;
    private boolean estOccupeeSurface;
    private boolean estOccupeeProfondeur;

    // Constructeur
    public CasePlateau() {
        this.occupant = null;
        this.sousmarin = null;
        this.estOccupeeSurface = false;
        this.estOccupeeProfondeur = false;
    }

    // Getter
    public Navire getOccupantSurface() {
        return occupant;
    }

    public NavireProfondeur getOccupantProfondeur() {
        return sousmarin;
    }

    /**
     * Ajoute un occupant selon le navire passé en paramètre (case haut ou bas)
     * si il y a de la place disponible
     *
     * @param navire : le navire a jouté (profondeur ou surface)
     */
    public void addUnOccupant(Navire navire) {
        // Séparation selon le navire
        if (navire instanceof NavireProfondeur) { // PROFONDEUR
            // Vérification qu'il a de la place
            if (!estOccupeeProfondeur) {
                this.sousmarin = (NavireProfondeur) navire;
                this.estOccupeeProfondeur = true;
            }
        } else { // SURFACE
            if (!estOccupeeSurface) {
                this.occupant = navire;
                this.estOccupeeSurface = true;
            }
        }
    }

    /**
     * Retourne l'état de l'occupation de la case en surface
     *
     * @return un boolean true si la case surface est occupée sinon false
     */
    public boolean isEstOccupeeSurface() {
        return estOccupeeSurface;
    }

    /**
     * Retourne l'état de l'occupation de la case en surface
     *
     * @return un boolean true si la case surface est occupée sinon false
     */
    public boolean isEstOccupeeProfondeur() {
        return estOccupeeProfondeur;
    }

    /**
     * Supprime le navire passé n paramètre de la case
     *
     * @param navire le navire à supprimer de la case
     */
    public void removeUnOccupant(Navire navire) {
        if (isEstOccupeeProfondeur() && navire instanceof NavireProfondeur) {
            this.sousmarin = null;
            this.estOccupeeProfondeur = false;
        } else if (isEstOccupeeSurface() && navire != null) {
            this.occupant = null;
            this.estOccupeeSurface = false;
        }
    }

    /**
     * Affichage couleur de la case
     *
     * @param joueur : le joueur qui est entrain de jouer (peut etre null)
     */
    public void affiche(Joueur joueur) {
        if (joueur == null) { // AUCUNE COULEUR
            // On gere l'affichage selon si on a 1 ou 2 bateau
            if (isEstOccupeeSurface() && isEstOccupeeProfondeur()) {
                System.out.print(occupant.affichagePlateau() + " " + sousmarin.affichagePlateau());
            } else if (isEstOccupeeSurface()) {
                System.out.print(" " + occupant.affichagePlateau() + " ");
            } else if (isEstOccupeeProfondeur()) {
                System.out.print(" " + sousmarin.affichagePlateau() + " ");
            } else {
                ToolCouleur.printCouleur(" ~ ", Couleur.ANSI_BLUE);
            }
        } else {
            // SI ON A 2 OCCUPANTS
            if (isEstOccupeeSurface() && isEstOccupeeProfondeur()) {
                // SI le bateau appartient au joueur qui joue
                if (occupant.getNumEq() == joueur.getId()) {
                    ToolCouleur.printCouleur(occupant.affichagePlateau(), Couleur.ANSI_YELLOW);
                } else {
                    System.out.print(occupant.affichagePlateau());
                }
                System.out.print(" ");

                // SI le bateau appartient au joueur qui joue
                if (sousmarin.getNumEq() == joueur.getId()) {
                    ToolCouleur.printCouleur(sousmarin.affichagePlateau(), Couleur.ANSI_YELLOW);
                } else {
                    System.out.print(sousmarin.affichagePlateau());
                }
            }
            // SI ON A SEULEMENT UN OCCUPANT EN SURFACE
            else if (isEstOccupeeSurface()) {
                if (occupant.getNumEq() == joueur.getId()) {
                    ToolCouleur.printCouleur(" " + occupant.affichagePlateau() + " ", Couleur.ANSI_YELLOW);
                } else {
                    System.out.print(" " + occupant.affichagePlateau() + " ");
                }
            }
            // SI ON A SEULEMENT UN OCCUPANT EN PROFONDEUR
            else if (isEstOccupeeProfondeur()) {
                // SI le bateau appartient au joueur qui joue
                if (sousmarin.getNumEq() == joueur.getId()) {
                    // SI le sous marin est endommagé alors on l'affiche en rouge
                    if (((SousMarin) sousmarin).isEndommage()) {
                        ToolCouleur.printCouleur(" " + sousmarin.affichagePlateau() + " ", Couleur.ANSI_RED);
                    } else {
                        ToolCouleur.printCouleur(" " + sousmarin.affichagePlateau() + " ", Couleur.ANSI_YELLOW);
                    }
                } else {
                    System.out.print(" " + sousmarin.affichagePlateau() + " ");
                }
            } else {
                ToolCouleur.printCouleur(" ~ ", Couleur.ANSI_BLUE);
            }
        }
    }

//
//    @Override
//    public String toString() {
//        String res = " ~ ";
//        if (isEstOccupeeSurface() && isEstOccupeeProfondeur()) {
//            res = "" + occupant.affichagePlateau() + " " + sousmarin.affichagePlateau();
//        } else if (isEstOccupeeSurface()) {
//            res = " " + occupant.affichagePlateau() + " ";
//        } else if (isEstOccupeeProfondeur()) {
//            res = " " + sousmarin.affichagePlateau() + " ";
//        }
//        return res;
//    }
}

package plateau;

import navire.Navire;
import navire.NavireProfondeur;

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

    public void addUnOccupant(Navire navire) {
        if (navire instanceof NavireProfondeur) {
            this.sousmarin = (NavireProfondeur) navire;
            this.estOccupeeProfondeur = true;
        } else {
            this.occupant = navire;
            this.estOccupeeSurface = true;
        }
    }

    public boolean isEstOccupeeSurface() {
        return estOccupeeSurface;
    }

    public boolean isEstOccupeeProfondeur() {
        return estOccupeeProfondeur;
    }

    public void removeUnOccupant(Navire navire) {
        if (isEstOccupeeProfondeur() && navire instanceof NavireProfondeur) {
            this.sousmarin = null;
            this.estOccupeeProfondeur = false;
        } else if (isEstOccupeeSurface() && navire != null) {
            this.occupant = null;
            this.estOccupeeSurface = false;
        }
    }

    @Override
    public String toString() {
        String res = " ~ ";
        if (isEstOccupeeSurface() && isEstOccupeeProfondeur()) {
            res = "" + occupant.affichagePlateau() + " " + sousmarin.affichagePlateau();
        } else if (isEstOccupeeSurface()) {
            res = " " + occupant.affichagePlateau() + " ";
        } else if (isEstOccupeeProfondeur()) {
            res = " " + sousmarin.affichagePlateau() + " ";
        }
        return res;
    }
}

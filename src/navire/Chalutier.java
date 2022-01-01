package navire;

import tools.TypeNav;

/**
 * Classe Chalutier qui hérite de NavireSurface, navire de l'équipe de pêcheur
 *
 * @author Julian TRANI 1A SRI
 */
public class Chalutier extends NavireSurface {
    // Attribut
    private boolean filetDeploye;

    //Constructeur
    public Chalutier(int id, TypeNav myType, int numEq) {
        super(id, myType, numEq);
        this.filetDeploye = false;
    }

    // GETTER
    public void setFilet() {
        this.filetDeploye = true;
    }

    /**
     * Reset le filet
     */
    public void resetFilet() {
        this.filetDeploye = false;
    }

    public boolean isFiletDeploye() {
        return filetDeploye;
    }
}

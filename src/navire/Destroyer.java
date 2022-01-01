package navire;

import tools.TypeNav;

/**
 * Classe Destroyer qui hérite de NavireSurface, navire de l'équipe de bataillon
 *
 * @author Julian TRANI 1A SRI
 */
public class Destroyer extends NavireSurface {
    // Constructeur
    public Destroyer(int id, TypeNav myType, int numEq) {
        super(id, myType, numEq);
        this.portee = 1;
    }
}

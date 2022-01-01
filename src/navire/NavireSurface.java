package navire;

import tools.TypeNav;

/**
 * Classe abstraite NavireSurface qui hérite de Navire
 * Elle permet de décrire le navire qui peuvent se déplacer uniquement sur l'eau
 *
 * @author Julian TRANI 1A SRI
 */
public abstract class NavireSurface extends Navire {
    // Constructeur
    public NavireSurface(int id, TypeNav myType, int numEq) {
        super(id, myType, numEq);
    }
}
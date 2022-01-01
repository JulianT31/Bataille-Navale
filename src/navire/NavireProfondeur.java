package navire;

import tools.TypeNav;

/**
 * Classe abstraite NavireProfondeur qui hérite de Navire
 * Elle permet de décrire le navire qui peuvent se déplacer totalement sous l'eau
 *
 * @author Julian TRANI 1A SRI
 */
public abstract class NavireProfondeur extends Navire {
    // Constructeur
    public NavireProfondeur(int id, TypeNav myType, int numEq) {
        super(id, myType, numEq);
    }
}

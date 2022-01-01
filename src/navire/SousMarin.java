package navire;

import tools.TypeNav;

/**
 * Classe Sous-marin qui hérite de NavireProfondeur
 *
 * @author Julian TRANI 1A SRI
 */
public class SousMarin extends NavireProfondeur {
    // Attribut
    protected boolean endommage;

    // Sous-marin
    public SousMarin(int id, TypeNav myType, int numEq) {
        super(id, myType, numEq);
        this.portee = 1;
    }

    public boolean isEndommage() {
        return endommage;
    }

    public void setEndommage(boolean endommage) {
        this.endommage = endommage;
    }
}

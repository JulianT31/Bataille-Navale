package navire;

import tools.TypeNav;

public class SousMarin extends NavireProfondeur {
    protected boolean endommage;

    public SousMarin(int id, TypeNav myType, int numEq) {
        super(id, myType, numEq);
        this.portee = 1;
    }

    public void setEndommage(boolean endommage) {
        this.endommage = endommage;
    }

    public boolean isEndommage() {
        return endommage;
    }
}

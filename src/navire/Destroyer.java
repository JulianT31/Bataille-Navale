package navire;

import tools.TypeNav;

public class Destroyer extends NavireSurface {
    public Destroyer(int id, TypeNav myType, int numEq) {
        super(id, myType, numEq);
        this.portee = 1;
    }
}

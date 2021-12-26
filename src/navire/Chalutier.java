package navire;

import tools.TypeNav;

public class Chalutier extends NavireSurface {
    private boolean filetDeploye;

    public Chalutier(int id, TypeNav myType, int numEq) {
        super(id, myType, numEq);
        this.filetDeploye = false;
    }

    public void setFilet() {
        this.filetDeploye = true;
    }

    public void resetFilet() {
        this.filetDeploye = false;
    }

    public boolean isFiletDeploye() {
        return filetDeploye;
    }
}

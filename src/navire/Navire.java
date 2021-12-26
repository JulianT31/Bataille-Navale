package navire;

import tools.TypeNav;

import java.awt.*;

public abstract class Navire {
    protected int id;
    protected TypeNav myType;
    protected String strAffichage;
    protected int numEq;
    protected int etat; // 0 => OK - 1 => COULE
    protected int portee;
    protected int vitesse;
    protected Point position;

    public Navire(int id, TypeNav myType, int numEq) {
        this.id = id;
        this.myType = myType;
        this.numEq = numEq;
        this.position = new Point(0, 0);
        this.portee = 0;
        this.vitesse = 1;
    }

    public int getId() {
        return id;
    }

    public TypeNav getMyType() {
        return myType;
    }

    public int getNumEq() {
        return numEq;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getPortee() {
        return portee;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setCoule() {
        this.etat = 1;
    }

    public boolean estValide() {
        return false;
    }

    public String affichagePlateau() {
        return String.valueOf(String.valueOf(myType).charAt(0));
    }

    @Override
    public String toString() {
        if (etat == 1) {
            return "ID : " + myType + " -  Coulé !";
        } else {
            return "ID : " + id + " - " + myType + " (" + position.x + "-" + position.y + ") - Portée : " + portee;
        }

    }
}

package manager;

import equipe.Equipe;
import tools.Action;
import tools.Direction;

/**
 * Classe commande
 */
public class Commande {
    private int idNavire;
    private Action actionChoisie;
    private Direction directionChoisie;
    private Equipe equipe;

    public Commande(Equipe eq, int id, int action, int direction) {
        this.equipe = eq;
        this.idNavire = id;
        this.actionChoisie = Action.values()[action];
        this.directionChoisie = Direction.values()[direction];
    }

    public Commande(Equipe eq, String strIdNav, String strAction, String strDirection) {
        this.equipe = eq;
        try {
            this.idNavire = Integer.parseInt(strIdNav);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
        // TO DO check exception
        this.actionChoisie = Action.valueOf(strAction);
        this.directionChoisie = Direction.valueOf(strDirection);
    }

    /* GETTER-SETTER */
    public Equipe getEquipe() {
        return equipe;
    }

    public int getIdNavire() {
        return idNavire;
    }

    public void setIdNavire(int idNavire) {
        this.idNavire = idNavire;
    }

    public Action getActionChoisie() {
        return actionChoisie;
    }

    public void setActionChoisie(Action actionChoisie) {
        this.actionChoisie = actionChoisie;
    }

    public Direction getDirectionChoisie() {
        return directionChoisie;
    }

    public void setDirectionChoisie(Direction directionChoisie) {
        this.directionChoisie = directionChoisie;
    }

    @Override
    public String toString() {
        switch (actionChoisie) {
            case DEPLACEMENT:
                return "\nL'équipe " + equipe.getId() +
                        " à DÉPLACÉ le navire " + idNavire +
                        " vers le " + directionChoisie +"\n";

            case PECHE:
                return "\nL'équipe " + equipe.getId() +
                        " à PECHÉ avec le navire " + idNavire +
                        " vers le " + directionChoisie +"\n";
            case TIR:
                return "\nL'équipe " + equipe.getId() +
                        " à TIRÉ avec le navire " + idNavire +
                        " vers le " + directionChoisie +"\n";
            default:
                return "Action inconnue";
        }
    }
}

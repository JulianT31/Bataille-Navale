package manager;

import equipe.Equipe;
import navire.Navire;
import tools.Action;
import tools.Direction;

/**
 * Classe commande qui va permettre de savoir l'action réaliser par les joueurs
 */
public class Commande {
    // Attributs
    private int idNavire;
    private Action actionChoisie;
    private Direction directionChoisie;
    private Equipe equipe;

    // Constructeur
    public Commande(Equipe eq, int id, int action, int direction) {
        this.equipe = eq;
        this.idNavire = id;
        this.actionChoisie = Action.values()[action];
        this.directionChoisie = Direction.values()[direction];
    }

    // Constructeur
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

    public Action getActionChoisie() {
        return actionChoisie;
    }

    public Direction getDirectionChoisie() {
        return directionChoisie;
    }

    @Override
    public String toString() {
        // Récupération du navire impliqué par la commande via son ID
        Navire navire = null;
        for (Navire nav : equipe.getListeNavire()) {
            if (nav.getId() == idNavire) {
                navire = nav;
            }
        }

        // Si on a trouvé le navire impliqué
        if (navire != null) {
            // Affichage selon l'action réalisée
            switch (actionChoisie) {
                case DEPLACEMENT:
                    return "\nL'équipe " + equipe.getId() +
                            " à DÉPLACÉ son " + navire.getMyType() + " n°" + idNavire +
                            " vers le " + directionChoisie + "";

                case PECHE:
                    return "\nL'équipe " + equipe.getId() +
                            " à PECHÉ son " + navire.getMyType() + " n°" + idNavire +
                            " vers le " + directionChoisie + "";
                case TIR:
                    return "\nL'équipe " + equipe.getId() +
                            " à TIRÉ avec son " + navire.getMyType() + " n°" + idNavire +
                            " vers le " + directionChoisie + "";
                default:
                    return "Action inconnue";
            }
        }
        return "Commande avec un navire inconnu";
    }
}

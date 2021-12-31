package equipe;

import tools.Couleur;
import tools.Nature;
import tools.Statut;

/**
 * Classe EqBataillon qui hérite de
 *
 * @author Julian TRANI 1A SRI
 */
public class EqBataillon extends Equipe {
    // Constructeurs
    public EqBataillon(Nature myNature, Couleur couleur) {
        super(myNature, couleur);
    }

    public EqBataillon(int idEq, Nature n, Couleur couleur) {
        super(n, couleur);
        this.ident = idEq;
        this.setStatut(Statut.MILITAIRE);
    }
}

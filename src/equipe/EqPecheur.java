package equipe;

import tools.Couleur;
import tools.Nature;
import tools.Statut;

/**
 * Classe EqBataillon qui hérite de Equipe
 *
 * @author Julian TRANI 1A SRI
 */
public class EqPecheur extends Equipe {
    public EqPecheur(Nature myNature, Couleur couleur) {
        super(myNature, couleur);
    }

    public EqPecheur(int idEq, Nature n, Couleur couleur) {
        super(n, couleur);
        this.ident = idEq;
        this.setStatut(Statut.NEUTRE);
    }
}

package equipe;

import tools.Couleur;
import tools.Nature;
import tools.Statut;

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

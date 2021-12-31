package joueur;

import manager.Commande;
import navire.Navire;
import tools.Nature;
import tools.Statut;

import java.util.Vector;

/**
 * Interface Joueur qui va implementer les comportements communs à tous les joueurs
 */
public interface Joueur {
    public Commande getCommande();

    public Statut getStatut();

    public void addNavire(Navire nav);

    public Vector<Navire> getListeNavire();

    public int getId();

    public Nature getNature();

    boolean isPerdu();
}

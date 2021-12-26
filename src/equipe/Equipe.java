package equipe;

import joueur.Humain;
import joueur.Ia;
import manager.Commande;
import navire.Navire;
import tools.Couleur;
import tools.Nature;
import tools.Statut;

import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public abstract class Equipe implements Humain, Ia {
    protected Vector<Navire> listeNavire;
    protected Statut myStatut;
    protected Commande myCommande;
    protected Nature myNature;
    protected Couleur couleur;
    protected int ident;
    private Random rd;
    private Scanner sc;

    public Equipe(Nature n, Couleur couleur) {
        this.listeNavire = new Vector<>();
        this.myNature = n;
        this.rd = new Random();
        this.sc = new Scanner(System.in);
        this.couleur = couleur;
    }

    public void setStatut(Statut s) {
        myStatut = s;
    }

    public Statut getStatut() {
        return myStatut;
    }

    public Commande getCommande() {
        if (myNature == Nature.HUMAIN) interrogationParClavier();
        else tirageAleatoire();
        System.out.println(myCommande);
        return myCommande;
    }

    public void tirageAleatoire() {
        int idNav = rd.nextInt(listeNavire.size());
        idNav = listeNavire.get(idNav).getId();
        int idAction = rd.nextInt(2);

        // Cas pour la pêche
        if ((myStatut == Statut.NEUTRE) && (idAction == 1)) {
            idAction++;
        }

        int idDirection = 0;
        if (idAction < 2) {
            idDirection = rd.nextInt(4);
        }
        myCommande = new Commande(this, idNav, idAction, idDirection);
    }

    public void interrogationParClavier() {
        // Variables
        String strIdNav = "";
        String strAction = "";
        String strDirection = "";
        System.out.println("Début du tour : ");

        // Saisie et vérification de la saisie du numéro du navire
        do {
            System.out.print("Numéro du navire (de 0 a " + (listeNavire.size() - 1) + ") : ");
            strIdNav = sc.nextLine();
        } while (Integer.parseInt(strIdNav) < 0 || Integer.parseInt(strIdNav) > (listeNavire.size() - 1));

        // Saisie et vérification de la saisie du déplacement
        do {
            if (myStatut == Statut.MILITAIRE)
                System.out.print("Action (DEPLACEMENT, TIR) : ");
            else System.out.print("Action (DEPLACEMENT, PECHE) : ");
            strAction = sc.nextLine();
        } while (((myStatut == Statut.MILITAIRE) && (strAction.compareTo("DEPLACEMENT") != 0) && (strAction.compareTo("TIR") != 0)) ||
                ((myStatut == Statut.NEUTRE) && (strAction.compareTo("DEPLACEMENT") != 0) && (strAction.compareTo("PECHE") != 0)));

        // Initialisation
        strDirection = "NORD";
        if (strAction.compareTo("PECHE") != 0) {
            // Saisie et vérification de la saisie de la direction
            do {
                System.out.print("Direction (NORD, SUD, EST, OUEST) : ");
                strDirection = sc.nextLine();
            } while ((strDirection.compareTo("NORD") != 0) && (strDirection.compareTo("SUD") != 0) &&
                    (strDirection.compareTo("EST") != 0) && (strDirection.compareTo("OUEST") != 0));
        }


        // Création de la commande avec les saisies précédentes
        myCommande = new Commande(this, String.valueOf(listeNavire.get(Integer.parseInt(strIdNav)).getId()), strAction, strDirection);
    }

    public String toString() {
        return "Equipe " + ident + " (" + myStatut + "," + myNature + "), avec " + listeNavire;
    }

    public void addNavire(Navire nav) {
        listeNavire.add(nav);
    }

    @Override
    public Vector<Navire> getListeNavire() {
        return listeNavire;
    }

    public int getId() {
        return ident;
    }

    public boolean isPerdu() {
        return listeNavire.size() == 0;
    }

    public Nature getNature() {
        return myNature;
    }

}
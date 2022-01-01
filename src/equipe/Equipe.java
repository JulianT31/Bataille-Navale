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
import java.util.concurrent.TimeUnit;

/**
 * Classe abstraite Equipe qui implémente le comportement humain et IA
 *
 * @author Julian TRANI 1A SRI
 */
public abstract class Equipe implements Humain, Ia {
    // Attributs
    protected Vector<Navire> listeNavire;
    protected Statut myStatut;
    protected Commande myCommande;
    protected Nature myNature;
    protected Couleur couleur;
    protected int ident;
    private Random rd;
    private Scanner sc;

    // Constructeur
    public Equipe(Nature n, Couleur couleur) {
        this.listeNavire = new Vector<>();
        this.myNature = n;
        this.rd = new Random();
        this.sc = new Scanner(System.in);
        this.couleur = couleur;
    }

    // GETTER / SETTER
    @Override
    public Vector<Navire> getListeNavire() {
        return listeNavire;
    }

    public int getId() {
        return ident;
    }

    public Nature getNature() {
        return myNature;
    }

    public Statut getStatut() {
        return myStatut;
    }

    public void setStatut(Statut s) {
        myStatut = s;
    }

    /**
     * Retourne la commande jouée selon si la nature de l'équipe
     *
     * @return la commande
     */
    public Commande getCommande() {
        if (myNature == Nature.HUMAIN) interrogationParClavier();
        else tirageAleatoire();
        System.out.println(myCommande);
        return myCommande;
    }

    /**
     * Saisie alétoire pour faire jouer le bot
     */
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
        // Simulation comme si l'ordi joue
        System.out.print("L'ordi est entrain de jouer ...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myCommande = new Commande(this, idNav, idAction, idDirection);
    }

    /**
     * Saisie au clavier pour jouer un tour
     */
    public void interrogationParClavier() {
        // Variables
        String strIdNav = "";
        String strAction = "";
        String strDirection = "";
        System.out.println("À vous de jouer ...");

        // Saisie et vérification de la saisie du numéro du navire
        do {
            System.out.print("Numéro du navire (de 0 a " + (listeNavire.size() - 1) + ") : ");
            strIdNav = sc.nextLine().trim();
        } while (!verifSaisie(strIdNav));

        // Saisie et vérification de la saisie du déplacement
        do {
            if (myStatut == Statut.MILITAIRE)
                System.out.print("Action (DEPLACEMENT, TIR) : ");
            else System.out.print("Action (DEPLACEMENT, PECHE) : ");
            strAction = sc.nextLine().trim();
        } while (((myStatut == Statut.MILITAIRE) && (strAction.compareTo("DEPLACEMENT") != 0) && (strAction.compareTo("TIR") != 0)) ||
                ((myStatut == Statut.NEUTRE) && (strAction.compareTo("DEPLACEMENT") != 0) && (strAction.compareTo("PECHE") != 0)));

        strDirection = "NORD"; // Initialisation de la variable si jamais on peche
        if (strAction.compareTo("PECHE") != 0) {
            // Saisie et vérification de la saisie de la direction
            do {
                System.out.print("Direction (NORD, SUD, EST, OUEST) : ");
                strDirection = sc.nextLine().trim();
            } while ((strDirection.compareTo("NORD") != 0) && (strDirection.compareTo("SUD") != 0) &&
                    (strDirection.compareTo("EST") != 0) && (strDirection.compareTo("OUEST") != 0));
        }
        // Création de la commande avec les saisies précédentes
        myCommande = new Commande(this, String.valueOf(listeNavire.get(Integer.parseInt(strIdNav)).getId()), strAction, strDirection);
    }

    /**
     * Vérification de la saisie du numéro de navire
     *
     * @param value : chaine de caractère à tester
     * @return un boolean
     */
    public boolean verifSaisie(String value) {
        // Vérification de la valeur en la convertissant
        try {
            int convertValue = Integer.parseInt(value);
            if (convertValue < 0 || convertValue > (listeNavire.size() - 1)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Ajoute un navire a la liste de l'équipe
     *
     * @param nav : le navire à ajouter
     */
    public void addNavire(Navire nav) {
        listeNavire.add(nav);
    }

    /**
     * Retourne l'état de l'équipe
     *
     * @return true si elle a perdu false sinon
     */
    public boolean isPerdu() {
        return listeNavire.size() == 0;
    }

    public String toString() {
        return "Equipe " + ident + " (" + myStatut + "," + myNature + "), avec " + listeNavire;
    }
}
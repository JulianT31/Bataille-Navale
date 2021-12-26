package manager;

import equipe.EqBataillon;
import equipe.EqPecheur;
import exceptions.JeuException;
import joueur.Joueur;
import navire.*;
import plateau.CasePlateau;
import plateau.Plateau;
import tools.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Jeu {
    // Attributs
    protected int taille;
    protected Plateau plateau;
    protected ArrayList<Joueur> joueurs;
    private Random random;
    private Scanner scanner;
    private boolean fini;
    private final int MAX_NBR_BATEAU = 3;
    private final int NBR_JOUEUR = 3;
    private int nbTour;

    // Constructeurs
    public Jeu(int taille) throws JeuException {
        if (taille < 3) {
            throw new JeuException("La taille du plateau doit être supérieur à 3");
        }
        this.plateau = new Plateau(taille);
        this.joueurs = new ArrayList<>();
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.taille = taille;
        this.nbTour = 1;
    }

    public void jouer() {

//        affichageMenu();
        System.out.println("Press Enter key to continue...");

        // Init Partie
        creationJoueur();
        attributionNavire();
        positionnementNavire();
        System.out.println("Voici le plateau :");

        plateau.affiche(null);
        affichageJoueurs();

        while (!isGagne()) {
            System.out.println("========================");
            System.out.println("Début du tour " + nbTour);
            System.out.println("========================");
            for (Joueur j : joueurs) {
                // Si on a perdu, on joue pas !
                if (j.getListeNavire().size() == 0) {
                    System.out.println("* L'équipe " + j.getId() + " a perdu, du coup elle passe son tour");
                } else {
                    System.out.println("* Au tour de l'équipe " + j.getId() + " de jouer !");
                    plateau.affiche(j);
                    majJeuAvCommande(j.getCommande());
                }
            }
            nbTour++;
            System.out.println("========================");
            System.out.println("Fin du tour " + nbTour);
            System.out.println("========================");
            System.out.println();
            affichageJoueurs();
        }
    }

    public void affichageMenu() {
        System.out.println("\n" +
                "Bienvenue dans la Bataille Navale !\n" +
                "\n" +
                "Les règles sont très simples, 3 équipes dont 2 militaires et une neutre s'affrontent sur un plateau, le but étant d'éliminer une équipe la plus vite possible.\n" +
                "Pour cela, différentes actions sont disponibles selon votre équipe. Le militaire peut tirer tandis que l'équipe neutre peut pécher avec leur chalutier.\n" +
                "Les chalutiers peuvent détruire les sous-marins grâce à leur filet de pêche.\n" +
                "\n" +
                "/!\\ Attention où vous tirez, vous pouvez attaquer vos propres bateaux.\n" +
                "\n" +
                "Les sous-marins peuvent se deplacer sous les autres navires.\n" +
                "\n" +
                "Appuyez sur la touche Entrer pour commencer la partie ! ");
    }

    public void choixJoueurs() {
        // Variables
        int action;
        int nombreAction = Action.values().length;

        do {
            for (int i = 1; i <= nombreAction; i++) {
                System.out.println("" + i + " - " + Action.values()[i - 1]);
            }
            System.out.print("Action : ");
            action = scanner.nextInt();
        } while (action < 0 || action > nombreAction);


        switch (action) {
            case 1:
                System.out.println("Déplacement");
                break;
            case 2:
                System.out.println("Tir");
                break;
            case 3:
                System.out.println("Peche");
                break;
            default:
                break;
        }
    }

    public void creationJoueur() {
        // Variables
        int numRandom = random.nextInt(3) + 1;
        Nature nature = Nature.IA;
        Couleur couleur = Couleur.ANSI_GREEN;
        ArrayList<Couleur> couleurs = new ArrayList<>();
        couleurs.add(Couleur.ANSI_BLUE);
        couleurs.add(Couleur.ANSI_GREEN);

        // Création de chaque équipe
        for (int i = 1; i <= NBR_JOUEUR; i++) {
            // Equipe humain
            if (i == numRandom) {
                nature = Nature.HUMAIN;
                couleur = Couleur.ANSI_YELLOW;
            } else {
                couleur = couleurs.get(0);
                couleurs.remove(couleurs.get(0));
            }
            // Ajout des différentes equipes
            if (i < 3) {
                joueurs.add(new EqBataillon(i, nature, couleur));
            } else {
                joueurs.add(new EqPecheur(i, nature, couleur));
            }

            // Reset nature ;
            nature = Nature.IA;
        }
        // Mélange l'ordre des equipes
        Collections.shuffle(this.joueurs);
    }

    public void affichageJoueurs() {
        System.out.println("\nRécaptitulatif des joueurs :");

        for (Joueur j : this.joueurs) {
            if (j.isPerdu()) {
                System.out.print("- EQUIPE " + j.getId() + "-  Perdu !\n");
            } else {
                // Si c'est notre équipe alors on l'affiche en couleur
                if (j.getNature() == Nature.HUMAIN) {
                    ToolCouleur.printCouleur("- (VOUS) " + j + "\n", Couleur.ANSI_BLUE);
                } else {
                    System.out.print("- " + j + "\n");
                }
            }
        }
        System.out.println("");
    }


    public void attributionNavire() {
        int idCpt = 1;
        for (Joueur joueur : this.joueurs) {
            if (joueur.getStatut() == Statut.MILITAIRE) {
                joueur.addNavire(new SousMarin(idCpt, TypeNav.SOUSMARIN, joueur.getId()));
                idCpt++;
                joueur.addNavire(new Destroyer(idCpt, TypeNav.DESTROYER, joueur.getId()));
                idCpt++;
            } else {
                joueur.addNavire(new Chalutier(idCpt, TypeNav.CHALUTIER, joueur.getId()));
                idCpt++;
            }
        }
    }

    public void positionnementNavire() {
        // Pour chaque joueurs
        for (Joueur joueur : this.joueurs) {
            // Pour chaque bateau du joueur
            for (Navire navire : joueur.getListeNavire()) {
                Point position;

                // Vérification si la case récupéré aléatoirement est pas prise aléatoirement
                do {
                    // Entre 0 - this.taille
                    position = new Point(random.nextInt(this.taille), random.nextInt(this.taille));
                } while (this.plateau.getCasePlateau(position.x, position.y).isEstOccupeeSurface()
                        || this.plateau.getCasePlateau(position.x, position.y).isEstOccupeeProfondeur());

                navire.setPosition(position);
                this.plateau.getCasePlateau(position.x, position.y).addUnOccupant(navire);
            }
        }
    }

    public void finDePartie(int numJ) {
        System.out.println("Bravo, le joueur " + numJ + " a gagné !");
    }

    public void majJeuAvCommande(Commande commande) {
        if (commande != null) {
            switch (commande.getActionChoisie()) {
                case DEPLACEMENT:
                    majJeuCasDeplacement(commande);
                    break;
                case TIR:
                    majJeuCasTir(commande);
                    break;
                case PECHE:
                    majJeuCasPeche(commande);
                    break;
                default:
                    break;
            }
        }
    }

    public void majJeuCasTir(Commande commande) {
        if (commande != null) {
            // Récupération du navire à partir de la commande
            Navire navire = null;
            for (Navire tempNav : commande.getEquipe().getListeNavire()) {
                if (tempNav.getId() == commande.getIdNavire()) {
                    navire = tempNav;
                    break;
                }
            }

            if (navire != null) {
                int portee = navire.getPortee();

                // Initialisation des coordonnées avec la position du navire
                Point position = new Point();
                position.x = navire.getPosition().x;
                position.y = navire.getPosition().y;

                boolean touche = false;
                for (int i = 1; i <= portee && !touche; i++) {
                    // Génération des nouvelles coordonnées
                    position = generateNewPosition(commande.getDirectionChoisie(), position.x, position.y);

                    // Vérification des coordonnées pour éviter de sortir de la grille
                    if (isValidPosition(position)) {

                        // Verification si on a touché un bateau
                        if (plateau.getCasePlateau(position.x, position.y).isEstOccupeeSurface()) {
                            Navire navireTouche = plateau.getCasePlateau(position.x, position.y).getOccupantSurface();
                            // Suppression du navire qui a été coulé
                            majListeNavire(navireTouche);
                            // Suppresion de l'occupant
                            plateau.getCasePlateau(position.x, position.y).removeUnOccupant(navireTouche);
                            touche = true; // Sortir si on a touché
                            System.out.println("Joli tir ! Vous avez coulé le " + navireTouche.getMyType() + " de l'équipe " + navireTouche.getNumEq());
                        }
                    }
                }
            }
        }
    }

    public void majJeuCasPeche(Commande commande) {
        if (commande != null) {
            // Récupération du CHALUTIER à partir de la commande
            Chalutier navire = null;
            for (Navire tempNav : commande.getEquipe().getListeNavire()) {
                if (tempNav.getId() == commande.getIdNavire()) {
                    navire = (Chalutier) tempNav;
                    break;
                }
            }

            if (navire != null) {
                // On descend le filet
                navire.setFilet();

                // Si on detecte un sous marin alors on l'emdommage
                CasePlateau casePlateau = plateau.getCasePlateau(navire.getPosition().x, navire.getPosition().y);
                if (casePlateau.isEstOccupeeProfondeur()) {
                    SousMarin sousMarin = (SousMarin) casePlateau.getOccupantProfondeur();
                    sousMarin.setEndommage(true);
                    System.out.println("Vous avez endommagé le sous marin n°" + sousMarin.getId());
                }
            }
        }
    }

    public void majListeNavire(Navire navire) {
        // On parcourt chaque équipe
        for (Joueur j : joueurs) {
            // Si on trouve le navire qui à été coulé alors on le supprime de la liste
            j.getListeNavire().removeIf(navire1 -> (navire1.getId() == navire.getId()));
        }
    }

    public void majJeuCasDeplacement(Commande commande) {
        if (commande != null) {
            // Récupération du navire à partir de la commande
            Navire navire = null;
            for (Navire tempNav : commande.getEquipe().getListeNavire()) {
                if (tempNav.getId() == commande.getIdNavire()) {
                    navire = tempNav;
                    break;
                }
            }

            if (navire != null) {
                // Cas particulier si on a un sous marin endommagé
                if (navire instanceof SousMarin && ((SousMarin) navire).isEndommage()) {
                    ToolCouleur.printCouleur("Vous ne pouvez pas bouger votre sous-marin il est endommagé", Couleur.ANSI_RED);
                    return;
                }

                // Génération de la nouvelle position selon la position actuelle et la direction
                Point position = generateNewPosition(commande.getDirectionChoisie(), navire.getPosition().x, navire.getPosition().y);

                // Déplacement seulement si les coordonnées sur valide sinon le joueur perd son tour tampis
                if (isValidPosition(position)) {
                    // Vérification de quelle case doit être dispo
                    if (navire instanceof NavireProfondeur) {

                        // Vérification qu'il n'y a pas deja un bateau dans la nouvelle case
                        if (!plateau.getCasePlateau(position.x, position.y).isEstOccupeeProfondeur()) {
                            // Suppression de l'ancien bateau dans le plateau
                            plateau.getCasePlateau(navire.getPosition().x, navire.getPosition().y).removeUnOccupant(navire);
                            // Modification avec les nouvelles coordonnées
                            navire.setPosition(position);
                            // Ajout du bateau dans la nouvelle case
                            plateau.getCasePlateau(position.x, position.y).addUnOccupant(navire);
                        }
                    } else {

                        // Vérification qu'il n'y a pas deja un bateau dans la nouvelle case
                        if (!plateau.getCasePlateau(position.x, position.y).isEstOccupeeSurface()) {
                            // Suppression de l'ancien bateau dans le plateau
                            plateau.getCasePlateau(navire.getPosition().x, navire.getPosition().y).removeUnOccupant(navire);
                            // Modification avec les nouvelles coordonnées
                            navire.setPosition(position);
                            // Ajout du bateau dans la nouvelle case
                            plateau.getCasePlateau(position.x, position.y).addUnOccupant(navire);
                        }
                    }
                } else {
                    ToolCouleur.printCouleur("Vous ne pouvez pas vous déplacer par la ! Dommage vous avez perdu votre tour !", Couleur.ANSI_RED);
                }
            }
        }
    }

    public Point generateNewPosition(Direction direction, int x, int y) {
        // New Position
        Point position = new Point();
        position.x = x;
        position.y = y;

        // Modification des coordonnées
        switch (direction.toString()) {
            case "NORD":
                position.x--;
                break;
            case "SUD":
                position.x++;
                break;
            case "OUEST":
                position.y--;
                break;
            case "EST":
                position.y++;
                break;
        }

        return position;
    }

    public boolean isValidPosition(Point position) {
        return position.x >= 0 && position.x < this.taille && position.y >= 0 && position.y < this.taille;
    }

    /**
     * Vérifie si la partie est fini
     *
     * @return boolean
     */
    public boolean isGagne() {
        // Variables
        int IDequipePerdante = -1;
        int IDequipeGagnante = -1;
        int maxBateaux = -1;

        // Recherche si on a une équipe perdante
        for (int i = 0; i < joueurs.size(); i++) {
            if (joueurs.get(i).getListeNavire().size() == 0) {
                IDequipePerdante = i;
            }
        }
        // Si on a pas d'équipe perdante alors la partie continue
        if (IDequipePerdante == -1) {
            return false;
        }

        // Recherche de l'équipe qui a le plus de bateaux en vie
        // TODO SI EGALITE
        for (int i = 0; i < joueurs.size(); i++) {
            int taille = joueurs.get(i).getListeNavire().size();
            if (taille > maxBateaux) {
                maxBateaux = taille;
                IDequipeGagnante = i;
            }
        }
        finDePartie(IDequipeGagnante);
        return true;
    }
}

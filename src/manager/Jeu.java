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

/**
 * Classe Jeu qui est la classe principale qui va appeler tout le monde
 *
 * @author Julian TRANI 1A SRI
 */
public class Jeu {
    // Attributs
    private final int NBR_JOUEUR = 3;
    protected int taille;
    protected Plateau plateau;
    protected ArrayList<Joueur> joueurs;
    private Random random;
    private Scanner scanner;
    private int nbTour;

    // Constructeurs
    public Jeu(int taille) throws JeuException {
        // Vérification que la grille ne soit pas trop petite
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

    /**
     * Porte d'entrée de la bataille navale.
     * C'est la function à éxécuter
     */
    public void jouer() {
        // Affichage du menu
        affichageMenu();
        ToolCouleur.printCouleur("Appupyer sur la touche Entrée pour continuer ... ", Couleur.ANSI_BLUE);
        scanner.nextLine();


        // Init Partie
        creationJoueur();
        attributionNavire();
        positionnementNavire();

        // Affichage du jeu avant de démarrer la partie
        System.out.println("\nVoici le plateau :");
        plateau.affiche(null);
        affichageJoueurs();

        // Boucle de jeu (tant que la partie n'est pas fini, on joue)
        while (!isGagne()) {
            System.out.println("========================");
            System.out.println("Début du tour " + nbTour);
            System.out.println("========================");
            // On fait jouer chaque équipe chacun leur tour
            for (Joueur j : joueurs) {
                // Si on a perdu, on joue pas !
                if (j.getListeNavire().size() == 0) {
                    System.out.println("\n=> Au tour de l'équipe " + j.getId() + " mais elle a perdu, du coup elle passe son tour");
                } else {
                    System.out.println("\n=> Au tour de l'équipe " + j.getId() + " de jouer !");
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

    /**
     * Méthode qui va simplement qui va simplement afficher
     * les règles du jeu avant de lancer la partie
     */
    public void affichageMenu() {
        System.out.println("\n" +
                "Bienvenue dans la Bataille Navale ! (Julian TRANI 1A SRI)\n" +
                "\n" +
                "Les règles sont très simples :" +
                "\n\t- un plateau " +
                "\n\t- trois équipes dont deux militaires (DESTROYER/SOUS-MARIN) et une neutre (CHALUTIER)" +
                "\n\t- trois actions possibles (DEPLACEMENT/TIR/PECHE)" +
                "\n\t- pour gagner, éliminer l'une des deux équipes adverses" +
                "\n\t- le chalutier peut endommager les sous marins avec leur filet" +
                "\n\t- le destroyer peut tirer seulement sur les navires de surface" +
                "\n\t- le sous marin peut tirer seulement sur les sous marins" +
                "\n" +
                "/!\\ Attention vous pouvez attaquer vos propres bateaux !\n");
    }

    /**
     * Création des différentes equipes (peche et bataillon)
     * + affectation de l'équipe pour l'utilisateur
     */
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

    /**
     * Affiche un récapitulatif de l'état de la partie selon les équipes
     * (affiche l'état de l'équipe (perdu ou non) et les navires encore vivants)
     */
    public void affichageJoueurs() {
        System.out.println("\nRécaptitulatif des joueurs :");

        for (Joueur j : this.joueurs) {
            // Si l'équipe a perdu
            if (j.isPerdu()) {
                System.out.print("- EQUIPE " + j.getId() + " - Perdu !\n");
            } else { // Sinon l'équipe est encore en vie
                // Si on a trouvé l'équipe alors on l'affiche en couleur pour faciliter la lecture
                if (j.getNature() == Nature.HUMAIN) {
                    ToolCouleur.printCouleur("- (VOUS) " + j + "\n", Couleur.ANSI_BLUE);
                } else {
                    System.out.print("- " + j + "\n");
                }
            }
        }
        System.out.println("");
    }

    /**
     * Création des navires
     */
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
                joueur.addNavire(new Chalutier(idCpt, TypeNav.CHALUTIER, joueur.getId()));
                idCpt++;
            }
        }
    }


    /**
     * Initialisation des bateaux, on les place dans la grille
     */
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

    /**
     * Affichage de la fin de partie avec le gagnant
     *
     * @param joueur
     */
    public void finDePartie(Joueur joueur) {
        ToolCouleur.printCouleur("Bravo, c'est le joueur de l'équipe " + joueur.getId() + " (" + joueur.getNature() + ") qui a gagné !", Couleur.ANSI_GREEN);
    }

    /**
     * Redirection vers les functions selon l'action de la commande passée en paramètre
     *
     * @param commande : commande joué juste avant
     */
    public void majJeuAvCommande(Commande commande) {
        // Vérification que la commande n'est pas null pour eviter les NullPointeurException
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

    /**
     * Réalise l'action de tir grâce les informations sur la commande
     *
     * @param commande
     */
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
                        if (navire instanceof NavireSurface) {
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
                            // Les sous marins peuvent touché que les sous marins
                        } else if (navire instanceof NavireProfondeur) {
                            // Verification si on a touché un bateau
                            if (plateau.getCasePlateau(position.x, position.y).isEstOccupeeProfondeur()) {
                                Navire navireTouche = plateau.getCasePlateau(position.x, position.y).getOccupantProfondeur();
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
                    System.out.print("Vous avez endommagé le sous marin n°" + sousMarin.getId());
                }
                // On remonte le filet (ou il est detruit par le sous marin en dessous)
                navire.resetFilet();
            }
        }
    }

    /**
     * Supprime le navire passé en paramètre
     *
     * @param navire : le navrire à supprimer
     */
    public void majListeNavire(Navire navire) {
        // On parcourt chaque équipe
        for (Joueur j : joueurs) {
            // Si on trouve le navire qui à été coulé alors on le supprime de la liste
            j.getListeNavire().removeIf(navire1 -> (navire1.getId() == navire.getId()));
        }
    }

    /**
     * Action de déplacement d'un navire
     *
     * @param commande : la commande avec tous les informations sur l'action
     */
    public void majJeuCasDeplacement(Commande commande) {
        // Vérification que la commande est pas null (normalement elle est jamais null mais on sait jamais)
        if (commande != null) {
            // Récupération du navire à partir de la commande
            Navire navire = null;
            for (Navire tempNav : commande.getEquipe().getListeNavire()) {
                if (tempNav.getId() == commande.getIdNavire()) {
                    navire = tempNav;
                    break;
                }
            }

            // Vérification que l'on a bien trouvé le navire
            if (navire != null) {
                // Cas particulier si on a un sous marin endommagé
                if (navire instanceof SousMarin && ((SousMarin) navire).isEndommage()) {
                    ToolCouleur.printCouleur("Vous ne pouvez pas bouger votre sous-marin il est endommagé\n", Couleur.ANSI_RED);
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
                        } else { // Erreur
                            ToolCouleur.printCouleur("Il y a déjà un bateau sur cette case !\n", Couleur.ANSI_RED);
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
                        } else { // Erreur
                            ToolCouleur.printCouleur("Il y a déjà un bateau sur cette case !\n", Couleur.ANSI_RED);
                        }
                    }
                } else { // Erreur
                    ToolCouleur.printCouleur("Vous ne pouvez pas vous déplacer par la ! Dommage vous avez perdu votre tour !\n", Couleur.ANSI_RED);
                }
            }
        }
    }

    /**
     * Génère un nouveau point en fonction de la direction passée en paramètre
     *
     * @param direction : la direction (NORD-SUD-OUEST-EST)
     * @param x         : la position x actuelle
     * @param y         : la position y actuelle
     * @return la nouvelle position
     */
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

    /**
     * Vérifie les coordonnées passé en paramètre selon la grille du plateau
     *
     * @param position : les coordonnées a vérifier
     * @return boolean
     */
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
        Joueur joueurGagnant;
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

        finDePartie(joueurs.get(IDequipeGagnante));
        return true;
    }
}

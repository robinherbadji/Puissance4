package fr.uha.ensisa.puissance4.util;

public abstract class Constantes {
	public static final int MODE_CONSOLE = 0;
	public static final int MODE_LIGNE_DE_COMMANDE = 1;
	public static final int MODE_INTERFACE_GRAPHIQUE = 2;
	
	public static final int JOUEUR_HUMAN = 1;
	public static final int JOUEUR_IA = 2;
	
	public static final int JOUEUR_1 = 1;
	public static final int JOUEUR_2 = 2;
	
	public static final int IA_MINIMAX = 0;
	public static final int IA_ALPHABETA = 1;
	
	public static final String[] IA_ALGOS = {"Minimax", "Alpha-Beta"};
	
	public static final String[] IA_NAMES = {"HAL", "Skynet", "Ultron", "R2-D2", "Rick Deckard", "IDA"};
	
	/**
	 * V = case vide
	 * X = case symbole joueur 1
	 * O = case symbole joueur 2
	 * @author weber
	 *
	 */
	public enum Case {V, X, O};
	
	//Affectation  des symboles aux joueurs
	public static final Case SYMBOLE_J1 = Case.X;
	public static final Case SYMBOLE_J2 = Case.O;
	
	//Définition de la taille de la grille
	public static final int NB_COLONNES = 7;
	public static final int NB_LIGNES = 6;
	
	//Définition du nombre de tours max (dépendant de la taille de la grille)
	public static final int NB_TOUR_MAX = NB_COLONNES*NB_LIGNES;
	
	//Etats de la partie
	public static final int PARTIE_EN_COURS = 0;
	public static final int MATCH_NUL = 1;
	public static final int VICTOIRE_JOUEUR_1 = 2;
	public static final int VICTOIRE_JOUEUR_2 = 3;
	
	public static final int NB_JETONS_VICTOIRE = 4;
	
	// Stratégies
	/*
	public static final int STRATEGIE_CIBLEE_IA = 0;
	public static final int STRATEGIE_GLOBALE = 1;
	public static final int STRATEGIE_ATTAQUANTE = 2;
	public static final int STRATEGIE_DEFENSIVE = 3;
	public static final int STRATEGIE_GAUSS = 4;
	public static final int STRATEGIE_GLOBALE_GAUSS = 5;
	*/
	
	//Directions de Parcours
	public static final int DIRECTION_HORIZONTALE = 1;
	public static final int DIRECTION_VERTICALE = 2;
	public static final int DIRECTION_OBLIQUE_GAUCHE_DROITE = 3;
	public static final int DIRECTION_OBLIQUE_DROITE_GAUCHE= 4;
	
	public static final double SCORE_MAX_NON_DEFINI = 100000000;
	public static final double SCORE_MIN_NON_DEFINI = -100000000;
	//public static final double SCORE_MAX_NON_DEFINI = Double.NEGATIVE_INFINITY;
	//public static final double SCORE_MIN_NON_DEFINI = Double.POSITIVE_INFINITY;
	public static final int COUP_NON_DEFINI = -1;
	
	public static final int MIN = 0;
	public static final int MAX = 1;
	
	
	//Paramètres IA
	public static final double COEF_IA = 1;
	public static final double COEF_OTHER = 1;
	
	public static final int STRATEGIE_DEPART = 5;
	
	public static final int SCORE_4 = 10000;
	public static final int SCORE_3 = 100;
	public static final int SCORE_2 = 10;
	public static final int SCORE_1 = 1;
	public static final int SCORE_BONUS = 100;
	public static final int SCORE_STRATEGIE = 1000;
	public static final int SCORE_ODD_EVEN = 100;
	
	
}

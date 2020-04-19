package fr.uha.ensisa.puissance4.data;


import fr.uha.ensisa.puissance4.data.evaluation.Strategie;
import fr.uha.ensisa.puissance4.data.evaluation.strategies.StrategieGlobale;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class Grille {
	
	private Case[][] grille;

	public Grille() {
		grille = new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for (int i = 0; i < Constantes.NB_COLONNES; i++) {
			for (int j = 0; j < Constantes.NB_LIGNES; j++) {
				grille[i][j] = Case.V;
			}
		}
	}
	
	/**
	 * Constructeur permettant de créer une copie de la grille donnée en argument
	 * 
	 * @param original
	 * @author Yassine & Robin
	 */
	private Grille(Grille original)
	{		
		grille = new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for(int i=0;i<Constantes.NB_COLONNES;i++) {
			for(int j=0;j<Constantes.NB_LIGNES;j++)
			{
				grille[i][j] = original.getCase(j, i);
			}
		}
	}
	
	/**
	 * Renvoie le contenu de la case aux coordonnées données en argument
	 * @param ligne
	 * @param colonne
	 * @return
	 */
	public Case getCase(int ligne, int colonne)
	{
		try {
			return grille[colonne][ligne];
		}
		catch (Exception e) {
			return null;
		}
	}

	/**
	 * Indique s'il y a encore de la place dans la colonne indiquée
	 * @param colonne
	 * @return
	 */
	public boolean isCoupPossible(int colonne) {
		if(colonne>=0&&colonne<Constantes.NB_COLONNES)
		{
			return grille[colonne][Constantes.NB_LIGNES-1]==Case.V;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Ajoute le symbole indiqué dans la colonne indiquée
	 * ce qui permet de jouer ce coup
	 * @param colonne
	 * @param symbole
	 */
	public void ajouterCoup(int colonne, Case symbole) {
		for(int j=0;j<Constantes.NB_LIGNES;j++)
		{
			if(grille[colonne][j] == Case.V)
			{
				grille[colonne][j]= symbole;
				break;
			}
		}
		
	}
	
	/**
	 * Renvoie l'état de la partie
	 * @param symboleJoueurCourant
	 * @param tour
	 * @return
	 */
	public int getEtatPartie(Case symboleJoueurCourant, int tour)
	{
		int victoire;
		if(symboleJoueurCourant==Constantes.SYMBOLE_J1)
		{
			victoire=Constantes.VICTOIRE_JOUEUR_1;
		}
		else
		{
			victoire=Constantes.VICTOIRE_JOUEUR_2;
		}
		int nbAlignes=0;
		//Vérification alignement horizontaux
		for(int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				if(nbAlignes==4)
				{
					return victoire;
				}
			}
			nbAlignes=0;
		}
		//Vérification alignement verticaux
		for(int j=0;j<Constantes.NB_COLONNES;j++)
		{
			for(int i=0;i<Constantes.NB_LIGNES;i++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				if(nbAlignes==4)
				{
					return victoire;
				}
			}
			nbAlignes=0;
		}
		
		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=0;j<Constantes.NB_COLONNES-3;j++)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j+x<Constantes.NB_COLONNES;x++)
				{
					if(grille[j+x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					if(nbAlignes==4)
					{
						return victoire;
					}
				}
				nbAlignes=0;
			}
		
		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=Constantes.NB_COLONNES-1;j>=3;j--)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j-x>=0;x++)
				{
					if(grille[j-x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					if(nbAlignes==4)
					{
						return victoire;
					}
				}
				nbAlignes=0;
			}
		
		if(tour==Constantes.NB_TOUR_MAX)
		{
			return Constantes.MATCH_NUL;
		}
		
		return Constantes.PARTIE_EN_COURS;
	}
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// EVALUATION
	///////////////////////////////////////////////////////////////////////////
	
	
	// VERSION 4
	/**
	 * Donne un score à la grille en fonction du joueur 
	 * @param symboleJoueurCourant
	 * @return
	 */
	public double evaluer(Case symboleJoueurCourant, int tourExplore)
	{
		Strategie strategie = new StrategieGlobale(this, symboleJoueurCourant, tourExplore);		
		return strategie.getUtility2();
	}
	
	
	
	// VERSION 3
	/**
	 * Donne un score à la grille en fonction du joueur 
	 * @param symboleJoueurCourant
	 * @return
	 */
	public double evaluer(Case symboleJoueurCourant)
	{
		Strategie strategie = new StrategieGlobale(this, symboleJoueurCourant);		
		return strategie.getUtility();
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////

	
	// VERSION 2
	/*
	private double evaluerJoueur_test1(Case symboleJoueurCourant) {
		double score = 0;
		
		//Vérification alignement horizontaux
		// Parcourt des 6 lignes
		for(int i=0; i < Constantes.NB_LIGNES; i++)
		{
			// Parcours de 4 possiblilités
			for(int j=0; j < Constantes.NB_COLONNES-Constantes.NB_JETONS_VICTOIRE+1; j++)
			{
				Combinaison combinaison = new CombinaisonHorizontale(symboleJoueurCourant);
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symboleCourant = this.getCase(i, j+k);
					combinaison.addSymbole(symboleCourant);
					//System.out.println("ligne : "+j+" et colonne : "+(i+k));
				}
				combinaison.addBonus(this.getCase(i, j+Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				//System.out.println(combinaison.getNbCombinaison());
				//System.out.println("colonne : "+j+" et ligne : "+i);
				//System.out.println(combinaison.toString());
			}
		}
		
		//Vérification alignement verticaux
		//Parcours des 3 lignes
		for(int i=0;i<Constantes.NB_LIGNES-Constantes.NB_JETONS_VICTOIRE+1;i++) {
			
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				Combinaison combinaison = new CombinaisonVerticale( symboleJoueurCourant);
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symboleCourant = this.getCase(i+k, j);
					combinaison.addSymbole(symboleCourant);
				}
				combinaison.addBonus(this.getCase(i+Constantes.NB_JETONS_VICTOIRE, j));
				score += combinaison.getScore();
				//System.out.println(combinaison.getNbCombinaison());
				//System.out.println("colonne : "+j+" et ligne : "+i);
				//System.out.println(combinaison.toString());
			}
		}
		
		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		//Parcours des 3 lignes
		for(int i=0;i<Constantes.NB_LIGNES-Constantes.NB_JETONS_VICTOIRE+1;i++) {
			// Parcours des 4 colonnes
			for(int j=0; j < Constantes.NB_COLONNES-Constantes.NB_JETONS_VICTOIRE+1; j++) {				
				Combinaison combinaison = new CombinaisonObliqueGaucheDroite(symboleJoueurCourant);
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symboleCourant = this.getCase(i+k, j+k);
					combinaison.addSymbole(symboleCourant);
				}
				combinaison.addBonus(this.getCase(i+Constantes.NB_JETONS_VICTOIRE, j+Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				//System.out.println(combinaison.getNbCombinaison());
				//System.out.println("colonne : "+j+" et ligne : "+i);
				//System.out.println(combinaison.toString());
			}
		}
		

		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		// Parcours des 3 lignes possibles
		for(int i=0;i<Constantes.NB_LIGNES-Constantes.NB_JETONS_VICTOIRE+1;i++)
			// Parcours des 4 colonnes en sens inverse
			for(int j=Constantes.NB_COLONNES-1;j>=Constantes.NB_COLONNES-Constantes.NB_JETONS_VICTOIRE;j--)
			{
				Combinaison combinaison = new CombinaisonObliqueDroiteGauche(symboleJoueurCourant);
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symboleCourant = this.getCase(i+k, j-k);
					combinaison.addSymbole(symboleCourant);
				}
				combinaison.addBonus(this.getCase(i+Constantes.NB_JETONS_VICTOIRE, j-Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				//System.out.println(combinaison.getNbCombinaison());
				//System.out.println("colonne : "+j+" et ligne : "+i);
				//System.out.println(combinaison.toString());
			}		
		return score; 
	}
	
	
	private double basicScoreAlignement(int nb_jetons_combin, boolean bonus) {
		double score = 0;
		if(nb_jetons_combin==4)
		{
			score += 1000;
		}
		if(nb_jetons_combin==3)
		{
			score += 100;
			if (bonus) {
				score += 400;
			}			
		}
		if(nb_jetons_combin==2)
		{
			score += 10;
		}
		if(nb_jetons_combin==1)
		{
			score += 1;
		}
		return score;
	}
	*/
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	// VERSION 1
	/*
	private double evaluerJoueur(Case symboleJoueurCourant) {
		double score = 0;
		
		int nbAlignes=0;
		//Vérification alignement horizontaux
		for(int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				
				score += basicScoreAlignement(nbAlignes);
			}
			nbAlignes=0;
		}
		//Vérification alignement verticaux
		for(int j=0;j<Constantes.NB_COLONNES;j++)
		{
			for(int i=0;i<Constantes.NB_LIGNES;i++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				
				score += basicScoreAlignement(nbAlignes);
			}
			nbAlignes=0;
		}
		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=0;j<Constantes.NB_COLONNES-3;j++)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j+x<Constantes.NB_COLONNES;x++)
				{
					if(grille[j+x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					
					score += basicScoreAlignement(nbAlignes);
				}
				nbAlignes=0;
			}
		
		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=Constantes.NB_COLONNES-1;j>=3;j--)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j-x>=0;x++)
				{
					if(grille[j-x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					
					score += basicScoreAlignement(nbAlignes);
				}
				nbAlignes=0;
			}
		
		return score; 
	}
	
	
	private double basicScoreAlignement(int nbAlignes) {
		double score = 0;
		if(nbAlignes==4)
		{
			score += 1000;
		}
		if(nbAlignes==3)
		{
			score += 100;
		}
		if(nbAlignes==2)
		{
			score += 10;
		}
		if(nbAlignes==1)
		{
			score += 1;
		}
		return score;
	}
	*/
	
	
	/**
	 * Clone la grille
	 */
	public Grille clone()
	{
		Grille copy = new Grille(this);
		return copy;
	}

}

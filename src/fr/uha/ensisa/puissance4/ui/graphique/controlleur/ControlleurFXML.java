package fr.uha.ensisa.puissance4.ui.graphique.controlleur;

import java.net.URL;
import java.util.ResourceBundle;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Humain;
import fr.uha.ensisa.puissance4.data.IA;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.jeu.Jeu;
import fr.uha.ensisa.puissance4.ui.Controlleur;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ControlleurFXML extends Controlleur implements Initializable {

	public ControlleurFXML() {
		super("Controlleur Graphique");
	}

	@FXML
	private ToggleGroup optionToggleGroup1;
	@FXML
	private ToggleGroup optionToggleGroup2;

	@FXML
	private RadioButton humain_j1;
	@FXML
	private RadioButton ia_j1;
	@FXML
	private RadioButton humain_j2;
	@FXML
	private RadioButton ia_j2;

	@FXML
	private GridPane ia_pane1;
	@FXML
	private GridPane ia_pane2;

	@FXML
	private Label nom_label_j1;
	@FXML
	private Label nom_label_j2;

	@FXML
	private TextField nom_field_j1;
	@FXML
	private TextField nom_field_j2;

	@FXML
	private TextField niveau_field_j1;
	@FXML
	private TextField niveau_field_j2;

	@FXML
	private ComboBox<String> algobox_j1;
	@FXML
	private ComboBox<String> algobox_j2;

	@FXML
	private Button button_start;

	@FXML
	private VBox parametres_pane;
	@FXML
	private HBox buttons;

	@FXML
	private GridPane joueur_pane1;
	@FXML
	private GridPane joueur_pane2;

	@FXML
	private HBox icone_pane_1;
	@FXML
	private HBox icone_pane_2;

	@FXML
	private Label message_start;
	@FXML
	private Label message_tour;
	@FXML
	private Label message_partie_1;
	@FXML
	private Label message_partie_2;

	@FXML
	private GridPane gridP4_pane;

	private boolean is_j1_humain = true;
	private boolean is_j2_humain = false;
	private Joueur joueur1 = null;
	private Joueur joueur2 = null;
	private boolean isPartieActive = false;
	private int coup = -1;
	private Jeu jeu = null;
	private Partie partie = null;

	/*
	 * public ControlleurFXML getControlleurFXML() { return this; }
	 */

	public Jeu getJeu() {
		return this.jeu;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		disableButtons(buttons);
		initialiserAlgos(algobox_j1);
		initialiserAlgos(algobox_j2);
		initialiserIcone(icone_pane_1, Constantes.JETON_JOUEUR_1);
		initialiserIcone(icone_pane_2, Constantes.JETON_JOUEUR_2);

		optionToggleGroup1.selectedToggleProperty()
				.addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
					if (newValue == humain_j1) {
						is_j1_humain = true;
						ia_pane1.setVisible(false);
					} else if (newValue == ia_j1) {
						is_j1_humain = false;
						ia_pane1.setVisible(true);
					}
				});
		optionToggleGroup2.selectedToggleProperty()
				.addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
					if (newValue == humain_j2) {
						is_j2_humain = true;
						ia_pane2.setVisible(false);
					} else if (newValue == ia_j2) {
						is_j2_humain = false;
						ia_pane2.setVisible(true);
					}
				});
	}

	// GESTION DES BOUTONS
	@FXML
	public void commencerPartie(ActionEvent event) {
		cleanGrille();

		// Création Joueur 1
		if (is_j1_humain) {
			joueur1 = creerHumain(nom_field_j1.getText(), 1);
		} else {
			joueur1 = creerIA(nom_field_j1.getText(), 1, algobox_j1.getValue().toString(), niveau_field_j1.getText());
		}

		// Création Joueur 2
		if (joueur1 != null) {
			if (is_j2_humain) {
				joueur2 = creerHumain(nom_field_j2.getText(), 2);
			} else {
				joueur2 = creerIA(nom_field_j2.getText(), 2, algobox_j2.getValue().toString(),
						niveau_field_j2.getText());
			}
		}

		// Démarrage du jeu
		if (joueur1 != null && joueur2 != null) {
			message_start.setFont(Font.font("System", FontWeight.BOLD, 30));
			message_partie_1.setText("");
			disableParametres(parametres_pane);
			this.jeu = new Jeu(joueur1, joueur2, this);
			this.partie = jeu.getPartie();
			jeu.start();
		} else {
			// System.out.println("Les paramètres ne sont pas bons");
		}
	}

	@FXML
	private synchronized void jouerColonne(ActionEvent event) {
		if (this.coup == -1) {
			Button button = (Button) event.getSource();
			this.coup = Integer.parseInt(button.getText());
			notify();
		}
	}

	public Joueur creerHumain(String nom_joueur, int order) {
		Joueur joueur = null;
		if (!nom_joueur.isEmpty()) {
			joueur = new Humain(nom_joueur, order);
		} else {
			message_start.setText("Joueur " + order + " : saisir un nom");
		}
		return joueur;
	}

	public Joueur creerIA(String nom_joueur, int order, String algo, String level) {
		Joueur joueur = null;

		int algo_id = -1;
		if (!nom_joueur.isEmpty()) {
			int intLevel = isNiveauOK(level);
			if (intLevel < 1 || intLevel > 42) {
				message_start.setText("Joueur " + order + " : Nombre dans [1,42]");
				return null;
			} else {
				for (int j = 0; j < Constantes.IA_ALGOS.length; j++) {
					if (algo == Constantes.IA_ALGOS[j]) {
						algo_id = j;
					}
				}
				if (algo_id < 0 || algo_id > Constantes.IA_ALGOS.length) {
					message_start.setText("Joueur " + order + " : algorithme non défini");
				} else {
					joueur = new IA(nom_joueur, order, algo_id, intLevel);
				}
			}
		} else {
			message_start.setText("Joueur " + order + " : saisir un nom");
		}
		return joueur;
	}

	public static int isNiveauOK(String str) {
		int intLevel = 0;
		try {
			intLevel = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
		return intLevel;
	}

	public void initialiserAlgos(ComboBox<String> algos) {
		for (int j = 0; j < Constantes.IA_ALGOS.length; j++) {
			algos.getItems().addAll(Constantes.IA_ALGOS[j]);
		}
		algos.setValue("Alpha-Beta");
	}

	public void disableParametres(VBox parametres) {
		for (Node parametre : parametres.getChildren()) {
			if (parametre instanceof GridPane) {
				parametre.setDisable(true);
			}
			if (parametre instanceof Button) {
				parametre.setDisable(true);
			}
		}
	}

	public void disableButtons(HBox buttons) {
		for (Node button : buttons.getChildren()) {
			button.setDisable(true);
		}
	}

	public void enableButtons(HBox buttons) {
		for (Node button : buttons.getChildren()) {
			button.setDisable(false);
		}
	}

	@Override
	public void lancementPartie(Joueur joueur1, Joueur joueur2) {
		nom_label_j1.setText(joueur1.getNom() + " (" + joueur1.getTypeNom() + ")");
		nom_label_j2.setText(joueur2.getNom() + " (" + joueur2.getTypeNom() + ")");
		if (!isPartieActive) {
			message_start.setText("C'est parti !");
		} else {
			message_start.setText("C'est reparti !");
		}
		button_start.setText("Recommencer");
		isPartieActive = true;
	}

	@Override
	public void lancementTour(int tour, Joueur joueurCourant, Grille grille) {
		// System.out.println("Tour " + tour);
		message_start.setText("A " + joueurCourant.getNom() + " de jouer !");
		message_tour.setText("Tour " + tour);
	}
	
	
	public void cleanGrille() {
		gridP4_pane.getChildren().removeAll(gridP4_pane.getChildren());
	}

	/*
	public void addJeton(String path_img_jeton, int ligne, int colonne) {
		Image img_jeton = new Image(getClass().getClassLoader().getResource(path_img_jeton).toString(), true);
		ImageView jeton = new ImageView(img_jeton);
		jeton.setFitHeight(100.0);
		jeton.setFitWidth(100.0);
		gridP4_pane.add(jeton, colonne, Constantes.NB_LIGNES - 1 - ligne);
	}
	*/
	/*
	public void initialiserIcone(HBox paneIcone, String path_img_jeton) {
		Image img_jeton = new Image(getClass().getClassLoader().getResource(path_img_jeton).toString(), true);
		ImageView jeton = new ImageView(img_jeton);
		jeton.setFitHeight(25.0);
		jeton.setFitWidth(25.0);
		paneIcone.getChildren().add(jeton);
	}
	*/
	
	public void initialiserIcone(HBox paneIcone, String path_img_jeton) {
		Image img_jeton = new Image(getClass().getClassLoader().getResource(path_img_jeton).toString(), true);
		ImageView jeton = new ImageView(img_jeton);
		jeton.setFitHeight(25.0);
		jeton.setFitWidth(25.0);
		paneIcone.getChildren().add(jeton);
	}

	
	public void resetBouton() {
		this.coup = -1;
	}

	@Override
	public void afficherCoup(Joueur joueurCourant, int coup, long t) {
		// System.out.println(joueurCourant.getNom() + " a joué en colonne " + (coup+1)
		// + " après " + timeToString(t));
		message_partie_2.setText(message_partie_1.getText());
		message_partie_1
				.setText(joueurCourant.getNom() + " a joué en colonne " + (coup + 1) + " après " + timeToString(t));
	}

	@Override
	public void afficherFinPartie(Partie partie) {
		StringBuilder msgVainqueur = new StringBuilder();
		switch (partie.getEtatPartie()) {
		case Constantes.VICTOIRE_JOUEUR_1:
			msgVainqueur.append("VICTOIRE " + partie.getJoueur1().getNom());
			break;
		case Constantes.VICTOIRE_JOUEUR_2:
			msgVainqueur.append("VICTOIRE " + partie.getJoueur2().getNom());
			break;
		default:
			msgVainqueur.append("MATCH NUL");
			break;
		}

		message_start.setFont(Font.font("System", FontWeight.BOLD, 35));
		message_start.setText(msgVainqueur.toString());
		message_tour.setFont(Font.font(28));
		message_tour.setText(" en " + (partie.getTour() - 1) + " tours");

		message_partie_2.setFont(Font.font(30));
		message_partie_1.setText("Temps de reflexion de " + partie.getJoueur1().getNom() + " : "
				+ timeToString(partie.getTempsReflexionJ1()));
		message_partie_2.setText("Temps de reflexion de " + partie.getJoueur2().getNom() + " : "
				+ timeToString(partie.getTempsReflexionJ2()));

	}

	@Override
	public synchronized int getHumanCoup(String nom) {
		// message_partie.setText(nom + " réfléchit ...");
		enableButtons(buttons);
		while (this.coup == -1) {
			try {
				wait();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
		disableButtons(buttons);
		return this.coup;
	}

	@Override
	public void reflexionIA(String nom) {
		// message_partie_1.setText(nom + " réfléchit ...");
	}

	public void jouerCoupGraphique(int colonne, long tempsReflexion) {
		Grille grille = this.partie.getGrille();
		if (!grille.isCoupPossible(colonne)) {
			message_partie_1.setText("Impossible de mettre le jeton dans cette colonne");
			System.out.println("COUP INVALIDE !");
		} else {
			if (partie.getJoueurCourant() == partie.getJoueur1()) {
				this.ajouterCoupGraphique(grille, colonne, Constantes.SYMBOLE_J1);
				partie.setTempsReflexionJoueur1(partie.getTempsReflexionJ1() + tempsReflexion);
				partie.verificationFinPartie();
				partie.setJoueurCourant(partie.getJoueur2());
			} else {
				this.ajouterCoupGraphique(grille, colonne, Constantes.SYMBOLE_J2);
				partie.setTempsReflexionJoueur2(partie.getTempsReflexionJ2() + tempsReflexion);
				partie.verificationFinPartie();
				partie.setJoueurCourant(partie.getJoueur1());
			}
			partie.nextTour();
		}
	}

	public void ajouterCoupGraphique(Grille grille, int colonne, Case symboleJoueur) {
		for (int j = 0; j < Constantes.NB_LIGNES; j++) {
			if (grille.getCase(j, colonne) == Case.V) {
				grille.addCase(j, colonne, symboleJoueur);
				switch (symboleJoueur) {
				case X:
					//addJeton(Constantes.JETON_JOUEUR_1, j, colonne);
					break;
				case O:
					//addJeton(Constantes.JETON_JOUEUR_2, j, colonne);
					break;
				default:
					break;
				}
				break;
			}
		}
	}

}

package fr.uha.ensisa.puissance4.ui.graphique.controlleur;

import java.net.URL;
import java.util.ResourceBundle;

import fr.uha.ensisa.puissance4.data.Humain;
import fr.uha.ensisa.puissance4.data.IA;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

public class Controlleur implements Initializable {
	
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
    private ComboBox algobox_j1;
    @FXML
    private ComboBox algobox_j2;
    
    @FXML
    private Button button_start;
    
    @FXML
    private Label message_start;

    
    
    private boolean is_j1_humain = true;
    private boolean is_j2_humain = false;
    private Joueur joueur1 = null;
    private Joueur joueur2 = null;
    private boolean isPartieActive = false;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initialiserAlgos(algobox_j1);
		initialiserAlgos(algobox_j2);
		
		
		optionToggleGroup1.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (newValue == humain_j1) {
                is_j1_humain = true;
                ia_pane1.setVisible(false);
            } else if (newValue == ia_j1) {
            	is_j1_humain = false;
            	ia_pane1.setVisible(true);
            }
        });
		optionToggleGroup2.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) -> {
            if (newValue == humain_j2) {
                is_j2_humain = true;
                ia_pane2.setVisible(false);
            } else if (newValue == ia_j2) {
            	is_j2_humain = false;
            	ia_pane2.setVisible(true);
            }
        });
		
	}
	
	
	
	public void commencerPartie(ActionEvent event) {
		// Création Joueur 1
		if (is_j1_humain) {
			joueur1 = creerHumain(nom_field_j1.getText(), 1);
		}
		else {
			joueur1 = creerIA(nom_field_j1.getText(), 1, algobox_j1.getValue().toString(), niveau_field_j1.getText());
		}
		
		// Création Joueur 2
		if (joueur1 != null) {
			nom_label_j1.setText(joueur1.getNom()+" ("+joueur1.getTypeNom()+")");
			
			if (is_j2_humain) {
				joueur2 = creerHumain(nom_field_j2.getText(), 2);
			} else {
				joueur2 = creerIA(nom_field_j2.getText(), 2, algobox_j2.getValue().toString(), niveau_field_j2.getText());
			}
		}
		
		// Affichage Paramètres Joueurs
		if (joueur1 != null && joueur2 != null) {
			nom_label_j1.setText(joueur1.getNom()+" ("+joueur1.getTypeNom()+")");
			nom_label_j2.setText(joueur2.getNom()+" ("+joueur2.getTypeNom()+")");
			if (!isPartieActive) {
				message_start.setText("C'est parti !");
			}
			else {
				message_start.setText("C'est reparti !");
			}			
			
			button_start.setText("Recommencer");
			isPartieActive = true;
			
			System.out.println("Jeu démarré");
		}		
		else {
			//System.out.println("Les paramètres ne sont pas bons");
		}		
	}	
	
	
	public Joueur creerHumain(String nom_joueur, int order) {
		Joueur joueur = null;
		if (!nom_joueur.isEmpty()) {
			joueur = new Humain(nom_joueur, order);
		}
		else {
			message_start.setText("Joueur "+order+" : saisir un nom");
		}
		return joueur;
	}
	
	
	public Joueur creerIA(String nom_joueur, int order, String algo, String level) {
		Joueur joueur = null;
		
		int algo_id = -1;
		if (!nom_joueur.isEmpty()) {
			int intLevel = isNiveauOK(level);
			if (intLevel < 1 || intLevel > 42) {
				System.out.println("Niveau entre 1 et 42");
				message_start.setText("Joueur "+order+" : Nombre dans [1,42]");
				return null;
			}
			else {				
				for (int j = 0; j < Constantes.IA_ALGOS.length; j++) {
					if (algo == Constantes.IA_ALGOS[j]) {
						algo_id = j;
					}
				}				
				if (algo_id < 0 || algo_id > Constantes.IA_ALGOS.length) {
					message_start.setText("Joueur "+order+" : algorithme non défini");
				}
				else {
					joueur = new IA(nom_joueur, order, algo_id, intLevel);
				}								
			}			
		}
		else {
			message_start.setText("Joueur "+order+" : saisir un nom");
		}		
		return joueur;
	}
	
	
	public static int isNiveauOK(String str) {
		int intLevel = 0;
		try {
			intLevel = Integer.parseInt(str);		    
		} catch(NumberFormatException e){
			return 0;  
		}
		return intLevel;  
	}
	
	
	public void initialiserAlgos(ComboBox algos) {
		for (int j = 0; j < Constantes.IA_ALGOS.length; j++) {
			algos.getItems().addAll(Constantes.IA_ALGOS[j]);
		}
		algos.setValue("Alpha-Beta");
	}
	
	
	/*
	@FXML
    public void handleButtonClose(ActionEvent actionEvent) {
        System.exit(0);
    }
	*/

}

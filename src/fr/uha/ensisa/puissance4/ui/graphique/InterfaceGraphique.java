package fr.uha.ensisa.puissance4.ui.graphique;

import java.io.IOException;
import java.net.URL;

import fr.uha.ensisa.puissance4.ui.graphique.controlleur.Controlleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InterfaceGraphique extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
		      // Localisation du fichier FXML.
		      final URL url = getClass().getResource("vue/VuePuissance4.fxml");
		      // Création du loader.
		      final FXMLLoader fxmlLoader = new FXMLLoader(url);
		      // Chargement du FXML.
		      final AnchorPane root = (AnchorPane) fxmlLoader.load();
		      // Récupération du Controller
		      Controlleur controller = (Controlleur) fxmlLoader.getController();
		      // Création de la scène.
		      final Scene scene = new Scene(root);
		      primaryStage.setScene(scene);
		      
		} catch (IOException ex) {
			System.err.println("Erreur au chargement: " + ex);
		}
		
		primaryStage.setTitle("Puissance 4 - Yassine & Robin");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

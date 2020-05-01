package fr.uha.ensisa.puissance4.ui.sample;

import fr.uha.ensisa.puissance4.data.*;
import fr.uha.ensisa.puissance4.jeu.JeuAppUi;
import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class UIConnect4 extends Application {

    private Controller controller;
    public static AnchorPane panel;
    public static BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        //this.controller = loader.getController();
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Puissance 4");
        primaryStage.setScene(new Scene(root,970,680));
        //primaryStage.setScene(new Scene(root, 970, 680));
        showView();
        initializeGame();
        primaryStage.show();
    }

    private void initializeGame() {
        String nom_IA = "Robin & Yassine";
        String nom_adversaire = "L'Ennemi";
        int algo_IA = Constantes.IA_MINIMAX;
        //int algo_IA = Constantes.IA_ALPHABETA;
        int niveauIA = 7;
        Joueur j1 = new IA(nom_IA, 1, algo_IA, niveauIA);
        Joueur j2 = new Humain(nom_adversaire, 2);
        JeuAppUi jui = new JeuAppUi(j1,j2,this.controller,new Console());
        //Jeu jeu = new Jeu(j1, j2, );
        //jeu.start();
        jui.start();
    }


    public void showView() {

        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(UIConnect4.class.getResource("sample.fxml"));
            panel = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            /*rootLayout.setCenter(panel);

            cb = new ChoiceBox(FXCollections.observableArrayList(
                    "Humain Vs Humain", "IA Vs Humain","IA Vs IA")
            );
            cb.setLayoutX(25);
            cb.setLayoutY(95);
            panel.getChildren().add(cb);

            algo1= new ChoiceBox(FXCollections.observableArrayList(
                    Constantes.IA_ALGOS[0], Constantes.IA_ALGOS[1]));
            algo1.setLayoutX(50);
            algo1.setLayoutY(415);
            panel.getChildren().add(algo1);

            algo2= new ChoiceBox(FXCollections.observableArrayList(
                    Constantes.IA_ALGOS[0], Constantes.IA_ALGOS[1]));
            algo2.setLayoutX(50);
            algo2.setLayoutY(475);
            panel.getChildren().add(algo2);
*/
            this.controller = loader.getController();
            controller.setMainApp(this);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }


}

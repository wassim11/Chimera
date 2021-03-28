/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ahmed Ezzine
 */
public class Slowlife extends Application {

    
    public static Stage stage = null;

    @Override
    public void start(Stage stage) throws Exception {

stage.setTitle("SlowLife");
       Parent root = FXMLLoader.load(getClass().getResource("/slowlifejava/gui/users/AccueilPage.fxml"));
       Scene scene=new Scene(root);
       stage.setScene(scene);
       stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class SidebarController implements Initializable {

    @FXML
    private BorderPane bp;
    private Button btnIngredient;
    @FXML
    private AnchorPane ap;
    private Button btnRecette;
    private Button btnRecetteIngredient;
    private Button btnSuivi;
    @FXML
    private Button btnRegime;
    @FXML
    private Button btnPublication;
    @FXML
    private Button btnEvenement;
    @FXML
    private Button btnCentre;
    @FXML
    private Button btnFeedback;
    @FXML
    private Button btnUtilisateur;
    @FXML
    private ImageView signip;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    private void LoadPage(String page){
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        }catch(IOException ex)
        {
            System.out.println("error de transition "+ex);
        }
        bp.setCenter(root);
    }
   /* private void ActionIngredient(ActionEvent event) {
        if(event.getSource()==btnIngredient)
            LoadPage("Ingredient/Ingredient");
    }

    private void ActionRecette(ActionEvent event) {    
        
    }

    private void ActionComplet(ActionEvent event) {
         if(event.getSource()==btnRecetteIngredient)
        LoadPage("");
    }

    private void ActionSuivi(ActionEvent event) {
         if(event.getSource()==btnSuivi)
        LoadPage("Suivi/SuiviRegime");
    }
*/
    @FXML
    private void SMRegime(ActionEvent event) {
        LoadPage("Recette/admin/RecetteAdmin");
    }

    @FXML
    private void SMPublication(ActionEvent event) {
    }

    @FXML
    private void SMEvenement(ActionEvent event) {
        LoadPage("/slowlifejava/gui/Home");
    }

    @FXML
    private void SMCentres(ActionEvent event) {
    }

    @FXML
    private void SMFeedback(ActionEvent event) {
    }

    @FXML
    private void SMUtilisateur(ActionEvent event) {
    }
   
   
   

}

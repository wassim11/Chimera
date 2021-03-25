/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    @FXML
    private ImageView sousmenu1;
    @FXML
    private ImageView sousmenu2;
    @FXML
    private ImageView sousmenu3;
    @FXML
    private ImageView sousmenu4;
    @FXML
    private ImageView sousmenu5;

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
    private void SMCentres(ActionEvent event) throws FileNotFoundException {
         int i=1; //a changer
        InputStream stream;
              if (i==1)
              {stream = new FileInputStream("src/imageSideBar/centre.png");
                Image image = new Image(stream);
                 sousmenu1.setImage(image);
                 stream = new FileInputStream("src/image/serviceIcon.png");
                image = new Image(stream);
                 sousmenu2.setImage(image);
                 stream = new FileInputStream("src/image/RdvIcon.png");
                image = new Image(stream);
                 sousmenu3.setImage(image);
                 stream = new FileInputStream("src/image/staticon.png");
                image = new Image(stream);
                 sousmenu4.setImage(image);
                 stream = new FileInputStream("src/image/c.png");
                image = new Image(stream);
                 sousmenu5.setImage(image);
                 sousmenu1.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/FXMLDocument");});
                 sousmenu2.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/gestionService");});
                 sousmenu3.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/AfficherRendezvous");});
                 sousmenu4.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/StatCentreService");});
                 
              }
              else if (i==2)
              {stream = new FileInputStream("src/imageSideBar/centre.png");
                Image image = new Image(stream);
                 sousmenu1.setImage(image);
                 
                 stream = new FileInputStream("src/image/RdvIcon.png");
                image = new Image(stream);
                 sousmenu2.setImage(image);
                 stream = new FileInputStream("src/image/staticon.png");
                image = new Image(stream);
                 sousmenu3.setImage(image);
                 stream = new FileInputStream("src/image/c.png");
                image = new Image(stream);
                 sousmenu4.setImage(image);
                 sousmenu1.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/FrontC");});
                 sousmenu2.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/GestionRendezvous");});
                 sousmenu3.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/StatPrixService");});
            
              }
    }

    @FXML
    private void SMFeedback(ActionEvent event) {
    }

    @FXML
    private void SMUtilisateur(ActionEvent event) {
    }
   
   
   

}

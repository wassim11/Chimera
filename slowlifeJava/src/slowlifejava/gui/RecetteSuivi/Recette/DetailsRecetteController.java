/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Recette;

import entities.RecetteSuivi.IngredientRecette;
import entities.RecetteSuivi.Recette;
//import Services.RecetteSuivi.ServiceIngredientRecette;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import slowlifejava.services.RecetteSuivi.ServiceIngredientRecette;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class DetailsRecetteController implements Initializable {

    @FXML
    private Label Titre;
    @FXML
    private ImageView image;
    @FXML
    private ScrollPane ingredient;
    @FXML
    private ScrollPane Description;
    
    
    private int id;
    @FXML
    private Label Type;
    @FXML
    private Label calories;
    @FXML
    private ImageView imagecoach;
    @FXML
    private Label nomcoach;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        id=45;
        try {
            Interface();// TODO
                    } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(DetailsRecetteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
     private ImageView initializeImages(InputStream URL,int longueur,int largeur){
      Image img = new Image(URL);
      ImageView imageview= new ImageView();
      imageview.setImage(img);
      imageview.setFitHeight(longueur);
      imageview.setFitWidth(largeur);
      return imageview;
    }
    private void Interface() throws SQLException, FileNotFoundException
    {
        ServiceIngredientRecette SIR = new ServiceIngredientRecette();
        List<IngredientRecette> LIR = SIR.readAll(new Recette(id));
        
        
        VBox ListeIngredient = new VBox();
        
        
        Label DescriptionR = new Label();
        
        Label Title = new Label();
        int Calories=0;
        for(IngredientRecette IR : LIR)
        {
            
            
            Label lb = new Label();
            InputStream stream = new FileInputStream(IR.getIng().getImage());
            lb.setGraphic(initializeImages(stream,50,50));
            lb.setText(IR.getQuantite()+"g "+IR.getIng().getNom());
            lb.setPrefSize(200, 50);
            lb.setPadding(new Insets(10));
            ListeIngredient.getChildren().add(lb);
            
            
            Calories+=(IR.getQuantite()*IR.getIng().getCalories());
           
                
        }
         System.out.println("Calories"+Calories);
         
         InputStream streamR = new FileInputStream(LIR.get(0).getRct().getImage());
         image.setImage(new Image(streamR));
         
        
         
         Titre.setText(LIR.get(0).getRct().getNomRecette());
         Titre.setPrefSize(200, 50);
         Titre.setAlignment(Pos.CENTER);
         
         DescriptionR.setText(LIR.get(0).getRct().getDescription());
         
         
        Description.setContent(DescriptionR);
        ingredient.setContent(ListeIngredient);
        
    }
    
    
}

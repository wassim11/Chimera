/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Recette;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import slowlifejava.entities.RecetteSuivi.IngredientRecette;
import slowlifejava.entities.RecetteSuivi.Recette;
import slowlifejava.services.RecetteSuivi.RecetteService;
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
    @FXML
    private Label Type;
    @FXML
    private Label calories;
    @FXML
    private ImageView imagecoach;
    @FXML
    private Label nomcoach;
    private int idr;
    private int Calories;
    @FXML
    private AnchorPane anchorid;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         Calories=0;
    }    
     private ImageView initializeImages(InputStream URL,int longueur,int largeur){
      Image img = new Image(URL);
      ImageView imageview= new ImageView();
      imageview.setImage(img);
      imageview.setFitHeight(longueur);
      imageview.setFitWidth(largeur);
      return imageview;
    }
    public void Interface(int id) throws SQLException, FileNotFoundException
    {
        ServiceIngredientRecette SIR = new ServiceIngredientRecette();
        System.out.println("id=" +id);
        idr=id;
        List<IngredientRecette> LIR = SIR.readAll(new Recette(id));

        VBox ListeIngredient = new VBox();
  
        Label DescriptionR = new Label();
        
        Label Title = new Label();
        
        for(IngredientRecette IR : LIR)
        {
            Label lb = new Label();
            InputStream stream = new FileInputStream(IR.getIng().getImage());
            lb.setGraphic(initializeImages(stream,50,50));
            lb.setText(IR.getQuantite()+IR.getIng().getUnite().split(" ")[1]+" "+IR.getIng().getNom());
            lb.setPrefSize(200, 50);
            lb.setPadding(new Insets(10));
            lb.setFont(new Font("Arial", 12));
            ListeIngredient.getChildren().add(lb);
            
            System.out.println("qtt="+IR.getQuantite()/100+"* calorie"+ IR.getIng().getCalories()+"="+ IR.getQuantite()*IR.getIng().getCalories());
            Calories+=((IR.getQuantite()*IR.getIng().getCalories())/Integer.parseInt(IR.getIng().getUnite().split("\\s+")[0]));       
        }
         calories.setText(String.valueOf(Calories)+"KCal");
         Type.setText(LIR.get(0).getRct().getTypeRecette());
         System.out.println("Calories"+Calories);
         
         InputStream streamR = new FileInputStream(LIR.get(0).getRct().getImage());
         image.setImage(new Image(streamR));

         Titre.setText(LIR.get(0).getRct().getNomRecette());
         Titre.setAlignment(Pos.CENTER);
         
         DescriptionR.setText(LIR.get(0).getRct().getDescription());
         DescriptionR.setFont(new Font("Arial", 16));
        Description.setContent(DescriptionR);
  
        ingredient.setContent(ListeIngredient);
        
    } 
        


    @FXML
    private void pdf(MouseEvent event) throws IOException, SQLException {
        RecetteService SR = new RecetteService();
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("src"));
        Stage stage = (Stage) anchorid.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        //PDF.pdfGeneration(file.getAbsolutePath()+"/"+SR.readone(idr).getNomRecette(),idr);
    }
}


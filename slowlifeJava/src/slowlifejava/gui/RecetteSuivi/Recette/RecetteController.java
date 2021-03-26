/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Recette;

import entities.RecetteSuivi.IngredientRecette;
import entities.RecetteSuivi.Recette;
import entities.RecetteSuivi.User;
//import Services.RecetteSuivi.RecetteService;
//import Services.RecetteSuivi.ServiceIngredientRecette;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import slowlifejava.services.RecetteSuivi.RecetteService;
import slowlifejava.services.RecetteSuivi.ServiceIngredientRecette;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class RecetteController implements Initializable {

    
    @FXML
    private ScrollPane sp;
    
    private User user;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       user = new User(1,"Yassine");
        try {
            afficher();
            //Image img = new Image("Ingredient.png");
            //SM1.setImage(img);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(RecetteController.class.getName()).log(Level.SEVERE, null, ex);
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
    
public void afficher() throws  IOException, SQLException
{
      RecetteService SR = new RecetteService();
   
      ServiceIngredientRecette SIR = new ServiceIngredientRecette(); 
      
      List<Recette> LI = SR.readAllClient();
      HBox Recette= new HBox();  
      Recette.setSpacing(50);
      for(int i=0;i<LI.size();i++){
      VBox Liste = new VBox();
      InputStream stream = new FileInputStream(LI.get(i).getImage());
      ImageView imageView=initializeImages(stream,150,150);
      Label Titre = new Label(LI.get(i).getNomRecette()); 
      
        Titre.setBackground(new Background(
        new BackgroundFill(
            Color.color(
                Color.YELLOW.getRed(), 
                Color.YELLOW.getGreen(), 
                Color.YELLOW.getBlue(), 0.4d),
            new CornerRadii(5), 
            null)));
        
    Titre.setTextAlignment(TextAlignment.RIGHT);
    Titre.setWrapText(true);
    Titre.setPrefSize(150,10);
    Titre.setAlignment(Pos.CENTER);
    Titre.setPadding(new Insets(5,5,5,5));
    Liste.getChildren().addAll(imageView,Titre);
      Liste.setOnMouseClicked(openDetail->{
          try {
              GoToDetails();
          } catch (IOException ex) {
              Logger.getLogger(RecetteController.class.getName()).log(Level.SEVERE, null, ex);
          }
          
      });
      Liste.setOnMouseEntered(event->{
          PopOver popOver;
          System.out.println("Titre="+Titre.getText());
          System.out.println("souris dans le champs");
             List<IngredientRecette> LIR = new ArrayList<>();
          try {
              System.out.println(Titre.getText());
             LIR = SIR.readAll(SR.RechercheParNom(new Recette(Titre.getText())));
          } catch (SQLException ex) {
              Logger.getLogger(RecetteController.class.getName()).log(Level.SEVERE, null, ex);
              
          }
           });  
          /*
      VBox Recettecomplette = new VBox(); 
      VBox Recettes = new VBox(); 
      Label TitlePop = new Label(LIR.get(0).getRct().getNomRecette());
      Label DescriptionPop = new Label(LIR.get(0).getRct().getDescription());
      Recettes.getChildren().add(TitlePop);
      for(int j=0;j<LIR.size();j++){
      System.out.println(LIR.get(j));
      VBox Ingredients = new VBox();    
      Label IngredientsPop = new Label(LIR.get(j).getQuantite()+" "+LIR.get(j).getIng().getNom());
      Ingredients.getChildren().add(IngredientsPop); 
      Recettes.getChildren().add(Ingredients);
      }
       Recettes.getChildren().add(DescriptionPop);
      popOver = new PopOver(Recettes);
      popOver.show(Liste);
      
          
        Liste.setOnMouseExited(Event->{
          System.out.println("souris pas dans le champs");
          popOver.hide();
     
         
      });*/
     
      Recette.getChildren().addAll(Liste);   
      
      }
      sp.setContent(Recette);
}
  private void GoToDetails() throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("DetailsRecette.fxml"));
    // Parent root = FXMLLoader.load(getClass().getResource("try.fxml"));
     Scene scene = new Scene(root);
     Stage stage = new Stage();
     stage.setScene(scene);
     stage.setTitle("Details");
     stage.show();
    }



    /* VBox Liste = new VBox();
      Liste.setSpacing(50);
      Liste.setAlignment(Pos.CENTER);
      
      ServiceIngredientRecette SIR = new ServiceIngredientRecette();
      List<IngredientRecette> LI=SIR.readAll2();
      
      for(int i=0;i<LI.size();i++){
      HBox Recette= new HBox();  
      Recette.setSpacing(15);
      Image image = new Image(LI.get(i).getRct().getImage());
      ImageView imageView= new ImageView();
      imageView.setImage(image);
      imageView.setFitHeight(100);
      imageView.setFitWidth(100);
      
      Label Titre = new Label(LI.get(i).getRct().getNomRecette());
      Label Ingredients = new Label(LI.get(i).getIng().getNom());
      Label Description = new Label(LI.get(i).getRct().getDescription());

      Recette.getChildren().addAll(imageView,Titre,Ingredients,Description);   
      Liste.getChildren().addAll(Recette);*/

//copie

/*public void afficher() throws  IOException, SQLException
{
      ServiceIngredientRecette SIR = new ServiceIngredientRecette();
      //List<IngredientRecette> LI=SIR.readAll2();
      RecetteService SR = new RecetteService();
      for(int j=1; j<SR.readAllId().size();j++){
      VBox Liste = new VBox();
      Liste.setSpacing(50);
      Liste.setAlignment(Pos.CENTER);
      List<IngredientRecette> LI = SIR.readAll(SR.readAllId().get(j));
      
      VBox ListeIngredient = new VBox();
      ListeIngredient.setSpacing(10);
      for(int i=0;i<LI.size();i++){
      HBox Recette= new HBox();  
      Recette.setSpacing(50);
      Image image = new Image(LI.get(i).getRct().getImage());
      
      ImageView imageView= new ImageView();
      imageView.setImage(image);
      imageView.setFitHeight(100);
      imageView.setFitWidth(100);
      
      Label Titre = new Label(LI.get(i).getRct().getNomRecette()); 
      Label Ingredients = new Label(LI.get(i).getIng().getNom());
      ListeIngredient.getChildren().add(Ingredients);
      Recette.getChildren().addAll(imageView,Titre,ListeIngredient);   
      Liste.getChildren().addAll(Recette);
      }
      Ap.getChildren().add(Liste);
}*/

    @FXML
    private void Entrées(ActionEvent event) {
    }

    @FXML
    private void Plats(ActionEvent event) {
    }

    @FXML
    private void Desserts(ActionEvent event) {
    }

    @FXML
    private void Amuses_bouches(ActionEvent event) {
    }

    @FXML
    private void Sauces(ActionEvent event) {
    }

    @FXML
    private void Accompagnements(ActionEvent event) {
    }

    @FXML
    private void Boissons(ActionEvent event) {
    }


}

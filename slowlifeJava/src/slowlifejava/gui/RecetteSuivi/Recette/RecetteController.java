/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Recette;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entities.users.Utilisateur;
import slowlifejava.entities.RecetteSuivi.IngredientRecette;
import slowlifejava.entities.RecetteSuivi.Recette;
//import slowlifejava.entities.RecetteSuivi.User;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import slowlifejava.entities.RecetteSuivi.Ingredient;
import slowlifejava.services.RecetteSuivi.RecetteService;
import slowlifejava.services.RecetteSuivi.ServiceIngredientRecette;
import slowlifejava.services.users.UserService;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class RecetteController implements Initializable {

    
    @FXML
    private ScrollPane sp;
    
    private Utilisateur user;
    @FXML
    private JFXTextField avecingredient;
    @FXML
    private JFXTextField sansingredient;
    @FXML
    private JFXTextField ingredient1;
    @FXML
    private JFXTextField ingredient2;
    
    private String NomRecette;
    @FXML
    private JFXButton btnrecherche1;
    @FXML
    private JFXButton btnrecherche2;
    
    private List<String> Ingredients;
    static int id;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        try {
         UserService us = new UserService();
         user = us.getUserlogged();
        } catch (SQLException ex) {
            Logger.getLogger(RecetteController.class.getName()).log(Level.SEVERE, null, ex);
        }
       NomRecette="";
       Ingredients= new ArrayList<>();
        try {
            RecetteService SR = new RecetteService();
            afficher(SR.readAllClient(new Recette("Entrée")));
        } catch (SQLException | IOException ex) {
            System.out.println("error initialize");
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

public void afficher(List<Recette> LI) throws  IOException, SQLException
{
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(25,35,25,35));
    gridPane.setHgap(20);
    gridPane.setVgap(30);
    for(int i=0;i<3;i++)
    {
      for(int j=i;j<LI.size();j+=3){
      VBox Liste = new VBox();
      HBox titresend = new HBox();
      InputStream stream = new FileInputStream(LI.get(j).getImage());
      Label Titre = new Label(LI.get(j).getNomRecette()); 
      Titre.setFont(new Font("Cambria", 14));
      ImageView img = new ImageView(new Image("/Gui/RecetteSuivi/Recette/images/sms.png"));
      img.prefWidth(40);
      img.prefHeight(40);
    
     img.setOnMouseClicked(event->{
         
         if(EnvoyerRecetteController.count==0){
          try {
              RecetteService SR = new RecetteService();
              Recette rc = SR.RechercheParNom(new Recette(Titre.getText()));
              id=rc.getIdRecette();
          } catch (SQLException ex) {
              System.out.println("Instanciation RecetteService ");
          }
     FXMLLoader loader = new FXMLLoader(getClass().getResource("EnvoyerRecette.fxml"));
     Parent root;  
          try {
               root = (Parent) loader.load();
               Stage stage = new Stage();
               stage.setScene(new Scene(root));
               stage.show();
          } catch (IOException ex) {
              System.out.println("erreur load page envoyerRecette 1");
          }
         }
         else
         {
             EnvoyerRecetteController.getInstance().closePage();
             try {
              RecetteService SR = new RecetteService();
              Recette rc = SR.RechercheParNom(new Recette(Titre.getText()));
              System.out.println(rc.getIdRecette());
          } catch (SQLException ex) {
                 System.out.println("Instanciation RecetteService ");
          }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EnvoyerRecette.fxml"));  
          try {
               Parent root = (Parent) loader.load();
               Stage stage = new Stage();
               stage.setScene(new Scene(root));
               stage.show();
          } catch (IOException ex) {
              System.out.println("erreur load page envoyerRecette 2");
          }
            
         }
      });
      
        Rectangle rectangle = new Rectangle(0, 0,250,225);
        rectangle.setArcWidth(30.0);   // Corner radius
        rectangle.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(new Image(stream,220, 195,  false, false));
        rectangle.setFill(pattern);
        rectangle.setEffect(new DropShadow(20, Color.ALICEBLUE));  // Shadow
        
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
    Titre.setPrefSize(210,40);
    Titre.setAlignment(Pos.CENTER);
    Titre.setPadding(new Insets(5,5,5,5));
      titresend.getChildren().addAll(Titre,img);
    Liste.getChildren().addAll(rectangle,titresend);
    
    rectangle.setOnMouseClicked(openDetail->{
          try {
              NomRecette=Titre.getText();
              GoToDetails();
          } catch (IOException|SQLException ex) {
              System.out.println("erreur passage recette -> details");
          }
          
      }); 
        Titre.setOnMouseClicked(openDetail->{
          try {
              NomRecette=Titre.getText();
              GoToDetails();
          } catch (IOException|SQLException ex) {
              System.out.println("erreur passage recette -> details");
          } 
      }); 
            
             gridPane.add(Liste,i,j/3);
             System.out.println("i="+i+"j="+j/3 +" "+  LI.get(j).getNomRecette());          
        }
    }
   
    sp.setContent(gridPane);
        
}

/*
public void afficher(List<Recette> LI) throws  IOException, SQLException
{
    
    
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(25,135,25,135));
    gridPane.setHgap(20);
    gridPane.setVgap(30);
    for(int i=0;i<2;i++)
    {
      for(int j=i;j<LI.size();j+=2){
      VBox Liste = new VBox();
      InputStream stream = new FileInputStream(LI.get(j).getImage());
      Label Titre = new Label(LI.get(j).getNomRecette()); 
      
      
        Rectangle rectangle = new Rectangle(0, 0,300,255);
        rectangle.setArcWidth(30.0);   // Corner radius
        rectangle.setArcHeight(30.0);

        ImagePattern pattern = new ImagePattern(new Image(stream, 225, 270, false, false));
        rectangle.setFill(pattern);
        rectangle.setEffect(new DropShadow(20, Color.ALICEBLUE));  // Shadow
        
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
    Titre.setPrefSize(300,10);
    Titre.setAlignment(Pos.CENTER);
    Titre.setPadding(new Insets(5,5,5,5));
    
    Liste.getChildren().addAll(rectangle,Titre);
    Liste.setOnMouseClicked(openDetail->{
          try {
              NomRecette=Titre.getText();
              GoToDetails();
          } catch (IOException|SQLException ex) {
              System.out.println("erreur passage recette -> details");
          }
          
      }); 
            
             gridPane.add(Liste,i,j/2);
             System.out.println("pair i="+i+"j="+j/2 +" "+  LI.get(j).getNomRecette());
                
       
                
                 
                
                
                
        }
    }
   
    sp.setContent(gridPane);
        
}
*/

     private void GoToDetails() throws IOException, SQLException {
     FXMLLoader loader = new FXMLLoader(getClass().getResource("DetailsRecette.fxml"));
     RecetteService RS = new RecetteService();
     Parent root = (Parent) loader.load();
     DetailsRecetteController DRC = loader.getController();    
     DRC.Interface(RS.RechercheParNom(new Recette(NomRecette)).getIdRecette());
     Scene scene = new Scene(root);
     Stage stage = new Stage();
     stage.setScene(scene);
     stage.setTitle("Details");
     stage.show();
    }

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


    @FXML
    private void Entrées() throws SQLException, IOException {
      RecetteService SR = new RecetteService();
      List<Recette> LI = SR.readAllClient(new Recette("Entrée"));
      afficher(LI);
    }

    @FXML
    private void Plats() throws IOException, SQLException {
      RecetteService SR = new RecetteService();
      List<Recette> LI = SR.readAllClient(new Recette("Plat"));
      afficher(LI);
    }
    @FXML
    private void Desserts() throws IOException, SQLException {
      RecetteService SR = new RecetteService();
      List<Recette> LI = SR.readAllClient(new Recette("Dessert"));
      afficher(LI);
    }

    @FXML
    private void Amuses_bouches() throws IOException, SQLException {
      RecetteService SR = new RecetteService();
      List<Recette> LI = SR.readAllClient(new Recette("Amuse bouche"));
      afficher(LI);
    }

    @FXML
    private void Sauces() throws IOException, SQLException {
      RecetteService SR = new RecetteService();
      List<Recette> LI = SR.readAllClient(new Recette("Sauce"));
      afficher(LI);
    }

    @FXML
    private void Accompagnements() throws SQLException, IOException {
      RecetteService SR = new RecetteService();
      List<Recette> LI = SR.readAllClient(new Recette("Accompagnement"));
      afficher(LI);
    }
    @FXML
    private void Boissons() throws SQLException, IOException {
      RecetteService SR = new RecetteService();
      List<Recette> LI = SR.readAllClient(new Recette("Boisson"));
      afficher(LI);
    }

    @FXML
    private void recherche1(ActionEvent event) throws IOException, SQLException {
        ServiceIngredientRecette SIR = new ServiceIngredientRecette();
        List<String> Recettes1 = new ArrayList<>();
        List<String> Recettes2 = new ArrayList<>();
        List<Recette> Recette = new ArrayList<>();
        Recette.clear();
        RecetteService SR = new RecetteService();
        if(avecingredient.getText().isEmpty() && sansingredient.getText().isEmpty())
        {
           //DO NOT SHOW
        }
        else
        {
            if(!avecingredient.getText().isEmpty() && sansingredient.getText().isEmpty())
            {
                Recettes1=SIR.readAllByIngredient(new Ingredient(avecingredient.getText()));
                for(int i=0;i<Recettes1.size();i++)
                {
                    String nom = Recettes1.get(i);
                    Recette id = SR.RechercheParNom(new Recette(nom));
                    Recette.add(id);
                }
                System.out.println(Recette);
                afficher(Recette); 
                
            }
            if(!sansingredient.getText().isEmpty() && avecingredient.getText().isEmpty())
            {
                System.out.println("sansIngredient= "+sansingredient);
                Recettes2=SIR.readAllByNotIngredient(new Ingredient(sansingredient.getText()));
                for(int i=0;i<Recettes2.size();i++)
                {
                    String nom = Recettes2.get(i);
                    Recette id = SR.RechercheParNom(new Recette(nom));
                    Recette.add(id);
                }
                System.out.println(Recette);
                afficher(Recette); 
            }
            if(!sansingredient.getText().isEmpty() && !avecingredient.getText().isEmpty())
            {
                Recettes1=SIR.readAllByIngredient(new Ingredient(avecingredient.getText()));
                Recettes2=SIR.readAllByNotIngredient(new Ingredient(sansingredient.getText()));
                List<String> common = new ArrayList<>(Recettes1);
                common.retainAll(Recettes2);
                for(int i=0;i<common.size();i++)
                {
                    String nom = Recettes1.get(i);
                    Recette id = SR.RechercheParNom(new Recette(nom));
                    Recette.add(id);
                    
                }
                System.out.println(Recette);
                afficher(Recette);
            }
                
        }
    }

    @FXML
    private void recherche2(ActionEvent event) throws SQLException, IOException {
        ServiceIngredientRecette SIR = new ServiceIngredientRecette();
        List<String> Recettes1 = new ArrayList<>();
        List<String> Recettes2 = new ArrayList<>();
        List<Recette> Recette = new ArrayList<>();
        Recette.clear();
        RecetteService SR = new RecetteService();
        if(ingredient1.getText().isEmpty() && ingredient2.getText().isEmpty())
        {
              //DO NOT SHOW
        }
        else
        {
            if(!ingredient1.getText().isEmpty() && ingredient2.getText().isEmpty())
            {
               
                Recettes1=SIR.readAllByIngredient(new Ingredient(ingredient1.getText()));
                for(int i=0;i<Recettes1.size();i++)
                {
                    String nom = Recettes1.get(i);
                    Recette id = SR.RechercheParNom(new Recette(nom));
                    Recette.add(id);
                }
                System.out.println(Recette);
                afficher(Recette); 
                
            }
             if(!ingredient2.getText().isEmpty() && ingredient1.getText().isEmpty())
            {
               Recettes2=SIR.readAllByIngredient(new Ingredient(ingredient2.getText()));
                 for(int i=0;i<Recettes2.size();i++)
                {
                    String nom = Recettes1.get(i);
                    Recette id = SR.RechercheParNom(new Recette(nom));
                    Recette.add(id);
                }
                 afficher(Recette); 
            }
            if(!ingredient1.getText().isEmpty() && !ingredient2.getText().isEmpty())
            {
                 Recettes1=SIR.readAllByIngredient(new Ingredient(ingredient1.getText()));
                 Recettes2=SIR.readAllByIngredient(new Ingredient(ingredient2.getText()));
                 List<String> common = new ArrayList<>(Recettes1);
                 common.retainAll(Recettes2);
                 System.out.println(common);
                 for(int i=0;i<common.size();i++)
                {
                    String nom = Recettes1.get(i);
                    Recette id = SR.RechercheParNom(new Recette(nom));
                    Recette.add(id);
                    
                }
                 System.out.println(Recette);
                  afficher(Recette);
                
            }
        }      
}


}

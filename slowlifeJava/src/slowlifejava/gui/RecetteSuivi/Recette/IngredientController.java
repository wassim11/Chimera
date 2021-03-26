/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Recette;

import entities.RecetteSuivi.Ingredient;
import entities.RecetteSuivi.IngredientRecette;
import entities.RecetteSuivi.Recette;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import slowlifejava.services.RecetteSuivi.IngredientService;
import slowlifejava.services.RecetteSuivi.ServiceIngredientRecette;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class IngredientController implements Initializable {

    @FXML
    private JFXTextField tfNom;
    @FXML
    private ImageView btnInsert;
    @FXML
    private ImageView btnUpdate;
    @FXML
    private ImageView btnDelete;
    @FXML
    private ScrollPane spIngredient;
    @FXML
    private AnchorPane anchorid;
    @FXML
    private JFXSlider Calorie;
    @FXML
    private JFXTextField qtt;
    @FXML
    private TextField tfImage;
    @FXML
    private JFXListView<Label> listNom;
    @FXML
    private JFXButton ajouter;
    @FXML
    private JFXListView<Label> listRecette;
    @FXML
    private JFXButton btnvalider;
    
    
    static List<String> liste;
    private VBox Area;
    private String filePath;
    private int id;
    private boolean modifier=false;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          Area = new VBox();
          
        try {
            showListeNom();
            showIngredient();
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(IngredientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void showListeNom() throws FileNotFoundException
    {
          try {
            IngredientService IS = new IngredientService();
            List<Ingredient> LI = IS.readAll();
            for(Ingredient I : LI)
            {
                Label Image = new Label();
                Image.setGraphic(InitializeImageStream(new FileInputStream(I.getImage())));
                Image.setText(I.getNom());
                listNom.getItems().add(Image);
            }
        } catch (SQLException ex) {
              System.out.println("error");
        }
    }
    @FXML
    private void InsertButtonAction(MouseEvent event) throws SQLException, FileNotFoundException {
        IngredientService IS = new IngredientService();
        if(!IS.Recherche(new Ingredient(tfNom.getText()))){
        IS.ajouter(new Ingredient(tfNom.getText(),filePath,(int)Calorie.getValue()));
        showIngredient();
        }
    }

    @FXML
    private void UpdateButtonAction(MouseEvent event) throws SQLException, FileNotFoundException {
        IngredientService IS = new IngredientService();
        IS.update(new Ingredient(id,tfNom.getText(),filePath,(int)Calorie.getValue()));
        showIngredient();
    }

    @FXML
    private void DeleteButtonAction(MouseEvent event) throws SQLException, FileNotFoundException {
        IngredientService IS = new IngredientService();
        IS.delete(new Ingredient(id));
        showIngredient();
    }
      private ImageView initializeImages(InputStream URL,int longueur,int largeur){
      Image Poids = new Image(URL);
      ImageView imageview= new ImageView();
      imageview.setImage(Poids);
      imageview.setFitHeight(longueur);
      imageview.setFitWidth(largeur);
      return imageview;
    }
        private void showIngredient() throws FileNotFoundException, SQLException{
            VBox Liste = new VBox();
            Liste.setSpacing(2);
            IngredientService IS = new IngredientService();
            List<Ingredient> list=IS.readAll();
            
            for(int i=0;i<list.size();i++){
             HBox Ligne = new HBox();
             Ligne.setSpacing(5);
                
             HBox NomImage = new HBox();
             NomImage.setSpacing(2);
             
            Ligne.setPrefSize(360, 50);
            Ligne.setPadding(Insets.EMPTY);
            Ligne.setBackground(new Background(
            new BackgroundFill(
            Color.color(
                Color.YELLOW.getRed(), 
                Color.YELLOW.getGreen(), 
                Color.YELLOW.getBlue(), 0.4d),
                new CornerRadii(5), null)));
             InputStream stream = new FileInputStream(list.get(i).getImage());
             ImageView imageRecette = initializeImages(stream,50,50);
             
             Label Titre = new Label(list.get(i).getNom());
             Titre.setPrefSize(200,65);
             Titre.setAlignment(Pos.CENTER);
             
             NomImage.getChildren().addAll(imageRecette,Titre);
             
             Label calorie = new Label(String.valueOf(list.get(i).getCalories()+" kcal/100g"));
             calorie.setPrefSize(200,75);
             calorie.setAlignment(Pos.CENTER);
             
             Ligne.getChildren().addAll(NomImage,calorie);
             Liste.setOnMouseClicked(click->{
                 
                 int nb = (int)click.getY()/69;
                 id=nb;
                 tfNom.setText(list.get(id).getNom());
                 tfImage.setText(list.get(id).getImage());
                 Calorie.setValue(list.get(id).getCalories());
              
                 
             });
             Liste.getChildren().add(Ligne);
            }
            spIngredient.setContent(Liste); 
        }

    @FXML
    private void GetPhoto(ActionEvent event) {
         final FileChooser filechooser = new FileChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
            );
        File file = filechooser.showOpenDialog(stage);
        if(file!=null)
        {   
            filePath=file.getAbsolutePath();
            //System.out.println("Path ="+file.getAbsolutePath());
           
        }
        if(file==null)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Image Alert");
            alert.setContentText("Please select an image");
            alert.setHeaderText(null);
            alert.show();   
        }
    }
    private ImageView InitializeImage(String URL)
    {
      Image image = new Image(URL);
      ImageView imageView= new ImageView(image);
      imageView.setFitHeight(20);
      imageView.setFitWidth(20);
      return imageView;
    }
    private ImageView InitializeImageStream(InputStream URL)
    {
      Image image = new Image(URL);
      ImageView imagev = new ImageView(image);
      imagev.setFitHeight(20);
      imagev.setFitWidth(20);
      return imagev;
    }
    @FXML
    private void ajouter() throws SQLException, FileNotFoundException {
        
        int Qt =0;
        try{
        Qt=Integer.parseInt(qtt.getText());      
        }
        catch(NumberFormatException ex)
        {
            System.out.println("pas d'input here");
        }
        if(Qt!=0 && listNom.getSelectionModel().getSelectedItem()!=null){
        //Liste 
        IngredientService IS = new IngredientService();
        String Ingr=Qt+"g "+listNom.getSelectionModel().getSelectedItem().getText();
        Label Ing = new Label();
        String img = IS.RechercheImageParNom(new Ingredient(listNom.getSelectionModel().getSelectedItem().getText()));
        Ing.setGraphic(InitializeImageStream(new FileInputStream(img)));
        Ing.setText(Ingr);
        listRecette.getItems().add(Ing);
        qtt.setText("");
        }
    }

    @FXML
    private void DeleteFromRecette(ActionEvent event) {
        int idd=listRecette.getSelectionModel().getSelectedIndex();
        listRecette.getItems().remove(idd);
    }

    @FXML
    private void Affecter(ActionEvent event) {
        List<String> Affecter= new ArrayList<>();
        for(int i=0;i<listRecette.getItems().size();i++){
        Affecter.add(listRecette.getItems().get(i).getText());
        }
        liste=Affecter;
        Stage stage = (Stage) btnvalider.getScene().getWindow();
        stage.close();
    }
    public void LoadRecette(int id) throws SQLException, FileNotFoundException
    {
        modifier=true;
        ServiceIngredientRecette SIR = new ServiceIngredientRecette();
        List<IngredientRecette> LIR = SIR.readAll(new Recette(id));
        if(!LIR.isEmpty())
        {
            for(IngredientRecette I : LIR)
            {
                Label Image = new Label();
                Image.setGraphic(InitializeImageStream(new FileInputStream(I.getIng().getImage())));
                Image.setText(I.getQuantite()+"g "+I.getIng().getNom());
                listRecette.getItems().add(Image);
            }
        }
    }
}

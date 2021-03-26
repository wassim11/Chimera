/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Recette;

import entities.RecetteSuivi.Ingredient;
import entities.RecetteSuivi.IngredientRecette;
import entities.RecetteSuivi.Recette;
import entities.RecetteSuivi.User;
//import Services.RecetteSuivi.IngredientService;
//import Services.RecetteSuivi.RecetteService;
//import Services.RecetteSuivi.ServiceIngredientRecette;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
import javax.swing.JOptionPane;
import slowlifejava.services.RecetteSuivi.IngredientService;
import slowlifejava.services.RecetteSuivi.RecetteService;
import slowlifejava.services.RecetteSuivi.ServiceIngredientRecette;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class RecettecoachController implements Initializable {

    @FXML
    private TextField tfNom;
    @FXML
    private JFXTextArea tfDesc;
    @FXML
    private ImageView btnInsert;
    @FXML
    private ImageView btnUpdate;
    @FXML
    private ImageView btnDelete;
    @FXML
    private TextField recherche;
    @FXML
    private CheckBox Description;
    @FXML
    private CheckBox nom;
    @FXML
    private AnchorPane anchorid;
    @FXML
    private CheckBox type;
    @FXML
    private ScrollPane sp;
    @FXML
    private JFXComboBox<String> cbType;
    
    private int Tri;
    List<String> Liste;
    private User coach;
    private String filePath;
    private int SelectedId; 
    @FXML
    private ImageView image_recette;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         //new FadeIn(nom).play();
         filePath="";
         Liste = new ArrayList<>();
         coach = new User(1, "Yassine");
         ObservableList<String> options = FXCollections.observableArrayList("Entrée","Plat","Dessert","Amuse bouche","Sauce","Accompagnement","Boisson");
         cbType.getItems().addAll(options);
        try{
            showRecette(trie(Tri));
            ToutTypeTri();
            //searchRecette();
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(RecettecoachController.class.getName()).log(Level.SEVERE, null, ex);
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
      private void SetInformations(Recette rct)
      {
          SelectedId=rct.getIdRecette();
          tfNom.setText(rct.getNomRecette());
          cbType.getSelectionModel().select(rct.getTypeRecette());
          tfDesc.setText(rct.getDescription());
          filePath = rct.getImage();
      }
        private void showRecette(List<Recette> list) throws FileNotFoundException, SQLException{
            VBox listes = new VBox();
            listes.setSpacing(1);
            ServiceIngredientRecette SIR = new ServiceIngredientRecette();
            
            
            for(int i=0;i<list.size();i++){
             HBox Ligne = new HBox();
             Ligne.setSpacing(1);
                
             HBox NomImage = new HBox();
             NomImage.setSpacing(2);
             
            Ligne.setPrefSize(620, 75);
            Ligne.setPadding(Insets.EMPTY);
            Ligne.setBackground(new Background(
            new BackgroundFill(
            Color.color(
                Color.YELLOW.getRed(), 
                Color.YELLOW.getGreen(), 
                Color.YELLOW.getBlue(), 0.4d),
                new CornerRadii(5), null)));
             InputStream stream = new FileInputStream(list.get(i).getImage());
             ImageView imageRecette = initializeImages(stream,75,75);
             
             Label Titre = new Label(list.get(i).getNomRecette());
             Titre.setPrefSize(150,75);
             Titre.setAlignment(Pos.CENTER);
             
             NomImage.getChildren().addAll(imageRecette,Titre);
             
             Label TypeRecette = new Label(list.get(i).getTypeRecette());
             TypeRecette.setPrefSize(100,75);
             TypeRecette.setAlignment(Pos.CENTER);
             List<IngredientRecette> LIR= SIR.readAll(new Recette(list.get(i).getIdRecette()));
             VBox ListeIngredients = new VBox();
                for (IngredientRecette LIR1 : LIR) {
                     Label Ingredient = new Label((int)LIR1.getQuantite()+"g "+LIR1.getIng().getNom());
                     Ingredient.setPrefSize(200, 75/LIR.size());
                     Ingredient.setAlignment(Pos.CENTER);
                     Ingredient.setTextFill(Color.DARKORANGE);
                     ListeIngredients.getChildren().add(Ingredient);
                     
                }
            Label Status = new Label();
            Status.setPrefSize(75, 75);
            if(list.get(i).getEtat().equals("En Attente")){
                Status.setGraphic(new ImageView(new Image("/Gui/buttons/enattente.png")));             
            }
            if(list.get(i).getEtat().equals("Refuser")){
                Status.setGraphic(new ImageView(new Image("/Gui/buttons/refuser.png")));
            }
            if(list.get(i).getEtat().equals("Accepter")){
                Status.setGraphic(new ImageView(new Image("/Gui/buttons/accepter.png")));
            }
             Ligne.getChildren().addAll(NomImage,TypeRecette,ListeIngredients,Status);
             listes.setOnMouseClicked((MouseEvent open)->{
                 int numeroLigne = (int)(open.getY()/76);
                 SelectedId = list.get(numeroLigne).getIdRecette();
                 if(open.getClickCount()==1){ 
                 SetInformations(new Recette(list.get(numeroLigne).getIdRecette(),list.get(numeroLigne).getNomRecette(),list.get(numeroLigne).getDescription(),list.get(numeroLigne).getTypeRecette(),list.get(numeroLigne).getImage()));
                 }
                 if(open.getClickCount()==2)
                 {
                     try {
                         GoToIngredientEdit();
              
                         
                     } catch (SQLException | IOException ex) {
                         System.out.println("passage de page Recette->Ingredient Impossible");
                         System.out.println(ex);
                     }
                 }
                 
                 
             });
             listes.getChildren().add(Ligne);
             
            }
            sp.setContent(listes);
            
        }

      public List<Recette> trie(int n) throws SQLException
{
    // 1 pour Nom
    // 2 pour Type
    // 4 pour Description
    String requete = null;
    if(n==0)
    {
      requete="select * from `recette` where idcoach='"+coach.getId()+"'";
    }
    if(n==1)
    {
      requete="select * from recette where idcoach='"+coach.getId()+"' order by nomRecette";
    }
    if (n==2)
    {
      requete="select * from recette where idcoach='"+coach.getId()+"' order by typeRecette";
    }
    if(n==3)
    {
         requete="select * from recette where idcoach='"+coach.getId()+"' order by nomRecette asc,typeRecette asc";
    }
    if (n==4)
    {
        requete="select * from recette where idcoach='"+coach.getId()+"' order by description";
    }
    if (n==5)
    {
         requete="select * from recette where idcoach='"+coach.getId()+"' order by nomRecette asc,description asc";
    }
    if (n==6)
    {
        requete="select * from recette where idcoach='"+coach.getId()+"' order by description,typeRecette asc";
    }
    if (n==7)
    {
         requete="select * from recette where idcoach='"+coach.getId()+"' order by nomRecette asc,typeRecette asc,description asc";
    }
    RecetteService RS=new RecetteService();
    return RS.Tri(requete);
}
    public void ToutTypeTri(){
        type.setOnAction(event->{
            if(type.isSelected()){Tri+=2;}
        else{Tri-=2;}
        try{showRecette(trie(Tri));}catch(SQLException |FileNotFoundException e){System.out.println("error affichage dans tri type"+e);} 
        });
        Description.setOnAction((event) -> {
            if(Description.isSelected()){Tri+=4;}
        else{Tri-=4;}
              try{showRecette(trie(Tri));}catch(SQLException |FileNotFoundException e){System.out.println("error affichage dans tri type"+e);}
        });
        nom.setOnAction((event) -> {
           if(nom.isSelected()){Tri+=1;}
        else{Tri-=1;}
          try{showRecette(trie(Tri));}catch(SQLException |FileNotFoundException e){System.out.println("error affichage dans tri type"+e);}
        });
    }
 /*public void searchRecette() throws SQLException {
        RecetteService RS = new RecetteService();
        List<Recette> liste;
        liste =  trie(Tri);
        ObservableList<Recette> data = FXCollections.observableArrayList(liste);
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomRecette"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeRecette"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        tvRecette.setItems(data);
       
        FilteredList<Recette> filteredData = new FilteredList<>(data, b -> true);
        
       recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Recette rct) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                System.out.println("newvalue"+newValue);
                System.out.println("oldvalue="+oldValue);
                if (rct.getNomRecette().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches username
               
                }
                else {
                    return false; // Does not match.
                }
            });
        });
        SortedList<Recette> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvRecette.comparatorProperty());
        tvRecette.setItems(sortedData);
    }
*/
    @FXML
    private void UpdateRecette(MouseEvent event) throws SQLException, FileNotFoundException {
           RecetteService RS=new RecetteService();
           System.out.println(IngredientController.liste);
           ServiceIngredientRecette SIR = new ServiceIngredientRecette();
           SIR.deleteAll(new Recette(SelectedId));
           System.out.println("id="+SelectedId + " nom=" + tfNom.getText() + " desc="+tfDesc.getText()+" type="+cbType.getValue()+" image="+filePath);
           Recette rct=new Recette(SelectedId,tfNom.getText(),tfDesc.getText(),cbType.getValue(),filePath,"En Attente");
           System.out.println(rct);
           RS.update(rct);
           showRecette(trie(Tri));   
        }

    @FXML
    private void InsertRecette(MouseEvent event) throws SQLException, FileNotFoundException {
          RecetteService RS=new RecetteService();
          Recette rct=new Recette(tfNom.getText(),tfDesc.getText(),cbType.getValue(),filePath,"En Attente",coach);
          RS.ajouter(rct);
               
               System.out.println(IngredientController.liste);
               ServiceIngredientRecette SIR = new ServiceIngredientRecette();
               IngredientService IS = new IngredientService();
               for(int i=0;i<IngredientController.liste.size();i++)
               {
               String[] str=IngredientController.liste.get(i).split("g ");
               for(int j=0,k=1; j<str.length && k<str.length;j+=2,k+=2)
                { System.out.println("j="+j+" "+Float.parseFloat(str[j])+" "+k);
                  SIR.ajouter(new IngredientRecette(RS.RechercheParNom(new Recette(tfNom.getText())), new Ingredient(IS.RechercheParNom(new Ingredient(str[k]))),Integer.parseInt(str[j])));
                }
               }
               tfNom.setText("");
               cbType.getSelectionModel().select("");
               tfDesc.setText("");
               filePath="";
               showRecette(trie(Tri));
               JOptionPane.showMessageDialog(null,"Ajout reussis");
    }
    @FXML
    private void DeleteRecette(MouseEvent event) throws SQLException, FileNotFoundException {
           RecetteService RS=new RecetteService();
           RS.delete(new Recette(SelectedId));
           showRecette(trie(Tri));
    }

    @FXML
    private void recherche(KeyEvent event) throws SQLException {
       // searchRecette();
    }
    @FXML
    private void GetPhoto() throws IOException, InterruptedException {
        final FileChooser filechooser = new FileChooser();
        Stage stage = (Stage) anchorid.getScene().getWindow();
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                 new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
            );
        File file = filechooser.showOpenDialog(stage);
        System.out.println("Path ="+file);
        if(file!=null)
        {   
            filePath=file.getAbsolutePath();
            image_recette.setImage(new Image(new FileInputStream(filePath)));
        }
        if(file==null && "".equals(filePath))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Image Alert");
            alert.setContentText("Please select an image");
            alert.setHeaderText(null);
            alert.show();   
        }
    }
 
 /*private void PopUp() throws SQLException {
     
        
        Recette rct=tvRecette.getSelectionModel().getSelectedItem();
        TableView<Ingredient> tvb = new TableView();
        TableColumn Ingredients = new TableColumn("Ingredients");
        TableColumn Quantité = new TableColumn("Quantité");
        TableColumn btn = new TableColumn("");
        tvb.getColumns().addAll(Ingredients,Quantité,btn);
        
        IngredientService IS = new IngredientService();
        List<Ingredient> LIR=IS.readAll();
        ServiceIngredientRecette SIR = new ServiceIngredientRecette();
        System.out.println(tvRecette.getSelectionModel().getSelectedItem().getIdRecette());
        System.out.println(SIR.readAll(new Recette(tvRecette.getSelectionModel().getSelectedItem().getIdRecette())));
        
        ObservableList<Ingredient> data = FXCollections.observableArrayList(LIR);
        Ingredients.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Quantité.setCellValueFactory(new PropertyValueFactory<>("qtt"));
        btn.setCellValueFactory(new PropertyValueFactory<>(""));   
            

        Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>> cellFoctory = (TableColumn<Ingredient, String> param) -> {
            // make cell containing buttons
            final TableCell<Ingredient, String> cell = new TableCell<Ingredient, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        final Button Affecterbtn= new Button("Add");
                        Affecterbtn.setOnAction(event ->{
                                
                            int idRecette=tvRecette.getSelectionModel().getSelectedItem().getIdRecette();
                            int Quantite=Integer.parseInt(getTableView().getItems().get(getIndex()).getQtt().getText());
                            int id =getTableView().getItems().get(getIndex()).getId();
                            System.out.println("Ingredient ID="+id + " Quantité "+Quantite);
                            
                            try {
                                ServiceIngredientRecette SIR = new ServiceIngredientRecette();
                                SIR.ajouter(new IngredientRecette(new Recette(id), new Ingredient(idRecette), Quantite));
                            } catch (SQLException ex) {
                                Logger.getLogger(RecettecoachController.class.getName()).log(Level.SEVERE, null, ex);
                            }     
                      });
                        setGraphic(Affecterbtn);
                        setText(null);
                    }
                }

            };

            return cell;
        };

        btn.setCellFactory(cellFoctory);
        tvb.setItems(data);
        
        int x=LIR.size();
        Button btnexit = new Button("X");
        btnexit.setOpacity(0.75);
        HBox emptyhBox= new HBox();
        emptyhBox.setPrefWidth(170);
        
        HBox hBox= new HBox(emptyhBox,btnexit);
        TextField rechercher = new TextField();
        recherche.setPromptText("Recherche");
        
        VBox vBox = new VBox(hBox,rechercher,tvb);  
        vBox.setSpacing(2);
        vBox.setPrefHeight(100+(50*x));
        vBox.setPrefWidth(200);
     
        
        PopOver popOver = new PopOver(vBox);
        popOver.setTitle(tvRecette.getSelectionModel().getSelectedItem().getNomRecette());

        
           btnexit.setOnMouseClicked(Event->{
               popOver.hide();
           });
     
        popOver.show(tvRecette);
    }*/

    private void GoToIngredientEdit() throws IOException, SQLException {
     FXMLLoader loader = new FXMLLoader(getClass().getResource("Ingredient.fxml"));
     Parent root = (Parent) loader.load();
     IngredientController IC = loader.getController();    
     IC.LoadRecette(SelectedId);
     Stage stage = new Stage();
     stage.setScene(new Scene(root));
     stage.show();
    }
    
        @FXML
    private void GoToIngredients() throws IOException, SQLException {
     FXMLLoader loader = new FXMLLoader(getClass().getResource("Ingredient.fxml"));
     Parent root = (Parent) loader.load();  
     Stage stage = new Stage();
     stage.setScene(new Scene(root));
     stage.show();
    }
      


}


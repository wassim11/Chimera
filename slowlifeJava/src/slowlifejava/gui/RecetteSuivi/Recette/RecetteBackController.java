/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Recette;

import entities.RecetteSuivi.Recette;
//import Services.RecetteSuivi.RecetteService;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import slowlifejava.services.RecetteSuivi.RecetteService;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class RecetteBackController implements Initializable {

@FXML
    private TableColumn<Recette, String> colNom;
    @FXML
    private TableColumn<Recette, String> colType;
    @FXML
    private TableColumn<Recette, String> colDescription;
    @FXML
    private TableColumn<Recette, String> colImage;
    @FXML
    private TableView<Recette> tvRecette;
    @FXML
    private TableColumn<Recette,String> colDelete;
    @FXML
    private CheckBox nom;
    @FXML
    private CheckBox type;
    @FXML
    private CheckBox Description;
    private int Tri;
    @FXML
    private TextField tfrecherche;
    @FXML
    private TableColumn<Recette,String> colAccepter;
    @FXML
    private TableColumn<Recette,String> colRefuser;
    @FXML
    private TableColumn<Recette,String> colEtat;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Tri=0;
        System.out.println(url.getPath());
        ToutTypeTri();
        try {
              
            showRecette(trie(Tri));
            // TODO
        } catch (SQLException ex) {
            
        }
        try {
            searchRecette();
        } catch (SQLException ex) {
           
        }
    }    
      public void showRecette(List<Recette> list) throws SQLException
    {
      
        RecetteService RS=new RecetteService();
        ObservableList<Recette> listRecette = FXCollections.observableArrayList(list);
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomRecette"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeRecette"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        
        Callback<TableColumn<Recette, String>, TableCell<Recette, String>> cellFoctory = (TableColumn<Recette, String> param) -> {
            // make cell containing buttons
            final TableCell<Recette, String> cell = new TableCell<Recette, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        
                        Label Delete = new Label();
                        Delete.setGraphic(new ImageView(new Image("slowlifejava/gui/RecetteSuivi/buttons/delete1.png")));
                        Delete.setOnMouseClicked(event ->{
                          Recette rct=getTableView().getItems().get(getIndex());
                            try {
                                System.out.println(getTableView().getItems().get(getIndex()));
                                RS.delete(rct);
                                showRecette(trie(Tri));
                            } catch (SQLException ex) {
                                System.out.println("error here");
                            }
                      });
                        
                        setGraphic(Delete);
                        setText(null);
                    }
                }

            };

            return cell;
        };
                 Callback<TableColumn<Recette, String>, TableCell<Recette, String>> cellFactoryImage
                = //
                new Callback<TableColumn<Recette, String>, TableCell<Recette, String>>() {
            @Override
            public TableCell call(final TableColumn<Recette, String> param) {
                final TableCell<Recette, String> cell = new TableCell<Recette, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                             InputStream stream = null;
                            try {
                                stream = new FileInputStream(item);
                            } catch (FileNotFoundException ex) {
                                Logger.getLogger(RecetteBackController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            Image image = new Image(stream);
                            ImageView imagev = new ImageView(image);
                            imagev.setFitHeight(100);
                            imagev.setFitWidth(100);
                            setGraphic(imagev);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

         Callback<TableColumn<Recette, String>, TableCell<Recette, String>> cellFactoryAccepter
                = //
                new Callback<TableColumn<Recette, String>, TableCell<Recette, String>>() {
            @Override
            public TableCell call(final TableColumn<Recette, String> param) {
                final TableCell<Recette, String> cell = new TableCell<Recette, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                        Label Accepter = new Label();
                        Accepter.setGraphic(new ImageView(new Image("slowlifejava/gui/RecetteSuivi/buttons/accepter.png")));
                        Accepter.setOnMouseClicked(event ->{
                        Recette rct=getTableView().getItems().get(getIndex());
                            
                            try {
                                System.out.println(getTableView().getItems().get(getIndex()));
                                RS.AccepterRefuser(new Recette(getTableView().getItems().get(getIndex()).getIdRecette(),"Accepter"));
                                showRecette(trie(Tri));
                            } catch (SQLException ex) {
                                System.out.println("error here");
                            }
                      });
                        
                        setGraphic(Accepter);
                        setText(null);
                        }
                    }
                };
                return cell;
            }
        };
         Callback<TableColumn<Recette, String>, TableCell<Recette, String>> cellFactoryRefuser
                 =
                   new Callback<TableColumn<Recette, String>, TableCell<Recette, String>>() {
            @Override
            public TableCell call(final TableColumn<Recette, String> param) {
                final TableCell<Recette, String> cell = new TableCell<Recette, String>() {

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                        Label Refuser = new Label();
                        Refuser.setGraphic(new ImageView(new Image("slowlifejava/gui/RecetteSuivi/buttons/refuser.png")));
                        Refuser.setOnMouseClicked(event ->{
                          Recette rct=getTableView().getItems().get(getIndex());
                            try {
                                int idSelection=getTableView().getItems().get(getIndex()).getIdRecette();
                                //System.out.println(getTableView().getItems().get(getIndex()));
                                Recette rcte=new Recette(idSelection,"Refuser");
                                RS.AccepterRefuser(rcte);
                                showRecette(trie(Tri));
                            } catch (SQLException ex) {
                                System.out.println("error here");
                            }
                      });
                        
                        setGraphic(Refuser);
                        setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        colImage.setCellFactory(cellFactoryImage);
        colAccepter.setCellFactory(cellFactoryAccepter);
        colRefuser.setCellFactory(cellFactoryRefuser);
        colDelete.setCellFactory(cellFoctory);
        tvRecette.setItems(listRecette);
                }
      
      
    public List<Recette> trie(int n) throws SQLException
{
    // 1 pour Nom
    // 2 pour Type
    // 4 pour Description
    String requete = null;
    if(n==0)
    {
      requete="select * from `recette`";
    }
    if(n==1)
    {
      requete="select * from recette order by nomRecette";
    }
    if (n==2)
    {
      requete="select * from recette order by typeRecette";
    }
    if(n==3)
    {
         requete="select * from recette order by nomRecette asc,typeRecette asc";
    }
    if (n==4)
    {
        requete="select * from recette order by description";
    }
    if (n==5)
    {
         requete="select * from recette order by nomRecette asc,description asc";
    }
    if (n==6)
    {
        requete="select * from recette order by description,typeRecette asc";
    }
    if (n==7)
    {
         requete="select * from recette order by nomRecette asc,typeRecette asc,description asc";
    }
    RecetteService RS=new RecetteService();
    return RS.Tri(requete);
}
    public void ToutTypeTri(){
        type.setOnAction(event->{
            if(type.isSelected()){Tri+=2;}
        else{Tri-=2;}
        try{showRecette(trie(Tri));}catch(SQLException e){System.out.println("error affichage dans tri type"+e);}
        });
        Description.setOnAction((event) -> {
            if(Description.isSelected()){Tri+=4;}
        else{Tri-=4;}
              try{showRecette(trie(Tri));}catch(SQLException e){System.out.println("error affichage dans tri type"+e);}
        });
        nom.setOnAction((event) -> {
           if(nom.isSelected()){Tri+=1;}
        else{Tri-=1;}
          try{showRecette(trie(Tri));}catch(SQLException e){System.out.println("error affichage dans tri type"+e);}
        });
    }

    public void searchRecette() throws SQLException {
        RecetteService RS = new RecetteService();
        ArrayList<Recette> liste;
        liste = (ArrayList<Recette>) trie(Tri);
        ObservableList<Recette> data = FXCollections.observableArrayList(liste);
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomRecette"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeRecette"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        tvRecette.setItems(data);
       
        FilteredList<Recette> filteredData = new FilteredList<>(data, b -> true);
        
       tfrecherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Recette rct) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

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

    private void backtoMenu(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/Gui/Sidebar.fxml"));/* Exception */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

  /*  @FXML
    private void PopUp(Event event) throws SQLException {
        System.out.println("this is target "+event.getSource()+"\n");
        if(tvRecette.getSelectionModel().getSelectedItem()!=null){
        Recette rct=tvRecette.getSelectionModel().getSelectedItem();
        System.out.println("this is id"+rct.getIdRecette()+"\n");
        TableView<IngredientRecette> tvb = new TableView();
        TableColumn Ingredients = new TableColumn("Ingredients");
        TableColumn Quantité = new TableColumn("Quantité");
        tvb.getColumns().addAll(Ingredients,Quantité);
        
        ServiceIngredientRecette SIR = new ServiceIngredientRecette();
        List<IngredientRecette> LIR=SIR.readAll(rct);
           System.out.println(rct.getIdRecette());
           System.out.println("\n");
           System.out.println(SIR.readAll(rct));
           
        ObservableList<IngredientRecette> data = FXCollections.observableArrayList(LIR);
        
        Ingredients.setCellValueFactory(new PropertyValueFactory<>("ing"));
        Quantité.setCellValueFactory(new PropertyValueFactory<>("Quantite"));
        tvb.setItems(data);
        
        int x=LIR.size();
        VBox vBox = new VBox(tvb);        
        vBox.setPrefHeight(100+(50*x));
        vBox.setPrefWidth(200);
     
        
        PopOver popOver = new PopOver(vBox);
           tvb.setOnMouseClicked(Event->{
               popOver.hide();
           });
     
        popOver.show(tvRecette);   
        
    }
         else
    {
        Alert ItemNotSelected = new Alert(Alert.AlertType.ERROR);
        ItemNotSelected.setTitle("Aucune ligne n'est selectionnée");
        ItemNotSelected.setContentText("veuillez selectionner une ligne du tableau");
        ItemNotSelected.show();
        
    }
    }*/
   

    
}

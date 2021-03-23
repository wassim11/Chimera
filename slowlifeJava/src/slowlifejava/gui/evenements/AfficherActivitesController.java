/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.evenements;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import slowlifejava.tests.Slowlife;
import slowlifejava.entities.evenements.Activite;
import slowlifejava.entities.evenements.Evenement;
import slowlifejava.services.evenements.ActiviteService;
import slowlifejava.services.evenements.EvenementService;

/**
 * FXML Controller class
 *
 * @author Ahmed Ezzine
 */
public class AfficherActivitesController implements Initializable {

    @FXML
    private TableView<Activite> activiteTable;
    @FXML
    private TableColumn<Activite, String> idEventCol;
    @FXML
    private TableColumn<Activite, String> nomCol;
    @FXML
    private TableColumn<Activite, String> typeCol;
    @FXML
    private TableColumn<Activite, String> descCol;
    @FXML
    private TableColumn<Activite, Integer> dureeCol;
    @FXML
    private ComboBox<Evenement> rechercheEvent;
    @FXML
    private TextField rechercheNom;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        idEventCol.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dureeCol.setCellValueFactory(new PropertyValueFactory<>("duree"));
        ActiviteService as=new ActiviteService();
        ObservableList<Activite> liste = FXCollections.observableArrayList(as.getAll());//observable list
        activiteTable.setItems(liste);
        Evenement e=new Evenement();
        e.setNom("All");
        rechercheEvent.getItems().add(e);
        rechercheEvent.getItems().addAll(new EvenementService().getAll());
        rechercheEvent.getSelectionModel().select(e);
        // TODO
    }    

    @FXML
    private void supprimerActivite(MouseEvent event) {
        Activite a =activiteTable.getSelectionModel().getSelectedItem();
        if (a!=null) {
            new ActiviteService().supprimer(a);
            activiteTable.getItems().remove(a);
            activiteTable.refresh();
             Alert al = new Alert(Alert.AlertType.INFORMATION);
             al.setAlertType(Alert.AlertType.INFORMATION);
             al.setHeaderText("Valider");
             al.setContentText("les informations ont été supprimées avec succès");
             al.show();
        }
    }

    @FXML
    private void ajouterActivite(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AjouterActivite.fxml"));
        /*Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
        Slowlife.stage.setScene(scene);*/
        ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
    }

    @FXML
    private void modifierActivite(MouseEvent event) throws IOException {
        Activite a =activiteTable.getSelectionModel().getSelectedItem();
        if (a!=null){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifierActivite.fxml"));
            Parent root=(Pane)fxmlLoader.load();
            ((ModifierActiviteController)fxmlLoader.getController()).setData(a);
            //Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
            ////scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
            //Slowlife.stage.setScene(scene);
            ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
        }
    }

    @FXML
    private void retourActivite(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/slowlifejava/gui/Home.fxml"));
        /*Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
        Slowlife.stage.setScene(scene);*/
        ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
    }

    @FXML
    private void trierEvent(ActionEvent event) {
        trierAll();
    }

    @FXML
    private void trierNom(KeyEvent event) {
        if (event.getCode()==KeyCode.ENTER)
            trierAll();
    }
    
    private void trierAll() {
        List<Activite> activites = new ActiviteService().getAll();
        activiteTable.getItems().clear();
        if (!rechercheNom.getText().isEmpty())
            activites=activites.stream().filter(a->a.getNom().contains(rechercheNom.getText())).collect(Collectors.toList());
        if (rechercheEvent.getSelectionModel().getSelectedIndex()!=0)
            activites=activites.stream().filter(a->a.getIdEvent()==rechercheEvent.getSelectionModel().getSelectedItem().getIdEvent()).collect(Collectors.toList());
        activiteTable.getItems().addAll(activites);
        activiteTable.refresh();
    }
    
}

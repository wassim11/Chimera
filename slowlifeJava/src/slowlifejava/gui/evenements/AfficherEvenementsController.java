/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.evenements;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class AfficherEvenementsController implements Initializable {

    @FXML
    private TableView<Evenement> eventTable;
    @FXML
    private TableColumn<Evenement, String> nomCol;
    @FXML
    private TableColumn<Evenement, String> lieuCol;
    @FXML
    private TableColumn<Evenement, String> typeCol;
    @FXML
    private TableColumn<Evenement, String> descCol;
    @FXML
    private TableColumn<Evenement, Date> dateDebutCol;
    @FXML
    private TableColumn<Evenement, Date> dateFinCol;
    @FXML
    private TextField recherche;
    @FXML
    private TableView<Activite> activiteTable;
    @FXML
    private TableColumn<Activite, String> typeAct;
    @FXML
    private TableColumn<Activite, String> descAct;
    @FXML
    private TableColumn<Activite, Integer> dureeAct;
    @FXML
    private TableColumn<Activite, String> nomAct;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateDebutCol.setCellValueFactory(new PropertyValueFactory<>("dateDeb"));
        dateFinCol.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
        nomAct.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeAct.setCellValueFactory(new PropertyValueFactory<>("type"));
        descAct.setCellValueFactory(new PropertyValueFactory<>("description"));
        dureeAct.setCellValueFactory(new PropertyValueFactory<>("duree"));
        EvenementService es=new EvenementService();
        ObservableList<Evenement> liste = FXCollections.observableArrayList(es.getAll());
        eventTable.setItems(liste);
    }    

    @FXML
    private void ajouterEvenement(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AjouterEvenement.fxml"));
       /* Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
        Slowlife.stage.setScene(scene);*/
       ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
    }

    @FXML
    private void supprimerEvenement(MouseEvent event) {
        Evenement e=eventTable.getSelectionModel().getSelectedItem();
        if (e!=null) {
            new EvenementService().supprimer(e);
            eventTable.getItems().remove(e);
            eventTable.refresh();
            activiteTable.getItems().clear();
            activiteTable.refresh();
             Alert al = new Alert(Alert.AlertType.INFORMATION);
             al.setAlertType(Alert.AlertType.INFORMATION);
             al.setHeaderText("Valider");
             al.setContentText("les informations ont été supprimées avec succès");
             al.show();
        }
    }

    @FXML
    private void modifierEvenement(MouseEvent event) throws IOException {
        Evenement e =eventTable.getSelectionModel().getSelectedItem();
        if (e!=null){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifierEvenement.fxml"));
            Parent root=(Pane)fxmlLoader.load();
            ((ModifierEvenementController)fxmlLoader.getController()).setData(e);
           /* Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
            scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
            Slowlife.stage.setScene(scene);*/
           ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
        }
    }

    @FXML
    private void retour(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/slowlifejava/gui/Home.fxml"));
        /*Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
        Slowlife.stage.setScene(scene);*/
        ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
    }

    @FXML
    private void trier(KeyEvent event) {
        if (event.getCode()==KeyCode.ENTER) {
            if (!recherche.getText().isEmpty()) {
                ObservableList<Evenement> liste = FXCollections.observableArrayList(new EvenementService().getAllTrier(recherche.getText()));
                eventTable.setItems(liste);
                eventTable.refresh();
            } else {
                ObservableList<Evenement> liste = FXCollections.observableArrayList(new EvenementService().getAll());
                eventTable.setItems(liste);
                eventTable.refresh();
            }
        }
    }

    @FXML
    private void afficherActivite(MouseEvent event) {
        Evenement e=eventTable.getSelectionModel().getSelectedItem();
        if (e!=null) {
            ActiviteService as=new ActiviteService();
            ObservableList<Activite> liste = FXCollections.observableArrayList(as.getAllParEvenement(e.getIdEvent()));
            activiteTable.setItems(liste);
            activiteTable.refresh();
        }
    }
}

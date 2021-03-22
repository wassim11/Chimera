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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import slowlifejava.tests.Slowlife;
import slowlifejava.entities.evenements.Evenement;
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
        EvenementService es=new EvenementService();
        ObservableList<Evenement> liste = FXCollections.observableArrayList(es.getAll());
        eventTable.setItems(liste);
    }    

    @FXML
    private void ajouterEvenement(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AjouterEvenement.fxml"));
        Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        Slowlife.stage.setScene(scene);
    }

    @FXML
    private void supprimerEvenement(MouseEvent event) {
        Evenement e=eventTable.getSelectionModel().getSelectedItem();
        if (e!=null) {
            new EvenementService().supprimer(e);
            eventTable.getItems().remove(e);
            eventTable.refresh();
        }
    }

    @FXML
    private void modifierEvenement(MouseEvent event) throws IOException {
        Evenement e =eventTable.getSelectionModel().getSelectedItem();
        if (e!=null){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifierEvenement.fxml"));
            Parent root=(Pane)fxmlLoader.load();
            ((ModifierEvenementController)fxmlLoader.getController()).setData(e);
            Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
            Slowlife.stage.setScene(scene);
        }
    }

    @FXML
    private void retour(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/slowlifejava/gui/Home.fxml"));
        Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        Slowlife.stage.setScene(scene);
    }
}

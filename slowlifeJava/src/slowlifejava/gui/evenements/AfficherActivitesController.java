/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.evenements;

import java.io.IOException;
import java.net.URL;
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
import slowlifejava.Slowlife;
import slowlifejava.entities.evenements.Activite;
import slowlifejava.services.evenements.ActiviteService;

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
        // TODO
    }    

    @FXML
    private void supprimerActivite(MouseEvent event) {
        Activite a =activiteTable.getSelectionModel().getSelectedItem();
        if (a!=null) {
            new ActiviteService().supprimer(a);
            activiteTable.getItems().remove(a);
            activiteTable.refresh();
        }
    }

    @FXML
    private void ajouterActivite(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AjouterActivite.fxml"));
        Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        Slowlife.stage.setScene(scene);
    }

    @FXML
    private void modifierActivite(MouseEvent event) throws IOException {
        Activite a =activiteTable.getSelectionModel().getSelectedItem();
        if (a!=null){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifierActivite.fxml"));
            Parent root=(Pane)fxmlLoader.load();
            ((ModifierActiviteController)fxmlLoader.getController()).setData(a);
            Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
            Slowlife.stage.setScene(scene);
        }
    }

    @FXML
    private void retourActivite(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/slowlifejava/gui/Home.fxml"));
        Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        Slowlife.stage.setScene(scene);
    }
    
}

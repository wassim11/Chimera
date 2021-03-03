/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import Entities.Centre;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import service.ServiceCentre;

/**
 *
 * @author amira
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private AnchorPane r;
    @FXML
    private AnchorPane titre;
    @FXML
    private TextField tfnomcentre;
    @FXML
    private TextField tflieucentre;
    @FXML
    private TextArea tfdesccentre;
    @FXML
    private TextField tfimagecentre;
    @FXML
    private Button ajoutercentre;
    @FXML
    private AnchorPane affichage;
    @FXML
    private TableColumn<Centre, Integer> colIdcentre;

    @FXML
    private TableColumn<Centre, String> colnomcentre;
    @FXML
    private TableColumn<Centre, String> coladressecentre;
    @FXML
    private TableColumn<Centre, String> coldescriptioncentre;
    @FXML
    private TableView<Centre> tableCentre;
    @FXML
    private Button supprimercentre;
    @FXML
    private Button modifiercentre;
    @FXML
    private Button enregistrermodcentre;
    @FXML
    private Button annulermodcentre;
    @FXML
    private ChoiceBox<String> cbtypecentre;
    @FXML
    private TableColumn<Centre, String> coltypecentre;
    @FXML
    private Label cntdesccentre;
    @FXML
    private Label cntadressecentre;
    @FXML
    private TableColumn<Centre, String> coldetailcentre;
    @FXML
    private Label cntimagecentre;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            
            showCentres();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        cntdesccentre.setText("");
        enregistrermodcentre.setVisible(false);
        annulermodcentre.setVisible(false);
        cbtypecentre.getItems().addAll("spa","salle de sport","centre de remise en forme","meditation","autre");
        
    }  
    
    
    

    @FXML
    private void btnajouterCentre(ActionEvent event) {
        if ((tfdesccentre.getText().length()>5) && (tflieucentre.getText().contains("rue")) && ((tfimagecentre.getText().endsWith(".jpg")) || (tfimagecentre.getText().endsWith(".png"))|| (tfimagecentre.getText().endsWith(".jpeg")))&& (tfimagecentre.getText().startsWith("src/image/")) && (tfnomcentre.getText().length()!=0) && (cbtypecentre.getValue()!=null))
        {ServiceCentre sc= new ServiceCentre();
        Centre c = new Centre();
        
        c.setDescription(tfdesccentre.getText());
        c.setLieu(tflieucentre.getText());
        c.setNom(tfnomcentre.getText());
        c.setImage(tfimagecentre.getText());
        c.setType(cbtypecentre.getValue());
        
        
        try {
            sc.ajouterCentre(c);
            showCentres();
            tfdesccentre.setText("");
        tflieucentre.setText("");
        tfnomcentre.setText("");
        tfimagecentre.setText("");
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else
        {
            Alert a = new Alert(AlertType.ERROR);
             a.setAlertType(AlertType.ERROR);
             a.setHeaderText("erreur!");
             a.setContentText("verifier les champs!");
             a.show();
        }
        
    }
    
    
    
    
    public void showCentres() throws SQLException
    {
        ServiceCentre sc =new ServiceCentre();
        ObservableList<Centre> lc= sc.afficherCentre();
        
        colIdcentre.setCellValueFactory(new PropertyValueFactory<Centre,Integer>("id"));
        
        
        colnomcentre.setCellValueFactory(new PropertyValueFactory<Centre,String>("nom"));
        coladressecentre.setCellValueFactory(new PropertyValueFactory<Centre,String>("lieu"));
        coldescriptioncentre.setCellValueFactory(new PropertyValueFactory<Centre,String>("description"));
        coltypecentre.setCellValueFactory(new PropertyValueFactory<Centre,String>("type"));
        
        
        //bouton detail********
        
        coldetailcentre.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<Centre, String>, TableCell<Centre, String>> cellFactory
                = //
                new Callback<TableColumn<Centre, String>, TableCell<Centre, String>>() {
            @Override
            public TableCell call(final TableColumn<Centre, String> param) {
                final TableCell<Centre, String> cell = new TableCell<Centre, String>() {

                    final Button btn = new Button("afficher Detsails");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Centre c = getTableView().getItems().get(getIndex());
                                System.out.println(c.getId());
                                Centre.iddet=c.getId();
                                try {
                                    Parent p= FXMLLoader.load(getClass().getResource("detailsCentre.fxml"));
                                    Scene scene = new Scene (p);
                                    Stage stage= new Stage();
                                    stage.setScene(scene);
                                    stage.show();
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        coldetailcentre.setCellFactory(cellFactory);
       //end button details************
        
        
        
        
        tableCentre.setItems(lc);
         enregistrermodcentre.setVisible(false);
         annulermodcentre.setVisible(false);

    }

    @FXML
    private void btnsupprimercentre(ActionEvent event) throws SQLException {
        
        int id= tableCentre.getSelectionModel().getSelectedItem().getId();
        ServiceCentre sc =new ServiceCentre();
        sc.supprimerCentre(id);
        showCentres();
        
    }

    @FXML
    private void btnmodifiercentre(ActionEvent event) {
       // ServiceCentre sc= new ServiceCentre();
        Centre c = new Centre();
        c= tableCentre.getSelectionModel().getSelectedItem();
        int id = c.getId();
        
        tfdesccentre.setText(c.getDescription());
        tflieucentre.setText(c.getLieu());
        tfnomcentre.setText(c.getNom());
        tfimagecentre.setText(c.getImage());
        cbtypecentre.setValue(c.getType());
        
        
         enregistrermodcentre.setVisible(true);
         annulermodcentre.setVisible(true);
         ajoutercentre.setDisable(true);
        
    }

    @FXML
    private void btnenregistrermodcentre(ActionEvent event) throws SQLException {
         if ((tfdesccentre.getText().length()>5) && (tflieucentre.getText().contains("rue")) && ((tfimagecentre.getText().endsWith(".jpg")) || (tfimagecentre.getText().endsWith(".png"))|| (tfimagecentre.getText().endsWith(".jpeg")))&& (tfimagecentre.getText().startsWith("src/image/")) && (tfnomcentre.getText().length()!=0) && (cbtypecentre.getValue()!=null))
        {
        ServiceCentre sc= new ServiceCentre();
        Centre c = new Centre();
        
        c.setDescription(tfdesccentre.getText());
        c.setLieu(tflieucentre.getText());
        c.setNom(tfnomcentre.getText());
        c.setImage(tfimagecentre.getText());
        c.setType(cbtypecentre.getValue());
        int id = tableCentre.getSelectionModel().getSelectedItem().getId();
        //System.out.println("identifiant selected: "+id);
        sc.modifierCentre(c, id);
        //System.out.println("out");
        showCentres();
        enregistrermodcentre.setVisible(false);
        annulermodcentre.setVisible(false);
        ajoutercentre.setDisable(false);
        tfdesccentre.setText("");
        tflieucentre.setText("");
        tfnomcentre.setText("");
        tfimagecentre.setText("");
        }
         else
        {
            Alert a = new Alert(AlertType.ERROR);
             a.setAlertType(AlertType.ERROR);
             a.setHeaderText("erreur!");
             a.setContentText("verifier les champs!");
             a.show();
        }
    }

    @FXML
    private void btnannulermodcentre(ActionEvent event) {
        cntdesccentre.setText("");
        ajoutercentre.setDisable(false);
         enregistrermodcentre.setVisible(false);
        annulermodcentre.setVisible(false);
        tfdesccentre.setText("");
        tflieucentre.setText("");
        tfnomcentre.setText("");
        tfimagecentre.setText("");
        
    }

   

    @FXML
    private void controlesdesccentre(KeyEvent event) {
        if(tfdesccentre.getText().length()<5)
        {
            cntdesccentre.setText("description trop courte!");
        }
        else
            cntdesccentre.setText("");
    }

    @FXML
    private void cntsadressecentre(KeyEvent event) {
        if(!tflieucentre.getText().contains("rue"))
        {
            cntadressecentre.setText("doit contenir rue!");
        }
        else
            cntadressecentre.setText("");
    }

    @FXML
    private void cntsimagecentre(KeyEvent event) {
        if(!tfimagecentre.getText().startsWith("src/image/"))
        {
            cntimagecentre.setText("image doit etre contenue dans src/image !!");
        }
        else if((!tfimagecentre.getText().endsWith(".jpg")) && (!tfimagecentre.getText().endsWith(".png"))&& (!tfimagecentre.getText().endsWith(".jpeg")))
        {
            cntimagecentre.setText("ce n'est pas une image!");
        }
        else
            cntimagecentre.setText("");
        
    }
    
}

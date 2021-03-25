/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.centre;

//import Entities.Centre;
//import Entities.ServiceOffert;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import slowlifejava.entities.centre.Centre;
import slowlifejava.entities.centre.ServiceOffert;
import slowlifejava.services.centre.ServiceCentre;
import slowlifejava.services.centre.ServiceServiceCentre;
//import service.ServiceCentre;
//import service.ServiceServiceCentre;

/**
 * FXML Controller class
 *
 * @author amira
 */
public class DetailsCentreController implements Initializable {

    @FXML
    private Label idcentredetail;
    @FXML
    private ImageView imagecentredetail;
    @FXML
    private Label nomcentredetail;
    @FXML
    private Label typecentredetail;
    @FXML
    private Label adressecentredetail;
    @FXML
    private Label desccentredetail;
    @FXML
    private Button btnajouterservicec;
    @FXML
    private TableView<ServiceOffert> tableserviceoffert;
    @FXML
    private TableColumn<ServiceOffert, String> colnomserviceoffert;
    @FXML
    private TableColumn<ServiceOffert, String> coltypeserviceoffert;
    @FXML
    private TableColumn<ServiceOffert, Float> colprixserviceoffert;
    @FXML
    private Button refreshtableserviceoff;
    @FXML
    private Button btnSupprimerService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            showServicesOfferts();
        } catch (SQLException ex) {
            Logger.getLogger(DetailsCentreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        idcentredetail.setText(""+Centre.iddet);
        Centre c =new Centre();
        ServiceCentre sc= new ServiceCentre();
        try {
            c= sc.afficherCentreDetail(Centre.iddet);
            nomcentredetail.setText(c.getNom());
            adressecentredetail.setText(c.getLieu());
            typecentredetail.setText(c.getType());
            desccentredetail.setText(c.getDescription());
           
            
            //imagecentredetail= new ImageView (new Image(this.getClass().getResourceAsStream(c.getImage())));
            
            //creating the image object
      InputStream stream;
            try {
                stream = new FileInputStream(c.getImage());
                Image image = new Image(stream);
                 imagecentredetail.setImage(image);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DetailsCentreController.class.getName()).log(Level.SEVERE, null, ex);
            }
      
            
        } catch (SQLException ex) {
            Logger.getLogger(DetailsCentreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
    
    
    
    
    
     public void showServicesOfferts() throws SQLException
    {
        ServiceServiceCentre sc =new ServiceServiceCentre();
        ObservableList<ServiceOffert> lc= sc.afficherServiceOffertCentre(Centre.iddet);
        
        
        colnomserviceoffert.setCellValueFactory(new PropertyValueFactory<ServiceOffert,String>("nom"));
       colprixserviceoffert.setCellValueFactory(new PropertyValueFactory<ServiceOffert,Float>("prix"));
       
        coltypeserviceoffert.setCellValueFactory(new PropertyValueFactory<ServiceOffert,String>("type"));
        tableserviceoffert.setItems(lc);
         
    } 
    
    
    
    
    

    @FXML
    private void ajouterServiceC(ActionEvent event) {
        
        try {
                                    Parent p= FXMLLoader.load(getClass().getResource("ajouterServiceCentre.fxml"));
                                    Scene scene = new Scene (p);
                                    Stage stage= new Stage();
                                    scene.getStylesheets().add("utils/centre.css");
                                    stage.setScene(scene);
                                    stage.show();
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                                }
    }

    @FXML
    private void btnRefreshServiceOffert(ActionEvent event) throws SQLException {
        showServicesOfferts();
    }

    @FXML
    private void SupprimerServiceC(ActionEvent event) {
        int s=-1;
         s= tableserviceoffert.getSelectionModel().getSelectedItem().getId();
        if (s!=-1)
        {  ServiceServiceCentre sc =new ServiceServiceCentre();
           sc.supprimerServiceC(s, Centre.iddet);
           try {
            
            showServicesOfferts();
        } catch (SQLException ex) {
            Logger.getLogger(DetailsCentreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import Entities.Centre;
import Entities.Service;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import Entities.CentreService;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import service.ServiceCentre;
import service.ServiceService;
import service.ServiceServiceCentre;

/**
 * FXML Controller class
 *
 * @author amira
 */
public class AjouterServiceCentreController implements Initializable {

    @FXML
    private Label idcentre;
    @FXML
    private Label nomcentre;
    @FXML
    private ComboBox<Integer> cbservice;
    @FXML
    private Button btnajouterservicecentre;
    @FXML
    private Button btnannulerservicecentre;
    @FXML
    private TextField prixservicecentre;
    @FXML
    private Label cntprixservicecentre;
    @FXML
    private Label nomservicech;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        Centre c =new Centre();
        ServiceCentre sc= new ServiceCentre();
        try {
            c= sc.afficherCentreDetail(Centre.iddet);
            idcentre.setText(""+c.getId());
            nomcentre.setText(c.getNom());
             ServiceServiceCentre ss =new ServiceServiceCentre();
              refreshComboBox();
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AjouterServiceCentreController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void refreshComboBox() throws SQLException
    {
        ServiceServiceCentre ss =new ServiceServiceCentre();
              ObservableList<Service> lc= ss.afficherSelectedCentreService(Centre.iddet);
              cbservice.getItems().clear();
              for (Service s:lc)
              cbservice.getItems().add(s.getId());
    }


    @FXML
    private void annulerServiceCentre(ActionEvent event) throws SQLException {
        Stage stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        
    }

    
    @FXML
    private void ajouterServiceCentre(ActionEvent event) throws SQLException {
        if ((cbservice.getValue()!=null) && (prixservicecentre.getText().length()>0) && test())
        {CentreService ccs =new CentreService();
        ServiceServiceCentre ssc= new ServiceServiceCentre();
        ccs.setId_centre(Centre.iddet);
        ccs.setId_service(cbservice.getValue());
        ccs.setPrix(Float.parseFloat(prixservicecentre.getText()));
        
        ssc.ajouterServiceCentre(ccs);
        prixservicecentre.setText("");
        Alert a = new Alert(Alert.AlertType.INFORMATION);
             a.setAlertType(Alert.AlertType.INFORMATION);
             a.setHeaderText("Confirmation");
             a.setContentText("Service ajoute !");
             a.show();
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR);
             a.setAlertType(Alert.AlertType.ERROR);
             a.setHeaderText("erreur!");
             a.setContentText("verifier les champs!");
             a.show();
        }
        refreshComboBox();
//        DetailsCentreController dcc= new DetailsCentreController();
//        dcc.showServicesOfferts();
//        
    }

    @FXML
    private void ctnsPrixServiceCentre(KeyEvent event) {
         boolean success = true;
        try
        { 
            
            Float.parseFloat(prixservicecentre.getText()); 
            cntprixservicecentre.setText(""); 
            success = true;
             
        }  
        catch (NumberFormatException e) 
        { 
            cntprixservicecentre.setText("doit etre un nombre valide!"); 
            success = false;
        } 
        
        if (success) {
         cntprixservicecentre.setText("");
         }
        
    }
    
    public boolean test()
    {
         boolean success = false;
        try
        { 
            
            Float.parseFloat(prixservicecentre.getText());  
            success = true;
             
        }  
        catch (NumberFormatException e) 
        { 
            
            success = false;
        } 
        
        return success;
    }

  


    @FXML
    private void changerNomCB(ActionEvent event) throws SQLException {
         ServiceServiceCentre ss =new ServiceServiceCentre();
              ObservableList<Service> lc= ss.afficherSelectedCentreService(Centre.iddet);
              
              for (Service s:lc)
                  if (s.getId()==cbservice.getValue())
              nomservicech.setText(s.getNom());
    }

    
}

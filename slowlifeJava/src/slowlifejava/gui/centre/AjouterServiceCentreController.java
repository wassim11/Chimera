/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.centre;

//import Entities.Centre;
//import Entities.Service;
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
//import Entities.CentreService;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import slowlifejava.entities.centre.Centre;
import slowlifejava.entities.centre.CentreService;
import slowlifejava.entities.centre.Service;
import slowlifejava.services.centre.ServiceCentre;
import slowlifejava.services.centre.ServiceService;
import slowlifejava.services.centre.ServiceServiceCentre;
//import service.ServiceCentre;
//import service.ServiceService;
//import service.ServiceServiceCentre;

/**
 * FXML Controller class
 *
 * @author amira
 */
public class AjouterServiceCentreController implements Initializable {

    @FXML
    private Label nomcentre;
    @FXML
    private ComboBox<String> cbservice;
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
    @FXML
    private TableView<Service> tableselectservice;
    @FXML
    private TableColumn<Service, String> colnomserviceselect;
    @FXML
    private TableColumn<Service, String> coltypeserviceselect;
    @FXML
    private TableColumn<Service, String> coldescserviceselect;
    @FXML
    private TextField chercherservajcentre;
    @FXML
    private Button selectajServiceCentre;
    @FXML
    private ComboBox<String> typeservrecherchecentre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                 typeservrecherchecentre.getItems().addAll("Tous les types","massage","yoga","seance meditation","autre");
       
        Centre c =new Centre();
        ServiceCentre sc= new ServiceCentre();
        
        try {
            c= sc.afficherCentreDetail(Centre.iddet);
           showServices();
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
              cbservice.getItems().add(s.getNom());
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
        Centre c1 = new Centre();
        ServiceCentre sc1= new ServiceCentre();
        c1= sc1.afficherCentreDetail(Centre.iddet);
        Service s1= new Service();
        ServiceService ss1= new ServiceService();
        s1 = ss1.RechercherServiceNom(cbservice.getValue());
        ccs.setCentre(c1);
        ccs.setService(s1);
        ccs.setPrix(Float.parseFloat(prixservicecentre.getText()));
        ssc.ajouterServiceCentre(ccs);
        showServices();
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
                  if (s.getNom()==cbservice.getValue())
              nomservicech.setText(s.getNom());
    }

    @FXML
    private void SelectServicecentreField(ActionEvent event) {
        String s= tableselectservice.getSelectionModel().getSelectedItem().getNom();
        if (s!=null)
        {
            cbservice.setValue(s);
        }
    }

    @FXML
    private void ChercherServicetabservcent(ActionEvent event) throws SQLException {
        if (typeservrecherchecentre.getValue().compareTo("Tous les types")==0)
            showServices();
        else
        {
        ServiceService ss =new ServiceService();
        ObservableList<Service> lc= ss.RechercherServiceType(typeservrecherchecentre.getValue());
        
        FilteredList<Service> filteredData = new FilteredList<>(lc, p -> true);

        chercherservajcentre.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getNom()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } 
                return false; // Does not match.
            });
        });
        colnomserviceselect.setCellValueFactory(new PropertyValueFactory<Service,String>("nom"));
        coldescserviceselect.setCellValueFactory(new PropertyValueFactory<Service,String>("description"));
        coltypeserviceselect.setCellValueFactory(new PropertyValueFactory<Service,String>("type"));
        SortedList<Service> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(tableselectservice.comparatorProperty());
        tableselectservice.setItems(sortedData);
        }
        
    }
    
    
        public void showServices() throws SQLException
    {
        ServiceServiceCentre ss =new ServiceServiceCentre();
        ObservableList<Service> lc= ss.afficherSelectedCentreService(Centre.iddet);
        
        FilteredList<Service> filteredData = new FilteredList<>(lc, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        chercherservajcentre.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getNom()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } 
                return false; // Does not match.
            });
        });

        
        colnomserviceselect.setCellValueFactory(new PropertyValueFactory<Service,String>("nom"));
        coldescserviceselect.setCellValueFactory(new PropertyValueFactory<Service,String>("description"));
        coltypeserviceselect.setCellValueFactory(new PropertyValueFactory<Service,String>("type"));
                     // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Service> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableselectservice.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        tableselectservice.setItems(sortedData);
    } 

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.centre;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import slowlifejava.entities.centre.Service;
import slowlifejava.services.centre.ServiceService;


/**
 * FXML Controller class
 *
 * @author amira
 */
public class GestionServiceController implements Initializable {

    @FXML
    private AnchorPane r;
    @FXML
    private AnchorPane titre;
    @FXML
    private TextField tfnomservice;
    private TextField tfprixservice;
    @FXML
    private Button ajouterservice;
    @FXML
    private Button supprimerservice;
    @FXML
    private Button modifierservice;
    @FXML
    private Button enregistrermodservice;
    @FXML
    private Button annulermodservice;
    @FXML
    private TextArea tfdescservice;
    @FXML
    private Label cntdescservice;
    @FXML
    private Label cntnomservice;
    
    @FXML
    private AnchorPane affichage;
    @FXML
    private TableView<Service> tableService;
    @FXML
    private TableColumn<Service,String> colnomservice;
    @FXML
    private TableColumn<Service,String> coldescriptionservice;
    private TableColumn<Service,Float> colprixservice;
    @FXML
    private ComboBox<String> cbtypeservice;
    @FXML
    private TableColumn<Service, String> coltypeservice;
    @FXML
    private TextField rechercherService;
    @FXML
    private ComboBox<String> cbTrierService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        cntdescservice.setText("");
        cntnomservice.setText("");
          cbtypeservice.getItems().addAll("massage","yoga","seance meditation","autre");
          cbTrierService.getItems().addAll("Nom Service","Type");
        try {
            showServices();
        } catch (SQLException ex) {
            Logger.getLogger(GestionServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
          enregistrermodservice.setVisible(false);
         annulermodservice.setVisible(false);
    }    

    @FXML
    private void cntsNomService(KeyEvent event) {
        
        if(tfnomservice.getText().length()<2)
            cntnomservice.setText("le nom doit etre plus long que 2!");
        else
           cntnomservice.setText("");
             
    }

    
    
    
    
    public void showServices() throws SQLException
    {
        ServiceService sc =new ServiceService();
        ObservableList<Service> lc= sc.afficherService();
        
        
                    //******************************************************************************
          FilteredList<Service> filteredData = new FilteredList<>(lc, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        rechercherService.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getNom()).toLowerCase().contains(lowerCaseFilter)) 
                    return true;
                    // Filter matches name.
                 else if (String.valueOf(myObject.getType()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                }
                return false; // Does not match.
            });
        });

        
        colnomservice.setCellValueFactory(new PropertyValueFactory<Service,String>("nom"));
       // colprixservice.setCellValueFactory(new PropertyValueFactory<Service,Float>("prix"));
        coldescriptionservice.setCellValueFactory(new PropertyValueFactory<Service,String>("description"));
        coltypeservice.setCellValueFactory(new PropertyValueFactory<Service,String>("type"));
        tableService.setItems(filteredData);
         enregistrermodservice.setVisible(false);
         annulermodservice.setVisible(false);
    } 
    
    
    public boolean testDonnees()
    {
        boolean success = true;
       
        if(tfdescservice.getText().length()<5)
            success = false;
        
        else if(tfnomservice.getText().length()<2)
            success = false;
        else if (cbtypeservice.getValue()== null)
            success = false;
        
        return success;
    }
    

    @FXML
    private void btnajouterService(ActionEvent event) {
        
         
        
        if(testDonnees())
        {
        ServiceService ss= new ServiceService();
        Service s = new Service();
        s.setNom(tfnomservice.getText());
        s.setDescription(tfdescservice.getText());
        s.setType(cbtypeservice.getValue());
        try {
            ss.ajouterService(s);
            showServices();
            tfnomservice.setText("");
        tfdescservice.setText("");
         
        cntdescservice.setText("");
        cntnomservice.setText("");
        
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else
        {
             Alert a = new Alert(Alert.AlertType.ERROR);
             a.setAlertType(Alert.AlertType.ERROR);
             a.setHeaderText("erreur!");
             a.setContentText("verifier les champs!");
             a.show();
        }
    }

    @FXML
    private void btnsupprimerService(ActionEvent event) throws SQLException {
        int id= tableService.getSelectionModel().getSelectedItem().getId();
        ServiceService sc =new ServiceService();
        sc.supprimerService(id);
        showServices();
        enregistrermodservice.setVisible(false);
         annulermodservice.setVisible(false);
         ajouterservice.setDisable(false); 
         tfnomservice.setText("");
        tfdescservice.setText("");
    }

    @FXML
    private void btnmodifierService(ActionEvent event) {
        
       //ServiceService ss= new ServiceService();
        Service s = new Service();
        s= tableService.getSelectionModel().getSelectedItem();
        int id = s.getId();
        
        tfdescservice.setText(s.getDescription());
         cbtypeservice.setValue(s.getType());
        tfnomservice.setText(s.getNom());
        
       
        
        
         enregistrermodservice.setVisible(true);
         annulermodservice.setVisible(true);
         ajouterservice.setDisable(true); 
        
    }

    @FXML
    private void btnenregistrermodService(ActionEvent event) throws SQLException {
        
        if(testDonnees())
        {
        ServiceService ss= new ServiceService();
        Service s = new Service();
        s.setNom(tfnomservice.getText());
        s.setDescription(tfdescservice.getText());
         s.setType(cbtypeservice.getValue());
        int id = tableService.getSelectionModel().getSelectedItem().getId();
        ss.modifierService(s, id);
        showServices();
        enregistrermodservice.setVisible(false);
         annulermodservice.setVisible(false);
         ajouterservice.setDisable(false); 
         tfnomservice.setText("");
        tfdescservice.setText("");
        }
         else
        {
             Alert a = new Alert(Alert.AlertType.ERROR);
             a.setAlertType(Alert.AlertType.ERROR);
             a.setHeaderText("erreur!");
             a.setContentText("verifier les champs!");
             a.show();
        }
        
    }

    @FXML
    private void btnannulermodService(ActionEvent event) {
        enregistrermodservice.setVisible(false);
         annulermodservice.setVisible(false);
         ajouterservice.setDisable(false); 
         tfnomservice.setText("");
        tfdescservice.setText("");
        
    }

    @FXML
    private void controlesdescservice(KeyEvent event) {
        if(tfdescservice.getText().length()<5)
        {
            cntdescservice.setText("description trop courte!");
        }
        else
            cntdescservice.setText("");
    }

    @FXML
    private void TrierService(ActionEvent event) throws SQLException {
        enregistrermodservice.setVisible(false);
         annulermodservice.setVisible(false);
         ajouterservice.setDisable(false); 
         tfnomservice.setText("");
        tfdescservice.setText("");
        rechercherService.setText("");
        ServiceService sc =new ServiceService();
        ObservableList<Service> lc= sc.afficherService();
        if (cbTrierService.getValue().compareTo("Nom Service")==0)
            lc= sc.afficherServicetriNom();
        else if (cbTrierService.getValue().compareTo("Type")==0)
              lc= sc.afficherServicetriType();
        
                    //******************************************************************************
          FilteredList<Service> filteredData = new FilteredList<>(lc, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        rechercherService.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getNom()).toLowerCase().contains(lowerCaseFilter)) 
                    return true;
                    // Filter matches name.
                 else if (String.valueOf(myObject.getType()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                }
                return false; // Does not match.
            });
        });

        
        colnomservice.setCellValueFactory(new PropertyValueFactory<Service,String>("nom"));
       // colprixservice.setCellValueFactory(new PropertyValueFactory<Service,Float>("prix"));
        coldescriptionservice.setCellValueFactory(new PropertyValueFactory<Service,String>("description"));
        coltypeservice.setCellValueFactory(new PropertyValueFactory<Service,String>("type"));
        tableService.setItems(filteredData);
         enregistrermodservice.setVisible(false);
         annulermodservice.setVisible(false);
        
    }

    
    
    
}

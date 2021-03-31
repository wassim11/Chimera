/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.centre;

import Entities.Service;
import Entities.StatPrixS;
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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ServiceService;

/**
 * FXML Controller class
 *
 * @author amira
 */
public class StatPrixServiceController implements Initializable {

    @FXML
    private LineChart<String, Float> Statpserv;
    @FXML
    private NumberAxis ystatpserv;
    @FXML
    private CategoryAxis xstatpserv;
    @FXML
    private ComboBox<String> nomservicestat;
    @FXML
    private TableView<Service> tableServicef;
    @FXML
    private TableColumn<Service, String> colnom;
    @FXML
    private TableColumn<Service, String> coldesc;
    @FXML
    private TextField RechercheService;
    @FXML
    private TableColumn<Service, String> coltype;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ServiceService ssc = new ServiceService();
        try {
            showServices();
            ObservableList<Service> lss= ssc.afficherService();
            for (Service s: lss)
              nomservicestat.getItems().add(s.getNom());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(StatPrixServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
    private void Stat2(String nom) throws SQLException
    { 
        Statpserv.getData().clear();
          Statpserv.setTitle("Prix Service dans les Centres");
        xstatpserv.setLabel("Centre");       
        ystatpserv.setLabel("Prix");
        ServiceService ssc = new ServiceService();
        ObservableList<StatPrixS> lss= ssc.StatPrixService(nom);
        System.out.println(lss);
        XYChart.Series<String, Float> series = new XYChart.Series<>();
        
        for (StatPrixS ss:lss)
        {   
            series.getData().add(new XYChart.Data<>(ss.getCentre(),ss.getPrix()));
            System.out.println(ss.getCentre());
        }
        
        series.setName(nom);
      
        Statpserv.getData().add(series);
    }



    @FXML
    private void ChangerComparerPrix(ActionEvent event) throws SQLException {
     String nom =nomservicestat.getValue();
       Stat2(nom);
    }

    @FXML
    private void selectService(ActionEvent event) throws SQLException {
         String s= tableServicef.getSelectionModel().getSelectedItem().getNom();
        if (s!=null)
        {
            nomservicestat.setValue(s);
            Stat2(s);
        }
    }
    
    
    
       
    public void showServices() throws SQLException
    {
        ServiceService sc =new ServiceService();
        ObservableList<Service> lc= sc.afficherService();
        
        
                    //******************************************************************************
          FilteredList<Service> filteredData = new FilteredList<>(lc, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        RechercheService.textProperty().addListener((observable, oldValue, newValue) -> {
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

        
        colnom.setCellValueFactory(new PropertyValueFactory<Service,String>("nom"));
        coldesc.setCellValueFactory(new PropertyValueFactory<Service,String>("description"));
        coltype.setCellValueFactory(new PropertyValueFactory<Service,String>("type"));
        tableServicef.setItems(filteredData);

    } 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.centre;

import Entities.StatCentre;
import Entities.StatRDV;
import Entities.StatService;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import service.ServiceCentre;
import service.ServiceRendezvous;
import service.ServiceServiceCentre;

/**
 * FXML Controller class
 *
 * @author amira
 */
public class StatCentreServiceController implements Initializable {

    @FXML
    private BarChart<String, Integer> StatNbrService;
    @FXML
    private NumberAxis yCentreServ;
    @FXML
    private CategoryAxis xCentreServ;
    @FXML
    private LineChart<String, Integer> StatDateRdv;
    @FXML
    private NumberAxis yRDV;
    @FXML
    private CategoryAxis xRDV;
    @FXML
    private BarChart<String, Integer> StatServC;
    @FXML
    private NumberAxis yservc;
    @FXML
    private CategoryAxis xservc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Stat1();
            Stat2();
            Stat3();
        } catch (SQLException ex) {
            Logger.getLogger(StatCentreServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 
    private void Stat1() throws SQLException
    {    
        StatNbrService.setTitle("Nombre des Services par Centre");
        xCentreServ.setLabel("Centre");       
        yCentreServ.setLabel("Nbr Services");
        ServiceServiceCentre ssc = new ServiceServiceCentre();
        ObservableList<StatService> lss= ssc.StatCentreService();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        for (StatService ss:lss)
        {
            series.getData().add(new XYChart.Data<>(ss.getC().getNom(), ss.getNbrService()));
           
        }
        
      
        StatNbrService.getData().add(series);
        StatNbrService.lookupAll(".default-color0.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: rgb(143,235,168);"));
        
             
    }
    
    private void Stat2() throws SQLException
    { 
          StatDateRdv.setTitle("Nombre des Rendezvous par Date");
        xRDV.setLabel("Date");       
        yRDV.setLabel("Nbr RDV");
        ServiceRendezvous ssc = new ServiceRendezvous();
        ObservableList<StatRDV> lss= ssc.StatRendezvous();
        System.out.println(lss);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        for (StatRDV ss:lss)
        {   
            series.getData().add(new XYChart.Data<>(ss.getD(), ss.getNbr()));
           
        }
        
      
        StatDateRdv.getData().add(series);
    }
    
       private void Stat3() throws SQLException
    {    
        StatServC.setTitle("Nombre des Rendezvous par Centre");
        xservc.setLabel("Centre");       
        yservc.setLabel("Nbr Rendezvous");
        ServiceCentre ssc = new ServiceCentre();
        ObservableList<StatCentre> lss= ssc.StatCentre();
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        
        for (StatCentre ss:lss)
        {
            series.getData().add(new XYChart.Data<>(ss.getC().getNom(), ss.getNbr()));
           
        }
        
      
        StatServC.getData().add(series);
              StatServC.lookupAll(".default-color0.chart-bar")
            .forEach(n -> n.setStyle("-fx-bar-fill: rgb(157, 236, 250);"));
        
             
    }
}

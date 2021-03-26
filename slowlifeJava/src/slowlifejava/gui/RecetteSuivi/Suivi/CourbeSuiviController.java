/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Suivi;

import entities.RecetteSuivi.SuiviRegime;
import entities.RecetteSuivi.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import slowlifejava.services.RecetteSuivi.SuiviService;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class CourbeSuiviController implements Initializable {

    @FXML
    private LineChart<?, ?> LCImc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showImc();
            // TODO
        } catch (SQLException ex) {
            Logger.getLogger(CourbeSuiviController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    private void showImc() throws SQLException
    {
        XYChart.Series Serie = new XYChart.Series();
        SuiviService SS = new SuiviService();
        List<SuiviRegime> LSR=SS.readAll(new User(1));
        for(int i=0;i<LSR.size();i++)
        {
         Serie.getData().add(new XYChart.Data(String.valueOf(LSR.get(i).getDateSuivi()), LSR.get(i).getIMC()));   
        }
        LCImc.getData().add(Serie);
    }
    /*XYChart.Series Serie = new XYChart.Series();
        Serie.getData().add(new XYChart.Data("1", 10)); //(X,Y)
        Serie.getData().add(new XYChart.Data("2", 15));
        Serie.getData().add(new XYChart.Data("3", 17));
        Serie.getData().add(new XYChart.Data("4", 18));
        Serie.getData().add(new XYChart.Data("5", 20));
        LCImc.getData().add(Serie);*/
}

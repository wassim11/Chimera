/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entities.CentreService;
import Entities.Service;
import Entities.ServiceOffert;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MaConnexion;

/**
 *
 * @author amira
 */
public class ServiceServiceCentre {
    
    Connection cnx;

    public ServiceServiceCentre() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    
    
    
    public void ajouterServiceCentre(CentreService c) {
        try {
            Statement stm = cnx.createStatement();

            String query = "INSERT INTO centre_service (Id_centre,Id_service,prix)"
                    + "VALUES ('" + c.getId_centre() + "','" + c.getId_service() + "','" + c.getPrix() + "')";

            stm.executeUpdate(query);
            //System.out.println("centre ajouté");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
 public ObservableList<Service> afficherSelectedCentreService(int id) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * FROM service WHERE NOT EXISTS (SELECT id_service FROM centre_service where centre_service.id_service = service.id and centre_service.id_centre='" + id + "')";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Service> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                Service s = new Service();
                
                s.setId(rst.getInt("id"));
                s.setNom(rst.getString("nom"));
                s.setDescription(rst.getString("description"));
            
                s.setType(rst.getString("type"));
                
                services.add(s);
            }

        
        return services;

    }
 
 
 
 
  public ObservableList<ServiceOffert> afficherServiceOffertCentre(int idd) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT s.id,s.nom,s.type,sc.prix FROM service s INNER JOIN centre_service sc ON sc.id_service =s.id where sc.id_centre= '" + idd + "'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<ServiceOffert> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                ServiceOffert s = new ServiceOffert();
               
                s.setNom(rst.getString("nom"));
                s.setPrix(rst.getFloat("prix"));
                s.setId(rst.getInt("id"));
            
                s.setType(rst.getString("type"));
                
                services.add(s);
            }

        
        return services;

    }
 
 
    
}

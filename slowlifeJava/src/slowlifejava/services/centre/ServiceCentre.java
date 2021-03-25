/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.centre;


//import Entities.StatCentre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import slowlifejava.entities.centre.Centre;
import slowlifejava.entities.centre.StatCentre;
import slowlifejava.utils.SlowlifeDB;

/**
 *
 * @author amira
 */
public class ServiceCentre {

    Connection cnx;

    public ServiceCentre() {
        cnx = SlowlifeDB.getInstance().getConnection();
    }

    
    
    
    public void ajouterCentre(Centre c) {
        try {
            Statement stm = cnx.createStatement();

            String query = "INSERT INTO centre (nom,description,lieu,image,type)"
                    + "VALUES ('" + c.getNom() + "','" + c.getDescription() + "','" + c.getLieu() + "','" + c.getImage() + "','" + c.getType() + "')";

            stm.executeUpdate(query);
            //System.out.println("centre ajouté");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    
    
    public ObservableList<Centre> afficherCentre() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres;

    }
    
    
    
     public void supprimerCentre(int id) {
        try {
            Statement stm = cnx.createStatement();

            String query = "Delete FROM centre WHERE id='" + id + "' ";

            stm.executeUpdate(query);
            //System.out.println("centre supprime");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
     
     
     public void modifierCentre(Centre c,int idd) {
        try {
            
                Statement stm = cnx.createStatement();

             String query = "UPDATE centre SET nom= '" + c.getNom() + "' , description='" + c.getDescription() + "' , lieu='" + c.getLieu() + "' , image='" + c.getImage() + "' , type='" + c.getType() + "' WHERE id='" + idd + "'";
             
              stm.executeUpdate(query);
           

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
     
     
      public Centre afficherCentreDetail(int idd) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre WHERE id='" + idd + "'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres.get(0);

    }
      
      
      
      public ObservableList<Centre> afficherCentretriNom() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre Order by nom";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres;

    }
      
      
      
         public ObservableList<Centre> afficherCentretriAdresse() throws SQLException {
             
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre Order by lieu";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres;

    }
         
         public ObservableList<Centre> afficherCentretriNomAdresse() throws SQLException {
             
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre Order by nom and lieu";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres;

    }
      
      
      
      
            public ObservableList<Centre> afficherCentreRecherche(String n) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre where nom like '%" + n + "%'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres;

    }
            
            
            
         public ObservableList<Centre> rechercherCentreType(String t) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre WHERE type='" + t + "'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres;

    }
         
         
           public ObservableList<StatCentre> StatCentre() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "select c.nom,c.id,c.lieu,c.Description,c.image,c.type, COUNT(*) nbr from centre c ,rendezvous r where c.id=r.id_centre GROUP by r.id_centre Limit 8;";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<StatCentre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                StatCentre sc = new StatCentre();
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                sc.setNbr(rst.getInt("nbr"));
                sc.setC(c);
                centres.add(sc);
            }

        
        return centres;

    }
           
           
           
           
              public ObservableList<Centre> afficherCentreMap() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre WHERE NOT EXISTS (SELECT id_centre FROM mapcentre where mapcentre.id_centre=centre.id)";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres;

    }

}

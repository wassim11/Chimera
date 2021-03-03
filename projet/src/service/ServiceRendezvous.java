/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import javafx.scene.layout.HBox;
import Entities.Rendezvous;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import projet.FXMLDocumentController;
import utils.MaConnexion;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author amira
 */
public class ServiceRendezvous {
    
    
    Connection cnx;

    public Connection getCnx() {
        return cnx;
    }

    public ServiceRendezvous() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    
    
    
    public void ajouterRendezvous(Rendezvous r) {
        try {
            Statement stm = cnx.createStatement();

            String query = "INSERT INTO rendezvous (Id_centre,Id_utilisateur,Id_service,date,temps)"
                    + "VALUES ('" + r.getId_centre() + "','" + r.getId_client() + "','" + r.getId_service() + "','" + r.getDate() + "','" + r.getTemps() + "')";

            stm.executeUpdate(query);
            //System.out.println("rdv ajouté");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    
    
    public List<Rendezvous> afficherRendezvous() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From rendezvous";

            ResultSet rst = stm.executeQuery(query);

            List<Rendezvous> listrdv = new ArrayList<>();
            
            while(rst.next()){
                Rendezvous r = new Rendezvous();
                r.setId(rst.getInt("id"));
                r.setId_centre(rst.getInt("id_centre"));
                r.setId_client(rst.getInt("id_client"));
                r.setId_service(rst.getInt("Id_service"));
                r.setTemps(rst.getString("temps"));
                r.setDate(rst.getDate("Date"));
                listrdv.add(r);
            }

        
        return listrdv;

    }

    
     public void supprimerRendezvous(int id) {
        try {
            Statement stm = cnx.createStatement();

            String query = "Delete FROM rendezvous WHERE id='" + id + "' ";

            stm.executeUpdate(query);
            //System.out.println("centre supprime");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
     
        public void modifierRendezvous(Rendezvous r,int idd) {
        try {
            
                Statement stm = cnx.createStatement();

             String query = "UPDATE Rendezvous SET Id_centre= '" + r.getId_centre() + "' , Id_utilisateur='" + r.getId_client() + "' , Id_service='" + r.getId_service() + "', date='" + r.getDate() +"', temps='" + r.getTemps() + "' WHERE id='" + idd + "'";
             
              stm.executeUpdate(query);
           

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        
        
        
        
        
//        public ResultSet SelectloadRendezvousForDate(LocalDateTime ldt, int iduser) throws SQLException
//    {  
//        List<HBox> tempList = new ArrayList();
//        Date d= java.sql.Timestamp.valueOf(ldt);
//        System.out.println(ldt);
//        System.out.println(d);
//        String dd=d.toString();
//        dd=dd.substring(0, 10);
//        System.out.println(dd);
//        
//        String query = "Select * FROM Rendezvous WHERE date = '" + dd + "' and id_utilisateur='" + iduser + "'";
//        
//        Statement stm = cnx.createStatement();
//             
//        
//            
//            ResultSet rst = stm.executeQuery(query);
//           
//        
//        return rst;
//    }
        
        
        
        
            public Rendezvous afficherRendezvousSelected(int idd) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From Rendezvous WHERE id='" + idd + "'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Rendezvous> rdv= FXCollections.observableArrayList();
            
            while(rst.next()){
                Rendezvous c = new Rendezvous();
                c.setId(idd);
                c.setId_centre(rst.getInt("id_centre"));
                c.setId_client(rst.getInt("id_utilisateur"));
                c.setTemps(rst.getString("temps"));
                c.setDate(rst.getDate("date"));
                c.setId_service(rst.getInt("id_service"));
                rdv.add(c);
            }

        
        return rdv.get(0);

    }  
        
        
        
        
        
    
    
}

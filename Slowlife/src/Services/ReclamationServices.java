/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Reclamation;
import Utils.Connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pkg3a19jdbc.IService;

/**
 *
 * @author Nadhir Boussetta
 */
public class ReclamationServices implements IService<Reclamation> {
    Connection cnx;
    Statement ste;

    public ReclamationServices() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }

    @Override
    public void ajouter(Reclamation a) throws SQLException {
        PreparedStatement pre = cnx.prepareStatement("INSERT INTO `pidev`.`Reclamation` (`sujet`, `description`,`etat`,`id_utilisateur`) VALUES ( ?, ?, ?, ?)");
       
        pre.setString(1, a.getSujet());
        pre.setString(2, a.getDescription());
        pre.setString(3, a.getEtat());
        pre.setInt(4, a.getId_utilisateur());
        
        
         try{
        pre.executeUpdate();
        System.out.println("Ajout avec SUCCEES");
        }catch(SQLException SQLex){
             System.out.println("Ajout Reclamation Impossible \n" +SQLex);
        }
        
    }

    @Override
    public boolean delete(Reclamation a) throws SQLException {
        PreparedStatement pre = cnx.prepareStatement("DELETE FROM `pidev`.`Reclamation` WHERE `ID`=?");
       
        pre.setInt(1, a.getId());
        
        
         try{
        pre.executeUpdate();
        System.out.println("Suppression avec SUCCEES");
        return true;
        }catch(SQLException SQLex){
             System.out.println("Suppression Reclamation Impossible \n" +SQLex);}
        return false;
    }

    public boolean update(Reclamation a) throws SQLException {
 PreparedStatement pre = cnx.prepareStatement("Update `pidev`.`Reclamation` set `sujet`=?,`description`=?,`etat`=?,`id_utilisateur`=? where `id`=? ");
       
        pre.setString(1, a.getSujet());
        pre.setString(2, a.getDescription());
        pre.setString(3, a.getEtat());
        pre.setInt(4, a.getId_utilisateur());
        pre.setInt(5, a.getId());
        
         try{
        pre.executeUpdate();
        System.out.println("Modification avec SUCCEES");
        return true;
        
        }catch(SQLException SQLex){
             System.out.println("Modification FEEDBACK Impossible \n" +SQLex);
        return false;
                }}

    @Override
    public List<Reclamation> readAll() throws SQLException {
        List<Reclamation> Reclamations = new ArrayList<>();
        ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `pidev`. `Reclamation`");
         while(result.next()){
            Reclamations.add(new Reclamation(result.getInt("id"),result.getString("sujet"),result.getString("description"),result.getString("etat"),result.getInt("id_utilisateur")));
         }}
          catch(SQLException SQLex)
        {
            System.out.println("Affichage Reclamation impossible"+ SQLex);
        }
      
        return Reclamations;
    }

    public void ajouter(ReclamationServices t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean delete(ReclamationServices t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean update(ReclamationServices t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    
    
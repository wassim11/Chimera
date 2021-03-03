/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Feedback;
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
public class FeedbackServices implements IService<Feedback> {
    Connection cnx;
    Statement ste;

    public FeedbackServices() throws SQLException {
        cnx = Connexion.getInstance().getConnection();
        ste = cnx.createStatement();
    }

    @Override
    public void ajouter(Feedback t) throws SQLException {
        PreparedStatement pre = cnx.prepareStatement("INSERT INTO `pidev`.`Feedback` (`sujet`, `nbr_etoiles`,`description`,`type`,`otherid`) VALUES ( ?, ?, ?, ?, ?);");
       
        pre.setString(1, t.getSujet());
        pre.setInt(2, t.getRate());
        pre.setString(3, t.getDescription());
        pre.setString(4, t.getType());
        pre.setInt(5, t.getOtherid());
        
         try{
        pre.executeUpdate();
        System.out.println("Ajout avec SUCCEES");
        }catch(SQLException SQLex){
             System.out.println("Ajout FEEDBACK Impossible \n" +SQLex);
        }
        
    }

    public boolean delete(Feedback t) throws SQLException {
        PreparedStatement pre = cnx.prepareStatement("DELETE FROM `pidev`.`Feedback` WHERE `ID`=?");
       
        pre.setInt(1, t.getId());
        
        
         try{
        pre.executeUpdate();
        System.out.println("Suppression avec SUCCEES");
        return true;
        }catch(SQLException SQLex){
             System.out.println("Suppression FEEDBACK Impossible \n" +SQLex);}
        return false;
    }

    public boolean update(Feedback t) throws SQLException {
 PreparedStatement pre = cnx.prepareStatement("Update `pidev`.`Feedback` set `sujet`=?,`nbr_etoiles`=?,`description`=?, `type`=?, `otherid`=? where `id`=? ");
       
        pre.setString(1, t.getSujet());
        pre.setInt(2, t.getRate());
        pre.setString(3, t.getDescription());
        pre.setString(4, t.getType());
        pre.setInt(5, t.getOtherid());
        pre.setInt(6, t.getId());
        
         try{
        pre.executeUpdate();
        System.out.println("Modification avec SUCCEES");
        return true;
        
        }catch(SQLException SQLex){
             System.out.println("Modification FEEDBACK Impossible \n" +SQLex);
        return false;
                }}

    @Override
    public List<Feedback> readAll() throws SQLException {
        List<Feedback> Feedbacks = new ArrayList<>();
        ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `pidev`. `Feedback`");
         while(result.next()){
            Feedbacks.add(new Feedback(result.getInt("id"),result.getString("sujet"),result.getInt("nbr_etoiles"),result.getString("description"),result.getString("type"),result.getInt("otherid")));
        }}
          catch(SQLException SQLex)
        {
            System.out.println("Affichage Feedback impossible"+ SQLex);
        }
      
        return Feedbacks;
    }
    
    
}

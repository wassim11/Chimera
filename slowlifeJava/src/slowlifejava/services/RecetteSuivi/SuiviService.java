/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.RecetteSuivi;

import entities.users.Utilisateur;
import slowlifejava.entities.RecetteSuivi.SuiviRegime;
//import slowlifejava.entities.RecetteSuivi.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import slowlifejava.utils.SlowlifeDB;

/**
 *
 * @author Yassine
 */
public class SuiviService{
    private final Connection cnx;
    private Statement ste;
    private PreparedStatement pre;

    public SuiviService() throws SQLException{
       cnx = SlowlifeDB.getInstance().getConnection(); 
    }
    
    public void ajouter(SuiviRegime t) throws SQLException {        
         pre = cnx.prepareStatement("INSERT INTO `slowlife`.`suivi` ( `date` , `poids` , `taille` "
                 + " , `heure_activite` ,`conso_eau` , `idUtilisateur`) "
                 + "VALUES (?,?,?,?,?,?);");
        t.setDateSuivi(t.getDateSuivi());
        java.sql.Date date_sql = new java.sql.Date(t.getDateSuivi().getTime());
        System.out.println(date_sql);
        pre.setDate(1,  date_sql);
        pre.setFloat(2, t.getPoid());
        pre.setFloat(3, t.getTaille());
        pre.setInt(4, t.getHeursActivite());
        pre.setInt(5,t.getConsommationEau());
        pre.setInt(6,t.getUser().getId());
        try{
        pre.executeUpdate();
        }catch(SQLException SQLex)
        {
            System.out.println("Ajout Suivi Impossible \n" +SQLex);
    }

//To change body of generated methods, choose Tools | Templates.
    }

    public boolean delete(SuiviRegime t) throws SQLException {
        pre = cnx.prepareStatement("delete from `slowlife`.`suivi` where id=?");
        pre.setInt(1, t.getIdSuivi());
        try{
        pre.executeUpdate(); }//To change body of generated methods, choose Tools | Templates.//To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Suppression recette impossible \n" + SQLex);
            return false;
        } 
        return true;
        
    }

    public boolean update(SuiviRegime t) throws SQLException {
       pre = cnx.prepareStatement("Update `slowlife`.`suivi` set `poids`=?,`taille`=?,`heure_activite`=?,`conso_eau`=? where `id`=? ");
         
        pre.setFloat(1, t.getPoid());
        pre.setFloat(2, t.getTaille());
        pre.setInt(3, t.getHeursActivite());
        pre.setInt(4,t.getConsommationEau());
        pre.setInt(5,t.getIdSuivi());
        try{
        pre.executeUpdate();
        return true;
        }catch(SQLException SQLex)
        {
            System.out.println("Ajout Suivi Impossible \n" +SQLex);
            return false;
    }
  //To change body of generated methods, choose Tools | Templates.
        
    }

    public List<SuiviRegime> readAll(Utilisateur user) throws SQLException {
         List<SuiviRegime> Suivi = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select *,(poids/POW(taille,2)) as IMC from `slowlife`. `suivi` where idUtilisateur="+user.getId());
         while(result.next()){
            Suivi.add(new SuiviRegime(result.getInt("id"),result.getDate("date"),result.getInt("poids"),result.getFloat("taille"), result.getInt("heure_activite"), result.getInt("conso_eau"),result.getFloat("IMC")));
        } //To change body of generated methods, choose Tools | Templates.
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Suivi impossible"+ SQLex);
        }
       return Suivi; //To change body of generated methods, choose Tools | Templates.
    }

     public SuiviRegime read(int id) throws SQLException {
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `slowlife`. `suivi` where id="+id);
         result.next();
            return(new SuiviRegime(result.getInt("id"),result.getDate("date"),result.getInt("poids"),result.getFloat("taille"), result.getInt("heure_activite"), result.getInt("conso_eau"),result.getFloat("IMC")));
        } //To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Suivi impossible"+ SQLex);
        }
       return (new SuiviRegime()); //To change body of generated methods, choose Tools | Templates.
    }
    public boolean deleteAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public SuiviRegime readone(int idSuivi) {
        try{
            String req="SELECT * FROM suivi WHERE id="+idSuivi;
            ste=cnx.createStatement();
            ResultSet result=ste.executeQuery(req);
            result.next();
        SuiviRegime SR=new SuiviRegime(result.getInt("id"),result.getDate("date"),result.getInt("poids"),result.getFloat("taille"), result.getInt("heure_activite"), result.getInt("conso_eau"),result.getFloat("IMC"));
        return SR;
        }
        catch(SQLException SQLex)
        {
            System.out.println("Recherche SuiviRegime impossible"+ SQLex);
        }
        //To change body of generated methods, choose Tools | Templates.
        return(new SuiviRegime());
    }

    public List<SuiviRegime> Tri(String requete) throws SQLException {
        List<SuiviRegime> Suivi = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery(requete);
         while(result.next()){
            Suivi.add(new SuiviRegime(result.getInt("id"),result.getDate("date"),result.getInt("poids"),result.getFloat("taille"), result.getInt("heure_activite"), result.getInt("conso_eau"),result.getFloat("IMC")));
        } //To change body of generated methods, choose Tools | Templates.
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Suivi impossible"+ SQLex);
        }
       return Suivi; //To change body of generated methods, choose Tools | Templates.
    }
     public boolean readoneByDate(java.sql.Date date,Utilisateur user) {
        try{
            String req="SELECT * FROM suivi WHERE date='"+date+"' AND idUtilisateur='"+user.getId()+"'";
            System.out.println(req);
            ste=cnx.createStatement();
            ResultSet result=ste.executeQuery(req);
            return result.next();
        }
        catch(SQLException SQLex)
        {
            System.out.println("Recherche SuiviRegime impossible"+ SQLex);
        }
        //To change body of generated methods, choose Tools | Templates.
        return false;
    }
      public boolean readoneByUser(Utilisateur user) {
        try{
            String req="SELECT * FROM suivi WHERE idUtilisateur='"+user.getId()+"'";
            System.out.println(req);
            ste=cnx.createStatement();
            ResultSet result=ste.executeQuery(req);
            return result.next();
        }
        catch(SQLException SQLex)
        {
            System.out.println("Recherche SuiviRegime impossible"+ SQLex);
        }
        //To change body of generated methods, choose Tools | Templates.
        return false;
    }
     public SuiviRegime readoneLastSuivi(Utilisateur user ) {
        try{
            String req="SELECT * FROM suivi where idUtilisateur='"+user.getId()+"'";
            System.out.println(req);
            ste=cnx.createStatement();
            ResultSet result=ste.executeQuery(req);
            while(result.next()){
                if(result.isLast())
                {
                     SuiviRegime SR=new SuiviRegime(result.getInt("id"),result.getDate("date"),result.getInt("poids"),result.getFloat("taille"), result.getInt("heure_activite"), result.getInt("conso_eau"),result.getFloat("IMC"));
                 return SR;
                }
            }
           
        }
        catch(SQLException SQLex)
        {
            System.out.println("Recherche SuiviRegime impossible"+ SQLex);
        }
        //To change body of generated methods, choose Tools | Templates.
        return new SuiviRegime();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.RecetteSuivi;

import slowlifejava.entities.RecetteSuivi.Ingredient;
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
public class IngredientService {

    private final Connection cnx;
    private Statement ste;
    private PreparedStatement pre;

    public IngredientService() throws SQLException {
        cnx = SlowlifeDB.getInstance().getConnection();  
    }
    
    
    public void ajouter(Ingredient t) throws SQLException {
       pre = cnx.prepareStatement("INSERT INTO `slowlife`.`ingredient` ( `calorie` , `nom` , `image` , `unite` ) VALUES (?,?,?,?);");
        pre.setInt(1, t.getCalories());
        pre.setString(2, t.getNom());
        pre.setString(3, t.getImage());
        pre.setString(4, t.getUnite());
        try{
        pre.executeUpdate();
        System.out.println("Ajout avec SUCCEES");
        }catch(SQLException SQLex){
             System.out.println("Ajout Ingredient Impossible \n" +SQLex);
        }
           //To change body of generated methods, choose Tools | Templates.
    }

    public boolean delete(Ingredient t) throws SQLException {
       pre = cnx.prepareStatement("delete from `slowlife`.`ingredient` where id=?");
        pre.setInt(1, t.getId());
        try{
        pre.executeUpdate(); 
        }
        catch(SQLException SQLex)
        {
            System.out.println("Suppression Ingredient impossible \n" + SQLex);
            return false;
        } 
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    public boolean update(Ingredient t) throws SQLException {
       pre = cnx.prepareStatement("Update `slowlife`.`ingredient` set `nom`=?,`image`=?,`calorie`=? ,`unite`=? where `id`=? ");
        pre.setString(1, t.getNom());
        pre.setString(2, t.getImage());
        pre.setInt(3, t.getCalories());
        pre.setString(4, t.getUnite());
        pre.setInt(5, t.getId());
        try{
        pre.executeUpdate();
        }catch(SQLException SQLex){
            System.out.println("Modification Ingredient impossible \n"+SQLex);
            return false;
        }
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    public List<Ingredient> readAll() throws SQLException {
        List<Ingredient> Ingredients = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `slowlife`. `ingredient` order by id");
         while(result.next()){
            Ingredients.add(new Ingredient(result.getInt("id"),result.getString("nom"),result.getString("image"),result.getInt("calorie"),result.getString("unite")));
            //System.out.println(result.getInt("id")+result.getString("nom")+result.getString("image")+result.getInt("calorie"));
        } 
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Ingredient impossible"+ SQLex);
        }
       return Ingredients; //To change body of generated methods, choose Tools | Templates.
       
    }

    public boolean deleteAll() throws SQLException {
        pre = cnx.prepareStatement("delete from `slowlife`.`ingredient`");
        try{
        pre.executeUpdate(); }//To change body of generated methods, choose Tools | Templates.//To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Suppression Ingredient impossible \n" + SQLex);
            return false;
        } 
        return true;
    } //To change body of generated methods, choose Tools | Templates.

    /**
     *
     * @param Ing
     * @return 
     * @throws SQLException
     */
    public boolean Recherche(Ingredient Ing) throws SQLException {
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `slowlife`. `ingredient`");
         while(result.next()){
           Ingredient ingbdd= new Ingredient(result.getInt("id"),result.getString("nom"),result.getString("image"),result.getInt("calorie"),result.getString("unite"));
            return Ing.equals(ingbdd);
        } 
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Ingredient impossible"+ SQLex);
        }
       return false; //To change body of generated methods, choose Tools | Templates.
    }
    public Ingredient readone(int id) throws SQLException
    {     
        try{
            String req="SELECT * FROM ingredient WHERE id="+id;
            ste=cnx.createStatement();
            ResultSet result=ste.executeQuery(req);
            result.next();
       Ingredient ing = new Ingredient(result.getInt("id"),result.getString("nom"),result.getString("image"),result.getInt("calorie"),result.getString("unite"));
        return ing;
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Ingredient impossible"+ SQLex);
        }
        //To change body of generated methods, choose Tools | Templates.
        return(new Ingredient());
    }
    
    
    
     public List<Ingredient> TriAffche(String Critere) throws SQLException {
        List<Ingredient> Ingredients = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `slowlife`. `ingredient` order by "+Critere);
         while(result.next()){
            Ingredients.add(new Ingredient(result.getInt("id"),result.getString("nom"),result.getString("image"),result.getInt("calorie"),result.getString("unite")));
            System.out.println(result.getInt("id")+result.getString("nom")+result.getString("image")+result.getInt("calorie"));
        } 
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Ingredient impossible"+ SQLex);
        }
       return Ingredients; //To change body of generated methods, choose Tools | Templates.
    }
        public List<String> AfficheToutLesNoms() throws SQLException {
        List<String> Ingredients = new ArrayList<>();
        ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select nom from `slowlife`. `ingredient` ");
         while(result.next()){
            Ingredients.add(result.getString("nom"));    
        } 
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Affiche Tout Les Noms impossible"+ SQLex);
        }
       return Ingredients; //To change body of generated methods, choose Tools | Templates.
    }
        public Ingredient RechercheParNom(Ingredient t) throws SQLException {
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from ingredient where nom='"+t.getNom()+"'");
        result.next();
            return (new Ingredient(result.getInt("id"),result.getString("nom"),result.getString("image"),result.getInt("calorie"),result.getString("unite")));
            
        } //To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
       return (new Ingredient());
    }
        
        public String RechercheImageParNom(Ingredient t) throws SQLException {
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select Image from ingredient where nom='"+t.getNom()+"'");
        result.next();
            return result.getString("Image");
            
        } //To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
       return "";
    }
        
}

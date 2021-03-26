/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.RecetteSuivi;

import entities.RecetteSuivi.Ingredient;
import entities.RecetteSuivi.IngredientRecette;
import entities.RecetteSuivi.Recette;
import entities.RecetteSuivi.User;
//import Utils.MaConnexion;
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
public class ServiceIngredientRecette{
    private final Connection cnx;
    private Statement ste;
    private PreparedStatement pre;

    public ServiceIngredientRecette() throws SQLException {
        cnx = SlowlifeDB.getInstance().getConnection();  
    }
    

    public void ajouter(IngredientRecette t) throws SQLException {
       if(Recherche(t)==false){
       pre = cnx.prepareStatement("INSERT INTO `slowlife`.`ingredientrecette` ( `idRecette` , `idIngredient` , `Quantite` ) VALUES (?,?,?);");
       pre.setInt(2, t.getIng().getId());
       pre.setInt(1, t.getRct().getIdRecette());
       pre.setFloat(3, t.getQuantite());
        try{
        pre.executeUpdate();
        System.out.println("Ajout avec SUCCEES");
        }catch(SQLException SQLex){
            System.out.println("Ajout IngredientRecette Impossible \n" +SQLex);
            
    } 
        }//To change body of generated methods, choose Tools | Templates.
    }

    public boolean delete(IngredientRecette t) throws SQLException {
          pre = cnx.prepareStatement("delete from `slowlife`.`ingredientrecette` where idRecette=? && idIngredient=?");
       pre.setInt(2, t.getIng().getId());
       pre.setInt(1, t.getRct().getIdRecette());
        try{
        pre.executeUpdate(); 
        }//To change body of generated methods, choose Tools | Templates.//To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Suppression ingredientrecette impossible \n" + SQLex);
            return false;
        } 
        return true;
    }

    public boolean deleteAll(Recette t) throws SQLException {
       pre = cnx.prepareStatement("delete from `slowlife`.`ingredientrecette` where idRecette=?");
       pre.setInt(1, t.getIdRecette());
        try{
        pre.executeUpdate(); 
        }//To change body of generated methods, choose Tools | Templates.//To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Suppression ingredientrecette impossible \n" + SQLex);
            return false;
        } 
        return true;
    }

    public boolean update(IngredientRecette t) throws SQLException {
 pre = cnx.prepareStatement("Update `slowlife`.`ingredientrecette` set `Quantite`=? where `idRecette`=? && `idIngredient`=? ");
       pre.setInt(3, t.getIng().getId());
       pre.setInt(2, t.getRct().getIdRecette());
       pre.setFloat(1, t.getQuantite());
        try{
        pre.executeUpdate();
        }catch(SQLException SQLex){
            System.out.println("Modification IngredientsRecette impossible \n"+SQLex);
            return false;
        }
        return true; //To change body of generated methods, choose Tools | Templates.    }
    }
    public List readAll(Recette id) throws SQLException {
        List<IngredientRecette> IngredientsRecette = new ArrayList<>();
        ste=cnx.createStatement();
        try{
               /* String SQL="SELECT recette.idRecette,recette.nomRecette, recette.typeRecette, recette.description,"
                + "ingredientrecette.Quantite,CONCAT(ingredient.nom ,'(',ingredient.typeIng,')') as Ingredients "
                + "FROM ((ingredientrecette INNER JOIN ingredient ON ingredientrecette.idIngredient = ingredient.idIng) "
                + "INNER JOIN recette ON recette.idRecette  = ingredientrecette.idRecette);";*/
        String SQL="SELECT recette.idRecette,recette.nomRecette, recette.image as imager,ingredient.image,  recette.typeRecette, recette.description,"
                + "ingredientrecette.Quantite,ingredient.nom as Ingredients "
                + "FROM ((ingredientrecette INNER JOIN recette ON (recette.idRecette  = ingredientrecette.idRecette AND ingredientrecette.idRecette="+id.getIdRecette()+")"
                + ")INNER JOIN ingredient ON ingredientrecette.idIngredient = ingredient.id) "
                + ";";
        ResultSet result =  ste.executeQuery(SQL);
         while(result.next()){
            Ingredient ing = new Ingredient(result.getString("Ingredients"),result.getString("ingredient.image"));
            Recette rct = new Recette(result.getInt("idRecette"),result.getString("nomRecette"),result.getString("description"),result.getString("typeRecette"),result.getString("imager"), new User(1));
             System.out.println("imager"+result.getString("imager"));
            IngredientsRecette.add(new IngredientRecette(rct,ing,result.getInt("Quantite")));
           
        }
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage IngredientsRecette impossible"+ SQLex);
        }
       return IngredientsRecette;    }
public List readAll2() throws SQLException {
        List<IngredientRecette> IngredientsRecette = new ArrayList<>();
        ste=cnx.createStatement();
        try{
                String SQL="SELECT recette.idRecette,recette.nomRecette,recette.image, recette.typeRecette, recette.description,"
                + "ingredientrecette.Quantite,CONCAT(ingredient.nomIng ,'(',ingredient.typeIng,')') as Ingredients "
                + "FROM ((ingredientrecette INNER JOIN ingredient ON ingredientrecette.idIngredient = ingredient.idIng) "
                + "INNER JOIN recette ON recette.idRecette  = ingredientrecette.idRecette);";
        
        ResultSet result =  ste.executeQuery(SQL);
         while(result.next()){
            Ingredient ing = new Ingredient(result.getString("Ingredients"),result.getString("Ingredients"));
            Recette rct = new Recette(result.getInt("idRecette"),result.getString("nomRecette"),result.getString("description"),result.getString("typeRecette"),result.getString("image"),new User(1));
            IngredientsRecette.add(new IngredientRecette(rct,ing,result.getInt("Quantite")));
           
        }
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage IngredientsRecette impossible"+ SQLex);
        }
       return IngredientsRecette;    }
    public boolean Recherche(IngredientRecette t) throws SQLException {
        ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `slowlife`. `ingredientrecette`");
         while(result.next()){
           IngredientRecette ingrecettebdd= new IngredientRecette(null,null,result.getInt("Quantite"));
            return t.equals(ingrecettebdd); // update(new IngredientRecette(ingrecettebdd.getIdRecette(),ingrecettebdd.getIdIngredient(),ingrecettebdd.getQuantite()+t.getQuantite()));
        }
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Ingredient impossible"+ SQLex);
        }
       return false; //To change body of generated methods, choose Tools | Templates.    }
    
}
    
}

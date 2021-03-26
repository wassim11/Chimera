/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.RecetteSuivi;
//import Utils.*;
import entities.RecetteSuivi.Recette;
import entities.RecetteSuivi.User;
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
public class RecetteService{
    private final Connection cnx;
    private Statement ste;
    
    //private Statement ste;
    private PreparedStatement pre;
    
     public RecetteService() throws SQLException {
        cnx = SlowlifeDB.getInstance().getConnection();      
    }
    public void ajouter(Recette t) throws SQLException {
        pre = cnx.prepareStatement("INSERT INTO `slowlife`.`recette` ( `nomRecette` , `typeRecette` , `description`, `image` , `etat`  ) VALUES (?,?,?,?,?);");
        pre.setString(1, t.getNomRecette());
        pre.setString(2, t.getTypeRecette());
        pre.setString(3, t.getDescription());
        pre.setString(4, t.getImage());
        pre.setString(5, t.getEtat());
        try{
        pre.executeUpdate();
        }catch(SQLException SQLex)
        {
            System.out.println("Ajout Recette Impossible \n" +SQLex);//To change body of generated methods, choose Tools | Templates.
    }
}
    public boolean delete(Recette t) throws SQLException {
        pre = cnx.prepareStatement("delete from `slowlife`.`recette` where idRecette=?");
        pre.setInt(1, t.getIdRecette());
        try{
        pre.executeUpdate(); }//To change body of generated methods, choose Tools | Templates.//To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Suppression recette impossible \n" + SQLex);
            return false;
        } 
        return true;
    }
   
    public boolean update(Recette t) throws SQLException {
        pre = cnx.prepareStatement("Update `slowlife`.`recette` set `nomRecette`=?,`typeRecette`=?,`description`=? ,`image`=? where `idRecette`=? ");
        pre.setString(1, t.getNomRecette());
        pre.setString(2, t.getTypeRecette());
        pre.setString(3, t.getDescription());
        pre.setString(4, t.getImage());
        pre.setInt(5, t.getIdRecette());
        try{
        pre.executeUpdate();
        }catch(SQLException SQLex){
            System.out.println("Modification Recette impossible \n"+SQLex);
            return false;
        }
        return true;//To change body of generated methods, choose Tools | Templates.//To change body of generated methods, choose Tools | Templates.
    }
 public boolean AccepterRefuser(Recette t) throws SQLException {
        pre = cnx.prepareStatement("Update `slowlife`.`recette` set `etat`=? where `idRecette`=? ");
        pre.setString(1, t.getEtat());
        pre.setInt(2, t.getIdRecette());
        try{
        pre.executeUpdate();
        }catch(SQLException SQLex){
            System.out.println("Modification Recette impossible \n"+SQLex);
            return false;
        }
        return true;//To change body of generated methods, choose Tools | Templates.//To change body of generated methods, choose Tools | Templates.
    }
    public List<Recette> readAllCoach(User user) throws SQLException {
         List<Recette> Regimes = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `slowlife`. `recette` where idcoach='"+user.getId()+"'");
         while(result.next()){
            Regimes.add(new Recette(result.getInt("idRecette"),result.getString("nomRecette"),result.getString("description"),result.getString("typeRecette"),result.getString("image"),result.getString("etat")));
            //System.out.println(result.getString("nomRecette")+result.getInt("calorie")+result.getString("description"));
        } //To change body of generated methods, choose Tools | Templates.
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
       return Regimes;
    }
    public List<Recette> readAllClient() throws SQLException {
         List<Recette> Regimes = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `slowlife`. `recette` where etat='Accepter'");
         while(result.next()){
            Regimes.add(new Recette(result.getInt("idRecette"),result.getString("nomRecette"),result.getString("description"),result.getString("typeRecette"),result.getString("image"),result.getString("etat")));
            //System.out.println(result.getString("nomRecette")+result.getInt("calorie")+result.getString("description"));
        } //To change body of generated methods, choose Tools | Templates.
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
       return Regimes;
    }
     public List<Recette> readAll() throws SQLException {
         List<Recette> Regimes = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select * from `slowlife`. `recette` ");
         while(result.next()){
            Regimes.add(new Recette(result.getInt("idRecette"),result.getString("nomRecette"),result.getString("description"),result.getString("typeRecette"),result.getString("image"),result.getString("etat")));
            //System.out.println(result.getString("nomRecette")+result.getInt("calorie")+result.getString("description"));
        } //To change body of generated methods, choose Tools | Templates.
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
       return Regimes;
    }
        public List<Recette> readAllId() throws SQLException {
         List<Recette> Regimes = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select idRecette from `slowlife`. `recette`");
         while(result.next()){
            Regimes.add(new Recette(result.getInt("idRecette")));
            //System.out.println(result.getString("nomRecette")+result.getInt("calorie")+result.getString("description"));
        } //To change body of generated methods, choose Tools | Templates.
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
       return Regimes;
    }
        
    public Recette readone(int id) throws SQLException
    {
         
        try{
            String req="SELECT * FROM recette WHERE idRecette="+id;
            ste=cnx.createStatement();
            ResultSet result=ste.executeQuery(req);
            result.next();
       Recette rct = new Recette(result.getInt("idRecette"),result.getString("nomRecette"),result.getString("description"),result.getString("typeRecette"),result.getString("image"),result.getString("etat"),new User(1));
       
       return rct;
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
        //To change body of generated methods, choose Tools | Templates.
        return(new Recette());
    }

    public List<Recette> Recherche(String input) throws SQLException {
          List<Recette> Regimes = new ArrayList<>();
        try{
            String req="SELECT * FROM recette WHERE nomRecette like '%"+input+"'%";
            ste=cnx.createStatement();
            ResultSet result=ste.executeQuery(req);
            while(result.next()){
       Regimes.add(new Recette(result.getInt("idRecette"),result.getString("nomRecette"),result.getString("description"),result.getString("typeRecette"),result.getString("image"),result.getString("etat")));
            }     
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
        //To change body of generated methods, choose Tools | Templates.
        return(Regimes);
    }
     public List<Recette> Tri(String sql) throws SQLException {
         List<Recette> Regimes = new ArrayList<>();
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery(sql);
         while(result.next()){
            Regimes.add(new Recette(result.getInt("idRecette"),result.getString("nomRecette"),result.getString("description"),result.getString("typeRecette"),result.getString("image"),result.getString("etat"),new User(1)));
            //System.out.println(result.getString("nomRecette")+result.getInt("calorie")+result.getString("description"));
        } //To change body of generated methods, choose Tools | Templates.
        }
        catch(SQLException SQLex)
        {
            System.out.println("Affichage Recette impossible"+ SQLex);
        }
       return Regimes;
    }

    public Recette RechercheParNom(Recette t) throws SQLException {
         ste=cnx.createStatement();
        try{
        ResultSet result =  ste.executeQuery("select idRecette from recette where nomRecette='"+t.getNomRecette()+"'");
        result.next();
            return (new Recette(result.getInt("idRecette")));
            
        } //To change body of generated methods, choose Tools | Templates.
        catch(SQLException SQLex)
        {
            System.out.println("Recherche Par Nom impossible"+ SQLex);
        }
       return (new Recette());
    }
   
}

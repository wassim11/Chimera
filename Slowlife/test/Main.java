
import Entities.Feedback;
import Entities.Reclamation;
import Services.FeedbackServices;
import Services.ReclamationServices;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nadhir Boussetta
 */
public class Main {
       public static void main(String[] args) throws SQLException {
    //FeedbackServices IS = new FeedbackServices();
    //Feedback F1= new Feedback(2, "aaaaaa", 1, "areeeee" , "aeeeez" , 2);
    
    //IS.ajouter(F1);
    //IS.update(F1);
    //IS.delete(F1);
    /*IS.readAll();
           System.out.println(IS.readAll());*/
    ReclamationServices IS = new ReclamationServices();
    Reclamation R1= new Reclamation(2, "tttttt", "description", "etat", 66);
    
     //IS.ajouter(R1);
    //IS.update(R1);
    //IS.delete(R1);
    /*IS.readAll();
      System.out.println(IS.readAll());*/
            
}}

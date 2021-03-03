/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amira
 */
public class MaConnexion {
    
     String url="jdbc:mysql://localhost:3306/slowlife";
    String login="root";
    String pwd="";
    Connection cnx;
    public static MaConnexion instance;
    
    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion établie!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection getCnx() {
        return cnx;
    }
    
    
    public static MaConnexion getInstance(){
        if(instance == null){
            instance = new MaConnexion();
        }
        return instance;
    }
    
}

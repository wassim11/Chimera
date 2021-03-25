/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.entities.centre;

/**
 *
 * @author amira
 */
public class ServiceOffert {
    private int id;
    private String nom,type;
    private float prix;
    public static int idselected;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public float getPrix() {
        return prix;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author amira
 */
public class CentreService {
 
    private int id_service , id_centre;
    private float prix;

    public int getId_service() {
        return id_service;
    }

    public int getId_centre() {
        return id_centre;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

    public void setId_centre(int id_centre) {
        this.id_centre = id_centre;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrix() {
        return prix;
    }
    
}

    


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
public class CentreService {
 
   // private int id_service , id_centre;
    private Centre centre;
    private Service service;

    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Centre getCentre() {
        return centre;
    }

    public Service getService() {
        return service;
    }
    private float prix;


    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrix() {
        return prix;
    }
    
}

    


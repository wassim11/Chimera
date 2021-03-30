/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.entities.RecetteSuivi;

import entities.users.Utilisateur;

/**
 *
 * @author Yassine
 */
public class Recette {
   private int idRecette;
   private String nomRecette;
   private String description;
   private String typeRecette;
   private String image;
   private String etat;
   private Utilisateur user;
   
   
   
    public Recette() {
    }

    public Recette(int idRecette) {
        this.idRecette = idRecette;
    }

    public Recette(String nomRecette) {
        this.nomRecette = nomRecette;
    }

    public Recette(int idRecette, String etat) {
        this.idRecette = idRecette;
        this.etat = etat;
    }

    public Recette(int idRecette, String nomRecette, String description, String typeRecette, String image, String etat) {
        this.idRecette = idRecette;
        this.nomRecette = nomRecette;
        this.description = description;
        this.typeRecette = typeRecette;
        this.image = image;
        this.etat = etat;
    }

    public Recette(String nomRecette, String description, String typeRecette, String image) {
        this.nomRecette = nomRecette;
        this.description = description;
        this.typeRecette = typeRecette;
        this.image = image;
    }

    public Recette(int idRecette, String nomRecette, String description, String typeRecette, String image) {
        this.idRecette = idRecette;
        this.nomRecette = nomRecette;
        this.description = description;
        this.typeRecette = typeRecette;
        this.image = image;
    }

    

    public Recette(String nomRecette, String description, String typeRecette,String image,String etat,Utilisateur user) {
        this.nomRecette = nomRecette;
        this.description = description;
        this.typeRecette = typeRecette;
        this.image=image;
        this.etat=etat;
        this.user=user;
    }

    public Recette(int idRecette, String nomRecette, String description, String typeRecette,String image,String etat,Utilisateur user) {
        this.idRecette = idRecette;
        this.nomRecette = nomRecette;
        this.description = description;
        this.typeRecette = typeRecette;
        this.image=image;
        this.etat=etat;
        this.user = user;
    }


    public Recette(int idRecette, String nomRecette, String description, String typeRecette,String image,Utilisateur user) {
        this.idRecette = idRecette;
        this.nomRecette = nomRecette;
        this.description = description;
        this.typeRecette = typeRecette;
        this.image=image;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

 
    public int getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(int idRecette) {
        this.idRecette = idRecette;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getNomRecette() {
        return nomRecette;
    }

    public void setNomRecette(String nomRecette) {
        this.nomRecette = nomRecette;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeRecette() {
        return typeRecette;
    }

    public void setTypeRecette(String typeRecette) {
        this.typeRecette = typeRecette;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Recette{" + "idRecette=" + idRecette + ", nomRecette=" + nomRecette + ", description=" + description + ", typeRecette=" + typeRecette + ", image=" + image + ", etat=" + etat + ", user=" + user + '}';
    }
    
}

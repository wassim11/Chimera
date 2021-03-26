/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.RecetteSuivi;

/**
 *
 * @author Yassine
 */
public class Ingredient {
    private int id;
    private String nom;
    private String image;
    private int calories;

  

    
        
    public Ingredient(int id) {
        this.id = id;
    }

    public Ingredient(String nom) {
        this.nom = nom;
    }

    public Ingredient(String nom, String image) {
        this.nom = nom;
        this.image = image;
    }

    public Ingredient(int id, String nom,String image,int calories) {
        this.id = id;
        this.nom = nom;
        this.image = image;
        this.calories = calories;
    }

    public Ingredient(String nom, String image, int calories) {
        this.nom = nom;
        this.image = image;
        this.calories = calories;
    }

    public Ingredient() {
    }

    public Ingredient(Ingredient ing) {
       this.id=ing.getId();
       this.nom=ing.getNom();
       this.image=ing.getImage();
       this.calories=ing.getCalories();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingredient other = (Ingredient) obj;
        return this.nom.equals(other.getNom());
    }

     


}

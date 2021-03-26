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
public class IngredientRecette {
 

    private Recette rct;
    private Ingredient ing;
    private int quantite;
    private float Calories;
    public IngredientRecette() {
    }

    public IngredientRecette(Recette rct, Ingredient ing, int quantite) {
        this.rct = rct;
        this.ing = ing;
        this.quantite = quantite;
    }

    public Recette getRct() {
        return rct;
    }

    public void setRct(Recette rct) {
        this.rct = rct;
    }

    public Ingredient getIng() {
        return ing;
    }

    public void setIng(Ingredient ing) {
        this.ing = ing;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "\n IngredientRecette{" + "rct=" + rct + "ing=" + ing + "quantite=" + quantite + '}';
    }

    
}

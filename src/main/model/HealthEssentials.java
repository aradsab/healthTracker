package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.Objects;

//store the amount of body essentials of the user.
public class HealthEssentials implements Writable {

    private int calcium;
    private int iron;
    private int zinc;
    private int vitaminB;
    private int vitaminC;
    private int sugar;

    private static final ImageIcon calciumFoodSuggestion = new ImageIcon("Calcium.png");

    private static final ImageIcon ironFoodSuggestion = new ImageIcon("Iron.png");
    private static final ImageIcon zincFoodSuggestion = new ImageIcon("Zinc.png");
    private static final ImageIcon vitaminBFoodSuggestion = new ImageIcon("Vitamine B.png");
    private static final ImageIcon vitaminCFoodSuggestion = new ImageIcon("Vitamine C.png");
    private static final ImageIcon sugarFoodSuggestion = new ImageIcon("Sugar.png");


    public HealthEssentials(int calcium, int iron, int zinc, int vitaminB, int vitaminC, int sugar) {
        this.calcium = calcium;
        this.iron = iron;
        this.vitaminB = vitaminB;
        this.vitaminC = vitaminC;
        this.sugar = sugar;
        this.zinc = zinc;
    }

    //setter and getters
    public void setCalcium(int n) {
        calcium = n;
    }

    public int getCalcium() {
        return calcium;
    }


    public void setIron(int n) {
        iron = n;
    }

    public int getIron() {
        return iron;
    }


    public void setVitaminB(int n) {
        vitaminB = n;
    }

    public int getVitaminB() {
        return vitaminB;
    }


    public void setVitaminC(int n) {
        vitaminC = n;
    }

    public int getVitaminC() {
        return vitaminC;
    }


    public void setSugar(int n) {
        sugar = n;
    }

    public int getSugar() {
        return sugar;
    }


    public void setZinc(int n) {
        zinc = n;
    }

    public int getZinc() {
        return zinc;
    }

    //EFFECTS: based on the first highest essentials it returns suggestion images.
    public ImageIcon getSuggestion() {
        ImageIcon img;
        if (calcium > 2) {
            img = calciumFoodSuggestion;
        } else if (iron > 2) {
            img = ironFoodSuggestion;
        } else if (zinc > 2) {
            img = zincFoodSuggestion;
        } else if (vitaminB > 2) {
            img = vitaminBFoodSuggestion;
        } else if (vitaminC > 2) {
            img = vitaminCFoodSuggestion;
        } else if (sugar > 2) {
            img = sugarFoodSuggestion;
        } else {
            img = sugarFoodSuggestion;
        }
        return img;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("calcium", calcium);
        json.put("iron", iron);
        json.put("zinc", zinc);
        json.put("vitaminB", vitaminB);
        json.put("vitaminC", vitaminC);
        json.put("sugar", sugar);
        return json;
    }

    public double calculateAverage() {
        return (Math.round((double) calcium + (double) sugar + (double) zinc + (double) vitaminC
                + (double) vitaminB + (double) iron) / (double) 6);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HealthEssentials that = (HealthEssentials) o;
        return calcium == that.calcium && iron == that.iron && zinc == that.zinc && vitaminB == that.vitaminB
                && vitaminC == that.vitaminC && sugar == that.sugar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calcium, iron, zinc, vitaminB, vitaminC, sugar);
    }
}
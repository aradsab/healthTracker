package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Profile implements Writable {

    protected String firstName;
    protected String lastName;
    protected boolean gender;
    protected int age;
    protected int height;
    protected int weight;
    private List<HealthEssentials> essentials;


    //EFFECTS: construct a new profile
    public Profile(String firstName, String lastName, Boolean gender, int age, int height, int weight) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        essentials = new ArrayList<>();
    }

    public void setYesSpecificEssential(int i) {
        HealthEssentials he = setTempEssential();
        switch (i) {
            case 0: case 1:
                he.setSugar(he.getSugar() + 1);
                he.setIron(he.getIron() + 1);
                break;
            case 2: case 3:
                he.setCalcium(he.getCalcium() + 1);
                break;
            case 4: case 5:
                he.setVitaminB(he.getVitaminB() + 1);
                break;
            case 6: case 7:
                he.setVitaminC(he.getVitaminC() + 1);
                break;
            case 8: case 9:
                he.setIron(he.getIron() + 1);
                break;
            default:
        }
        setEssentials(he);
    }

    public HealthEssentials setTempEssential() {
        HealthEssentials he;
        if (essentials.size() == 0) {
            he = new HealthEssentials(2,2,2,2,2,2);
        } else {
            he = essentials.get(essentials.size() - 1);
        }
        return he;
    }

    public void setEssentials(HealthEssentials he) {
        if (essentials.size() == 0) {
            essentials.add(he);
        } else {
            essentials.set(essentials.size() - 1,he);
        }

    }

    // set first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // set last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // get first name
    public String getFirstName() {
        return firstName;
    }

    // get last name
    public String getLastName() {
        return lastName;
    }

    // set age
    public void setAge(int age) {
        this.age = age;
    }

    // get age
    public int getAge() {
        return age;
    }

    // get gender
    public Boolean getGender() {
        return gender;
    }

    // set gender
    public void setGender(boolean gender) {
        this.gender = gender;
    }


    // set height
    public void setHeight(int age) {
        this.height = age;
    }

    // get height
    public int getHeight() {
        return height;
    }

    // set weight
    public void setWeight(int weight) {
        this.weight = weight;
    }

    // get weight
    public int getWeight() {
        return weight;
    }


    public void addEssentials(HealthEssentials essentials) {
        this.essentials.add(essentials);
    }

    public List<HealthEssentials> getEssentials() {
        return essentials;
    }


    public Boolean isHealthy() {
        return 120 > (height - weight * 0.45) && (height - weight * 0.45) > 90;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("gender", gender);
        json.put("age", age);
        json.put("height", height);
        json.put("weight", weight);
        json.put("essentials", essentialsToJson());
        return json;
    }


    // EFFECTS: returns health essentials in this profile as a JSON array
    private JSONArray essentialsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (HealthEssentials he : essentials) {
            jsonArray.put(he.toJson());
        }
        return jsonArray;
    }

    //EFFECTS: based on the essentials in the person history it will calculate what element is most needed for him/her.
    //       : 1: calcium - 2: iron - 3: zinc - 4:vitaminB 5:vitaminC 6:sugar
    public int mostNeeded() {
        HealthEssentials lastEssential = essentials.get(essentials.size() - 1);
        int calcium = lastEssential.getCalcium();
        int iron = lastEssential.getIron();
        int zinc = lastEssential.getZinc();
        int vitaminB = lastEssential.getVitaminB();
        int vitaminC = lastEssential.getVitaminC();
        int sugar = lastEssential.getSugar();

        return findFirstBiggerThanTwo(calcium,iron,zinc,vitaminB,vitaminC,sugar);
    }

    //REQUIRES: inputs < 4
    //EEFECTS: and return its index
    public int findFirstBiggerThanTwo(int one, int two, int three, int four, int five, int six) {
        List<Integer> maxEntry = new ArrayList<>();
        maxEntry.add(one);
        maxEntry.add(two);
        maxEntry.add(three);
        maxEntry.add(four);
        maxEntry.add(five);
        maxEntry.add(six);
        int maxii = 0;
        for (int n : maxEntry) {
            maxii++;
            if (n > 2) {
                break;
            }
        }
        return maxii;
    }

    //MODIFIES: this
    //EFFECTS: adds a new essential to the list
    public void makeNewEssentials(int n) {
        HealthEssentials lastEssential = essentials.get(essentials.size() - 1);
        switch (n) {
            case 1:
                addNewEssentialCalcium(lastEssential);
                break;
            case 2:
                addNewEssentialIron(lastEssential);
                break;
            case 3:
                addNewEssentialZinc(lastEssential);
                break;
            case 4:
                addNewEssentialVitaminB(lastEssential);
                break;
            case 5:
                addNewEssentialVitaminC(lastEssential);
                break;
            case 6:
                addNewEssentialSugar(lastEssential);
                break;
            default:
                break;
        }
    }


    //REQUIRES: essentials != empty
    //MODIFIES: this
    //EFFECTS: adds the last essential again
    public void addSameEssentials() {
        HealthEssentials lastEssential = essentials.get(essentials.size() - 1);
        addEssentials(lastEssential);
    }


    public void addNewEssentialCalcium(HealthEssentials he) {
        addEssentials(new HealthEssentials(he.getCalcium() - 1,he.getIron(),he.getZinc(),he.getVitaminB(),
                he.getVitaminC(),he.getSugar()));
        EventLog.getInstance().logEvent(new Event("New Daily Status Created"));
    }

    public void addNewEssentialIron(HealthEssentials he) {
        addEssentials(new HealthEssentials(he.getCalcium(),he.getIron() - 1,he.getZinc(),he.getVitaminB(),
                he.getVitaminC(),he.getSugar()));
        EventLog.getInstance().logEvent(new Event("New Daily Status Created"));
    }

    public void addNewEssentialZinc(HealthEssentials he) {
        addEssentials(new HealthEssentials(he.getCalcium(),he.getIron(),he.getZinc() - 1,he.getVitaminB(),
                he.getVitaminC(),he.getSugar()));
        EventLog.getInstance().logEvent(new Event("New Daily Status Created"));
    }

    public void addNewEssentialVitaminB(HealthEssentials he) {
        addEssentials(new HealthEssentials(he.getCalcium(),he.getIron(),he.getZinc(),he.getVitaminB() - 1,
                he.getVitaminC(),he.getSugar()));
        EventLog.getInstance().logEvent(new Event("New Daily Status Created"));
    }

    public void addNewEssentialVitaminC(HealthEssentials he) {
        addEssentials(new HealthEssentials(he.getCalcium(),he.getIron(),he.getZinc(),he.getVitaminB(),
                he.getVitaminC() - 1,he.getSugar()));
        EventLog.getInstance().logEvent(new Event("New Daily Status Created"));
    }

    public void addNewEssentialSugar(HealthEssentials he) {
        addEssentials(new HealthEssentials(he.getCalcium(),he.getIron(),he.getZinc(),he.getVitaminB(),
                he.getVitaminC(),he.getSugar() - 1));
        EventLog.getInstance().logEvent(new Event("New Daily Status Created"));
    }

    public ImageIcon getLastEssentialSuggestions() {
        HealthEssentials lastEssential = essentials.get(essentials.size() -1);
        return lastEssential.getSuggestion();

    }


    //EFFECTS: given the essentials it will calculate the average.
    public List calculateProfileAverage() {
        List<Double> loi = new ArrayList<>();
        for (HealthEssentials he : essentials) {
            Double average = Math.abs(he.calculateAverage() - 5);
            if (average > 3) {
                loi.add(average);
            } else if (average > 2) {
                loi.add(average);
            } else {
                loi.add(average);
            }
        }
        EventLog.getInstance().logEvent(new Event("The improvement was shown to the user"));
        return loi;
    }

    public void addEventForEssential() {
        EventLog.getInstance().logEvent(new Event("Profile created and first Essential added"));
    }


    public void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
    }
}

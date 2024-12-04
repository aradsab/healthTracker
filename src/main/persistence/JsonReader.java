package persistence;

import model.Event;
import model.EventLog;
import model.HealthEssentials;
import model.Profile;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Profile read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Profile loaded"));
        return parseProfile(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Profile parseProfile(JSONObject jsonObject) {
        String firstName = jsonObject.getString("firstName");
        String lastName = jsonObject.getString("lastName");
        Boolean gender = jsonObject.getBoolean("gender");
        int age = jsonObject.getInt("age");
        int height = jsonObject.getInt("height");
        int weight = jsonObject.getInt("weight");
        Profile pr = new Profile(firstName,lastName,gender,age,height,weight);
        addEssentials(pr, jsonObject);
        return pr;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addEssentials(Profile pr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("essentials");
        for (Object json : jsonArray) {
            JSONObject nextEssential = (JSONObject) json;
            addEssential(pr, nextEssential);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addEssential(Profile pr, JSONObject jsonObject) {
        int calcium = jsonObject.getInt("calcium");
        int iron = jsonObject.getInt("iron");
        int zinc = jsonObject.getInt("zinc");
        int vitaminB = jsonObject.getInt("vitaminB");
        int vitaminC = jsonObject.getInt("vitaminC");
        int sugar = jsonObject.getInt("sugar");

        HealthEssentials healthEssentials = new HealthEssentials(calcium, iron,zinc,vitaminB,vitaminC,sugar);
        pr.addEssentials(healthEssentials);
    }
}

package ui;


public class HealthTracker {


    static ProfileQuestions user;

    public HealthTracker() {

        user = new ProfileQuestions();
        user.initializeGraphics();
    }


    public static void main(String[] args) {
        new HealthTracker();
    }




}

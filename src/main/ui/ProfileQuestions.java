package ui;

import model.Profile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

//Information about the user
public class ProfileQuestions extends JFrame implements ActionListener, FocusListener {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    private static final String JSON_STORE = "./data/profile.json";
    private Profile profile;


    private JButton buttonA;
    private JButton buttonB;
    private JButton quit;

    private JTextField textField;
    private JTextArea textArea;
    private JRadioButton radioButtonYes;


    private JTextField textFirstName;
    private JTextField textLastName;
    private JTextField age;
    private JTextField weight;
    private JTextField height;
    private JPanel panelMain;
    private CardLayout cardLayout;
    private JPanel firstPanel;
    private JPanel secondPanel;
    private JPanel thirdPanel;
    private JPanel forthPanel;
    private JPanel fifthPanel;
    private JPanel sixthPanel;



    private JsonWriter jsonWriter;
    private JsonReader jsonReader;



    //make Profile
    public ProfileQuestions() {
        super("Heath Tracker");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        cardLayout = new CardLayout();
        panelMain = new JPanel();
        firstPanel = new JPanel();
        secondPanel = new JPanel();
        thirdPanel = new JPanel();
        forthPanel = new JPanel();
        fifthPanel = new JPanel();
        sixthPanel = new JPanel();

        buttonA = new JButton();
        textField = new JTextField();
        textArea = new JTextArea();
        buttonB = new JButton();
        quit = new JButton("Quit");

        quit.setBounds(900, 650, 100, 30);
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this HealTracker will operate, and populates.
    public void initializeGraphics() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        panelMain.setLayout(cardLayout);
        add(panelMain);
        setFirstPanel();
        panelMain.add(firstPanel,"1");
        panelMain.add(secondPanel,"2");
        panelMain.add(thirdPanel, "3");
        panelMain.add(forthPanel,"4");
        panelMain.add(fifthPanel,"5");
        panelMain.add(sixthPanel,"6");
        cardLayout.show(panelMain,"1");


        setVisible(true);

    }

    //sets the first panel text labels and panels
    public void setFirstPanel() {
        firstPanel.setLayout(null);
        firstPanel.setBackground(new Color(0xFFFFFF));
        welcomeMessage();
        firstPanel.setLayout(null);
    }

    //Effects: moves to third panel where health questions are asked.
    public void setThirdPanel() {
        if (profile.isHealthy()) {
            healthyProfileAdvanceToTest();
        } else {
            unHealthyProfileAdvanceToTest();
        }
        JButton submit = new JButton("Submit");
        thirdPanel.add(submit);
        thirdPanel.add(quit);
        profile.addEventForEssential();
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProfile();
                cardLayout.show(panelMain,"5");
                setFifthPanel();
            }
        });
    }

    //sets the forth panel text labels and panels
    public void setForthPanel() {
        loadProfile();
        makeNewSuggestions();
        forthPanel.add(quit);

    }

    //sets the fifth panel text labels and panels
    public void setFifthPanel() {
        JLabel suggestionText = new JLabel("Here is your suggestion of the day");
        JLabel suggestionImage = new JLabel(recommendFood());
        fifthPanel.add(suggestionText);
        fifthPanel.add(suggestionImage);
        fifthPanel.add(quit);
        saveProfile();
    }



    //EFFECTS: make the panel of a healthy person
    public void healthyProfileAdvanceToTest() {
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JTextField healthyText = new JTextField("Great you are healthy!",20);
        JTextField start = new JTextField("now please the fill in the information below",20);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));

        topPanel.add(healthyText);
        topPanel.add(start);

        JPanel testPanel = createTestPanel();

        bottomPanel.add(testPanel);
        repaint();
        revalidate();

    }

    //EFFECTS: make the panel of an unhealthy person
    public void unHealthyProfileAdvanceToTest() {
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JLabel unhealthyText = new JLabel("Unfortunately your height/weight ratio is unhealthy!");
        JLabel start = new JLabel("now please the fill in the information below:");

        topPanel.add(unhealthyText);
        topPanel.add(start);
        thirdPanel.add(topPanel);
        thirdPanel.add(bottomPanel);

        JPanel testPanel = createTestPanel();
        bottomPanel.add(testPanel);
    }

    //show 10 yes-no quiz
    public JPanel createTestPanel() {
        JPanel[] panels1 = new JPanel[10];

        JPanel test = new JPanel();
        test.setLayout(new BoxLayout(test, BoxLayout.PAGE_AXIS));
        for (int i = 0; i < 10; i++) {
            panels1[i] = questionToPanel(i);
            panels1[i].setAlignmentX(CENTER_ALIGNMENT);
            test.add(panels1[i]);
        }
        return test;
    }

    //show 10 yes-no quiz
    public JPanel questionToPanel(int i) {
        String[] questions;
        JPanel panel = new JPanel();

        questions = new String[]{"1. Do you have oily skin?", "2. Do you have white spots on your nails?",
                "3. Do your arm and joints crack often? ", "4. Do your knees pop when you bend on them? ",
                "5. Do you experience back pain often?  ",
                "6. Do you have hair loss? ",
                "7. Do your gums bleed while brushing your teeth",
                "8. Do you feel tired often? ",
                "9. Do you have pail skin? ",
                "10. Do you have dark circles under your eyes?"};
        JLabel text = new JLabel(questions[i]);

        panel.add(text);
        radioButtonYes = new JRadioButton("Yes");
        JRadioButton radioButtonNo = new JRadioButton("No");
        ButtonGroup radioButtons = new ButtonGroup();
        radioButtons.add(radioButtonYes);
        radioButtons.add(radioButtonNo);

        JPanel panelRadio = new JPanel();
        panelRadio.add(radioButtonNo);
        panelRadio.add(radioButtonYes);


        setRadioButtonListenerForYes(i);

        panel.add(panelRadio);


        return panel;

    }

    //adds action for the quiz yes radio button
    public void setRadioButtonListenerForYes(int i) {
        radioButtonYes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profile.setYesSpecificEssential(i);
            }
        });
    }


    //EFFECTS: show the first frame which determines whether the user has profile or not
    public void welcomeMessage() {

        setTexts();

        buttonA.setBounds(450, 200, 100, 100);
        buttonA.setFont(new Font("ink Free", Font.ITALIC, 25));
        buttonA.setFocusable(false);
        buttonA.addActionListener(this);
        buttonA.setText("Yes");

        buttonB.setBounds(600, 200, 100, 100);
        buttonB.setFont(new Font("ink Free", Font.ITALIC, 25));
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonB.setText("No");

        quit.addActionListener(this);

        firstPanel.add(quit);
        firstPanel.add(buttonA);
        firstPanel.add(buttonB);
        firstPanel.add(textField);
        firstPanel.add(textArea);

    }

    //set the text fields for welcome message
    private void setTexts() {
        textField.setBounds((WIDTH - 650) / 2, 0, 650, 50);
        textField.setFont(new Font("ink Free", Font.BOLD, 30));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        textField.setBorder(BorderFactory.createBevelBorder(1));
        textField.setText("Welcome to Health Tracker!");
        textField.setEditable(false);


        textArea.setBounds(5, 225, 650, 50);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("ink Free", Font.ITALIC, 25));
        textArea.setEditable(false);
        textArea.setText("Do you have a profile with us?");
    }





    //MODIFIES: this
    //EFFECTS: start asking questions with prompts and user inputs and create a profile for the user
    public void askQuestions() {
        JButton submit = new JButton("Submit");
        JPanel informationPanel = new JPanel();
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.PAGE_AXIS));
        informationPanel.add(createEntryFields());
        informationPanel.add(submit);
        secondPanel.add(informationPanel);
        secondPanel.add(quit);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelMain,"3");
                askHealthEssentials();
            }
        });

    }

    //create the text fields for the info tab
    public JComponent createEntryFields() {

        JPanel panel1 = new JPanel(new SpringLayout());

        String[] labelStrings = {"First name: ", "Last name: ",
                "Age: ", "Weight (lbs): ", "Height (cm): "
        };


        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;

        //Create the text field and set it up.
        textFirstName = new JTextField();
        textFirstName.setColumns(20);
        fields[fieldNum++] = textFirstName;

        textLastName = new JTextField();
        textLastName.setColumns(20);
        fields[fieldNum++] = textLastName;

        age = new JFormattedTextField(createFormatter("##"));
        fields[fieldNum++] = age;

        weight = new JFormattedTextField(createFormatter("###"));
        fields[fieldNum++] = weight;


        height = new JFormattedTextField(createFormatter("###"));
        fields[fieldNum++] = height;


        //Associate lab
        //and lay it out.
        createSpringLayout(panel1, labelStrings, labels, fields);
        SpringUtilities.makeCompactGrid(panel1, labelStrings.length, 2,
                10, 10, 10, 10 / 2);

        return panel1;
    }

    //create the info tab Spring Layout
    private void createSpringLayout(JPanel panel1, String[] labelStrings, JLabel[] labels, JComponent[] fields) {
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i],
                    JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel1.add(labels[i]);
            panel1.add(fields[i]);

            //Add listeners to each field.
            JTextField tf;
            if (fields[i] instanceof JSpinner) {
                tf = getTextField((JSpinner) fields[i]);
            } else {
                tf = (JTextField) fields[i];
            }
            tf.addActionListener(this);
            tf.addFocusListener(this);
        }
    }


    //EFFECT: start asking health essential questions.
    public void askHealthEssentials() {
        saveProfile();
        profile = new Profile(textFirstName.getText(), textLastName.getText(),
                true, Integer.parseInt(age.getText()), Integer.parseInt(height.getText()),
                Integer.parseInt(weight.getText()));
        setThirdPanel();
    }


    //saves the project to profile.json
    public void saveProfile() {
        if (profile != null) {
            try {
                jsonWriter.open();
                jsonWriter.write(profile);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }


    //shows the check panel for user if he/she has followed yesterday's instructions
    public void makeNewSuggestions() {
        forthPanel.setBackground(new Color(0xFFFFFF));
        String tt = "Have you done the previous instructions?";
        ImageIcon suggestions = resizeImage(profile.getLastEssentialSuggestions());
        JLabel checking = new JLabel(tt);
        JLabel picLabel = new JLabel(suggestions);
        JButton yes = new JButton("Yes");
        JButton no = new JButton("No");
        forthPanel.add(checking);
        forthPanel.add(picLabel);
        int mn = profile.mostNeeded();

        doActionEvent(yes, no, mn);
        forthPanel.add(yes);
        forthPanel.add(no);
    }

    //action events for the check panel
    private void doActionEvent(JButton yes, JButton no, int mn) {
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profile.makeNewEssentials(mn);
                cardLayout.show(panelMain,"6");
                showNewSuggestions();
            }
        });

        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profile.addSameEssentials();
                cardLayout.show(panelMain,"6");
                showNewSuggestions();
            }
        });
    }

    //MODIFIES: create the 6th panel text labels and panels
    public void showNewSuggestions() {
        sixthPanel.setBackground(new Color(0xFFFFFF));
        JLabel suggestions = new JLabel("Here is your new suggestion:");
        JLabel newSuggestions = new JLabel(resizeImage(profile.getLastEssentialSuggestions()));
        sixthPanel.add(suggestions);
        sixthPanel.add(newSuggestions);
        saveProfile();
        JButton showImprovement = new JButton("Show My Improvement");
        sixthPanel.add(showImprovement);
        sixthPanel.add(quit);
        showImprovement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showImprovement();
            }
        });

    }

    //show the line chart of improvement
    public void showImprovement() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart improvementChart = ChartFactory.createLineChart("Improvement","Days",
                "Score out of 3",dataset);
        List los = profile.calculateProfileAverage();
        for (int i = 0; i < los.size(); i++) {
            dataset.setValue((Double) los.get(i), "Score out of 3", "day" + (i + 1));
        }
        ChartFrame chartFrame = new ChartFrame("Improvement",improvementChart);
        chartFrame.setSize(450,350);
        chartFrame.setVisible(true);
    }



    //starts the project for a new user
    private void startProject(ProfileQuestions user) {
        user.askQuestions();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);

        if (e.getSource() == buttonA) {
            cardLayout.show(panelMain,"4");
            setForthPanel();
        } else if (e.getSource() == buttonB) {
            cardLayout.show(panelMain,"2");
            startProject(this);
        } else if (e.getSource() == quit) {
            askToSave();
        }
    }


    //EFFECTS: asks if the user wants to save or not.
    public void askToSave() {
        JFrame quitingFrame = new JFrame("Do you want to quit without saving?");
        JButton yes = new JButton("Quit");
        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");

        JPanel quitingPanel = new JPanel();
        quitingFrame.setBounds(WIDTH / 2 - 250, HEIGHT / 2 - 50,500,100);
        quitingPanel.add(yes);
        quitingPanel.add(save);
        quitingPanel.add(cancel);


        addActionListenerForButtonYes(yes);
        //close the frame with saving
        addActionListenerForButtonSave(save);
        cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    quitingFrame.setVisible(false);
                    welcomeMessage();
                }
            });

        quitingFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        quitingFrame.add(quitingPanel);
        quitingFrame.setResizable(false);
        quitingFrame.setVisible(true);

    }

    //close the frame without saving
    public void addActionListenerForButtonYes(JButton b) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profile.printLog();
                System.exit(1);
            }
        });
    }

    //quit the frame with saving
    public void addActionListenerForButtonSave(JButton b) {
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profile.printLog();
                saveProfile();
                System.exit(1);
            }
        });
    }




    // MODIFIES: this
    // EFFECTS: loads workroom from file
    public void loadProfile() {
        try {
            profile = jsonReader.read();
            System.out.println("Loaded " + profile.getFirstName() + profile.getLastName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    public ImageIcon recommendFood() {
        return resizeImage(profile.getLastEssentialSuggestions());
    }



    //A convenience method for creating a MaskFormatter.
    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }


    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor)editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }



     //Called when one of the fields gets the focus so that
     //we can select the focused field.
    @Override
    public void focusGained(FocusEvent e) {
        Component c = e.getComponent();
        if (c instanceof JFormattedTextField) {
            selectItLater(c);
        } else if (c instanceof JTextField) {
            ((JTextField)c).selectAll();
        }
    }


    //Workaround for formatted text field focus side effects.
    private void selectItLater(Component c) {
        if (c instanceof JFormattedTextField) {
            final JFormattedTextField ftf = (JFormattedTextField)c;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    ftf.selectAll();
                }
            });
        }
    }

    //Needed for FocusListener interface.
    public void focusLost(FocusEvent e) { } //ignore

    //EFFECTS: Resizes the given image and return it.
    public ImageIcon resizeImage(ImageIcon i) {
        Image image = i.getImage(); // transform it
        Image newImg = image.getScaledInstance(700, 600,  java.awt.Image.SCALE_SMOOTH);
        i = new ImageIcon(newImg);
        return i;
    }

}
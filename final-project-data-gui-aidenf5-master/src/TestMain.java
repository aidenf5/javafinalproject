import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class TestMain extends Application {

    private ArrayList<String> data = FileReader.parseData(new File("/Users/aidenf/Desktop/JavaCoding/final-project-data-gui-aidenf5/data/Dataset for Project 5-6 CSV.csv"));
    Text header = new Text(0,30,"Welcome to my data analysis GUI! Below are the three questions addressed " +
            "within " + "the GitHub" + "\n"+ "Question Depiction section. Question 1 comes first, then Question 3," +
            " then finally Question 2.");
    Label getValuesReadout = new Label("Values for (Question 1): ");
    Label getValuesReadoutSpacer = new Label("");
    Label question3Readout =  new Label("For coaches within the chosen conference (Question 3): ");
    Label question3ReadoutSpacer = new Label("");
    Label question2Readout = new Label("For coaches within the chosen conference (Question 2): ");
    Label question2ReadoutSpacer = new Label("");
    @Override
    public void start(Stage primaryStage) throws Exception {
        //line 1 header
        HBox headerBox = new HBox(30);
        BorderPane headerPane = new BorderPane();
        headerPane.setBottom(headerBox);
        Pane headerTextPane = new Pane();
        headerTextPane.getChildren().add(header);
        headerPane.setCenter(headerTextPane);

        //question 1 JavaFX Integration
        Label coachesLabel = new Label("Coaches:");
        final ComboBox<String> coaches = new ComboBox<>();
        coaches.getItems().addAll("All Coaches");
        coaches.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                getValues(coaches);
            }
        });
        HBox coachBox = new HBox();
        coachBox.getChildren().addAll(coachesLabel, coaches);
        HBox readoutBox = new HBox();
        readoutBox.getChildren().addAll(getValuesReadout, getValuesReadoutSpacer);
        //////////////////////////////////////////////////////////////////////////
        //question 3 JavaFX Integration
        Label conferencesLabel = new Label("Conferences:");
        final ComboBox<String> conferences = new ComboBox<>();
        //ACC, Big 12, Big Ten, Pac-12, SEC
        conferences.getItems().addAll("ACC","Big 12","Big Ten","Pac-12","SEC");
        conferences.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                conferenceCoach(conferences);
            }
        });
        HBox conferencesBox = new HBox();
        conferencesBox.getChildren().addAll(conferencesLabel,conferences);
        HBox conferencesReadoutBox = new HBox();
        conferencesReadoutBox.getChildren().addAll(question3Readout,question3ReadoutSpacer);
        //////////////////////////////////////////////////////////////////////////
        //question 2 JavaFX Integration
        Label conferencesLabel2 = new Label("Conferences:");
        final ComboBox<String> conferences2 = new ComboBox<>();
        conferences2.getItems().addAll("ACC","Big 12","Big Ten","Pac-12","SEC");
        conferences2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override public void handle(ActionEvent e)
            {
                conferenceCoachPandemicReduction(conferences2);
            }
        });
        HBox conferencesBox2 = new HBox();
        conferencesBox2.getChildren().addAll(conferencesLabel2,conferences2);
        HBox conferencesReadoutBox2 = new HBox();
        conferencesReadoutBox2.getChildren().addAll(question2Readout,question2ReadoutSpacer);

        //Master List to which all of the questions and boxes for the questions get added to
        VBox root = new VBox();
        root.getChildren().addAll(headerPane,coachBox,readoutBox,conferencesBox,conferencesReadoutBox,conferencesBox2,conferencesReadoutBox2);
        root.setSpacing(10);

        Scene scene = new Scene(root,1100,400);
        primaryStage.setTitle("NCAA Assistant Coaches Data Analysis"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public void getValues(ComboBox<String> coaches)
    {
        ArrayList<String> coachNames = new ArrayList<String>();
        ArrayList<Integer> coachPay = new ArrayList<Integer>();
        int currentPay = 0;
        String currentPayString = null;
        for(int i = 0; i<data.size(); i++)
        {
            currentPay = 999999999;
            String tempLine = data.get(i);
            String[] tempSplit = tempLine.split(",");
            coachNames.add(tempSplit[3]);
            currentPayString = tempSplit[4];
            if(currentPayString.length()>3)
            {
                currentPay = Integer.parseInt(currentPayString);
                coachPay.add(currentPay);
            }
            else
            {
                coachPay.add(999999999);
            }
        }
        int[] values = FileReader.getValues(coachNames,coachPay);
        String placerValue = coaches.getValue();
        placerValue = ("Minimum: " +  values[0] + ". Maxiumum: " + values[1] + ". Average: " + values[2] + ".");
        getValuesReadoutSpacer.setText(placerValue);
    }

    public void conferenceCoach(ComboBox<String> conferences)
    {
        ArrayList<String> coachesInConference = new ArrayList<String>();
        ArrayList<Integer> coachesInConferencePay = new ArrayList<Integer>();
        int actualSchoolPay = 0;
        String actualSchoolPayString = null;
        String placerValue = conferences.getValue();
        for(int i = 0; i<data.size();i++)
        {
            actualSchoolPay = 999999999;
            String tempLine2 = data.get(i);
            String[] tempSplit2 = tempLine2.split(",");
            actualSchoolPayString = tempSplit2[6];
            if(tempSplit2[2].equals(placerValue))
            {
                coachesInConference.add(tempSplit2[3]);
                if(actualSchoolPayString.length()>3)
                {
                    actualSchoolPay = Integer.parseInt(actualSchoolPayString);
                    coachesInConferencePay.add(actualSchoolPay);
                }
                else
                {
                    coachesInConferencePay.add(999999999);
                }
            }
        }
        CoachAndPay currentCoachSelected = FileReader.getCoachByConferenceAndPay(coachesInConference,coachesInConferencePay,placerValue);
        placerValue = ("The coach within " + placerValue + " conference that was paid the most " +
                "is " + currentCoachSelected.getName() + " and was paid $" + currentCoachSelected.getPay() + ".");
        question3ReadoutSpacer.setText(placerValue);
    }

    public void conferenceCoachPandemicReduction(ComboBox<String> conferences2)
    {
        String placerValue = conferences2.getValue();
        ArrayList<String> coachesInConference2 = new ArrayList<String>();
        ArrayList<Integer> coachesInConferenceScheduledPay2 = new ArrayList<Integer>();
        ArrayList<Integer> coachesInConferencePandemicPayReduction = new ArrayList<Integer>();
        int scheduledSchoolPay = 0;
        String scheduledSchoolPayString = null;
        int pandemicPayReduction = 0;
        String pandemicPayReductionString = null;
        for(int i = 0; i<data.size();i++)
        {
            scheduledSchoolPay = 999999999;
            pandemicPayReduction = 999999999;
            String tempLine3 = data.get(i);
            String[] tempSplit3 = tempLine3.split(",");
            scheduledSchoolPayString = tempSplit3[4];
            pandemicPayReductionString = tempSplit3[5];
            if(tempSplit3[2].equals(placerValue))
            {
                coachesInConference2.add(tempSplit3[3]);
                if (scheduledSchoolPayString.length() > 3)
                {
                    scheduledSchoolPay = Integer.parseInt(scheduledSchoolPayString);
                    coachesInConferenceScheduledPay2.add(scheduledSchoolPay);
                }
                else
                {
                    coachesInConferenceScheduledPay2.add(999999999);
                }
                if (pandemicPayReductionString.length() > 3 || pandemicPayReductionString.length()==1)
                {
                    pandemicPayReduction = Integer.parseInt(pandemicPayReductionString);
                    coachesInConferencePandemicPayReduction.add(pandemicPayReduction);
                }
                else
                {
                    coachesInConferencePandemicPayReduction.add(999999999);
                }
            }
        }
        ArrayList<Coach> question3 = FileReader.avgReduc(coachesInConference2,coachesInConferencePandemicPayReduction,
                coachesInConferenceScheduledPay2,placerValue);
        placerValue = ("The top three coaches with the greatest pandemic pay reductions within the " +
                "chosen conference are: " + "\n" + question3.get(0).getName() + ": " + question3.get(0).getPercentageCut()*100
        + "%" + "\n" + question3.get(1).getName() + ": " + question3.get(1).getPercentageCut()*100 + "%" + "\n"+
                question3.get(2).getName() + ": " + question3.get(2).getPercentageCut()*100 + "%");
        question2ReadoutSpacer.setText(placerValue);
    }

    public static void main(String[] args) {
        launch(args);
        //answer key to test functions with:
        //question 1:
        int correctMinimumPaidCoachAmount = 100000;
        int correctMaxiumumPaidCoachAmount = 2500000;
        int correctAveragePaidCoachAmount = 496059;
        //question 2:
        //Within the SEC:
        String[] topCoachesCutSEC = {"Kyle Krantz","Des Kitchings","Mike Peterson"};
        //Within the ACC:
        String[] topCoachesCutACC = {"Brent Venables","Tim Beck","Dre Bly"};
        //Within the Big 12:
        String[] topCoachesCutBig12 = {"Andre Coleman","Mick McCall","Jeff Myers"};
        //question 3:
        //Pac-12:
        CoachAndPay topPac12Coach = new CoachAndPay("Pete Kwiatkowski",982508);
        //Big Ten:
        CoachAndPay topBigTenCoach = new CoachAndPay("Don Brown",1700000);
        //ACC:
        CoachAndPay topACCCoach = new CoachAndPay("Brent Venables",2160000);

        //test cases:
        //question 1:

        int[] question1TestCase = question1TestCase();
        if(question1TestCase[0]==100000 && question1TestCase[1]==2500000 && question1TestCase[2]==496059)
        {
            System.out.println("Question 1 Code Check!");
        }

        //question 2:

        //SEC Check:
        ArrayList<Coach> question2Checker = question2TestCase("SEC");
        if(question2Checker.get(0).getName().equals(topCoachesCutSEC[0]) && question2Checker.get(1).getName().equals(topCoachesCutSEC[1])
        && question2Checker.get(2).getName().equals(topCoachesCutSEC[2]))
        {
            System.out.println("First Question 2 Test Case Code Check!");
        }
        //ACC Check:
        question2Checker = question2TestCase("ACC");
        if(question2Checker.get(0).getName().equals(topCoachesCutACC[0]) && question2Checker.get(1).getName().equals(topCoachesCutACC[1])
                && question2Checker.get(2).getName().equals(topCoachesCutACC[2]))
        {
            System.out.println("Second Question 2 Test Case Code Check!");
        }
        //Big 12 Check:
        question2Checker = question2TestCase("Big 12");
        if(question2Checker.get(0).getName().equals(topCoachesCutBig12[0]) && question2Checker.get(1).getName().equals(topCoachesCutBig12[1])
                && question2Checker.get(2).getName().equals(topCoachesCutBig12[2]))
        {
            System.out.println("Third Question 2 Test Case Code Check!");
        }

        //question 3:

        //Pac-12:
        CoachAndPay question3Checker = question3TestCase("Pac-12");
        if(question3Checker.getName().equals(topPac12Coach.getName()))
        {
            System.out.println("First Question 3 Test Case Code Check!");
        }

        //Big Ten:
        question3Checker = question3TestCase("Big Ten");
        if(question3Checker.getName().equals(topBigTenCoach.getName()))
        {
            System.out.println("Second Question 3 Test Case Code Check!");
        }

        //ACC:
        question3Checker = question3TestCase("ACC");
        if(question3Checker.getName().equals(topACCCoach.getName()))
        {
            System.out.println("Second Question 3 Test Case Code Check!");
        }
    }

    public static int[] question1TestCase()
    {
        int[] output = new int[3];
        ArrayList<String> data = new ArrayList<String>();
        data = FileReader.parseData(new File("/Users/aidenf/Desktop/JavaCoding/final-project-data-gui-aidenf5/data/Dataset for Project 5-6 CSV.csv"));
        ArrayList<String> coachNamesHere = new ArrayList<String>();
        ArrayList<Integer> coachPay = new ArrayList<Integer>();
        int currentPay = 0;
        String currentPayString = null;
        for(int i = 0; i<data.size(); i++)
        {
            currentPay = 999999999;
            String tempLine = data.get(i);
            String[] tempSplit = tempLine.split(",");
            coachNamesHere.add(tempSplit[3]);
            currentPayString = tempSplit[4];
            if(currentPayString.length()>3)
            {
                currentPay = Integer.parseInt(currentPayString);
                coachPay.add(currentPay);
            }
            else
            {
                coachPay.add(999999999);
            }
        }
        output = FileReader.getValues(coachNamesHere,coachPay);
        return output;
    }

    public static ArrayList<Coach> question2TestCase(String conference)
    {
        ArrayList<String> data = new ArrayList<String>();
        data = FileReader.parseData(new File("/Users/aidenf/Desktop/JavaCoding/final-project-data-gui-aidenf5/data/Dataset for Project 5-6 CSV.csv"));
        ArrayList<String> coachesInConference2 = new ArrayList<String>();
        ArrayList<Integer> coachesInConferenceScheduledPay2 = new ArrayList<Integer>();
        ArrayList<Integer> coachesInConferencePandemicPayReduction = new ArrayList<Integer>();
        int scheduledSchoolPay = 0;
        String scheduledSchoolPayString = null;
        int pandemicPayReduction = 0;
        String pandemicPayReductionString = null;
        for(int i = 0; i<data.size();i++)
        {
            scheduledSchoolPay = 999999999;
            pandemicPayReduction = 999999999;
            String tempLine3 = data.get(i);
            String[] tempSplit3 = tempLine3.split(",");
            scheduledSchoolPayString = tempSplit3[4];
            pandemicPayReductionString = tempSplit3[5];
            if(tempSplit3[2].equals(conference))
            {
                coachesInConference2.add(tempSplit3[3]);
                if (scheduledSchoolPayString.length() > 3)
                {
                    scheduledSchoolPay = Integer.parseInt(scheduledSchoolPayString);
                    coachesInConferenceScheduledPay2.add(scheduledSchoolPay);
                }
                else
                {
                    coachesInConferenceScheduledPay2.add(999999999);
                }
                if (pandemicPayReductionString.length() > 3 || pandemicPayReductionString.length()==1)
                {
                    pandemicPayReduction = Integer.parseInt(pandemicPayReductionString);
                    coachesInConferencePandemicPayReduction.add(pandemicPayReduction);
                }
                else
                {
                    coachesInConferencePandemicPayReduction.add(999999999);
                }
            }
        }
        ArrayList<Coach> question3 = FileReader.avgReduc(coachesInConference2,coachesInConferencePandemicPayReduction,
                coachesInConferenceScheduledPay2,conference);
        return question3;
    }

    public static CoachAndPay question3TestCase(String conference)
    {
        ArrayList<String> data = new ArrayList<String>();
        data = FileReader.parseData(new File("/Users/aidenf/Desktop/JavaCoding/final-project-data-gui-aidenf5/data/Dataset for Project 5-6 CSV.csv"));
        ArrayList<String> coachesInConference = new ArrayList<String>();
        ArrayList<Integer> coachesInConferencePay = new ArrayList<Integer>();
        int actualSchoolPay = 0;
        String actualSchoolPayString = null;
        for(int i = 0; i<data.size();i++)
        {
            actualSchoolPay = 999999999;
            String tempLine2 = data.get(i);
            String[] tempSplit2 = tempLine2.split(",");
            actualSchoolPayString = tempSplit2[6];
            if(tempSplit2[2].equals(conference))
            {
                coachesInConference.add(tempSplit2[3]);
                if(actualSchoolPayString.length()>3)
                {
                    actualSchoolPay = Integer.parseInt(actualSchoolPayString);
                    coachesInConferencePay.add(actualSchoolPay);
                }
                else
                {
                    coachesInConferencePay.add(999999999);
                }
            }
        }
        CoachAndPay highestPaidCoach = FileReader.getCoachByConferenceAndPay(coachesInConference,coachesInConferencePay,conference);
        return highestPaidCoach;
    }
}

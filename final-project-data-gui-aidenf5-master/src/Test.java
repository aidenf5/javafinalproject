import java.io.File;
import java.util.ArrayList;

public class Test{
    public static void main(String[] args)
    {
        ArrayList<String> data = new ArrayList<String>();
        data = FileReader.parseData(new File("/Users/aidenf/Desktop/JavaCoding/final-project-data-gui-aidenf5/data/Dataset for Project 5-6 CSV.csv"));
        //question 1 code
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
            //System.out.println("Name: " + coachNames.get(i) + ". Pay: " + currentPay);
        }
        int[] question1 = FileReader.getValues(coachNames,coachPay);
        System.out.println(question1[0] + " " + question1[1] + " " + question1[2]);

        //question 3 code
        //ACC, Big 12, Big Ten, Pac-12, SEC
        String conferenceUserInput = "Big Ten";
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
            if(tempSplit2[2].equals(conferenceUserInput))
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
        CoachAndPay highestPaidCoach = FileReader.getCoachByConferenceAndPay(coachesInConference,coachesInConferencePay,conferenceUserInput);
        System.out.println(highestPaidCoach.getName() + " " + highestPaidCoach.getPay());



        //question 2 code
        //coach name, pandemic pay reduction, scheduled school pay, conference
        String conferenceUserInput2 = "Big 12";
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
            if(tempSplit3[2].equals(conferenceUserInput2))
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
                coachesInConferenceScheduledPay2,conferenceUserInput2);
        System.out.println("Top 3 Coaches with Highest Percentage Cut in Input Conference: " + question3.get(0).getName()
        + " " + question3.get(1).getName() + " " + question3.get(2).getName());
    }

}

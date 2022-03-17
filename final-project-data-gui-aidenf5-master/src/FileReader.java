import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class FileReader {
    public static ArrayList<String> parseData (File fileName)
    {
        ArrayList<String> data = new ArrayList<String>();
        try
        {
            File myObj = new File("/Users/aidenf/Desktop/JavaCoding/final-project-data-gui-aidenf5/data/Dataset for Project 5-6 CSV.csv");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine())
            {
                String line = myReader.nextLine();
                data.add(line);
            }
            myReader.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
    public static int[] getValues (ArrayList<String> coachNames, ArrayList<Integer> coachSalaries)
    {
        //Implement method body with for loop later
        int[] values = new int[3];
        ArrayList<CoachAndPay> namePay = new ArrayList<CoachAndPay>();
        int minimum = 10000000, maximum = 0, sum = 0, counter = 0;
        for(int i = 0; i<coachSalaries.size();i++)
        {
            namePay.add(new CoachAndPay(coachNames.get(i),coachSalaries.get(i)));
        }
        CoachAndPay currentCoach = null;
        for(int i = 0; i<namePay.size();i++)
        {
            currentCoach = namePay.get(i);
            if(currentCoach.getPay()!=999999999)
            {
                if(currentCoach.getPay()<minimum)
                {
                    minimum = currentCoach.getPay();
                }
                if(currentCoach.getPay()>maximum)
                {
                    maximum = currentCoach.getPay();
                }
                sum += currentCoach.getPay();
                counter++;
            }
        }
        values[0] = minimum;
        values[1] = maximum;
        values[2] = sum/counter;
        return values;
    }
    public static ArrayList<Coach> avgReduc(ArrayList<String> coachNames, ArrayList<Integer> pandemicPayReduction,
                                            ArrayList<Integer> scheduledSchoolPay, String conference)
    {
        //Implement method body later that calculates percentage cut from paycheck, with for loop utilizing helper
        //function coachCut()
        double temp = coachCut(1,2);
        ArrayList<Coach> coachList = new ArrayList<Coach>();
        double currentPercentage = 0;
        int currentPercentageInt = 0;
        for(int i = 0; i<coachNames.size();i++)
        {
            currentPercentage = coachCut(pandemicPayReduction.get(i),scheduledSchoolPay.get(i));
            currentPercentageInt = (int) Math.round(currentPercentage);
            if(pandemicPayReduction.get(i)!=999999999 && scheduledSchoolPay.get(i)!=999999999)
            {
                coachList.add(new Coach(coachNames.get(i),currentPercentage));
            }
        }
        coachList.sort(Comparator.comparingDouble(Coach::getPercentageCut));
        Collections.reverse(coachList);
        //for(int i = 0; i<coachList.size();i++)
        //{
            //Coach tempCoach = coachList.get(i);
            //System.out.println(tempCoach.getName() + " " + tempCoach.getPercentageCut());
        //}

        return coachList;
    }

    public static double coachCut (double pandemicPay, double scheduledPay)
    {
        //implement helper method body for avgReduc() function below
        if(pandemicPay==0)
        {
            return 0.0;
        }
        else
        {
            double percentage = pandemicPay/scheduledPay;
            return percentage;
        }

    }

    public static CoachAndPay getCoachByConferenceAndPay(ArrayList<String> coachesInConference,
                                                                    ArrayList<Integer> actualSchoolPay,
                                                         String conference)
    {
        //implement method to loop through list of current coaches in conference and their pay, assign value to a
        //new ArrayList<CoachAndPay>
        CoachAndPay highestPaidCoach = new CoachAndPay("temp",0);
        ArrayList<CoachAndPay> coaches = new ArrayList<CoachAndPay>();
        int maximum = 0;
        String name = null;
        for(int i = 0; i<coachesInConference.size();i++)
        {
            coaches.add(new CoachAndPay(coachesInConference.get(i),actualSchoolPay.get(i)));
        }
        CoachAndPay currentCoach = null;
        for(int i = 0; i<coaches.size();i++)
        {
            currentCoach = coaches.get(i);
            if(currentCoach.getPay()!=999999999)
            {
                if(currentCoach.getPay()>maximum)
                {
                    maximum = currentCoach.getPay();
                    name = currentCoach.getName();
                }
            }
        }
        highestPaidCoach = new CoachAndPay(name,maximum);
        return highestPaidCoach;
    }



}

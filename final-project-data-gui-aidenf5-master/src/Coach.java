public class Coach {
    private String name;
    private double percentageCut;

    public Coach(String name, double percentageCut)
    {
        this.name = name;
        this.percentageCut = percentageCut;
    }
    public String getName()
    {
        return name;
    }
    public double getPercentageCut()
    {
        return percentageCut;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPercentageCut(double percentageCut)
    {
        this.percentageCut = percentageCut;
    }


}

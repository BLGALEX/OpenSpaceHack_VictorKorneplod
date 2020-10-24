enum RB {
    INTERNET,
    MOBILE
}

public class Record {
    private String request;
    private String clarification;
    private RB rb;
    private String question;
    public int nSteps;
    public int[] steps;
}
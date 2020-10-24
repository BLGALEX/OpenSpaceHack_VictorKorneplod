enum RB {
    INTERNET,
    MOBILE
}

public class Record {
    private String request;
    private String clarification;
    private RB rb;
    private String question;
    private int[] steps;

    public String getRequest() {
        return request;
    }

    public String getClarification() {
        return clarification;
    }

    public RB getRb() {
        return rb;
    }

    public String getQuestion() {
        return question;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setClarification(String clarification) {
        this.clarification = clarification;
    }

    public void setRb(RB rb) {
        this.rb = rb;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int[] getSteps() {
        return steps;
    }

    public void setSteps(int[] steps) {
        this.steps = steps;
    }
}
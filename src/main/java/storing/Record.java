package storing;

import java.util.ArrayList;
import java.util.List;

public class Record {
    int id;
    private String request;
    private String clarification;
    private RB rb;
    private String question;
    private int nSteps;
    private List<String> steps;

    public Record() {
        this.steps = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

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

    public int getnSteps() {
        return nSteps;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setnSteps(int nSteps) {
        this.nSteps = nSteps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
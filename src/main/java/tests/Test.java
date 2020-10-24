package tests;

import java.io.Serializable;

public class Test implements Serializable {
    private int classification;
    private String input;
    private int output;

    Test(int classification, String input, int output) {
        this.classification = classification;
        this.input = input;
        this.output = output;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}

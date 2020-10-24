package tests;

import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.util.Pair;
import storing.Record;

public class TestGenerator {

    static final String TESTS_FILE_NAME = "tests.txt";

    public static void makeTests(List<Record> records) {

    }

    public ArrayList<Test> makeTestsCorrectQuestions(List<Record> records) {
        ArrayList<Test> tests = new ArrayList<>();
        for (Record record : records) {
            tests.add(new Test(0, record.getQuestion(), record.getId()));
        }
        return tests;
    }

    public ArrayList<Test> makeTestsMistakeQuestions(List<Record> records) {
        ArrayList<Test> tests = new ArrayList<>();
        for (Record record : records) {
            String question = record.getQuestion();
            tests.add(new Test(0, record.getQuestion(), record.getId()));
        }
        return tests;
    }

}

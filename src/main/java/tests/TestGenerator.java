package tests;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import storing.Record;

public class TestGenerator {

    static final String TESTS_FILE_NAME = "tests.txt";

    public static void exportTests(List<Record> records) throws IOException {
        File file = new File(TESTS_FILE_NAME);
        PrintWriter printWriter = new PrintWriter(file);

        ArrayList<Test> tests = makeTests(records);
        for (Test test : tests) {
            printWriter.print(test.getClassification());
            printWriter.print(";");
            printWriter.print(test.getInput());
            printWriter.print(";");
            printWriter.print(test.getOutput());
            printWriter.println();
        }

        printWriter.close();
    }

    public static ArrayList<Test> makeTests(List<Record> records) {
        ArrayList<Test> tests = new ArrayList<>();
        tests.addAll(makeTestsCorrectQuestions(records));
        tests.addAll(makeTestsMistakeQuestions(records));

        return tests;
    }

    public static ArrayList<Test> makeTestsCorrectQuestions(List<Record> records) {
        ArrayList<Test> tests = new ArrayList<>();
        for (Record record : records) {
            tests.add(new Test(0, record.getQuestion(), record.getId()));
        }
        return tests;
    }

    public static ArrayList<Test> makeTestsMistakeQuestions(List<Record> records) {
        ArrayList<Test> tests = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < records.size(); ++i) {
            String correctQuestion = records.get(i).getQuestion();
            String question = "";
            int pos = 0;
            char symbol = 'а';
            switch (i % 3) {
                case 0: // Добавление случайного символа внутрь строки
                    pos = random.nextInt(correctQuestion.length() + 1);
                    symbol = (char)('а' + random.nextInt(33));
                    question = correctQuestion.substring(0, pos)
                             + symbol
                             + correctQuestion.substring(pos);
                    break;
                case 1: // Удаление случйного символа из строки
                    pos = random.nextInt(correctQuestion.length());
                    question = correctQuestion.substring(0, pos)
                             + correctQuestion.substring(pos + 1);
                    break;
                case 2: // Замена случайного символа
                    pos = random.nextInt(correctQuestion.length());
                    symbol = (char)('а' + random.nextInt(33));
                    question = correctQuestion.substring(0, pos)
                            + symbol
                            + correctQuestion.substring(pos + 1);
                    break;
            }
            tests.add(new Test(1, question, records.get(i).getId()));
        }

        return tests;
    }

}

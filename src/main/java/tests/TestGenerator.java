package tests;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import storing.Record;

import static tests.TestData.TESTS_FILE_NAME;

public class TestGenerator {
    public static void exportTests(List<Record> records) throws IOException {
        File file = new File(TESTS_FILE_NAME);

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TESTS_FILE_NAME)))
        {
            ArrayList<Test> tests = makeTests(records);
            for (Test test : tests) {
                oos.writeObject(test);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
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

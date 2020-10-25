package tests;

import java.io.*;
import java.util.*;

import Text.InvertedIndex;
import Text.TextFormatter;
import storing.Record;
import storing.ParserExcel;

import static tests.TestData.TESTS_FILE_NAME;
import static tests.TestData.DATABASE_FILE_NAME;

public class TestGenerator {

    public static void main(String[] args) {
//        ArrayList<Record> records = ParserExcel.parse(DATABASE_FILE_NAME);
//        exportTests(records);
//        TestRunner.runTests(new InvertedIndex(records));
//
        File file = new File("pozhaluista_ne_padai.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            HashMap<String, HashMap<Integer, Integer>> index = new HashMap<>();
            for (int i = 1; i <= 289; ++i) {
                Integer first = Integer.parseInt(reader.readLine());
                String second = reader.readLine();
                String third = reader.readLine();
                String fourth = reader.readLine();
                for (String word : second.split(" ")) {
                    if (!index.containsKey(word)) {
                        index.put(word, new HashMap<>());
                    }
                    Map<Integer, Integer> mp = index.get(word);
                    mp.put(first, mp.getOrDefault(first, 0) + 1);
                }
                for (String word : third.split(" ")) {
                    if (!index.containsKey(word)) {
                        index.put(word, new HashMap<>());
                    }
                    Map<Integer, Integer> mp = index.get(word);
                    mp.put(first, mp.getOrDefault(first, 0) + 2);
                }
                for (String word : fourth.split(" ")) {
                    if (!index.containsKey(word)) {
                        index.put(word, new HashMap<>());
                    }
                    Map<Integer, Integer> mp = index.get(word);
                    mp.put(first, mp.getOrDefault(first, 0) + 2);
                }
            }

            HashMap<Integer, Integer> mp = index.get("бонусный");

            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("kakoi.dat"))) {
                oos.writeObject(index);
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Record> records = ParserExcel.parse(DATABASE_FILE_NAME);
        InvertedIndex index = new InvertedIndex(records);
        List<String> lst = TextFormatter.getFixedWords("Как потратить бонусные рубли");
        System.out.println(index.processQuestion(lst));

        int counter = 0;
    }

    public static void exportTests(List<Record> records) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TESTS_FILE_NAME)))
        {
            ArrayList<Test> tests = makeTests(records);
            Integer size = tests.size();
            oos.writeObject(size);
            for (Test test : tests) {
                if (test.getInput() == null || test.getInput().isEmpty())
                    continue;
                oos.writeObject(test);
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static ArrayList<Test> makeTests(List<Record> records) {
        ArrayList<Test> tests = new ArrayList<>();
        tests.addAll(makeTestsCorrectQuestions(records));
        tests.addAll(makeTestsMistakeQuestions(records));

        return tests;
    }

    private static ArrayList<Test> makeTestsCorrectQuestions(List<Record> records) {
        ArrayList<Test> tests = new ArrayList<>();
        for (Record record : records) {
            tests.add(new Test(0, record.getQuestion(), record.getId()));
        }
        return tests;
    }

    private static ArrayList<Test> makeTestsMistakeQuestions(List<Record> records) {
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

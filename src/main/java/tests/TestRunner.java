package tests;

import Text.InvertedIndex;
import Text.TextFormatter;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class TestRunner {

    public static void runTests(InvertedIndex invertedIndex) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TestData.TESTS_FILE_NAME)))
        {
            int testAmount = 0;
            int correctTestAmount = 0;
            Integer size = (Integer)ois.readObject();
            for (int i = 0; i < size; ++i) {
                Test t = (Test)ois.readObject();
                List<Integer> answers = invertedIndex.processQuestion(TextFormatter.getFixedWords(t.getInput()));
                if (answers.get(0) == t.getOutput())
                    ++correctTestAmount;
                ++testAmount;
            }
            System.out.println(correctTestAmount + "/" + testAmount + " tests passed.");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

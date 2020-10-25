package tests;

import Text.InvertedIndex;
import Text.TextFormatter;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class TestRunner {
    public static void runTests(InvertedIndex invertedIndex) {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tests.dat")))
        {
            int testAmount = 0;
            int correctTestAmount = 0;
            while (ois.available() > 0) {
                Test t = (Test)ois.readObject();
                List<Integer> answers = invertedIndex.processQuestion(TextFormatter.getFixedWords(t.getInput()));
                if (answers.get(0) == t.getOutput())
                    ++correctTestAmount;
                ++testAmount;
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

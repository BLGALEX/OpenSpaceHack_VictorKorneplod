package tests;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class TestRunner {
    public static void runTests() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tests.dat")))
        {
            while (ois.available() > 0) {
                Test t = (Test)ois.readObject();
                // TODO run test
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

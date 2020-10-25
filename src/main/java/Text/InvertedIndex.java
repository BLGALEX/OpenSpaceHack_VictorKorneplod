package Text;

import storing.Record;
import tests.Test;
import tests.TestData;

import java.io.*;
import java.util.*;

import static tests.TestData.TESTS_FILE_NAME;

public class InvertedIndex {

    public static final String INVERTED_INDEX_FILE_NAME = "src/main/resources/inverted_index.dat";
    private static final int PRIORITY_QUESTION = 1;
    private static final int PRIORITY_REQUEST = 2;
    private static final int PRIORITY_CLARIFICATION = 2;
    private static final int RESPONSE_AMOUNT = 5;

    private HashMap<String, HashMap<Integer, Integer>> index;

    public InvertedIndex(List<Record> records) {
        index = new HashMap<>();

        File file = new File(INVERTED_INDEX_FILE_NAME);
        if (file.exists()) {
            try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file)))
            {
                index = (HashMap<String, HashMap<Integer, Integer>>)ois.readObject();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        } else {
            int counter = 1;
            try (PrintWriter writer = new PrintWriter("pozhaluista_ne_padai.txt")) {
                for (Record record : records) {
                    writer.println(counter);

                    List<String> questionWords = TextFormatter.getFixedWords(record.getQuestion());
                    for (String word : questionWords) {
                        Map<Integer, Integer> mp = index.getOrDefault(word, new HashMap<>());
                        mp.put(record.getId(), mp.getOrDefault(record.getId(), 0) + PRIORITY_QUESTION);
                    }

                    for (String word : questionWords) {
                        writer.print(word);
                        writer.print(" ");
                    }
                    writer.println();

                    List<String> requestWords = TextFormatter.getFixedWords(record.getRequest());
                    for (String word : requestWords) {
                        Map<Integer, Integer> mp = index.getOrDefault(word, new HashMap<>());
                        mp.put(record.getId(), mp.getOrDefault(record.getId(), 0) + PRIORITY_REQUEST);
                    }

                    for (String word : requestWords) {
                        writer.print(word);
                        writer.print(" ");
                    }
                    writer.println();

                    List<String> clarificationWords = TextFormatter.getFixedWords(record.getClarification());
                    for (String word : clarificationWords) {
                        Map<Integer, Integer> mp = index.getOrDefault(word, new HashMap<>());
                        mp.put(record.getId(), mp.getOrDefault(record.getId(), 0) + PRIORITY_CLARIFICATION);
                    }

                    for (String word : clarificationWords) {
                        writer.print(word);
                        writer.print(" ");
                    }
                    writer.println();

                    writer.flush();
                    System.out.println("Строка #" + counter + " считана, охуенно!");
                    ++counter;

                    try {
                        Thread.sleep(12000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                    oos.writeObject(index);
                } catch(Exception e){
                    System.out.println(e.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // InvertedIndex.processQuestion(TextFormatter.getFixedWords(****question****));
    public List<Integer> processQuestion(List<String> questionWords) {
        Map<Integer, Integer> idToPrioritySum = new HashMap<>();
        for (String word : questionWords) {
            if (index.containsKey(word)) {
                for (Map.Entry<Integer, Integer> entry : index.get(word).entrySet()) {
                    idToPrioritySum.put(entry.getKey(),
                                     idToPrioritySum.getOrDefault(entry.getKey(), 0) + entry.getValue());
                }
            }
        }

        ArrayList<Map.Entry<Integer, Integer>> requests = new ArrayList<>(idToPrioritySum.entrySet());
        requests.sort(new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> lhs, Map.Entry<Integer, Integer> rhs) {
                return Integer.compare(rhs.getValue(), lhs.getValue());
            }
        });

        ArrayList<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : requests.subList(0, Math.min(requests.size(), RESPONSE_AMOUNT))) {
            result.add(entry.getKey());
        }

        return result;
    }

}

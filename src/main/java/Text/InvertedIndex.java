package Text;

import storing.Record;

import java.util.*;

public class InvertedIndex {

    private static final int PRIORITY_QUESTION = 1;
    private static final int PRIORITY_REQUEST = 2;
    private static final int PRIORITY_CLARIFICATION = 2;
    private static final int RESPONSE_AMOUNT = 5;

    private Map<String, Map<Integer, Integer>> index;

    static List<String> getFixedWords(String text) { return new ArrayList<>(); }

    public InvertedIndex(List<Record> records) {
        index = new HashMap<>();

        for (Record record : records) {
            for (String word : getFixedWords(record.getQuestion())) {
                Map<Integer, Integer> mp = index.getOrDefault(word, new HashMap<>());
                mp.put(record.getId(), mp.getOrDefault(record.getId(), 0) + PRIORITY_QUESTION);
            }

            for (String word : getFixedWords(record.getRequest())) {
                Map<Integer, Integer> mp = index.getOrDefault(word, new HashMap<>());
                mp.put(record.getId(), mp.getOrDefault(record.getId(), 0) + PRIORITY_REQUEST);
            }

            for (String word : getFixedWords(record.getRequest())) {
                Map<Integer, Integer> mp = index.getOrDefault(word, new HashMap<>());
                mp.put(record.getId(), mp.getOrDefault(record.getId(), 0) + PRIORITY_CLARIFICATION);
            }
        }
    }


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

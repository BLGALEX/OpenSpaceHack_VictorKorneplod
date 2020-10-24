import java.util.*;
public class TextFormatter {

    private static final String API_KEY = "165f7b51cef1b84f577a130793205dbdaec40308";

    private static Set<String> wordsToRemove = new HashSet<>(
            Arrays.asList(
                    "как",
                    "по",
                    "с",
                    "печать",
                    "быть",
                    "за",
                    "текущий",
                    "месяц",
                    "о",
                    "по",
                    "я",
                    "после",
                    "мой",
                    "в",
                    "общий",
                    "умолчание",
                    "через"
            )
    );
    
    private static Map<Character, Character> enToRus = new HashMap<Character, Character>() {{
        enToRus.put('q', 'й'); enToRus.put('w', 'ц');
        enToRus.put('e', 'у'); enToRus.put('r', 'к');
        enToRus.put('t', 'е'); enToRus.put('y', 'н');
        enToRus.put('u', 'г'); enToRus.put('i', 'ш');
        enToRus.put('o', 'щ'); enToRus.put('p', 'з');
        enToRus.put('a', 'ф'); enToRus.put('s', 'ы');
        enToRus.put('d', 'в'); enToRus.put('f', 'а');
        enToRus.put('g', 'п'); enToRus.put('h', 'р');
        enToRus.put('j', 'о'); enToRus.put('k', 'л');
        enToRus.put('l', 'д'); enToRus.put('z', 'я');
        enToRus.put('x', 'ч'); enToRus.put('c', 'с');
        enToRus.put('v', 'м'); enToRus.put('b', 'и');
        enToRus.put('n', 'т'); enToRus.put('m', 'ь');
    }};


    private TextFormatter(){};

    public static List<String> getFixedWords(String text) {
        String validLayoutText = fixKeyboardLayout(text);
//        String[] badWords = text.split("([^\\w\\s]|_)+");
//        List<String> validWords = new ArrayList<>();
//        for (int i = 0; i < badWords.length; i++) {
//            String validWord = getFixed(badWords[i]);
//            if (!wordsToRemove.contains(validWord)) {
//                validWords.add(validWord);
//            }
//        }
        String apiGetString =
                "http://api.ispras.ru/texterra/v1/nlp?targetType=spelling-correction-token&apikey=" + API_KEY;
        return null;
    }

    public static String getFixed(String string) {
        // Порядок: приводим к нижнему регистру => исправляем раскладку =>
        // => исправляем опечатки => приводим к нормальной форме
        String validString = string.toLowerCase();
        validString = fixKeyboardLayout(validString);
        validString = getValidForm(validString);
        validString = getNormalizedForm(validString);
        return validString;
    }

    public static String getValidForm(String string) {
        String apiGetString =
                "http://api.ispras.ru/texterra/v1/nlp?targetType=lemma&apikey=" + API_KEY;
        return apiGetString;
    }

    public static String fixKeyboardLayout(String string) {
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            sb.append(enToRus.getOrDefault(c, c));
        }
        return sb.toString();
    }

    public static String getNormalizedForm(String string) {
        String validString = "1";
        return validString;
    }

}

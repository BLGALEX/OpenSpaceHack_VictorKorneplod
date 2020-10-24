import java.io.IOException;
import java.util.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TextFormatter {
    // Ключ должен лежать в отдельном файле и должен быть записан
    // в .gitignore. В рамках хакатона пусть ключ будет публичным.
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
    
    private static Map<Character, Character> enToRus = new HashMap<Character, Character>() {
        {
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
    }
    };


    private TextFormatter(){};

    public static List<String> getFixedWords(String text) {
        String validKeyboardLayoutText = fixKeyboardLayout(text);
        final String apiCorrectionString =
            "http://api.ispras.ru/texterra/v1/nlp?targetType=spelling-correction-token&apikey=" + API_KEY;
        final String apiNormilizeString =
            "http://api.ispras.ru/texterra/v1/nlp?targetType=spelling-correction-token&apikey=" + API_KEY;
        List<String> validWords = new ArrayList<>();
        try{
            String correctionText = sendPOST(apiCorrectionString, validKeyboardLayoutText);
            correctionText = parseToString(correctionText);

            String normalizedText = sendPOST(apiNormilizeString, correctionText);
            validWords = Arrays.asList(parseToString(normalizedText).split(" "));
        }
        catch (Exception e) {}
        return validWords;
    }

    private static String parseToString(String string) {
        return null;
    }

    private static String sendPOST(String url, String text) throws IOException {
        HttpPost post = new HttpPost(url);
        post.addHeader("Accept", "application/json");
        post.addHeader("Content-Type", "application/json");

        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"text\":\"" + text + "\"");
        json.append("}");

        post.setEntity(new StringEntity(json.toString()));

        String result;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = EntityUtils.toString(response.getEntity());
        }

        return result;
    }

    public static String fixKeyboardLayout(String string) {
        StringBuilder sb = new StringBuilder();
        for (char c : string.toCharArray()) {
            sb.append(enToRus.getOrDefault(c, c));
        }
        return sb.toString();
    }
}

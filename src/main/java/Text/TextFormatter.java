package Text;

import java.io.IOException;
import java.util.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jsonModel.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

public class TextFormatter {
    // Ключ должен лежать в отдельном файле и должен быть записан
    // в .gitignore. В рамках хакатона пусть ключ будет публичным.
    private static final String API_KEY = "165f7b51cef1b84f577a130793205dbdaec40308";

    private static final Set<String> wordsToRemove = new HashSet<>(
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
                    "через",
                    ",", ".", "-", "_", "+", "\"",
                    "\'", "\"\"", " ", "", "\n"
            )
    );

    private static final Map<Character, Character> enToRus;
    static {
        Map<Character, Character> tMap = new HashMap<>();
        tMap.put('q', 'й'); tMap.put('w', 'ц');
        tMap.put('e', 'у'); tMap.put('r', 'к');
        tMap.put('t', 'е'); tMap.put('y', 'н');
        tMap.put('u', 'г'); tMap.put('i', 'ш');
        tMap.put('o', 'щ'); tMap.put('p', 'з');
        tMap.put('a', 'ф'); tMap.put('s', 'ы');
        tMap.put('d', 'в'); tMap.put('f', 'а');
        tMap.put('g', 'п'); tMap.put('h', 'р');
        tMap.put('j', 'о'); tMap.put('k', 'л');
        tMap.put('l', 'д'); tMap.put('z', 'я');
        tMap.put('x', 'ч'); tMap.put('c', 'с');
        tMap.put('v', 'м'); tMap.put('b', 'и');
        tMap.put('n', 'т'); tMap.put('m', 'ь');
        enToRus = Collections.unmodifiableMap(tMap);
    }


    private TextFormatter(){};

    public static List<String> getFixedWords(String text) {
        String validKeyboardLayoutText = fixKeyboardLayout(text);
        final String apiCorrectionString =
            "http://api.ispras.ru/texterra/v1/nlp?targetType=spelling-correction-token&apikey=" + API_KEY;
        final String apiNormilizeString =
            "http://api.ispras.ru/texterra/v1/nlp?targetType=lemma&apikey=" + API_KEY;
        List<String> words = new ArrayList<>();
        try{
            String correctionJsonText = sendPOST(apiCorrectionString, validKeyboardLayoutText);
            String correctionText = parseCorrectToString(correctionJsonText);

            String normalizedJsonText = sendPOST(apiNormilizeString, correctionText);
            words = Arrays.asList(parseNormToString(normalizedJsonText).split(" "));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        List<String> validWords = new ArrayList<>();
        for (String w : words) {
            if (!wordsToRemove.contains(w)) {
                validWords.add(w);
            }
        }
        return validWords;
    }

    private static String parseCorrectToString(String text) {
        Gson gson = new Gson();
        CorrectionJson obj = gson.fromJson(text.substring(1, text.length() - 1), CorrectionJson.class);
        List<SpellingCorrectionTokenJson> tokens = obj.annotations.spellingCorrectionToken;

        StringBuilder sb = new StringBuilder();
        for (SpellingCorrectionTokenJson t : tokens) {
            if (!wordsToRemove.contains(t.value)){
                sb.append(t.value);
                sb.append(' ');
            }
        }
        sb.delete(sb.length(), sb.length());
        return sb.toString();
    }

    private static String parseNormToString(String text) {
        Gson gson = new Gson();
        LemmatizationJson obj = gson.fromJson(text.substring(1, text.length() - 1), LemmatizationJson.class);
        List<LemmaJson> lemmas = obj.annotations.lemmas;

        StringBuilder sb = new StringBuilder();
        for (LemmaJson t : lemmas) {
            if (!wordsToRemove.contains(t.value)){
                sb.append(t.value);
                sb.append(' ');
            }
        }
        sb.delete(sb.length(), sb.length());
        return sb.toString();
    }

    private static String sendPOST(String url, String text) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("[{ ");
        json.append("\"text\" : \"" + text + "\"");
        json.append(" }]");

        HttpPost post = new HttpPost(url);
        post.addHeader("Accept", "application/json");
        post.addHeader("Content-Type", "application/json");

        post.setEntity(new StringEntity(json.toString(), ContentType.APPLICATION_JSON));

        String result = "";
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        String text = "Еду в магазин гучи, в санкпетербурге - она жрет мй хуй как будта это бургер";
        List<String> res = getFixedWords(text);
        System.out.println(res);
    }
}

import Text.InvertedIndex;
import Text.TextFormatter;
import com.vdurmont.emoji.EmojiParser;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import storing.ParserExcel;
import storing.Record;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.min;
import static tests.TestData.DATABASE_FILE_NAME;

public class Bot extends TelegramLongPollingBot {

    static InvertedIndex invertedIndex;
    static List<Record> records;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        //invertedIndex = new InvertedIndex();
        records = ParserExcel.parse(DATABASE_FILE_NAME);
        try{
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiRequestException e){
            e.printStackTrace();
        }
    }


    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setButtons(sendMessage);
            execute(sendMessage);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }

    }


    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardThirdRow = new KeyboardRow();
        KeyboardRow keyboardFourthRow = new KeyboardRow();

        switch (sendMessage.getText()) {
            case "Напишите, что вы хотите найти?":
                break;
            case "Выберите категорию":
                keyboardFirstRow.add(new KeyboardButton("Карты"));
                keyboardFirstRow.add(new KeyboardButton("Переводы"));

                keyboardSecondRow.add(new KeyboardButton("Платежи"));
                keyboardSecondRow.add(new KeyboardButton("Счета"));

                keyboardThirdRow.add(new KeyboardButton("Вклады"));
                keyboardThirdRow.add(new KeyboardButton("Инвестиции"));

                keyboardFourthRow.add(new KeyboardButton("Кредиты"));
                keyboardFourthRow.add(new KeyboardButton("Профиль"));

                keyboardRowList.add(keyboardFirstRow);
                keyboardRowList.add(keyboardSecondRow);
                keyboardRowList.add(keyboardThirdRow);
                keyboardRowList.add(keyboardFourthRow);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                break;
            case "Выберите нужный сервис":
                keyboardFirstRow.add(new KeyboardButton("Выдача справок"));
                keyboardFirstRow.add(new KeyboardButton("Открой возможности"));

                keyboardRowList.add(keyboardFirstRow);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                break;
            case "Выберите сервис":
                keyboardFirstRow.add(new KeyboardButton("Поиск контакта"));
                keyboardFirstRow.add(new KeyboardButton("Обратно"));

                keyboardSecondRow.add(new KeyboardButton("Оставить отзыв"));

                keyboardRowList.add(keyboardFirstRow);
                keyboardRowList.add(keyboardSecondRow);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                break;
            case "Выберите форму справки":
                keyboardFirstRow.add(new KeyboardButton("Выписка с места работы"));

                keyboardSecondRow.add(new KeyboardButton("НДФЛ"));
                keyboardSecondRow.add(new KeyboardButton("Обратно"));

                keyboardRowList.add(keyboardFirstRow);
                keyboardRowList.add(keyboardSecondRow);

                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                break;
            case "Что вы бы улучшили?":
                keyboardFirstRow.add(new KeyboardButton("Сайт"));
                keyboardFirstRow.add(new KeyboardButton("Мобильное приложение"));

                keyboardSecondRow.add(new KeyboardButton("Работа с клиентами"));
                keyboardSecondRow.add(new KeyboardButton("Другое"));

                keyboardThirdRow.add(new KeyboardButton("Обратно"));

                keyboardRowList.add(keyboardFirstRow);
                keyboardRowList.add(keyboardSecondRow);
                keyboardRowList.add(keyboardThirdRow);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                break;
            case "Как вы оцениваете мою работу?":
                String gemEmoji = EmojiParser.parseToUnicode(":gem:");
                keyboardFirstRow.add(new KeyboardButton(gemEmoji + gemEmoji + gemEmoji));
                keyboardFirstRow.add(new KeyboardButton(gemEmoji + gemEmoji));
                keyboardFirstRow.add(new KeyboardButton(gemEmoji));

                keyboardRowList.add(keyboardFirstRow);

                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                break;
            default:
                keyboardFirstRow.add(new KeyboardButton("Ввести вопрос"));
                keyboardFirstRow.add(new KeyboardButton("Категории"));

                keyboardSecondRow.add(new KeyboardButton("Заявки"));
                keyboardSecondRow.add(new KeyboardButton("Прочее"));

                keyboardRowList.add(keyboardFirstRow);
                keyboardRowList.add(keyboardSecondRow);
                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                break;
        }

    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (message == null || !message.hasText())
            return;

        String messageText = message.getText();
        if (messageText.substring(0, 9).equals("/question")) {
            try {
                int nQuestion = Integer.valueOf(messageText.substring(9, messageText.length()));
                sendMsg(message, getAnswerSteps(nQuestion));
            } catch (Exception e) {

            }
            return;
        }

        switch (messageText){
            case "/start":
                sendMsg(message, "Привет! Я Бот Офелия банка Открытие!\n\n" +
                "Выберите нужное действие и я помогу.\n\n" +
                "Добавить стикеры со мной: https://t.me/addstickers/Ophelia_theBot");
                break;
            case "Обратно":
                sendMsg(message, "Ок");
                break;
            case "Ввести вопрос":
                sendMsg(message, "Напишите, что вы хотите найти?");
                break;
            case "Категории":
                sendMsg(message, "Выберите категорию");
                break;
            case "Заявки":
                sendMsg(message, "Выберите нужный сервис");
                break;
            case "Прочее":
                sendMsg(message, "Выберите сервис");
                break;
            case "Выдача справок":
                sendMsg(message, "Выберите форму справки");
                break;
            case "Открой возможности":
                sendMsg(message, "Что вы бы улучшили?");
                break;
            case "Оставить отзыв":
                sendMsg(message, "Как вы оцениваете мою работу?");
                break;
            default:
                String answer = getAnswer(message.getText());
                sendMsg(message, answer);
                break;
        }
    }



    private String getAnswer(String answer) {
        InvertedIndex index = new InvertedIndex();
        List<Integer> list = index.processQuestion(TextFormatter.getFixedWords(answer));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < min(5, list.size()); i++) {
            sb.append(list.get(i));
            sb.append(' ');
        }
        return sb.toString();
    }

    String getAnswerSteps(int i) {
        StringBuilder sb = new StringBuilder();
        int nSteps = records.get(i).getnSteps();
        List<String> steps = records.get(i).getSteps();
        for (int j = 0; j < nSteps; j++) {
            sb.append(steps.get(j));
            sb.append('\n');
        }
        return sb.toString();
    }

    public String getBotUsername() {
        return "OPB_Ophelia";
    }

    public String getBotToken() {
        return "1204989387:AAGklcRfaF0aK5kzyunHOMK5QWUFtawKjLM";
    }
}

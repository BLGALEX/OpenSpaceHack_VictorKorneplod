import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
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
            case "Выберите форму заявки":
                break;
            case "Выберите сервис":
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


        if(message!=null && message.hasText()){
            switch (message.getText()){
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
                    sendMsg(message, "Выберите форму заявки");
                    break;
                case "Прочее":
                    sendMsg(message, "Выберите сервис");
                    break;
                default:
                    sendMsg(message, message.getText());
                    break;

            }
        }

    }

    public String getBotUsername() {
        return "OPB_Ophelia";
    }

    public String getBotToken() {
        return "1204989387:AAGklcRfaF0aK5kzyunHOMK5QWUFtawKjLM";
    }
}

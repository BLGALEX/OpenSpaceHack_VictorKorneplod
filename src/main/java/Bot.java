import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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


    public void sendMsg(org.telegram.telegrambots.api.objects.Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try{
            setButton(sendMessage);
            execute(sendMessage);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }

    }

    public void setButton(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/test"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();


        if(message!=null && message.hasText()){
            switch (message.getText()){
                case "/help":
                    sendMsg(message, "чем помочь?");
                    break;
                case "/test":
                    sendMsg(message, "я готов");
                    break;
                case "/start":
                    sendMsg(message, "я готов");
                    break;
                default:
                    sendMsg(message, message.getText());
                    break;

            }
        }

    }

    public String getBotUsername() {
        return "VictorKorneplodChatBot";
    }

    public String getBotToken() {
        return "1204989387:AAGklcRfaF0aK5kzyunHOMK5QWUFtawKjLM";
    }
}

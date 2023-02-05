import com.fasterxml.jackson.core.JsonProcessingException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class telegramBot extends TelegramLongPollingBot {
    // Telegram bot token & name
    public static final String BOT_TOKEN = "YOUR bot token from @BotFather";
    public static final String BOT_USERNAME = "YOUR Telagram username_bot";
    //Use your own OpenAI API
    public static final String OPENAI_API = "YOUR API";
    public static long CHAT_ID;
    public static int MESSAGE_ID;
    public static String textQuestion;
    public static String textAnswer;

    public telegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            CHAT_ID = update.getMessage().getChatId();
            MESSAGE_ID = update.getMessage().getMessageId();
            String userQuestion = update.getMessage().getText();

            System.out.print("\nID: " + update.getMessage().getChatId() + " @" + update.getMessage().getFrom().getUserName() + " Msg: " + update.getMessage().getText());
            if (userQuestion.contains("/say")) {
                textQuestion = userQuestion.substring(5);
                try {
                    textAnswer = Utils.getText(OPENAI_API, textQuestion);
                    replyMessage(textAnswer);
                } catch (JsonProcessingException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            switch (userQuestion) {
                case "/help":
                    sendMessage("Hi! I`m ChatGPT AI. " +
                            "Type /say then your own question");
                    break;
                case "/example":
                    try {
                        replyMessage(Utils.getExampleText(OPENAI_API));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }

    // If you want a dialogue without reply on your message
    private void sendMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(CHAT_ID);
        message.setText(messageText);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // If you want a dialogue with reply on your message (use it if you added bot in group)
    private void replyMessage(String messageText) {
        SendMessage message = new SendMessage();
        message.setChatId(CHAT_ID);
        message.setText(messageText);
        message.setReplyToMessageId(MESSAGE_ID);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


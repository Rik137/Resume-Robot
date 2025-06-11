package com.sergey.nazarov.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;

public class ResumeBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "ResumeRobot"; // Заменить на имя бота
    }

    @Override
    public String getBotToken() {
        return "YOUR_BOT_TOKEN"; // Вставь токен сюда
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message.toLowerCase()) {
                case "/start":
                    sendText(chatId, "Здравствуйте! Я бот-кандидат. " +
                            "Вот что я умею:\n" +
                            "/resume — показать резюме\n" +
                            "/skills — мои ключевые навыки\n" +
                            "/contacts — как со мной связаться");
                    break;
                case "/resume":
                    sendText(chatId, "Вот краткое описание:\n" +
                            "ФИО: Иван Иванов\n" +
                            "Позиция: Java-разработчик\n" +
                            "Опыт: 3 года\n\nОтправляю PDF-файл с резюме...");
                    sendResumePdf(chatId);
                    break;
                case "/skills":
                    sendText(chatId, "Мои навыки:\n- Java, Spring, " +
                            "Hibernate\n- ORACLE,MySQL\n- REST API\n- Git");
                    break;
                case "/contacts":
                    sendText(chatId, "Email: ric19902002@icloud.com\n" +
                            "Telegram: Rick13\n" +
                            "LinkedIn: linkedin.com/in/ivan");
                    break;
                default:
                    sendText(chatId, "Извините, я не понял команду." +
                            " Попробуйте /start.");
            }
        }
    }

    private void sendText(long chatId, String text) {
        try {
            execute(new SendMessage(String.valueOf(chatId), text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendResumePdf(long chatId) {
        try {
            File pdfFile = new File("path/to/resume.pdf"); // путь к резюме
            InputFile inputFile = new InputFile(pdfFile);
            SendDocument document = new SendDocument();
            document.setChatId(String.valueOf(chatId));
            document.setDocument(inputFile);
            document.setCaption("Резюме в PDF формате");
            execute(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new ResumeBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


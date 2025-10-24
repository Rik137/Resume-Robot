package com.sergey.nazarov.bot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ResumeBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Your bot name"; // Заменить на имя бота
    }

    @Override
    public String getBotToken() {
        return "token"; // Вставь токен сюда
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start":
                    sendTextWithKeyboard(chatId, "напиши свое приветсвие");
                    break;
                case "📄 Резюме":
                case "/resume":
                    sendTextWithKeyboard(chatId, """
                    напиши о себе: фио, опыт работы и т.д.
                    """);
                    break;
                case "💡 Навыки":
                case "/skills":
                    sendTextWithKeyboard(chatId, """
                    напиши свои навыки
                    """);
                    break;
                case "☎️ Контакты":
                case "/contacts":
                    sendTextWithKeyboard(chatId, """
                    напиши свои контакты
                    """);
                    break;
                case "🐱 GitHub":
                case "/github":
                    sendGitHubPreview(chatId, "ник от GitHub");
                    break;
                case "🔍 Проект":
                case "/searchengine":
                    sendTextWithKeyboard(chatId, "сылка на проект");
                    break;
                case "📄 Скачать резюме":
                    sendResumePdf(chatId);
                    break;
                default:
                    sendTextWithKeyboard(chatId, "Извините, я не понял команду. Попробуйте кнопки ниже.");
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
            File pdfFile = new File("путь к твоему резюме"); // Укажи реальный путь
            if (pdfFile.exists()) {
                SendDocument document = new SendDocument();
                document.setChatId(String.valueOf(chatId));
                document.setDocument(new InputFile(pdfFile));
                document.setCaption("Резюме в PDF формате");
                execute(document);
            } else {
                sendText(chatId, "PDF-файл с резюме не найден.");
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextWithKeyboard(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("📄 Резюме");
        row1.add("💡 Навыки");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("☎️ Контакты");
        row2.add("🐱 GitHub");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("🔍 Проект");
        row3.add("📄 Скачать резюме");

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        keyboardMarkup.setKeyboard(keyboard);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendKeyboard(long chatId) {
        SendMessage keyboardMessage = new SendMessage();
        keyboardMessage.setChatId(String.valueOf(chatId));

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        KeyboardRow row1 = new KeyboardRow();
        row1.add("📄 Резюме");
        row1.add("💡 Навыки");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("☎️ Контакты");
        row2.add("🐱 GitHub");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("🔍 Проект");
        row3.add("📄 Скачать резюме");

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        keyboardMarkup.setKeyboard(keyboard);
        keyboardMessage.setReplyMarkup(keyboardMarkup);

        try {
            execute(keyboardMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendGitHubPreview(long chatId, String username) {
        try {

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.github.com/users/" + username))
                    .header("Accept", "application/vnd.github.v3+json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                sendText(chatId, "Не удалось получить данные с GitHub.");
                return;
            }

            ObjectMapper mapper = new ObjectMapper();
            JsonNode user = mapper.readTree(response.body());
            String avatarUrl = user.path("avatar_url").asText();
            String name = user.path("name").asText("");
            String bio = user.path("bio").asText("");
            String htmlUrl = user.path("html_url").asText("https://github.com/" + username);

            String caption = (name.isEmpty() ? username : name) + "\n"
                    + (bio.isEmpty() ? "" : bio + "\n")
                    + htmlUrl;

            SendPhoto photo = new SendPhoto();
            photo.setChatId(String.valueOf(chatId));
            photo.setPhoto(new InputFile(avatarUrl));
            photo.setCaption(caption);
            execute(photo);

        } catch (Exception e) {
            e.printStackTrace();
            sendText(chatId, "Произошла ошибка при получении данных GitHub.");
        }
    }

}




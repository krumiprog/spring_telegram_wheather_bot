package com.example.wheatherbot.bot;

import com.example.wheatherbot.service.WheatherService;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class WheatherBot extends TelegramWebhookBot {

    private String botToken;
    private String botUsername;
    private String webhookPath;

    private final WheatherService wheatherService;

    public WheatherBot(DefaultBotOptions options, WheatherService wheatherService) {
        super(options);
        this.wheatherService = wheatherService;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return wheatherService.handleUpdate(update);
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotPath() {
        return webhookPath;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    public void setWebHookPath(String webHookPath) {
        this.webhookPath = webHookPath;
    }
}

package com.example.wheatherbot.controller;

import com.example.wheatherbot.bot.WheatherBot;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class MainController {

    private final WheatherBot wheatherBot;

    public MainController(WheatherBot wheatherBot) {
        this.wheatherBot = wheatherBot;
    }

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return wheatherBot.onWebhookUpdateReceived(update);
    }
}

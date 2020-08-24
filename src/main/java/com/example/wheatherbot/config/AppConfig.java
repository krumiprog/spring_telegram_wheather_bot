package com.example.wheatherbot.config;

import com.example.wheatherbot.bot.WheatherBot;
import com.example.wheatherbot.service.WheatherService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Configuration
public class AppConfig {

    private final BotConfig botConfig;
    private final WheatherService wheatherService;

    public AppConfig(BotConfig botConfig, WheatherService wheatherService) {
        this.botConfig = botConfig;
        this.wheatherService = wheatherService;
    }

    @Bean
    public WheatherBot wheatherBot() {
        DefaultBotOptions options = ApiContext.getInstance(DefaultBotOptions.class);

        WheatherBot wheatherBot = new WheatherBot(options, wheatherService);
        wheatherBot.setBotToken(botConfig.getBotToken());
        wheatherBot.setBotUsername(botConfig.getBotUsername());
        wheatherBot.setWebHookPath(botConfig.getWebhookPath());

        return wheatherBot;
    }
}

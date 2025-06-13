package org.nerdwire.bot.config;

import org.nerdwire.bot.service.telegram.NerdWireBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import jakarta.annotation.PostConstruct;

@Service
public class BotInitializer {

    private final BotConfig config;
    private final NerdWireBot bot;

    @Autowired
    public BotInitializer(BotConfig config, NerdWireBot bot) {
        this.config = config;
        this.bot = bot;
    }    @PostConstruct
    public void init() throws TelegramApiException {
        try {
            // Clear any existing webhooks
            bot.clearWebhook();
            
            // Initialize bot API
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Error initializing bot: " + e.getMessage());
        }
    }
}

package org.nerdwire.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.nerdwire.bot.service.telegram.NerdWireBot;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@EnableScheduling
@EnableCaching
@Configuration
public class MainConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(NerdWireBot nerdWireBot) throws Exception {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(nerdWireBot);
        return api;
    }
}

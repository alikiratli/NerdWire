package org.nerdwire.bot.service.telegram;

import org.nerdwire.bot.config.BotProperties;
import org.nerdwire.bot.service.news.NewsAggregatorService;
import org.nerdwire.bot.service.news.NewsItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class NerdWireBot extends TelegramLongPollingBot {
    private final BotProperties botProperties;
    private final NewsAggregatorService newsAggregatorService;
    private final Set<String> sentNewsUrls = new HashSet<>();

    @Autowired
    public NerdWireBot(BotProperties botProperties, NewsAggregatorService newsAggregatorService) {
        this.botProperties = botProperties;
        this.newsAggregatorService = newsAggregatorService;
    }

    @Override
    public String getBotUsername() {
        return botProperties.getName();
    }

    @Override
    public String getBotToken() {
        return botProperties.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            if (text.equalsIgnoreCase("/news")) {
                List<NewsItem> news = newsAggregatorService.getAllNews();
                StringBuilder sb = new StringBuilder();
                sb.append("\uD83D\uDCF0 <b>Latest Software Development News</b>\n\n");
                for (int i = 0; i < Math.min(news.size(), 10); i++) {
                    NewsItem item = news.get(i);
                    sb.append("<b>").append(i+1).append(".</b> <a href=\"")
                      .append(item.getUrl()).append("\">")
                      .append(item.getTitle()).append("</a> <i>(")
                      .append(item.getSource()).append(")</i>\n");
                }
                SendMessage message = new SendMessage();
                message.setChatId(update.getMessage().getChatId().toString());
                message.setText(sb.toString());
                message.setParseMode("HTML");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    // log or handle error
                }
            }
        }
    }

    @Scheduled(fixedRate = 600000) // 10 dakikada bir
    public void sendLatestNewsToOwner() {
        List<NewsItem> news = newsAggregatorService.getAllNews();
        StringBuilder sb = new StringBuilder();
        int newCount = 0;
        for (int i = 0; i < Math.min(news.size(), 10); i++) {
            NewsItem item = news.get(i);
            if (!sentNewsUrls.contains(item.getUrl())) {
                sentNewsUrls.add(item.getUrl());
                sb.append("<b>").append(i+1).append(".</b> <a href=\"")
                  .append(item.getUrl()).append("\">")
                  .append(item.getTitle()).append("</a> <i>(")
                  .append(item.getSource()).append(")</i>\n");
                newCount++;
            }
        }
        if (newCount > 0) {
            SendMessage message = new SendMessage();
            // Burada chatId'yi sabit bir kullanıcıya veya gruba gönderebilirsiniz
            // Örneğin, kendi chatId'nizi veya bir kanalın chatId'sini girin
            String chatId = "<NerdWire>"; 
            message.setChatId(chatId);
            message.setText("\uD83D\uDCF0 <b>Yeni Yazılım Haberleri</b>\n\n" + sb.toString());
            message.setParseMode("HTML");
            try {
                execute(message);
            } catch (TelegramApiException e) {
                // log or handle error
            }
        }
    }
}

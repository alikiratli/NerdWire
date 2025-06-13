package org.nerdwire.bot.service.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Service
@RestController
public class NewsAggregatorService {
    private final List<NewsSupplier> suppliers;

    @Autowired
    public NewsAggregatorService(List<NewsSupplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<NewsItem> getAllNews() {
        List<NewsItem> allNews = new ArrayList<>();
        for (NewsSupplier supplier : suppliers) {
            allNews.addAll(supplier.getLatestNews());
        }
        return allNews;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "Bot is running!";
    }

    @Scheduled(fixedRate = 3600000) // Her saat başı çalışır
    public void aggregateAndSendNews() {
        // Mevcut haber toplama ve gönderme kodları
    }
}

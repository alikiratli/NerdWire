package org.nerdwire.bot.service.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
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
}

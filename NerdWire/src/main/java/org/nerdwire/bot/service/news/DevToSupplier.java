package org.nerdwire.bot.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DevToSupplier implements NewsSupplier {
    private static final String DEVTO_URL = "https://dev.to/";

    @Override
    public List<NewsItem> getLatestNews() {
        List<NewsItem> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(DEVTO_URL).get();
            for (Element article : doc.select("div.crayons-story")) {
                String title = article.select("h2.crayons-story__title a").text();
                String url = article.select("h2.crayons-story__title a").attr("href");
                if (!title.isEmpty() && !url.isEmpty()) {
                    if (!url.startsWith("http")) url = "https://dev.to" + url;
                    news.add(new NewsItem(title, url, "Dev.to"));
                }
            }
        } catch (Exception e) {
            // log or handle error
        }
        return news;
    }
}

package org.nerdwire.bot.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class VergeTechSupplier implements NewsSupplier {
    private static final String VERGE_TECH_URL = "https://www.theverge.com/tech";

    @Override
    public List<NewsItem> getLatestNews() {
        List<NewsItem> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(VERGE_TECH_URL).get();
            for (Element article : doc.select("h2.c-entry-box--compact__title a")) {
                String title = article.text();
                String url = article.attr("href");
                if (!title.isEmpty() && !url.isEmpty()) {
                    news.add(new NewsItem(title, url, "The Verge Tech"));
                }
            }
        } catch (Exception e) {
            // log or handle error
        }
        return news;
    }
}

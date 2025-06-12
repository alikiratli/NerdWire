package org.nerdwire.bot.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StackOverflowBlogSupplier implements NewsSupplier {
    private static final String SO_BLOG_URL = "https://stackoverflow.blog/";

    @Override
    public List<NewsItem> getLatestNews() {
        List<NewsItem> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(SO_BLOG_URL).get();
            for (Element article : doc.select("article.post")) {
                String title = article.select("h2.entry-title a").text();
                String url = article.select("h2.entry-title a").attr("href");
                if (!title.isEmpty() && !url.isEmpty()) {
                    news.add(new NewsItem(title, url, "Stack Overflow Blog"));
                }
            }
        } catch (Exception e) {
            // log or handle error
        }
        return news;
    }
}

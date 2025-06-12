package org.nerdwire.bot.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class HackerNewsSupplier implements NewsSupplier {
    private static final String HN_URL = "https://news.ycombinator.com/";

    @Override
    public List<NewsItem> getLatestNews() {
        List<NewsItem> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(HN_URL).get();
            for (Element row : doc.select("tr.athing")) {
                String title = row.select("a.storylink").text();
                String url = row.select("a.storylink").attr("href");
                if (!title.isEmpty() && !url.isEmpty()) {
                    news.add(new NewsItem(title, url, "Hacker News"));
                }
            }
        } catch (Exception e) {
            // log or handle error
        }
        return news;
    }
}

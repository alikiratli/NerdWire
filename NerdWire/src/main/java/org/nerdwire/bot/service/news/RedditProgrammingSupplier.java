package org.nerdwire.bot.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedditProgrammingSupplier implements NewsSupplier {
    private static final String REDDIT_URL = "https://www.reddit.com/r/programming/";

    @Override
    public List<NewsItem> getLatestNews() {
        List<NewsItem> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(REDDIT_URL).userAgent("Mozilla/5.0").get();
            for (Element post : doc.select("div.Post")) {
                Element titleElem = post.selectFirst("h3");
                Element linkElem = post.selectFirst("a[data-click-id=body]");
                if (titleElem != null && linkElem != null) {
                    String title = titleElem.text();
                    String url = linkElem.attr("href");
                    if (!url.startsWith("http")) url = "https://www.reddit.com" + url;
                    news.add(new NewsItem(title, url, "Reddit r/programming"));
                }
            }
        } catch (Exception e) {
            // log or handle error
        }
        return news;
    }
}

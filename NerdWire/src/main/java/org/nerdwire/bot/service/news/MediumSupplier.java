package org.nerdwire.bot.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MediumSupplier implements NewsSupplier {
    private static final String MEDIUM_URL = "https://medium.com/tag/software-development";

    @Override
    public List<NewsItem> getLatestNews() {
        List<NewsItem> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(MEDIUM_URL).get();
            for (Element article : doc.select("div.js-postListHandle section div h2")) {
                String title = article.text();
                Element link = article.parent().parent().selectFirst("a");
                String url = link != null ? link.attr("href") : "";
                if (!title.isEmpty() && !url.isEmpty()) {
                    if (!url.startsWith("http")) url = "https://medium.com" + url;
                    news.add(new NewsItem(title, url, "Medium"));
                }
            }
        } catch (Exception e) {
            // log or handle error
        }
        return news;
    }
}

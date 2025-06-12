package org.nerdwire.bot.service.news;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class GithubBlogSupplier implements NewsSupplier {
    private static final String GITHUB_BLOG_URL = "https://github.blog/";

    @Override
    public List<NewsItem> getLatestNews() {
        List<NewsItem> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(GITHUB_BLOG_URL).get();
            for (Element article : doc.select("article.post-item")) {
                String title = article.select("h2.post-item-title a").text();
                String url = article.select("h2.post-item-title a").attr("href");
                if (!title.isEmpty() && !url.isEmpty()) {
                    news.add(new NewsItem(title, url, "GitHub Blog"));
                }
            }
        } catch (Exception e) {
            // log or handle error
        }
        return news;
    }
}

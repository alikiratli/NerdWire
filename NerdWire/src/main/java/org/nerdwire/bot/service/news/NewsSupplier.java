package org.nerdwire.bot.service.news;

import java.util.List;

public interface NewsSupplier {
    List<NewsItem> getLatestNews();
}

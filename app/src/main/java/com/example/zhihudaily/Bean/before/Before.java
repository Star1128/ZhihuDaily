package com.example.zhihudaily.Bean.before;

import com.example.zhihudaily.Bean.latest.NewsItem;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/18
 * @version 1.0.0
 */
public class Before {

    private String date;
    private List<NewsItem> stories;

    public Before(String date, List<NewsItem> stories) {
        this.date = date;
        this.stories = stories;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<NewsItem> getStories() {
        return stories;
    }

    public void setStories(List<NewsItem> stories) {
        this.stories = stories;
    }
}

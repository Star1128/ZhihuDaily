package com.example.zhihudaily.Bean.latest;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/16
 * @version 1.0.0
 */
public class LatestGroup {

    private String date;
    private List<NewsItem> stories;
    private List<CarouselItem> top_stories;

    public LatestGroup(String date, List<NewsItem> stories, List<CarouselItem> top_stories) {
        this.date = date;
        this.stories = stories;
        this.top_stories = top_stories;
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

    public List<CarouselItem> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<CarouselItem> top_stories) {
        this.top_stories = top_stories;
    }
}

package com.example.zhihudaily.Bean.latest;

import java.util.List;

/**
 * NOTE:
 *
 * @author wxc 2021/8/16
 * @version 1.0.0
 */
public class NewsItem extends Item{


    private List<String> images;


    public NewsItem(String title, String ga_prefix, String image_hue, String hint, String url, int type, int id, boolean isGood,List<String> images) {
        super(title, ga_prefix, image_hue, hint, url, type, id, isGood);
        this.images=images;
    }

    public String getImages() {
        return images.get(0);
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}

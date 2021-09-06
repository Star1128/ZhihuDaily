package com.example.zhihudaily.Bean.latest;

/**
 * NOTE:
 *
 * @author wxc 2021/8/16
 * @version 1.0.0
 */
public class CarouselItem extends Item{

    private String image;

    public CarouselItem(String title, String ga_prefix, String image_hue, String hint, String url, int type, int id, boolean isGood,String image) {
        super(title, ga_prefix, image_hue, hint, url, type, id, isGood);
        this.image=image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

package com.example.zhihudaily.Bean.latest;

/**
 * NOTE:
 *
 * @author wxc 2021/8/21
 * @version 1.0.0
 */
public class Item {
    private String title;
    private String ga_prefix;
    private String image_hue;
    private String hint;
    private String url;
    private int type;
    private int id;
    private boolean isGood;

    public Item(String title, String ga_prefix, String image_hue, String hint, String url, int type, int id, boolean isGood) {
        this.title = title;
        this.ga_prefix = ga_prefix;
        this.image_hue = image_hue;
        this.hint = hint;
        this.url = url;
        this.type = type;
        this.id = id;
        this.isGood = isGood;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getImage_hue() {
        return image_hue;
    }

    public void setImage_hue(String image_hue) {
        this.image_hue = image_hue;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsGood() {
        return isGood;
    }

    public void setIsGood(boolean good) {
        isGood = good;
    }
}

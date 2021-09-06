package com.example.zhihudaily.Bean.version;

/**
 * NOTE:
 *
 * @author wxc 2021/8/23
 * @version 1.0.0
 */

public class Version {

    private int status;
    private String msg;
    private String url;
    private String latest;

    public Version(int status, String msg, String url, String latest) {
        this.status = status;
        this.msg = msg;
        this.url = url;
        this.latest = latest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLatest() {
        return latest;
    }

    public void setLatest(String latest) {
        this.latest = latest;
    }
}

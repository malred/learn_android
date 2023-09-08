package com.malugy.activitydemo.entity;

/**
 * 对应请求得到的json数据
 */
public class jsondata {
    public jsondata(int ret, String text, String source, String author) {
        this.ret = ret;
        this.text = text;
        this.source = source;
        this.author = author;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private int ret;
    private String text;
    private String source;
    private String author;

    @Override
    public String toString() {
        return "jsondata{" +
                "ret=" + ret +
                ", text='" + text + '\'' +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

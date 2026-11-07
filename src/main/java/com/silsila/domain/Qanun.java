package com.silsila.domain;

// A modern law (qanun) that turns a ruling into a statute.
public class Qanun {

    private int id;
    private String lawName;       // for example "Egyptian Law No. 25 of 1929"
    private String article;       // for example "3"
    private String description;   // what the article says

    public Qanun(int id, String lawName, String article, String description) {
        this.id = id;
        this.lawName = lawName;
        this.article = article;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getLawName() {
        return lawName;
    }

    public String getArticle() {
        return article;
    }

    public String getDescription() {
        return description;
    }

    // The citation shown to students.
    public String getReference() {
        return lawName + ", Article " + article;
    }
}
package com.silsila.domain;

public class Masala {

    private int id;
    private String title;
    private String category;     // "NIKAH" or "TALAQ"
    private String chapter;      // for example "Conditions of a valid contract"
    private String description;
    private String keywords;     

    // The constructor runs when we write: new Masala(...)
    // It copies the values we pass in into the fields above.
    public Masala(int id, String title, String category, String chapter,
                  String description, String keywords) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.chapter = chapter;
        this.description = description;
        this.keywords = keywords;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getChapter() {
        return chapter;
    }

    public String getDescription() {
        return description;
    }

    public String getKeywords() {
        return keywords;
    }
}
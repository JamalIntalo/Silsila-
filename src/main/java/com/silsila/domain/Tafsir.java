package com.silsila.domain;

// A scholar's explanation of one verse.
// ayahId tells us which verse it explains.
// edition is the name the tafsir website uses for this book (used in Step 5).
public class Tafsir {

    private int id;
    private int ayahId;
    private String sourceName;    // for example "Tafsir Ibn Kathir (English)"
    private String edition;       // for example "en-tafisr-ibn-kathir"
    private String text;          // filled in later from the website

    public Tafsir(int id, int ayahId, String sourceName, String edition, String text) {
        this.id = id;
        this.ayahId = ayahId;
        this.sourceName = sourceName;
        this.edition = edition;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public int getAyahId() {
        return ayahId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getEdition() {
        return edition;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
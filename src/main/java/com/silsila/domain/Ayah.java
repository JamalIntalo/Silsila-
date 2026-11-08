package com.silsila.domain;

// One verse of the Qur'an.
// We only type WHERE the verse is (surah number and ayah number).
// The Arabic text and the English translation are loaded from a Qur'an website
// when the program starts, so nobody has to type them (and nobody can mistype them).
public class Ayah {

    private int id;
    private int surahNumber;
    private String surahName;
    private int ayahNumber;
    private String text;          // the Arabic text, filled in from the website
    private String translation;   // the English translation, filled in from the website

    public Ayah(int id, int surahNumber, String surahName, int ayahNumber, String text) {
        this.id = id;
        this.surahNumber = surahNumber;
        this.surahName = surahName;
        this.ayahNumber = ayahNumber;
        this.text = text;
        this.translation = "";
    }

    public int getId() {
        return id;
    }

    public int getSurahNumber() {
        return surahNumber;
    }

    public String getSurahName() {
        return surahName;
    }

    public int getAyahNumber() {
        return ayahNumber;
    }

    public String getText() {
        return text;
    }

    public String getTranslation() {
        return translation;
    }

    // Setters: let another class change the text after the object was created.
    public void setText(String text) {
        this.text = text;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    // The citation shown to students, for example "Surah Al-Baqarah 2:232".
    public String getReference() {
        return "Surah " + surahName + " " + surahNumber + ":" + ayahNumber;
    }
}
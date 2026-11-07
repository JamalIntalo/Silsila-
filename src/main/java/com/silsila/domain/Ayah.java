package com.silsila.domain;

// One verse of the Qur'an.
// We only type WHERE the verse is (surah number and ayah number).
// The Arabic text itself will be loaded from a Qur'an website in Step 5,
// so nobody has to type it (and nobody can mistype it).
public class Ayah {

    private int id;
    private int surahNumber;
    private String surahName;
    private int ayahNumber;
    private String text;          // the Arabic text, filled in later from the website

    public Ayah(int id, int surahNumber, String surahName, int ayahNumber, String text) {
        this.id = id;
        this.surahNumber = surahNumber;
        this.surahName = surahName;
        this.ayahNumber = ayahNumber;
        this.text = text;
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

    // A setter: lets another class change the text after the object was created.
    public void setText(String text) {
        this.text = text;
    }

    // The citation shown to students, for example "Surah Al-Baqarah 2:232".
    public String getReference() {
        return "Surah " + surahName + " " + surahNumber + ":" + ayahNumber;
    }
}
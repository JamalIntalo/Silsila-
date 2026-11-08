package com.silsila.domain;

// A hadith that supports a ruling.
// We only type WHICH hadith it is (the book and its number).
// The text and the grade are loaded from a hadith website when the program starts.
public class Hadith {

    private int id;
    private String collection;      // the website's name for the book, for example "abudawud"
    private String collectionName;  // the name shown to students, for example "Sunan Abi Dawud"
    private int number;             // the number shown to students (the usual citation)
    private int apiNumber;          // the number the website uses (sometimes different)
    private String text;            // the English text, filled in from the website
    private String arabicText;      // the Arabic text, filled in from the website
    private String grade;           // for example "Sahih (Al-Albani)", filled in from the website

    public Hadith(int id, String collection, String collectionName, int number,
                  int apiNumber, String text, String grade) {
        this.id = id;
        this.collection = collection;
        this.collectionName = collectionName;
        this.number = number;
        this.apiNumber = apiNumber;
        this.text = text;
        this.arabicText = "";
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public String getCollection() {
        return collection;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public int getNumber() {
        return number;
    }

    public int getApiNumber() {
        return apiNumber;
    }

    public String getText() {
        return text;
    }

    public String getArabicText() {
        return arabicText;
    }

    public String getGrade() {
        return grade;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setArabicText(String arabicText) {
        this.arabicText = arabicText;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    // The citation shown to students, for example "Sunan Abi Dawud no. 2085".
    public String getReference() {
        return collectionName + " no. " + number;
    }
}
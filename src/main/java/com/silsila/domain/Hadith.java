package com.silsila.domain;

// A hadith that supports a ruling.
// We only type WHICH hadith it is (the book and its number).
// The text and the grade will be loaded from a hadith website in Step 5.
public class Hadith {

    private int id;
    private String collection;      // the website's name for the book, for example "abudawud"
    private String collectionName;  // the name shown to students, for example "Sunan Abi Dawud"
    private int number;
    private String text;            // filled in later from the website
    private String grade;           // for example "Sahih", filled in later from the website

    public Hadith(int id, String collection, String collectionName, int number,
                  String text, String grade) {
        this.id = id;
        this.collection = collection;
        this.collectionName = collectionName;
        this.number = number;
        this.text = text;
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

    public String getText() {
        return text;
    }

    public String getGrade() {
        return grade;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    // The citation shown to students, for example "Sunan Abi Dawud no. 2085".
    public String getReference() {
        return collectionName + " no. " + number;
    }
}
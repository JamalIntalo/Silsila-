package com.example.silsila.data;

public class Masala {
    private String title;
    private String madhab;
    private String ruling;

    // Constructor
    public Masala(String title, String madhab, String ruling) {
        this.title = title;
        this.madhab = madhab;
        this.ruling = ruling;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public String getMadhab() { return madhab; }
    public String getRuling() { return ruling; }

    @Override
    public String toString() {
        return "Masala: " + title + " [" + madhab + "] - Ruling: " + ruling;
    }
}
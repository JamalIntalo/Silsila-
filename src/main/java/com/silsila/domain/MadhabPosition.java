package com.silsila.domain;

// What ONE madhab says about ONE issue.
// An issue has up to four of these (Hanafi, Maliki, Shafi'i, Hanbali),
// and the page shows them side by side (requirement FR-2).
public class MadhabPosition {

    private int id;
    private int masalaId;        // which issue this position belongs to
    private String madhab;       // "Hanafi", "Maliki", "Shafi'i" or "Hanbali"
    private String ruling;       // what the school says
    private String evidence;     // the texts the school relies on
    private String illah;        // the reasoning behind the ruling
    private String citation;     // the book it was taken from (requirement FR-5)

    public MadhabPosition(int id, int masalaId, String madhab, String ruling,
                          String evidence, String illah, String citation) {
        this.id = id;
        this.masalaId = masalaId;
        this.madhab = madhab;
        this.ruling = ruling;
        this.evidence = evidence;
        this.illah = illah;
        this.citation = citation;
    }

    public int getId() {
        return id;
    }

    public int getMasalaId() {
        return masalaId;
    }

    public String getMadhab() {
        return madhab;
    }

    public String getRuling() {
        return ruling;
    }

    public String getEvidence() {
        return evidence;
    }

    public String getIllah() {
        return illah;
    }

    public String getCitation() {
        return citation;
    }
}
package com.silsila.domain;

// A link connects one issue (Masala) to something that supports it:
// a verse, a hadith or a law.
// Students only see links whose status is "APPROVED".
public class Link {

    private int id;
    private int masalaId;      // the issue
    private String type;       // "AYAH", "HADITH" or "QANUN"
    private int targetId;      // the id of that verse, hadith or law
    private String citation;   // where the connection comes from (requirement NFR-4)
    private String status;     // "PENDING", "APPROVED" or "REJECTED"

    public Link(int id, int masalaId, String type, int targetId, String citation, String status) {
        this.id = id;
        this.masalaId = masalaId;
        this.type = type;
        this.targetId = targetId;
        this.citation = citation;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getMasalaId() {
        return masalaId;
    }

    public String getType() {
        return type;
    }

    public int getTargetId() {
        return targetId;
    }

    public String getCitation() {
        return citation;
    }

    public String getStatus() {
        return status;
    }

    // True if a reviewer has approved this link.
    public boolean isApproved() {
        return status.equals("APPROVED");
    }
}
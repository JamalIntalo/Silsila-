package com.silsila.dataaccess;

import com.silsila.domain.Hadith;
import com.silsila.domain.Link;
import java.util.ArrayList;

// Finds hadith in the database.
public class HadithDAO {

    private Database database;

    public HadithDAO(Database database) {
        this.database = database;
    }

    // Returns the hadith with this id, or null if there is none.
    public Hadith findById(int id) {
        for (Hadith h : database.hadiths) {
            if (h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    // Returns the hadith linked to this issue through approved links only.
    public ArrayList<Hadith> findApprovedByMasala(int masalaId) {
        ArrayList<Hadith> results = new ArrayList<Hadith>();
        for (Link link : database.links) {
            if (link.getMasalaId() == masalaId
                    && link.getType().equals("HADITH")
                    && link.isApproved()) {
                Hadith hadith = findById(link.getTargetId());
                if (hadith != null) {
                    results.add(hadith);
                }
            }
        }
        return results;
    }
}
package com.silsila.dataaccess;

import com.silsila.domain.Ayah;
import com.silsila.domain.Link;
import java.util.ArrayList;

// Finds verses in the database.
public class AyahDAO {

    private Database database;

    public AyahDAO(Database database) {
        this.database = database;
    }

    // Returns the verse with this id, or null if there is none.
    public Ayah findById(int id) {
        for (Ayah a : database.ayat) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    // Returns the verses linked to this issue, but ONLY through approved links.
    // A link that is still pending or was rejected must never reach the students.
    public ArrayList<Ayah> findApprovedByMasala(int masalaId) {
        ArrayList<Ayah> results = new ArrayList<Ayah>();
        for (Link link : database.links) {
            if (link.getMasalaId() == masalaId
                    && link.getType().equals("AYAH")
                    && link.isApproved()) {
                Ayah ayah = findById(link.getTargetId());
                if (ayah != null) {
                    results.add(ayah);
                }
            }
        }
        return results;
    }
}
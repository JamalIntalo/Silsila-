package com.silsila.dataaccess;

import com.silsila.domain.Tafsir;
import java.util.ArrayList;

// Finds tafsir entries in the database.
public class TafsirDAO {

    private Database database;

    public TafsirDAO(Database database) {
        this.database = database;
    }

    // Returns the tafsir entries that explain one verse.
    public ArrayList<Tafsir> findByAyah(int ayahId) {
        ArrayList<Tafsir> results = new ArrayList<Tafsir>();
        for (Tafsir t : database.tafsirs) {
            if (t.getAyahId() == ayahId) {
                results.add(t);
            }
        }
        return results;
    }
}
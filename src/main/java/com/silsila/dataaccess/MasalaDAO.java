package com.silsila.dataaccess;

import com.silsila.domain.Masala;
import java.util.ArrayList;

// DAO = Data Access Object.
// This class is the only place that knows how to find issues in the database.
// The layers above never touch the database directly; they ask this class.
public class MasalaDAO {

    private Database database;

    // We receive the database when this object is created.
    public MasalaDAO(Database database) {
        this.database = database;
    }

    // Returns every issue.
    public ArrayList<Masala> findAll() {
        return database.masalas;
    }

    // Returns the issue with this id, or null if there is none.
    public Masala findById(int id) {
        for (Masala m : database.masalas) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    // Returns the issues of one category ("NIKAH" or "TALAQ").
    public ArrayList<Masala> findByCategory(String category) {
        ArrayList<Masala> results = new ArrayList<Masala>();
        for (Masala m : database.masalas) {
            if (m.getCategory().equals(category)) {
                results.add(m);
            }
        }
        return results;
    }

    // Search: returns the issues whose title or keywords contain ALL the words typed.
    public ArrayList<Masala> searchByKeyword(String keyword) {
        ArrayList<Masala> results = new ArrayList<Masala>();

        // Make everything lowercase so "Wali" and "wali" match, then cut into words.
        String[] words = keyword.toLowerCase().trim().split(" ");

        for (Masala m : database.masalas) {
            // The text we search in: the title and the keywords together.
            String text = (m.getTitle() + " " + m.getKeywords()).toLowerCase();

            boolean allWordsFound = true;
            for (String word : words) {
                if (!text.contains(word)) {
                    allWordsFound = false;
                }
            }

            if (allWordsFound) {
                results.add(m);
            }
        }
        return results;
    }
}
package com.silsila.application;

import com.silsila.dataaccess.ContentDAO;
import com.silsila.dataaccess.MasalaDAO;
import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.MadhabPosition;
import com.silsila.domain.Masala;
import com.silsila.domain.Qanun;
import com.silsila.domain.Tafsir;
import java.util.ArrayList;

// The service holds the logic of the system.
public class MasalaService {

    private MasalaDAO masalaDAO;
    private ContentDAO contentDAO;

    public MasalaService(MasalaDAO masalaDAO, ContentDAO contentDAO) {
        this.masalaDAO = masalaDAO;
        this.contentDAO = contentDAO;
    }

    public ArrayList<Masala> getAllMasalas() {
        return masalaDAO.findAll();
    }

    // Search (requirement FR-3). An empty search returns every issue.
    public ArrayList<Masala> searchByKeyword(String keyword) {
        if (keyword == null) {
            keyword = "";
        }
        return masalaDAO.searchByKeyword(keyword);
    }

    // Browsing (requirement FR-4): Book > Chapter > Issue.
    // Returns the names of the chapters of one book, without repeating a chapter.
    public ArrayList<String> getChapters(String category) {
        ArrayList<String> chapters = new ArrayList<String>();
        for (Masala m : masalaDAO.findByCategory(category)) {
            if (!chapters.contains(m.getChapter())) {
                chapters.add(m.getChapter());
            }
        }
        return chapters;
    }

    // Returns the issues of one chapter of one book.
    public ArrayList<Masala> getMasalasInChapter(String category, String chapter) {
        ArrayList<Masala> results = new ArrayList<Masala>();
        for (Masala m : masalaDAO.findByCategory(category)) {
            if (m.getChapter().equals(chapter)) {
                results.add(m);
            }
        }
        return results;
    }

    // Builds the whole chain of one issue (requirements FR-1, FR-2 and FR-5).
    // Returns null if there is no issue with this id.
    public MasalaChain getChain(int masalaId) {
        Masala masala = masalaDAO.findById(masalaId);
        if (masala == null) {
            return null;
        }

        // Only approved links reach this point, because the DAO filters them.
        ArrayList<Ayah> ayat = contentDAO.findApprovedAyatByMasala(masalaId);

        // Collect the tafsir of every verse of this issue.
        ArrayList<Tafsir> tafsirs = new ArrayList<Tafsir>();
        for (Ayah a : ayat) {
            tafsirs.addAll(contentDAO.findTafsirsByAyah(a.getId()));
        }

        ArrayList<Hadith> hadiths = contentDAO.findApprovedHadithsByMasala(masalaId);
        ArrayList<MadhabPosition> positions = contentDAO.findPositionsByMasala(masalaId);
        ArrayList<Qanun> qanuns = contentDAO.findApprovedQanunsByMasala(masalaId);

        return new MasalaChain(masala, ayat, tafsirs, hadiths, positions, qanuns);
    }
}
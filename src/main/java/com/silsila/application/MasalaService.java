package com.silsila.application;

import com.silsila.dataaccess.AyahDAO;
import com.silsila.dataaccess.HadithDAO;
import com.silsila.dataaccess.MadhabPositionDAO;
import com.silsila.dataaccess.MasalaDAO;
import com.silsila.dataaccess.QanunDAO;
import com.silsila.dataaccess.TafsirDAO;
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
    private AyahDAO ayahDAO;
    private TafsirDAO tafsirDAO;
    private HadithDAO hadithDAO;
    private QanunDAO qanunDAO;
    private MadhabPositionDAO positionDAO;

    public MasalaService(MasalaDAO masalaDAO, AyahDAO ayahDAO, TafsirDAO tafsirDAO,
                         HadithDAO hadithDAO, QanunDAO qanunDAO, MadhabPositionDAO positionDAO) {
        this.masalaDAO = masalaDAO;
        this.ayahDAO = ayahDAO;
        this.tafsirDAO = tafsirDAO;
        this.hadithDAO = hadithDAO;
        this.qanunDAO = qanunDAO;
        this.positionDAO = positionDAO;
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

        // Only approved links reach this point, because the DAOs filter them.
        ArrayList<Ayah> ayat = ayahDAO.findApprovedByMasala(masalaId);

        // Collect the tafsir of every verse of this issue.
        ArrayList<Tafsir> tafsirs = new ArrayList<Tafsir>();
        for (Ayah a : ayat) {
            tafsirs.addAll(tafsirDAO.findByAyah(a.getId()));
        }

        ArrayList<Hadith> hadiths = hadithDAO.findApprovedByMasala(masalaId);
        ArrayList<MadhabPosition> positions = positionDAO.findByMasala(masalaId);
        ArrayList<Qanun> qanuns = qanunDAO.findApprovedByMasala(masalaId);

        return new MasalaChain(masala, ayat, tafsirs, hadiths, positions, qanuns);
    }
}
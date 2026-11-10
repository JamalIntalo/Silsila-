package com.silsila.dataaccess;

import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.Link;
import com.silsila.domain.MadhabPosition;
import com.silsila.domain.Qanun;
import com.silsila.domain.Tafsir;
import java.util.ArrayList;

// This DAO finds the CONTENT of an issue: verses, tafsir, hadith, laws and madhab positions.
// (The issues themselves are found by MasalaDAO, the links by LinkDAO, the users by UserDAO.)
//
// The "approved" methods give only the items whose link has the status APPROVED.
// A link that is pending or rejected must never reach the students.
public class ContentDAO {

    private Database database;

    public ContentDAO(Database database) {
        this.database = database;
    }

    // ---------------------------------------------------------------
    // Verses
    // ---------------------------------------------------------------
    public ArrayList<Ayah> findAllAyat() {
        return database.ayat;
    }

    // Returns the verse with this id, or null if there is none.
    public Ayah findAyahById(int id) {
        for (Ayah a : database.ayat) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    // Returns the verses linked to this issue through approved links.
    public ArrayList<Ayah> findApprovedAyatByMasala(int masalaId) {
        ArrayList<Ayah> results = new ArrayList<Ayah>();
        for (Link link : approvedLinks(masalaId, "AYAH")) {
            Ayah ayah = findAyahById(link.getTargetId());
            if (ayah != null) {
                results.add(ayah);
            }
        }
        return results;
    }

    // ---------------------------------------------------------------
    // Tafsir
    // ---------------------------------------------------------------
    // Returns the tafsir entries that explain one verse.
    public ArrayList<Tafsir> findTafsirsByAyah(int ayahId) {
        ArrayList<Tafsir> results = new ArrayList<Tafsir>();
        for (Tafsir t : database.tafsirs) {
            if (t.getAyahId() == ayahId) {
                results.add(t);
            }
        }
        return results;
    }

    // ---------------------------------------------------------------
    // Hadith
    // ---------------------------------------------------------------
    public ArrayList<Hadith> findAllHadiths() {
        return database.hadiths;
    }

    // Returns the hadith with this id, or null if there is none.
    public Hadith findHadithById(int id) {
        for (Hadith h : database.hadiths) {
            if (h.getId() == id) {
                return h;
            }
        }
        return null;
    }

    // Returns the hadith linked to this issue through approved links.
    public ArrayList<Hadith> findApprovedHadithsByMasala(int masalaId) {
        ArrayList<Hadith> results = new ArrayList<Hadith>();
        for (Link link : approvedLinks(masalaId, "HADITH")) {
            Hadith hadith = findHadithById(link.getTargetId());
            if (hadith != null) {
                results.add(hadith);
            }
        }
        return results;
    }

    // ---------------------------------------------------------------
    // Laws
    // ---------------------------------------------------------------
    public ArrayList<Qanun> findAllQanuns() {
        return database.qanuns;
    }

    // Returns the law with this id, or null if there is none.
    public Qanun findQanunById(int id) {
        for (Qanun q : database.qanuns) {
            if (q.getId() == id) {
                return q;
            }
        }
        return null;
    }

    // Returns the laws linked to this issue through approved links.
    public ArrayList<Qanun> findApprovedQanunsByMasala(int masalaId) {
        ArrayList<Qanun> results = new ArrayList<Qanun>();
        for (Link link : approvedLinks(masalaId, "QANUN")) {
            Qanun qanun = findQanunById(link.getTargetId());
            if (qanun != null) {
                results.add(qanun);
            }
        }
        return results;
    }

    // ---------------------------------------------------------------
    // Madhab positions
    // ---------------------------------------------------------------
    // Returns the positions of the madhabs on one issue.
    public ArrayList<MadhabPosition> findPositionsByMasala(int masalaId) {
        ArrayList<MadhabPosition> results = new ArrayList<MadhabPosition>();
        for (MadhabPosition p : database.positions) {
            if (p.getMasalaId() == masalaId) {
                results.add(p);
            }
        }
        return results;
    }

    // ---------------------------------------------------------------
    // Helper (private: only this class uses it)
    // ---------------------------------------------------------------
    // The approved links of one issue and one type ("AYAH", "HADITH" or "QANUN").
    private ArrayList<Link> approvedLinks(int masalaId, String type) {
        ArrayList<Link> results = new ArrayList<Link>();
        for (Link link : database.links) {
            if (link.getMasalaId() == masalaId
                    && link.getType().equals(type)
                    && link.isApproved()) {
                results.add(link);
            }
        }
        return results;
    }
}
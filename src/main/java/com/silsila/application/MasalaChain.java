package com.silsila.application;

import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.MadhabPosition;
import com.silsila.domain.Masala;
import com.silsila.domain.Qanun;
import com.silsila.domain.Tafsir;
import java.util.ArrayList;

// The whole chain of one issue, collected in one object:
// the issue, its verses, the tafsir of those verses, its hadith,
// the positions of the madhabs, and the laws.
// The service builds it and the page just shows it.
public class MasalaChain {

    private Masala masala;
    private ArrayList<Ayah> ayat;
    private ArrayList<Tafsir> tafsirs;
    private ArrayList<Hadith> hadiths;
    private ArrayList<MadhabPosition> positions;
    private ArrayList<Qanun> qanuns;

    public MasalaChain(Masala masala, ArrayList<Ayah> ayat, ArrayList<Tafsir> tafsirs,
                       ArrayList<Hadith> hadiths, ArrayList<MadhabPosition> positions,
                       ArrayList<Qanun> qanuns) {
        this.masala = masala;
        this.ayat = ayat;
        this.tafsirs = tafsirs;
        this.hadiths = hadiths;
        this.positions = positions;
        this.qanuns = qanuns;
    }

    public Masala getMasala() {
        return masala;
    }

    public ArrayList<Ayah> getAyat() {
        return ayat;
    }

    public ArrayList<Tafsir> getTafsirs() {
        return tafsirs;
    }

    public ArrayList<Hadith> getHadiths() {
        return hadiths;
    }

    public ArrayList<MadhabPosition> getPositions() {
        return positions;
    }

    public ArrayList<Qanun> getQanuns() {
        return qanuns;
    }

    // Returns only the tafsir entries that explain one particular verse.
    public ArrayList<Tafsir> getTafsirsOf(int ayahId) {
        ArrayList<Tafsir> results = new ArrayList<Tafsir>();
        for (Tafsir t : tafsirs) {
            if (t.getAyahId() == ayahId) {
                results.add(t);
            }
        }
        return results;
    }
}
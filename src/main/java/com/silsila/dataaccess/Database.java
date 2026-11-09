package com.silsila.dataaccess;

import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.Link;
import com.silsila.domain.MadhabPosition;
import com.silsila.domain.Masala;
import com.silsila.domain.Qanun;
import com.silsila.domain.Tafsir;
import java.util.ArrayList;
import com.silsila.domain.User;

// This is our "database" for now: just lists kept in memory.
// The data is created when the program starts and disappears when it stops.
//
// IMPORTANT: this is DRAFT data. The madhab positions and the book references must be
// checked against the real books by your supervisor before the project is presented as correct.
// The texts of the verses, tafsir and hadith are NOT typed here: Step 5 loads them from websites.
public class Database {

    public ArrayList<Masala> masalas = new ArrayList<Masala>();
    public ArrayList<Ayah> ayat = new ArrayList<Ayah>();
    public ArrayList<Tafsir> tafsirs = new ArrayList<Tafsir>();
    public ArrayList<Hadith> hadiths = new ArrayList<Hadith>();
    public ArrayList<Qanun> qanuns = new ArrayList<Qanun>();
    public ArrayList<MadhabPosition> positions = new ArrayList<MadhabPosition>();
    public ArrayList<Link> links = new ArrayList<Link>();
    public ArrayList<User> users = new ArrayList<User>();
    
    public Database() {
        addMasalas();
        addAyat();
        addTafsirs();
        addHadiths();
        addQanuns();
        addPositions();
        addLinks();
    }

    // ---------------------------------------------------------------
    // The issues
    // ---------------------------------------------------------------
    private void addMasalas() {
        masalas.add(new Masala(1,
                "Is a wali (guardian) required for the marriage contract?",
                "NIKAH", "Conditions of a valid contract",
                "Whether a woman can conclude her own marriage contract, or whether her wali must do it.",
                "wali guardian contract validity"));

        masalas.add(new Masala(2,
                "Are witnesses required at the marriage contract?",
                "NIKAH", "Conditions of a valid contract",
                "Whether witnesses must be present when the contract is made, or whether announcing the marriage is enough.",
                "witnesses announcement contract"));

        masalas.add(new Masala(3,
                "Is there a minimum mahr (dowry)?",
                "NIKAH", "Mahr (dowry)",
                "Whether the law sets a minimum value for the mahr.",
                "mahr dowry minimum"));

        masalas.add(new Masala(4,
                "Three talaqs pronounced in one sitting",
                "TALAQ", "Types and procedure of talaq",
                "Whether saying 'you are divorced three times' in one sitting counts as one talaq or three.",
                "triple talaq three divorce one sitting"));

        masalas.add(new Masala(5,
                "Talaq during menstruation",
                "TALAQ", "Types and procedure of talaq",
                "Whether a divorce pronounced while the wife is menstruating takes effect.",
                "talaq menstruation haid innovation"));

        masalas.add(new Masala(6,
                "The meaning of quru in the iddah of a divorced woman",
                "TALAQ", "Iddah (waiting period)",
                "Whether the three quru of the iddah are three menstrual periods or three periods of purity.",
                "iddah quru menstruation purity waiting period"));
    }

    // ---------------------------------------------------------------
    // Verses: only the place of each verse. The Arabic text comes in Step 5.
    // ---------------------------------------------------------------
    private void addAyat() {
        String later = "(The Arabic text will be loaded from the Qur'an website in Step 5.)";
        ayat.add(new Ayah(1, 2, "Al-Baqarah", 232, later));
        ayat.add(new Ayah(2, 4, "An-Nisa", 4, later));
        ayat.add(new Ayah(3, 2, "Al-Baqarah", 229, later));
        ayat.add(new Ayah(4, 2, "Al-Baqarah", 228, later));
        ayat.add(new Ayah(5, 65, "At-Talaq", 1, later));
    }

    // ---------------------------------------------------------------
    // Tafsir: which book explains which verse. The text comes in Step 5.
    // ---------------------------------------------------------------
    private void addTafsirs() {
        String later = "(The tafsir text will be loaded from the tafsir website in Step 5.)";
        String name = "Tafsir Ibn Kathir (English)";
        String edition = "en-tafisr-ibn-kathir";
        tafsirs.add(new Tafsir(1, 1, name, edition, later));
        tafsirs.add(new Tafsir(2, 2, name, edition, later));
        tafsirs.add(new Tafsir(3, 3, name, edition, later));
        tafsirs.add(new Tafsir(4, 4, name, edition, later));
        tafsirs.add(new Tafsir(5, 5, name, edition, later));
    }

    // ---------------------------------------------------------------
    // Hadith: only the book and the number. Text and grade come in Step 5.
    // ---------------------------------------------------------------
    private void addHadiths() {
        String later = "(The hadith text will be loaded from the hadith website in Step 5.)";
        // Hadith(id, website book name, name shown, number shown, number on the website, text, grade)
        // For Sahih Muslim the usual citation is 1472, but the website numbers that
        // hadith 3673, so we keep both numbers.
        hadiths.add(new Hadith(1, "abudawud", "Sunan Abi Dawud", 2085, 2085, later, ""));
        hadiths.add(new Hadith(2, "bukhari", "Sahih al-Bukhari", 5087, 5087, later, ""));
        hadiths.add(new Hadith(3, "muslim", "Sahih Muslim", 1472, 3673, later, ""));
    }

    // ---------------------------------------------------------------
    // Laws
    // ---------------------------------------------------------------
    private void addQanuns() {
        qanuns.add(new Qanun(1, "Egyptian Law No. 25 of 1929", "3",
                "A talaq accompanied by a number, in words or by gesture, counts as only one talaq."));
    }

    // ---------------------------------------------------------------
    // Madhab positions (only issues 1, 3 and 4 so far; the others can be added
    // later, with sources, together with your supervisor).
    // ---------------------------------------------------------------
    private void addPositions() {
        // Issue 1: Is a wali required?
        positions.add(new MadhabPosition(1, 1, "Hanafi",
                "Not a condition for an adult, sane woman: she may conclude her own marriage. The wali may object if the husband is not her equal (kafa'a).",
                "Verses that attribute the marriage to the women themselves (2:232 and 2:230).",
                "A mature woman has the legal capacity to conclude her own contracts.",
                "al-Marghinani, al-Hidaya, Kitab al-Nikah (add page)"));
        positions.add(new MadhabPosition(2, 1, "Maliki",
                "The wali is a condition for the validity of the contract; without one the marriage is invalid.",
                "Hadith: no marriage without a wali.",
                "Protecting the woman's interests and her family's rights.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Nikah (add page)"));
        positions.add(new MadhabPosition(3, 1, "Shafi'i",
                "The wali is a condition for the validity of the contract.",
                "Hadith: no marriage without a wali; and verse 2:232, which forbids guardians from preventing a marriage.",
                "The verse speaks to the guardians, which shows they have a role in the contract.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Nikah (add page)"));
        positions.add(new MadhabPosition(4, 1, "Hanbali",
                "The wali is a condition for the validity of the contract (the main view of the school).",
                "Hadith: no marriage without a wali.",
                "Protecting the woman's interests and her family's rights.",
                "Ibn Qudama, al-Mughni, Kitab al-Nikah (add page)"));

        // Issue 3: Is there a minimum mahr?
        positions.add(new MadhabPosition(5, 3, "Hanafi",
                "A minimum of ten dirhams (silver).",
                "Reports that fix a minimum of ten dirhams (scholars discuss their chains).",
                "The mahr honours the contract, so it needs a significant minimum value.",
                "al-Marghinani, al-Hidaya, Kitab al-Nikah (add page)"));
        positions.add(new MadhabPosition(6, 3, "Maliki",
                "A minimum of a quarter dinar of gold (or three dirhams of silver).",
                "Analogy with the minimum value for the punishment of theft.",
                "The mahr makes the marriage lawful, so it is compared with the threshold of theft.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Nikah (add page)"));
        positions.add(new MadhabPosition(7, 3, "Shafi'i",
                "No fixed minimum: anything that has value can be a mahr.",
                "Hadith: 'Find something, even an iron ring.'",
                "The Prophet accepted an iron ring, so no minimum is set.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Nikah (add page)"));
        positions.add(new MadhabPosition(8, 3, "Hanbali",
                "No fixed minimum: anything that has value can be a mahr.",
                "Hadith: 'Find something, even an iron ring.'",
                "The Prophet accepted an iron ring, so no minimum is set.",
                "Ibn Qudama, al-Mughni, Kitab al-Nikah (add page)"));

        // Issue 4: Three talaqs in one sitting
        positions.add(new MadhabPosition(9, 4, "Hanafi",
                "The three talaqs take effect, although pronouncing them together is a prohibited innovation.",
                "The decision of 'Umar, accepted by the Companions.",
                "Closing the door on the misuse of talaq.",
                "al-Marghinani, al-Hidaya, Kitab al-Talaq (add page)"));
        positions.add(new MadhabPosition(10, 4, "Maliki",
                "The three talaqs take effect.",
                "The decision of 'Umar, accepted by the Companions.",
                "Closing the door on the misuse of talaq.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Talaq (add page)"));
        positions.add(new MadhabPosition(11, 4, "Shafi'i",
                "The three talaqs take effect.",
                "The decision of 'Umar, accepted by the Companions.",
                "The husband used the right of talaq he owns, so it counts as he pronounced it.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Talaq (add page)"));
        positions.add(new MadhabPosition(12, 4, "Hanbali",
                "The three talaqs take effect in the main view; Ibn Taymiyyah and Ibn al-Qayyim held that it counts as one talaq.",
                "'Umar's decision (main view); the hadith of Ibn 'Abbas in Sahih Muslim (the one-talaq view).",
                "Closing the door on misuse (main view), versus following the practice of the Prophet's time.",
                "Ibn Qudama, al-Mughni, Kitab al-Talaq (add page)"));
    }

    // ---------------------------------------------------------------
    // Links between issues and their evidence. All are APPROVED for now.
    // Link(id, issue, type, target, citation, status)
    // ---------------------------------------------------------------
    private void addLinks() {
        links.add(new Link(1, 1, "AYAH", 1, "Qur'an 2:232", "APPROVED"));
        links.add(new Link(2, 1, "HADITH", 1, "Sunan Abi Dawud 2085", "APPROVED"));

        links.add(new Link(3, 3, "AYAH", 2, "Qur'an 4:4", "APPROVED"));
        links.add(new Link(4, 3, "HADITH", 2, "Sahih al-Bukhari 5087", "APPROVED"));

        links.add(new Link(5, 4, "AYAH", 3, "Qur'an 2:229", "APPROVED"));
        links.add(new Link(6, 4, "HADITH", 3, "Sahih Muslim 1472", "APPROVED"));
        links.add(new Link(7, 4, "QANUN", 1, "Egyptian Law No. 25 of 1929, Art. 3", "APPROVED"));

        links.add(new Link(8, 5, "AYAH", 5, "Qur'an 65:1", "APPROVED"));
        links.add(new Link(9, 6, "AYAH", 4, "Qur'an 2:228", "APPROVED"));
    }
}
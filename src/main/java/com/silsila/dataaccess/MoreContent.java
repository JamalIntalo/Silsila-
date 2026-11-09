package com.silsila.dataaccess;

import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.Link;
import com.silsila.domain.MadhabPosition;
import com.silsila.domain.Masala;
import com.silsila.domain.Qanun;
import com.silsila.domain.Tafsir;

// The second batch of content: ten more issues, and the madhab positions
// for the issues 2, 5 and 6 that had none.
//
// IMPORTANT: this is DRAFT data, written from memory. Every ruling, evidence, reasoning,
// book reference and law article below must be checked against the original books and laws
// by the supervisor or a reviewer before the project is presented as correct.
// Where the schools hold more than one view, the text says so.
//
// The texts of the verses, tafsir and hadith are NOT typed here: RemoteLoader loads them
// from the websites. Here we only say WHICH verse or hadith it is.
public class MoreContent {

    // Called from the constructor of Database.
    public static void addTo(Database db) {
        addMasalas(db);
        addAyat(db);
        addTafsirs(db);
        addHadiths(db);
        addQanuns(db);
        addPositionsOfOldIssues(db);
        addPositionsOfNewIssues(db);
        addLinks(db);
    }

    // ---------------------------------------------------------------
    // Small helpers, so that we do not have to count the ids by hand
    // ---------------------------------------------------------------
    private static void position(Database db, int masalaId, String madhab, String ruling,
                                 String evidence, String illah, String citation) {
        int id = db.positions.size() + 1;
        db.positions.add(new MadhabPosition(id, masalaId, madhab, ruling, evidence, illah, citation));
    }

    private static void link(Database db, int masalaId, String type, int targetId, String citation) {
        int id = db.links.size() + 1;
        db.links.add(new Link(id, masalaId, type, targetId, citation, "APPROVED"));
    }

    // ---------------------------------------------------------------
    // Ten new issues (ids 7 to 16)
    // ---------------------------------------------------------------
    private static void addMasalas(Database db) {
        db.masalas.add(new Masala(7,
                "Can a father marry his adult virgin daughter without her consent?",
                "NIKAH", "Conditions of a valid contract",
                "Whether the father may compel a mature virgin daughter to marry (ijbar), or whether her own consent is required.",
                "consent virgin father ijbar compel daughter permission"));

        db.masalas.add(new Masala(8,
                "Is a marriage contract made during ihram valid?",
                "NIKAH", "Conditions of a valid contract",
                "Whether a person in the state of ihram for hajj or umrah can conclude a marriage contract.",
                "ihram pilgrimage hajj umrah muhrim contract"));

        db.masalas.add(new Masala(9,
                "Is a marriage for a fixed period (mut'a) valid?",
                "NIKAH", "Invalid and prohibited marriages",
                "Whether a marriage contract limited to a fixed time is valid.",
                "mutah mut'a temporary fixed period time limited"));

        db.masalas.add(new Masala(10,
                "Marriage arranged to make a thrice-divorced woman lawful again (tahlil)",
                "NIKAH", "Invalid and prohibited marriages",
                "Whether a marriage made only to make a woman lawful for her first husband is valid, and whether it achieves that aim.",
                "tahlil muhallil thrice divorced lawful first husband"));

        db.masalas.add(new Masala(11,
                "May a Muslim man marry a Christian or Jewish woman?",
                "NIKAH", "Invalid and prohibited marriages",
                "Whether a marriage with a woman of the People of the Book (kitabiyya) is permitted, and under what conditions.",
                "kitabiyya christian jewish people of the book non muslim"));

        db.masalas.add(new Masala(12,
                "Does a talaq pronounced under compulsion take effect?",
                "TALAQ", "Types and procedure of talaq",
                "Whether a divorce that a man was forced to pronounce counts as a divorce.",
                "compulsion coerced forced duress ikrah"));

        db.masalas.add(new Masala(13,
                "Khul': is it a talaq or an annulment?",
                "TALAQ", "Khul' (divorce at the wife's request)",
                "Whether a khul' (the wife pays to be released) counts as one of the husband's three talaqs, or as an annulment (faskh).",
                "khul khula wife request mahr return annulment faskh"));

        db.masalas.add(new Masala(14,
                "Maintenance and housing of an irrevocably divorced woman during her iddah",
                "TALAQ", "Iddah (waiting period)",
                "Whether a woman whose divorce is final (she cannot be taken back) is entitled to maintenance and housing during her iddah.",
                "maintenance housing iddah nafaqa irrevocable final divorce"));

        db.masalas.add(new Masala(15,
                "How does a husband take back his wife (raj'a)?",
                "TALAQ", "Revocation (raj'a)",
                "Whether the husband must use words to take his wife back, or whether acts such as intercourse also count, and whether witnesses are needed.",
                "rajah raja revocation take back wife words intercourse witnesses"));

        db.masalas.add(new Masala(16,
                "The consolation gift (mut'a) for a divorced woman",
                "TALAQ", "Financial rights after talaq",
                "Whether the husband must give a divorced woman a gift (mut'a), and for which women it is obligatory.",
                "mutah mut'a gift consolation provision divorced"));
    }

    // ---------------------------------------------------------------
    // New verses (ids 6 to 15): only the place of each verse
    // ---------------------------------------------------------------
    private static void addAyat(Database db) {
        String later = "(The Arabic text will be loaded from the Qur'an website.)";
        db.ayat.add(new Ayah(6, 4, "An-Nisa", 24, later));
        db.ayat.add(new Ayah(7, 2, "Al-Baqarah", 230, later));
        db.ayat.add(new Ayah(8, 5, "Al-Ma'idah", 5, later));
        db.ayat.add(new Ayah(9, 2, "Al-Baqarah", 221, later));
        db.ayat.add(new Ayah(10, 65, "At-Talaq", 6, later));
        db.ayat.add(new Ayah(11, 65, "At-Talaq", 2, later));
        db.ayat.add(new Ayah(12, 2, "Al-Baqarah", 236, later));
        db.ayat.add(new Ayah(13, 2, "Al-Baqarah", 241, later));
        db.ayat.add(new Ayah(14, 33, "Al-Ahzab", 49, later));
        db.ayat.add(new Ayah(15, 2, "Al-Baqarah", 231, later));
    }

    // One tafsir entry (Ibn Kathir, English) for each new verse.
    private static void addTafsirs(Database db) {
        String later = "(The tafsir text will be loaded from the tafsir website.)";
        String name = "Tafsir Ibn Kathir (English)";
        String edition = "en-tafisr-ibn-kathir";
        for (int ayahId = 6; ayahId <= 15; ayahId++) {
            int id = db.tafsirs.size() + 1;
            db.tafsirs.add(new Tafsir(id, ayahId, name, edition, later));
        }
    }

    // ---------------------------------------------------------------
    // New hadith (ids 4 to 17).
    // Hadith(id, website book name, name shown, number shown, number on the website, text, grade)
    // The website numbers the books of Muslim differently, so for Sahih Muslim we keep two numbers.
    // ---------------------------------------------------------------
    private static void addHadiths(Database db) {
        String later = "(The hadith text will be loaded from the hadith website.)";
        db.hadiths.add(new Hadith(4, "bukhari", "Sahih al-Bukhari", 5136, 5136, later, ""));
        db.hadiths.add(new Hadith(5, "muslim", "Sahih Muslim", 1421, 3476, later, ""));
        db.hadiths.add(new Hadith(6, "bukhari", "Sahih al-Bukhari", 1837, 1837, later, ""));
        db.hadiths.add(new Hadith(7, "muslim", "Sahih Muslim", 1409, 3448, later, ""));
        db.hadiths.add(new Hadith(8, "bukhari", "Sahih al-Bukhari", 5115, 5115, later, ""));
        db.hadiths.add(new Hadith(9, "muslim", "Sahih Muslim", 1406, 3422, later, ""));
        db.hadiths.add(new Hadith(10, "abudawud", "Sunan Abi Dawud", 2076, 2076, later, ""));
        db.hadiths.add(new Hadith(11, "bukhari", "Sahih al-Bukhari", 5260, 5260, later, ""));
        db.hadiths.add(new Hadith(12, "abudawud", "Sunan Abi Dawud", 2193, 2193, later, ""));
        db.hadiths.add(new Hadith(13, "abudawud", "Sunan Abi Dawud", 2194, 2194, later, ""));
        db.hadiths.add(new Hadith(14, "ibnmajah", "Sunan Ibn Majah", 2043, 2043, later, ""));
        db.hadiths.add(new Hadith(15, "bukhari", "Sahih al-Bukhari", 5273, 5273, later, ""));
        db.hadiths.add(new Hadith(16, "muslim", "Sahih Muslim", 1480, 3708, later, ""));
        db.hadiths.add(new Hadith(17, "muslim", "Sahih Muslim", 1471, 3652, later, ""));
    }

    // ---------------------------------------------------------------
    // New laws (ids 2 and 3). DRAFT: check the article numbers and wording in the official text.
    // ---------------------------------------------------------------
    private static void addQanuns(Database db) {
        db.qanuns.add(new Qanun(2, "Egyptian Law No. 25 of 1929", "1",
                "A talaq pronounced by a drunk person or by a person under compulsion does not take effect."));
        db.qanuns.add(new Qanun(3, "Egyptian Law No. 1 of 2000", "20",
                "If the spouses do not agree on khul', the wife may ask the court for it by giving up her financial rights and returning the mahr; the court then divorces them, and the divorce is final (ba'in)."));
    }

    // ---------------------------------------------------------------
    // Positions for the issues that had none: 2 (witnesses), 5 and 6
    // ---------------------------------------------------------------
    private static void addPositionsOfOldIssues(Database db) {
        // Issue 2: Are witnesses required at the contract?
        position(db, 2, "Hanafi",
                "Two witnesses are a condition for the validity of the contract: two men, or one man and two women. Their justice ('adala) is not required.",
                "The report 'no marriage without a wali and two just witnesses' (scholars discuss its chain), and the need to prove the marriage because of lineage and inheritance.",
                "Marriage creates rights of lineage and inheritance, so it must be publicly witnessed and provable.",
                "al-Marghinani, al-Hidaya, Kitab al-Nikah (add page)");
        position(db, 2, "Maliki",
                "Witnesses at the moment of the contract are not a condition, but the marriage must be announced (i'lan) and witnesses must attend before consummation. If the spouses agree to keep it secret, the marriage is invalid.",
                "The command to announce marriage; and the report 'no marriage without a wali and two just witnesses'.",
                "What matters is publicity: announcement achieves the purpose that witnesses serve.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Nikah (add page)");
        position(db, 2, "Shafi'i",
                "Two just (adl) male witnesses are a condition for the validity of the contract. The testimony of women is not accepted for it.",
                "The report 'no marriage except with a wali and two just witnesses'.",
                "Protecting lineage and preventing denial of the marriage; the witnesses must be fit to give testimony.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Nikah (add page)");
        position(db, 2, "Hanbali",
                "Two just male Muslim witnesses are a condition (the main view of the school). Another narration says that announcing the marriage is enough.",
                "The report 'no marriage except with a wali and two just witnesses'.",
                "Protecting lineage and preventing denial of the marriage.",
                "Ibn Qudama, al-Mughni, Kitab al-Nikah (add page)");

        // Issue 5: Talaq during menstruation
        position(db, 5, "Hanafi",
                "It takes effect, but it is a prohibited innovation (bid'i). The husband should take her back.",
                "The hadith of Ibn 'Umar: the Prophet commanded him to take his wife back after he divorced her during her menses.",
                "The prohibition concerns the manner of the divorce (the wrong time), not the divorce itself, so the effect remains.",
                "al-Marghinani, al-Hidaya, Kitab al-Talaq (add page)");
        position(db, 5, "Maliki",
                "It takes effect, but it is forbidden. The husband is forced to take her back, and may divorce her later in a period of purity if he wishes.",
                "The hadith of Ibn 'Umar (Bukhari and Muslim) and verse 65:1.",
                "Taking her back corrects the wrong that was done; the divorce is counted because Ibn 'Umar's divorce was counted.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Talaq (add page)");
        position(db, 5, "Shafi'i",
                "It takes effect, but it is forbidden. It is recommended (mustahabb) that the husband take her back.",
                "The hadith of Ibn 'Umar; Ibn 'Umar said that his divorce was counted as one talaq.",
                "The command to take her back is a recommendation to repair the wrong, not a condition for the divorce.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Talaq (add page)");
        position(db, 5, "Hanbali",
                "It takes effect, and the husband is commanded to take her back. Scholars of the school differ on whether this is obligatory or recommended. Ibn Taymiyya and Ibn al-Qayyim held that it does not take effect.",
                "The hadith of Ibn 'Umar; verse 65:1; for the minority view, that the divorce is not the one the Prophet allowed.",
                "Majority view: wrong time, but the divorce is counted. Minority view: a divorce outside the Prophet's way is not valid.",
                "Ibn Qudama, al-Mughni, Kitab al-Talaq (add page)");

        // Issue 6: The meaning of quru' in the iddah
        position(db, 6, "Hanafi",
                "The quru' are menstrual periods (hayd). The iddah ends when the third period is finished.",
                "Verse 2:228; the hadith telling a woman with irregular bleeding to leave prayer 'during the days of your aqra''.",
                "The aim of the iddah is to show that the womb is empty, and menstruation is the sign of that.",
                "al-Marghinani, al-Hidaya, Kitab al-Talaq (add page)");
        position(db, 6, "Maliki",
                "The quru' are periods of purity (tuhr) between menstruations.",
                "Verse 65:1 ('divorce them for their iddah') means divorce in purity, so the iddah is counted in periods of purity.",
                "The Qur'an links the divorce to the beginning of the iddah, and divorce takes place in purity.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Talaq (add page)");
        position(db, 6, "Shafi'i",
                "The quru' are periods of purity (tuhr). The iddah ends when the third menstruation begins.",
                "Verse 65:1 and the hadith of Ibn 'Umar: divorce is in purity, which starts the iddah.",
                "Same reasoning as the Maliki school: the divorce comes in purity, and the iddah follows it.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-'Idad (add page)");
        position(db, 6, "Hanbali",
                "The quru' are menstrual periods (hayd) (the main view). Another narration says periods of purity.",
                "Verse 2:228 and the hadith about leaving prayer 'during the days of your aqra''.",
                "The word quru' is used in the sense of menstruation in the Prophet's speech.",
                "Ibn Qudama, al-Mughni, Kitab al-'Idad (add page)");
    }

    // ---------------------------------------------------------------
    // Positions for the new issues 7 to 16
    // ---------------------------------------------------------------
    private static void addPositionsOfNewIssues(Database db) {
        // Issue 7: Can a father compel his adult virgin daughter?
        position(db, 7, "Hanafi",
                "No. A mature woman, virgin or previously married, cannot be forced to marry. Her consent is required. (The father may marry off a minor daughter.)",
                "Hadith: a virgin must not be married until her permission is asked (Bukhari 5136; Muslim 1421).",
                "A mature woman has full legal capacity, and marriage is a lifelong contract that concerns her above all.",
                "al-Marghinani, al-Hidaya, Kitab al-Nikah, bab al-awliya' (add page)");
        position(db, 7, "Maliki",
                "Yes. The father may marry his virgin daughter without her consent (ijbar), even if she is mature. A previously married woman cannot be forced.",
                "The hadith 'the thayyib has more right over herself than her wali', which shows that the virgin does not have the same right.",
                "A virgin has no experience of marriage, and her father's compassion protects her interest.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Nikah (add page)");
        position(db, 7, "Shafi'i",
                "Yes. The father (and the grandfather) may marry a virgin daughter without her consent, but it is recommended to ask her. A previously married woman must give explicit consent.",
                "The hadith 'the thayyib has more right over herself than her wali' (Muslim 1421).",
                "The father's compassion and her lack of experience justify his right of ijbar over a virgin.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Nikah (add page)");
        position(db, 7, "Hanbali",
                "Yes in the main view of the school: the father may marry his virgin daughter without her consent. Another narration requires the consent of a mature virgin.",
                "The hadith 'the thayyib has more right over herself than her wali' (Muslim 1421) for the main view; Bukhari 5136 for the other narration.",
                "The father's compassion and protection of the daughter, versus her right over her own marriage.",
                "Ibn Qudama, al-Mughni, Kitab al-Nikah (add page)");

        // Issue 8: Marriage during ihram
        position(db, 8, "Hanafi",
                "The contract is valid, although the muhrim must not have intercourse while in ihram.",
                "Hadith of Ibn 'Abbas: the Prophet married Maymuna while in ihram (Bukhari 1837).",
                "Ihram forbids sexual acts, not the making of a contract; so the contract itself is valid.",
                "al-Marghinani, al-Hidaya, Kitab al-Hajj (add page)");
        position(db, 8, "Maliki",
                "The contract is invalid and must be annulled.",
                "Hadith of 'Uthman: a muhrim must neither marry nor be married nor propose (Muslim 1409).",
                "The prohibition in the hadith is clear; the report of Ibn 'Abbas is explained as concerning the Prophet's own case or as a mistake of memory.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Hajj (add page)");
        position(db, 8, "Shafi'i",
                "The contract is invalid.",
                "Hadith of 'Uthman (Muslim 1409).",
                "The marriage contract leads to sexual enjoyment, which is forbidden in ihram, so the contract is forbidden and void.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Hajj (add page)");
        position(db, 8, "Hanbali",
                "The contract is invalid.",
                "Hadith of 'Uthman (Muslim 1409).",
                "The prohibition in the hadith means the contract is not valid; Ibn 'Abbas's report is explained as particular to the Prophet.",
                "Ibn Qudama, al-Mughni, Kitab al-Hajj (add page)");

        // Issue 9: Marriage for a fixed period (mut'a)
        position(db, 9, "Hanafi",
                "Invalid. A marriage limited to a fixed time is not a valid marriage.",
                "Hadith of 'Ali: the Prophet forbade mut'a marriage at Khaybar (Bukhari 5115); Muslim 1406: it is forbidden until the Day of Resurrection.",
                "Marriage is meant to be lasting, to build a family and establish lineage; a fixed term contradicts that purpose.",
                "al-Marghinani, al-Hidaya, Kitab al-Nikah (add page)");
        position(db, 9, "Maliki",
                "Invalid, and it must be annulled.",
                "Bukhari 5115 and Muslim 1406 (abrogation of mut'a).",
                "The permission was removed, and the contract does not have the lasting nature of marriage.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Nikah (add page)");
        position(db, 9, "Shafi'i",
                "Invalid.",
                "Bukhari 5115 and Muslim 1406.",
                "Marriage is a lasting contract and cannot be limited by time.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Nikah (add page)");
        position(db, 9, "Hanbali",
                "Invalid.",
                "Bukhari 5115 and Muslim 1406.",
                "Marriage is a lasting contract and cannot be limited by time. (The four schools agree here; the permission of the early period was abrogated.)",
                "Ibn Qudama, al-Mughni, Kitab al-Nikah (add page)");

        // Issue 10: Tahlil marriage
        position(db, 10, "Hanafi",
                "The contract is valid but strongly disliked (makruh). If the second marriage is consummated and her iddah ends, she is lawful to the first husband.",
                "Verse 2:230 asks for a marriage with another husband; the curse in Abu Dawud 2076 shows sin, not invalidity.",
                "All the conditions of a valid contract are met; the curse refers to the sin of the intention.",
                "al-Marghinani, al-Hidaya, Kitab al-Talaq (add page)");
        position(db, 10, "Maliki",
                "The marriage is invalid and must be annulled; it does not make her lawful to the first husband.",
                "Abu Dawud 2076: the Prophet cursed the muhallil and the one for whom it was done.",
                "A curse shows the act is forbidden, and the forbidden act cannot produce the lawful result.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Talaq (add page)");
        position(db, 10, "Shafi'i",
                "If the aim (to make her lawful) is stated as a condition in the contract, the marriage is invalid. If it is only an inner intention, the contract is valid but disliked.",
                "Abu Dawud 2076; verse 2:230.",
                "A stated condition changes the contract itself; an inner intention does not, since contracts are judged by what is said.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Talaq (add page)");
        position(db, 10, "Hanbali",
                "The marriage is invalid, and it does not make her lawful to the first husband.",
                "Abu Dawud 2076; verse 2:230.",
                "It is a trick to avoid the rule of the Qur'an; the real marriage which the verse requires is not present.",
                "Ibn Qudama, al-Mughni, Kitab al-Talaq (add page)");

        // Issue 11: Marrying a Christian or Jewish woman
        position(db, 11, "Hanafi",
                "Permitted, even if the woman is not of Israelite descent. It is disliked in a land of war or non-Muslim land.",
                "Verse 5:5 permits chaste women of the People of the Book; verse 2:221 forbids marrying polytheists.",
                "The People of the Book follow a revealed scripture, which makes them different from polytheists.",
                "al-Marghinani, al-Hidaya, Kitab al-Nikah, fasl al-muharramat (add page)");
        position(db, 11, "Maliki",
                "Permitted, but disliked (makruh).",
                "Verse 5:5; verse 2:221 for the prohibition of polytheists.",
                "The verse permits it, but she may influence the husband and the children, so it is disliked.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Nikah (add page)");
        position(db, 11, "Shafi'i",
                "Permitted if her ancestors entered her religion before it was changed (in practice, women of Israelite descent for Jews and Christians) (the relied-on view).",
                "Verse 5:5, understood to mean the People of the Book that were given the scripture before Islam.",
                "The verse speaks of those who were 'given the Scripture before you', so descent from that community is considered.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Nikah (add page)");
        position(db, 11, "Hanbali",
                "Permitted. It is disliked where there is a danger to the religion of the children.",
                "Verse 5:5; verse 2:221 for the prohibition of polytheists.",
                "The verse permits it; the dislike comes from the influence on the family.",
                "Ibn Qudama, al-Mughni, Kitab al-Nikah (add page)");

        // Issue 12: Talaq under compulsion
        position(db, 12, "Hanafi",
                "It takes effect.",
                "Abu Dawud 2194: marriage, divorce and taking back are serious even in jest.",
                "The man chose, among two evils, to divorce; his words are those of a person with legal capacity, so they have effect even if he was pushed.",
                "al-Marghinani, al-Hidaya, Kitab al-Talaq, bab talaq al-sunna (add page)");
        position(db, 12, "Maliki",
                "It does not take effect.",
                "Ibn Majah 2043: 'Allah has forgiven for me my nation their mistakes, forgetfulness and what they are forced to do'; Abu Dawud 2193 (no divorce in 'ghalaq', understood as compulsion).",
                "A compelled person has no free intention to divorce.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Talaq (add page)");
        position(db, 12, "Shafi'i",
                "It does not take effect.",
                "Ibn Majah 2043; Abu Dawud 2193.",
                "The speaker did not choose the divorce, so the words do not carry legal effect.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Talaq (add page)");
        position(db, 12, "Hanbali",
                "It does not take effect.",
                "Ibn Majah 2043; Abu Dawud 2193 (many scholars explain its word 'ghalaq' as compulsion; Abu Dawud himself thought it means anger, and its grading is disputed).",
                "The speaker did not choose the divorce.",
                "Ibn Qudama, al-Mughni, Kitab al-Talaq (add page)");

        // Issue 13: Khul': talaq or annulment?
        position(db, 13, "Hanafi",
                "It is a talaq, and it is final (ba'in). It counts as one of the three talaqs.",
                "Bukhari 5273: the Prophet told the husband of Thabit's wife to accept the garden and divorce her once.",
                "The Prophet used the word 'divorce her', so khul' is a talaq.",
                "al-Marghinani, al-Hidaya, Kitab al-Talaq, bab al-khul' (add page)");
        position(db, 13, "Maliki",
                "It is a talaq, and it is final (ba'in).",
                "Bukhari 5273.",
                "Same as the Hanafi school: the hadith commands 'divorce her'.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Talaq (add page)");
        position(db, 13, "Shafi'i",
                "It is a talaq (the relied-on view), and it is final. (An older view of al-Shafi'i said it is an annulment.)",
                "Bukhari 5273.",
                "The hadith commands 'divorce her'.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Khul' (add page)");
        position(db, 13, "Hanbali",
                "It is an annulment (faskh) in the main view, so it does not reduce the number of talaqs. Another narration says it is a talaq.",
                "Verse 2:229 (khul' is mentioned between the first two talaqs and the third, so it is not counted as one); Ibn 'Abbas's view; Bukhari 5273.",
                "Khul' is a contract made at the wife's wish for money, different in nature from the husband's own talaq.",
                "Ibn Qudama, al-Mughni, Kitab al-Khul' (add page)");

        // Issue 14: Maintenance and housing of an irrevocably divorced woman
        position(db, 14, "Hanafi",
                "She is entitled to both maintenance and housing during the iddah.",
                "Verses 65:1 and 65:6 (lodge them where you dwell); 'Umar rejected the report of Fatima bint Qais against the Book of Allah.",
                "The iddah is a period in which the woman is held for the husband's right, so he must provide for her.",
                "al-Marghinani, al-Hidaya, Kitab al-Talaq, bab al-nafaqa (add page)");
        position(db, 14, "Maliki",
                "She is entitled to housing, but not to maintenance unless she is pregnant.",
                "Verse 65:6: 'lodge them' (housing for all) and 'if they are pregnant, spend on them' (maintenance only for pregnant women).",
                "The verse links maintenance to pregnancy; housing is general.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Talaq (add page)");
        position(db, 14, "Shafi'i",
                "She is entitled to housing, but not to maintenance unless she is pregnant.",
                "Verse 65:6; Muslim 1480 (no maintenance for Fatima bint Qais), understood as concerning maintenance but not housing.",
                "Maintenance is tied to pregnancy in the verse; housing is general.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Nafaqat (add page)");
        position(db, 14, "Hanbali",
                "She is entitled to neither maintenance nor housing unless she is pregnant (the main view).",
                "Muslim 1480: the Prophet gave Fatima bint Qais no housing and no maintenance after her final divorce.",
                "The hadith is explicit about both housing and maintenance.",
                "Ibn Qudama, al-Mughni, Kitab al-Nafaqat (add page)");

        // Issue 15: How does a husband take his wife back?
        position(db, 15, "Hanafi",
                "By words, or by acts such as intercourse, touching or kissing with desire. No intention is needed for the acts. Witnesses are recommended, not required.",
                "Verses 2:228 (the husbands have more right to take them back), 2:231 and 65:2.",
                "The marriage still exists during the revocable iddah, so acts that belong to marriage show that he kept her.",
                "al-Marghinani, al-Hidaya, Kitab al-Talaq, bab al-raj'a (add page)");
        position(db, 15, "Maliki",
                "By words, or by acts if he intends to take her back.",
                "Verses 2:228, 2:231 and 65:2.",
                "The aim is to show that he wants to keep the marriage, so intention matters.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Talaq (add page)");
        position(db, 15, "Shafi'i",
                "By words only (explicit or clearly implied). Intercourse is not a revocation. Witnesses are recommended.",
                "Verses 2:228, 2:231 and 65:2 (witnesses: 'bring to witness two just persons').",
                "Revocation is a statement that restores the marriage, like the divorce which is a statement.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Raj'a (add page)");
        position(db, 15, "Hanbali",
                "By words; and by intercourse in the main view (scholars of the school differ on whether intention is needed). Witnesses are recommended.",
                "Verses 2:228, 2:231 and 65:2.",
                "The marriage still exists during the revocable iddah, so intercourse with the wife shows that he kept her.",
                "Ibn Qudama, al-Mughni, Kitab al-Talaq, bab al-raj'a (add page)");

        // Issue 16: The consolation gift (mut'a)
        position(db, 16, "Hanafi",
                "Obligatory for a woman divorced before consummation when no mahr was named. Recommended for the other divorced women.",
                "Verse 2:236 (a provision for women with no named mahr); verse 2:241.",
                "A woman who gets no mahr needs a provision that replaces it.",
                "al-Marghinani, al-Hidaya, Kitab al-Nikah, bab al-mahr (add page)");
        position(db, 16, "Maliki",
                "Recommended (not obligatory) for every divorced woman, except one who is divorced before consummation with a named mahr (she gets half the mahr).",
                "Verse 2:241 calls it 'a duty upon the righteous', which they understand as a moral duty.",
                "The wording 'upon the righteous' shows a recommendation, not a legal obligation.",
                "Ibn Rushd, Bidayat al-Mujtahid, Kitab al-Nikah (add page)");
        position(db, 16, "Shafi'i",
                "Obligatory for every divorced woman, except one who gets half the mahr (divorced before consummation with a named mahr) and cases where the separation comes from her side.",
                "Verse 2:241 ('for divorced women is a provision'), which is general; verse 33:49.",
                "The verse is general and speaks of divorced women as a group.",
                "al-Nawawi, Minhaj al-Talibin, Kitab al-Sadaq (add page)");
        position(db, 16, "Hanbali",
                "Obligatory for a woman divorced before consummation without a named mahr; recommended for the others (scholars of the school differ in details).",
                "Verses 2:236, 2:241 and 33:49.",
                "The mahr or its half already compensates the woman who had one named.",
                "Ibn Qudama, al-Mughni, Kitab al-Sadaq (add page)");
    }

    // ---------------------------------------------------------------
    // Links between the new issues and their evidence. All are APPROVED for now.
    // Ids of verses: see addAyat(). Ids of hadith: see addHadiths(). Ids of laws: see addQanuns().
    // ---------------------------------------------------------------
    private static void addLinks(Database db) {
        // Extra evidence for the old issues
        link(db, 1, "HADITH", 5, "Sahih Muslim 1421");
        link(db, 5, "HADITH", 17, "Sahih Muslim 1471");

        // 7: father and the virgin daughter
        link(db, 7, "HADITH", 4, "Sahih al-Bukhari 5136");
        link(db, 7, "HADITH", 5, "Sahih Muslim 1421");

        // 8: ihram
        link(db, 8, "HADITH", 6, "Sahih al-Bukhari 1837");
        link(db, 8, "HADITH", 7, "Sahih Muslim 1409");

        // 9: mut'a marriage
        link(db, 9, "AYAH", 6, "Qur'an 4:24");
        link(db, 9, "HADITH", 8, "Sahih al-Bukhari 5115");
        link(db, 9, "HADITH", 9, "Sahih Muslim 1406");

        // 10: tahlil
        link(db, 10, "AYAH", 7, "Qur'an 2:230");
        link(db, 10, "HADITH", 10, "Sunan Abi Dawud 2076");
        link(db, 10, "HADITH", 11, "Sahih al-Bukhari 5260");

        // 11: Christian or Jewish woman
        link(db, 11, "AYAH", 8, "Qur'an 5:5");
        link(db, 11, "AYAH", 9, "Qur'an 2:221");

        // 12: compulsion
        link(db, 12, "HADITH", 14, "Sunan Ibn Majah 2043");
        link(db, 12, "HADITH", 12, "Sunan Abi Dawud 2193");
        link(db, 12, "HADITH", 13, "Sunan Abi Dawud 2194");
        link(db, 12, "QANUN", 2, "Egyptian Law No. 25 of 1929, Art. 1");

        // 13: khul'
        link(db, 13, "AYAH", 3, "Qur'an 2:229");
        link(db, 13, "HADITH", 15, "Sahih al-Bukhari 5273");
        link(db, 13, "QANUN", 3, "Egyptian Law No. 1 of 2000, Art. 20");

        // 14: maintenance in the iddah
        link(db, 14, "AYAH", 10, "Qur'an 65:6");
        link(db, 14, "AYAH", 5, "Qur'an 65:1");
        link(db, 14, "HADITH", 16, "Sahih Muslim 1480");

        // 15: revocation
        link(db, 15, "AYAH", 4, "Qur'an 2:228");
        link(db, 15, "AYAH", 15, "Qur'an 2:231");
        link(db, 15, "AYAH", 11, "Qur'an 65:2");
        link(db, 15, "HADITH", 17, "Sahih Muslim 1471");

        // 16: consolation gift
        link(db, 16, "AYAH", 12, "Qur'an 2:236");
        link(db, 16, "AYAH", 13, "Qur'an 2:241");
        link(db, 16, "AYAH", 14, "Qur'an 33:49");
    }
}
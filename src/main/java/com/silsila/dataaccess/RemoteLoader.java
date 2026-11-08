package com.silsila.dataaccess;

import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.Tafsir;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.Duration;

// Loads the texts of verses, tafsir and hadith from public websites (APIs)
// and puts them into the objects of the Database.
//
// To keep the program fast and to work without internet, every answer from a website
// is saved in the folder "cache". The next time, the saved file is used instead.
public class RemoteLoader {

    // The websites (served by the jsDelivr CDN from public GitHub projects).
    private static final String QURAN = "https://cdn.jsdelivr.net/gh/fawazahmed0/quran-api@1/editions/";
    private static final String TAFSIR = "https://cdn.jsdelivr.net/gh/spa5k/tafsir_api@main/tafsir/";
    private static final String HADITH = "https://cdn.jsdelivr.net/gh/fawazahmed0/hadith-api@1/editions/";

    private Database database;
    private HttpClient client = HttpClient.newHttpClient();
    private boolean offline = false;   // becomes true if a website cannot be reached

    public RemoteLoader(Database database) {
        this.database = database;
    }

    public void loadAll() {
        System.out.println("Loading verses, tafsir and hadith (the first run needs internet)...");
        loadAyat();
        loadTafsirs();
        loadHadiths();
        System.out.println("Loading finished.");
    }

    // ---------------------------------------------------------------
    // Verses: Arabic text and English translation
    // ---------------------------------------------------------------
    private void loadAyat() {
        for (Ayah a : database.ayat) {
            String place = a.getSurahNumber() + "/" + a.getAyahNumber();
            String cache = a.getSurahNumber() + "-" + a.getAyahNumber() + ".json";

            String arabic = get(QURAN + "ara-quranuthmanihaf/" + place + ".json", "ara-" + cache);
            String english = get(QURAN + "eng-ummmuhammad/" + place + ".json", "eng-" + cache);

            if (arabic != null) {
                a.setText(SimpleJson.getString(arabic, "text"));
            } else {
                a.setText("(Could not load the text. Check the internet connection and restart.)");
            }
            if (english != null) {
                a.setTranslation(SimpleJson.getString(english, "text"));
            }
        }
    }

    // ---------------------------------------------------------------
    // Tafsir: the text of the book for each verse
    // ---------------------------------------------------------------
    private void loadTafsirs() {
        for (Tafsir t : database.tafsirs) {
            // Find the verse this tafsir explains, to know its place.
            Ayah ayah = null;
            for (Ayah a : database.ayat) {
                if (a.getId() == t.getAyahId()) {
                    ayah = a;
                }
            }
            if (ayah == null) {
                continue;
            }

            String place = ayah.getSurahNumber() + "/" + ayah.getAyahNumber();
            String cache = "tafsir-" + t.getEdition() + "-" + ayah.getSurahNumber() + "-"
                    + ayah.getAyahNumber() + ".json";
            String json = get(TAFSIR + t.getEdition() + "/" + place + ".json", cache);

            if (json != null) {
                t.setText(SimpleJson.getString(json, "text"));
            } else {
                t.setText("(Could not load the tafsir. Check the internet connection and restart.)");
            }
        }
    }

    // ---------------------------------------------------------------
    // Hadith: English text, Arabic text and the grade
    // ---------------------------------------------------------------
    private void loadHadiths() {
        for (Hadith h : database.hadiths) {
            String file = h.getCollection() + "/" + h.getApiNumber() + ".json";
            String cache = h.getCollection() + "-" + h.getApiNumber() + ".json";

            String english = get(HADITH + "eng-" + file, "eng-" + cache);
            String arabic = get(HADITH + "ara-" + file, "ara-" + cache);

            if (english != null) {
                h.setText(SimpleJson.getString(english, "text"));
                h.setGrade(readGrades(english));
            } else {
                h.setText("(Could not load the text. Check the internet connection and restart.)");
            }
            if (arabic != null) {
                h.setArabicText(SimpleJson.getString(arabic, "text"));
            }
        }
    }

    // The website lists grades like: "grades": [ {"name": "Al-Albani", "grade": "Sahih"}, ... ]
    // This turns them into one line: "Sahih (Al-Albani), Sahih (Zubair Ali Zai)".
    // Some books (for example Sahih Muslim) have no grades in the website: then it returns "".
    private String readGrades(String json) {
        int start = json.indexOf("\"grades\"");
        if (start < 0) {
            return "";
        }
        int end = json.indexOf("\"reference\"", start);
        if (end < 0) {
            end = json.length();
        }
        String part = json.substring(start, end);

        String result = "";
        int from = 0;
        while (true) {
            int g = part.indexOf("\"grade\"", from);
            if (g < 0) {
                break;
            }
            String grade = SimpleJson.getStringFrom(part, "grade", g);
            int n = part.lastIndexOf("\"name\"", g);
            String name = SimpleJson.getStringFrom(part, "name", n);

            if (!result.equals("")) {
                result = result + ", ";
            }
            result = result + grade + " (" + name + ")";
            from = g + 7;
        }
        return result;
    }

    // ---------------------------------------------------------------
    // Getting a page from a website, with the cache folder
    // ---------------------------------------------------------------
    // Returns the text of the answer, or null if it could not be loaded.
    private String get(String url, String cacheName) {
        File file = new File("cache/" + cacheName);

        // 1. If we saved this answer before, use it.
        if (file.exists()) {
            try {
                return Files.readString(file.toPath());
            } catch (IOException e) {
                // the saved file is unreadable: try the website instead
            }
        }

        // 2. If a website was unreachable before, do not wait for every other request.
        if (offline) {
            return null;
        }

        // 3. Ask the website. The internet can fail for a moment, so we try twice.
        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(20))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    System.out.println("  The website answered " + response.statusCode() + " for " + url);
                    return null;
                }

                // Save the answer for next time.
                new File("cache").mkdirs();
                Files.writeString(file.toPath(), response.body());
                System.out.println("  Loaded " + cacheName);
                return response.body();

            } catch (IOException e) {
                System.out.println("  Attempt " + attempt + " failed for " + cacheName + " (" + e.getMessage() + ")");
            } catch (InterruptedException e) {
                return null;
            }
        }

        // Both attempts failed: stop asking the websites, so the program does not wait for every request.
        System.out.println("  Could not reach the website. Working without internet.");
        offline = true;
        return null;
    }
}
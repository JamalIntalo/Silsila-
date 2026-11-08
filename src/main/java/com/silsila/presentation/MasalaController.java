package com.silsila.presentation;

import com.silsila.application.MasalaChain;
import com.silsila.application.MasalaService;
import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.MadhabPosition;
import com.silsila.domain.Masala;
import com.silsila.domain.Qanun;
import com.silsila.domain.Tafsir;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

// This controller answers the address /masala?id=1
// It shows one issue with its whole chain of evidence.
public class MasalaController implements HttpHandler {

    private MasalaService masalaService;

    public MasalaController(MasalaService masalaService) {
        this.masalaService = masalaService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Read the id from the address. If it is not a number, use -1 (no issue has that id).
        int id = -1;
        try {
            id = Integer.parseInt(Request.getQueryParam(exchange, "id"));
        } catch (NumberFormatException e) {
            id = -1;
        }

        MasalaChain chain = masalaService.getChain(id);

        if (chain == null) {
            Html.send(exchange, 404, Html.page("Not found",
                    "<h1>Issue not found</h1><p><a href=\"/\">Back to the home page</a></p>"));
            return;
        }

        Masala masala = chain.getMasala();

        String content = "<p><a href=\"/\">&larr; All issues</a></p>\n";
        content = content + "<h1>" + Html.escape(masala.getTitle()) + "</h1>\n";
        content = content + "<p class=\"small\">" + categoryName(masala.getCategory())
                + " - " + Html.escape(masala.getChapter()) + "</p>\n";
        content = content + "<p>" + Html.escape(masala.getDescription()) + "</p>\n";

        // Section 1: verses and their tafsir
        content = content + "<h2>1. Qur'an and tafsir</h2>\n";
        if (chain.getAyat().size() == 0) {
            content = content + "<p class=\"small\">No verse has been linked to this issue yet.</p>\n";
        }
        for (Ayah a : chain.getAyat()) {
            content = content + "<div class=\"box\">";
            content = content + "<strong>" + Html.escape(a.getReference()) + "</strong>";
                        // The Arabic text, written right to left and in a bigger font.
                        content = content + "<p dir=\"rtl\" style=\"font-size:24px; line-height:1.8;\">" + Html.escape(a.getText()) + "</p>";
                        if (!a.getTranslation().equals("")) {
                            content = content + "<p>" + Html.escape(a.getTranslation()) + "</p>";
                        }
                        // The tafsir is long, so it is inside a <details> block that opens when clicked.
                        for (Tafsir t : chain.getTafsirsOf(a.getId())) {
                            content = content + "<details><summary class=\"small\">Tafsir - " + Html.escape(t.getSourceName())
                                    + " (click to read)</summary>"
                                    + "<div style=\"white-space:pre-wrap;\">" + Html.escape(t.getText()) + "</div></details>";
                        }
            content = content + "</div>\n";
        }

        // Section 2: hadith
        content = content + "<h2>2. Hadith</h2>\n";
        if (chain.getHadiths().size() == 0) {
            content = content + "<p class=\"small\">No hadith has been linked to this issue yet.</p>\n";
        }
        for (Hadith h : chain.getHadiths()) {
            content = content + "<div class=\"box\">";
            content = content + "<strong>" + Html.escape(h.getReference()) + "</strong>";
            if (!h.getArabicText().equals("")) {
                content = content + "<p dir=\"rtl\" style=\"font-size:20px; line-height:1.8;\">" + Html.escape(h.getArabicText()) + "</p>";
            }
            content = content + "<p>" + Html.escape(h.getText()) + "</p>";
            content = content + "</div>\n";
        }

        // Section 3: the four madhabs side by side
        content = content + "<h2>3. Positions of the madhabs</h2>\n";
        if (chain.getPositions().size() == 0) {
            content = content + "<p class=\"small\">The positions of the madhabs have not been added yet.</p>\n";
        } else {
            // A grid: as many columns as fit, each at least 200px wide.
            // On a phone the columns stack under each other.
            content = content + "<div style=\"display:grid; grid-template-columns:repeat(auto-fit, minmax(200px, 1fr)); gap:10px;\">\n";
            for (MadhabPosition p : chain.getPositions()) {
                content = content + "<div class=\"box\">";
                content = content + "<strong>" + Html.escape(p.getMadhab()) + "</strong>";
                content = content + "<p>" + Html.escape(p.getRuling()) + "</p>";
                content = content + "<div class=\"small\"><strong>Evidence:</strong> " + Html.escape(p.getEvidence()) + "</div>";
                content = content + "<div class=\"small\"><strong>Reasoning:</strong> " + Html.escape(p.getIllah()) + "</div>";
                content = content + "<div class=\"small\"><strong>Source:</strong> " + Html.escape(p.getCitation()) + "</div>";
                content = content + "</div>\n";
            }
            content = content + "</div>\n";
        }

        // Section 4: laws
        content = content + "<h2>4. Qanun (modern law)</h2>\n";
        if (chain.getQanuns().size() == 0) {
            content = content + "<p class=\"small\">No law has been linked to this issue yet.</p>\n";
        }
        for (Qanun q : chain.getQanuns()) {
            content = content + "<div class=\"box\">";
            content = content + "<strong>" + Html.escape(q.getReference()) + "</strong>";
            content = content + "<p>" + Html.escape(q.getDescription()) + "</p>";
            content = content + "</div>\n";
        }

        Html.send(exchange, 200, Html.page(masala.getTitle(), content));
    }

    // Turns "NIKAH" into a nicer name for the page.
    private String categoryName(String category) {
        if (category.equals("NIKAH")) {
            return "Nikah (Marriage)";
        }
        if (category.equals("TALAQ")) {
            return "Talaq (Divorce)";
        }
        return category;
    }
}
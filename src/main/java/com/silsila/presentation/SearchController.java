package com.silsila.presentation;

import com.silsila.application.MasalaService;
import com.silsila.domain.Masala;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.ArrayList;

// This controller answers two addresses:
//   /        the home page: a search box and the list of issues by book and chapter
//   /search  the search results
public class SearchController implements HttpHandler {

    private MasalaService masalaService;

    public SearchController(MasalaService masalaService) {
        this.masalaService = masalaService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/")) {
            showHome(exchange);
        } else if (path.equals("/search")) {
            showResults(exchange);
        } else {
            Html.send(exchange, 404, Html.page("Not found",
                    "<h1>Page not found</h1><p><a href=\"/\">Back to the home page</a></p>"));
        }
    }

    // The home page: search box + browse by book and chapter.
    private void showHome(HttpExchange exchange) throws IOException {
        String content = "<h1>Fiqh issues of nikah and talaq</h1>\n";
        content = content + "<p>Search for an issue, or browse by book and chapter.</p>\n";
        content = content + searchForm("");

        content = content + "<h2>Browse</h2>\n";

        // Two books: NIKAH and TALAQ.
        String[] categories = {"NIKAH", "TALAQ"};
        for (String category : categories) {
            content = content + "<h3>" + categoryName(category) + "</h3>\n";

            // Each book has chapters, and each chapter has issues.
            for (String chapter : masalaService.getChapters(category)) {
                content = content + "<div class=\"box\"><strong>" + Html.escape(chapter) + "</strong><ul>\n";
                for (Masala m : masalaService.getMasalasInChapter(category, chapter)) {
                    content = content + "<li><a href=\"/masala?id=" + m.getId() + "\">"
                            + Html.escape(m.getTitle()) + "</a></li>\n";
                }
                content = content + "</ul></div>\n";
            }
        }

        Html.send(exchange, 200, Html.page("Home", content));
    }

    // The search results page.
    private void showResults(HttpExchange exchange) throws IOException {
        // Read what the visitor typed: /search?q=wali
        String query = Request.getQueryParam(exchange, "q").trim();

        // Measure how long the search takes (requirement NFR-1: under 2 seconds).
        long start = System.currentTimeMillis();
        ArrayList<Masala> results = masalaService.searchByKeyword(query);
        long milliseconds = System.currentTimeMillis() - start;

        String content = "<h1>Search</h1>\n";
        content = content + searchForm(query);
        content = content + "<p class=\"small\">" + results.size() + " issue(s) found in "
                + milliseconds + " ms</p>\n";

        if (results.size() == 0) {
            content = content + "<p>No issue matches your search. Try one word, for example wali, mahr or iddah.</p>\n";
        }

        for (Masala m : results) {
            content = content + "<div class=\"box\">";
            content = content + "<a href=\"/masala?id=" + m.getId() + "\">"
                    + Html.escape(m.getTitle()) + "</a>";
            content = content + "<div class=\"small\">" + categoryName(m.getCategory())
                    + " - " + Html.escape(m.getChapter()) + "</div>";
            content = content + "<p>" + Html.escape(m.getDescription()) + "</p>";
            content = content + "</div>\n";
        }

        Html.send(exchange, 200, Html.page("Search", content));
    }

    // The search box. "query" is the text to show inside it.
    private String searchForm(String query) {
        String form = "<form method=\"get\" action=\"/search\">";
        form = form + "<input type=\"text\" name=\"q\" value=\"" + Html.escape(query) + "\" "
                + "placeholder=\"For example: wali, mahr, iddah\" "
                + "style=\"padding:8px; width:60%; max-width:400px;\"> ";
        form = form + "<button type=\"submit\" style=\"padding:8px 16px;\">Search</button>";
        form = form + "</form>\n";
        return form;
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
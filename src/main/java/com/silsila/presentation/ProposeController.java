package com.silsila.presentation;

import com.silsila.application.LinkService;
import com.silsila.domain.Ayah;
import com.silsila.domain.Hadith;
import com.silsila.domain.Qanun;
import com.silsila.domain.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.HashMap;

// This controller answers /propose
//   GET  /propose?id=4   shows the form "propose a link for issue 4"
//   POST /propose        receives the form and saves the proposal as PENDING
// Only logged-in people can use it (requirement FR-6).
public class ProposeController implements HttpHandler {

    private LinkService linkService;

    public ProposeController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        User user = SessionManager.getUser(exchange);
        if (user == null) {
            Html.redirect(exchange, "/login");
            return;
        }

        if (exchange.getRequestMethod().equals("POST")) {
            receiveForm(exchange, user);
        } else {
            int masalaId = readNumber(Request.getQueryParam(exchange, "id"));
            showForm(exchange, masalaId, "", "");
        }
    }

    // Turns text into a number. Returns -1 if the text is not a number.
    private int readNumber(String text) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Shows the form. "error" is a message to show above it (or "").
    // "citation" is the text to put back in the citation box.
    private void showForm(HttpExchange exchange, int masalaId, String error, String citation) throws IOException {
        String title = linkService.getMasalaTitle(masalaId);
        if (title == null) {
            Html.send(exchange, 404, Html.page("Not found",
                    "<h1>Issue not found</h1><p><a href=\"/\">Back to the home page</a></p>"));
            return;
        }

        String content = "<p><a href=\"/masala?id=" + masalaId + "\">&larr; Back to the issue</a></p>\n";
        content = content + "<h1>Propose a link</h1>\n";
        content = content + "<p>Issue: <strong>" + Html.escape(title) + "</strong></p>\n";
        content = content + "<p class=\"small\">Connect this issue to a verse, a hadith or a law. "
                + "A reviewer will check your proposal. Students see it only after it is approved.</p>\n";
        if (!error.equals("")) {
            content = content + "<div class=\"error\">" + Html.escape(error) + "</div>\n";
        }

        content = content + "<form method=\"post\" action=\"/propose\">\n";
        content = content + "<input type=\"hidden\" name=\"masalaId\" value=\"" + masalaId + "\">\n";

        // The drop-down menu. Each choice has a value like "AYAH:3" (the type and the id).
        content = content + "<label for=\"target\">What supports this issue?</label>\n";
        content = content + "<select id=\"target\" name=\"target\">\n";
        content = content + "<optgroup label=\"Verses\">\n";
        for (Ayah a : linkService.getAllAyat()) {
            content = content + "<option value=\"AYAH:" + a.getId() + "\">" + Html.escape(a.getReference()) + "</option>\n";
        }
        content = content + "</optgroup>\n<optgroup label=\"Hadith\">\n";
        for (Hadith h : linkService.getAllHadiths()) {
            content = content + "<option value=\"HADITH:" + h.getId() + "\">" + Html.escape(h.getReference()) + "</option>\n";
        }
        content = content + "</optgroup>\n<optgroup label=\"Laws\">\n";
        for (Qanun q : linkService.getAllQanuns()) {
            content = content + "<option value=\"QANUN:" + q.getId() + "\">" + Html.escape(q.getReference()) + "</option>\n";
        }
        content = content + "</optgroup>\n</select>\n";

        content = content + "<label for=\"citation\">Citation (required): where does this connection come from?</label>\n";
        content = content + "<input type=\"text\" id=\"citation\" name=\"citation\" maxlength=\"300\" value=\""
                + Html.escape(citation) + "\" placeholder=\"For example: Ibn Qudama, al-Mughni, Kitab al-Nikah\">\n";
        content = content + "<br><button type=\"submit\">Send for review</button>\n";
        content = content + "</form>\n";

        Html.send(exchange, 200, Html.page("Propose a link", content));
    }

    private void receiveForm(HttpExchange exchange, User user) throws IOException {
        HashMap<String, String> form = Request.getForm(exchange);

        int masalaId = readNumber(valueOf(form, "masalaId"));
        String citation = valueOf(form, "citation");

        // The target looks like "AYAH:3": cut it at the colon.
        String type = "";
        int targetId = -1;
        String[] parts = valueOf(form, "target").split(":");
        if (parts.length == 2) {
            type = parts[0];
            targetId = readNumber(parts[1]);
        }

        String error = linkService.propose(user, masalaId, type, targetId, citation);
        if (!error.equals("")) {
            showForm(exchange, masalaId, error, citation);
            return;
        }

        String content = "<h1>Thank you</h1>\n";
        content = content + "<div class=\"ok\">Your proposal was saved. It will appear on the issue page after a reviewer approves it.</div>\n";
        content = content + "<p><a href=\"/masala?id=" + masalaId + "\">Back to the issue</a></p>\n";
        Html.send(exchange, 200, Html.page("Proposal sent", content));
    }

    // Reads a field of the form, or "" if it is missing.
    private String valueOf(HashMap<String, String> form, String name) {
        String value = form.get(name);
        if (value == null) {
            return "";
        }
        return value;
    }
}
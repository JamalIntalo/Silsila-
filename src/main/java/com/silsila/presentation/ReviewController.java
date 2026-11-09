package com.silsila.presentation;

import com.silsila.application.LinkService;
import com.silsila.domain.Link;
import com.silsila.domain.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.HashMap;

// This controller answers /review
//   GET  /review   lists the proposed links that wait for a decision
//   POST /review   approves or rejects one of them
// Only reviewers can use it (requirement FR-7).
public class ReviewController implements HttpHandler {

    private LinkService linkService;

    public ReviewController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        User user = SessionManager.getUser(exchange);
        if (user == null) {
            Html.redirect(exchange, "/login");
            return;
        }
        if (!user.isReviewer()) {
            // 403 means "forbidden": you are logged in but not allowed here.
            Html.send(exchange, 403, Html.page("Not allowed",
                    "<h1>Not allowed</h1><p>Only reviewers can use this page.</p><p><a href=\"/\">Back to the home page</a></p>"));
            return;
        }

        String message = "";
        if (exchange.getRequestMethod().equals("POST")) {
            message = receiveDecision(exchange, user);
        }
        showList(exchange, message);
    }

    // Reads the form of one approve/reject button and applies it. Returns a message for the page.
    private String receiveDecision(HttpExchange exchange, User user) throws IOException {
        HashMap<String, String> form = Request.getForm(exchange);
        int linkId = -1;
        try {
            linkId = Integer.parseInt(form.get("linkId"));
        } catch (NumberFormatException e) {
            linkId = -1;
        }
        String decision = form.get("decision");
        if (decision == null) {
            decision = "";
        }

        String error = linkService.decide(user, linkId, decision);
        if (!error.equals("")) {
            return "<div class=\"error\">" + Html.escape(error) + "</div>\n";
        }
        if (decision.equals("approve")) {
            return "<div class=\"ok\">The link was approved. Students can now see it.</div>\n";
        }
        return "<div class=\"ok\">The link was rejected.</div>\n";
    }

    private void showList(HttpExchange exchange, String message) throws IOException {
        String content = "<h1>Links waiting for review</h1>\n";
        content = content + message;

        if (linkService.getPendingLinks().size() == 0) {
            content = content + "<p>There is nothing to review right now.</p>\n";
        }

        for (Link link : linkService.getPendingLinks()) {
            String issueTitle = linkService.getMasalaTitle(link.getMasalaId());
            content = content + "<div class=\"box\">";
            content = content + "<div>Issue: <a href=\"/masala?id=" + link.getMasalaId() + "\">"
                    + Html.escape(issueTitle) + "</a></div>";
            content = content + "<div>Proposed evidence: <strong>" + Html.escape(linkService.describeTarget(link)) + "</strong></div>";
            content = content + "<div>Citation: " + Html.escape(link.getCitation()) + "</div>";
            content = content + "<div class=\"small\">Proposed by " + Html.escape(link.getProposedBy()) + "</div>";

            // One small form with two buttons. The hidden field tells which link it is about.
            content = content + "<form method=\"post\" action=\"/review\">";
            content = content + "<input type=\"hidden\" name=\"linkId\" value=\"" + link.getId() + "\">";
            content = content + "<button type=\"submit\" name=\"decision\" value=\"approve\">Approve</button>";
            content = content + "<button type=\"submit\" name=\"decision\" value=\"reject\">Reject</button>";
            content = content + "</form>";
            content = content + "</div>\n";
        }

        Html.send(exchange, 200, Html.page("Review links", content));
    }
}
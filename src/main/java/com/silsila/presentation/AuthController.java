package com.silsila.presentation;

import com.silsila.application.AuthService;
import com.silsila.domain.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.HashMap;

// This controller answers two addresses:
//   /login   shows the login form (GET) and checks it (POST)
//   /logout  logs the person out
public class AuthController implements HttpHandler {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();

        if (path.equals("/logout")) {
            SessionManager.logout(exchange);
            Html.redirect(exchange, "/");
        } else if (exchange.getRequestMethod().equals("POST")) {
            checkLogin(exchange);
        } else {
            showForm(exchange, "");
        }
    }

    private void showForm(HttpExchange exchange, String error) throws IOException {
        String content = "<h1>Log in</h1>\n";
        content = content + "<p class=\"small\">Accounts are for contributors and reviewers. Students can read the website without logging in.</p>\n";
        if (!error.equals("")) {
            content = content + "<div class=\"error\">" + Html.escape(error) + "</div>\n";
        }
        content = content + "<form method=\"post\" action=\"/login\">\n";
        content = content + "<label for=\"username\">Username</label>\n";
        content = content + "<input type=\"text\" id=\"username\" name=\"username\">\n";
        content = content + "<label for=\"password\">Password</label>\n";
        content = content + "<input type=\"password\" id=\"password\" name=\"password\">\n";
        content = content + "<br><button type=\"submit\">Log in</button>\n";
        content = content + "</form>\n";
        Html.send(exchange, 200, Html.page("Log in", content));
    }

    private void checkLogin(HttpExchange exchange) throws IOException {
        HashMap<String, String> form = Request.getForm(exchange);
        String username = form.get("username");
        String password = form.get("password");
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }

        User user = authService.login(username.trim(), password);
        if (user == null) {
            // The same message for a wrong username and a wrong password,
            // so nobody can find out which usernames exist.
            showForm(exchange, "Wrong username or password.");
            return;
        }

        SessionManager.login(exchange, user);
        Html.redirect(exchange, "/");
    }
}
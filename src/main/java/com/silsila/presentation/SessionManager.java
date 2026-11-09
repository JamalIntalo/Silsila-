package com.silsila.presentation;

import com.silsila.domain.User;
import com.sun.net.httpserver.HttpExchange;
import java.security.SecureRandom;
import java.util.HashMap;

// Remembers who is logged in.
//
// The web is "stateless": every request arrives alone, and the server does not know
// that two requests come from the same person. To solve this:
//   1. When someone logs in, we make a long random text (the "session token") and
//      remember: token -> user.
//   2. We send the token to the browser in a COOKIE. The browser sends it back
//      with every later request.
//   3. We look the token up to know who is asking.
// The sessions are kept in memory, so everyone is logged out when the program restarts.
public class SessionManager {

    private static HashMap<String, User> sessions = new HashMap<String, User>();

    // Called after a correct login: remembers the user and gives the browser its cookie.
    public static void login(HttpExchange exchange, User user) {
        String token = makeToken();
        sessions.put(token, user);
        // HttpOnly: JavaScript in the page cannot read the cookie.
        // SameSite=Strict: the browser does not send it when the request comes from another website
        // (this helps against forged forms, called CSRF).
        exchange.getResponseHeaders().add("Set-Cookie",
                "session=" + token + "; Path=/; HttpOnly; SameSite=Strict");
    }

    // Forgets the session and tells the browser to delete the cookie.
    public static void logout(HttpExchange exchange) {
        String token = readToken(exchange);
        if (token != null) {
            sessions.remove(token);
        }
        exchange.getResponseHeaders().add("Set-Cookie",
                "session=; Path=/; Max-Age=0; HttpOnly; SameSite=Strict");
    }

    // Returns the logged-in user of this request, or null if nobody is logged in.
    public static User getUser(HttpExchange exchange) {
        String token = readToken(exchange);
        if (token == null) {
            return null;
        }
        return sessions.get(token);
    }

    // Finds the session cookie in the request. The Cookie header looks like  a=1; session=abc; b=2
    private static String readToken(HttpExchange exchange) {
        String header = exchange.getRequestHeaders().getFirst("Cookie");
        if (header == null) {
            return null;
        }
        for (String part : header.split(";")) {
            String[] pair = part.trim().split("=", 2);
            if (pair.length == 2 && pair[0].equals("session")) {
                return pair[1];
            }
        }
        return null;
    }

    // 16 random bytes written as 32 characters. Nobody can guess it.
    private static String makeToken() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);
        StringBuilder token = new StringBuilder();
        for (byte b : bytes) {
            token.append(String.format("%02x", b));
        }
        return token.toString();
    }
}
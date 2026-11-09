package com.silsila.presentation;

import com.silsila.domain.User;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

// Small helper methods used by every page.
public class Html {

    // Makes text safe to put inside a web page.
    // Without this, if someone typed <script> in a form, the browser would run it.
    // RULE: any text that comes from data or from a user goes through escape().
    public static String escape(String text) {
        if (text == null) {
            return "";
        }
        text = text.replace("&", "&amp;");
        text = text.replace("<", "&lt;");
        text = text.replace(">", "&gt;");
        text = text.replace("\"", "&quot;");
        text = text.replace("'", "&#39;");
        return text;
    }

    // Puts the content of a page inside the common layout (head, title, menu, footer).
    public static String page(String title, String content) {
        String html = "<!DOCTYPE html>\n";
        html = html + "<html lang=\"en\">\n<head>\n";
        html = html + "<meta charset=\"UTF-8\">\n";
        // This line makes the page fit phone screens (responsive design).
        html = html + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n";
        html = html + "<title>" + escape(title) + " - Silsila</title>\n";
        html = html + "<style>\n";
        html = html + "body { font-family: Arial, sans-serif; margin: 0; background: #f5f7f6; color: #222; }\n";
        html = html + "header { background: #14532d; padding: 12px 20px; display: flex; flex-wrap: wrap; justify-content: space-between; align-items: center; }\n";
        html = html + "header a { color: white; font-size: 22px; font-weight: bold; text-decoration: none; }\n";
        html = html + "header .nav, header .nav a { color: white; font-size: 15px; font-weight: normal; }\n";
        html = html + "header .nav a { margin-left: 14px; text-decoration: underline; }\n";
        html = html + "main { max-width: 900px; margin: 0 auto; padding: 20px; }\n";
        html = html + ".box { background: white; border: 1px solid #ccd5d0; border-radius: 6px; padding: 10px 14px; margin: 8px 0; }\n";
        html = html + ".small { color: #555; font-size: 14px; }\n";
        html = html + ".error { background: #fdecea; border-left: 4px solid #c62828; padding: 8px 12px; margin: 10px 0; }\n";
        html = html + ".ok { background: #e8f5e9; border-left: 4px solid #2e7d32; padding: 8px 12px; margin: 10px 0; }\n";
        html = html + "label { display: block; margin-top: 10px; font-weight: bold; }\n";
        html = html + "input[type=text], input[type=password], select, textarea { padding: 8px; width: 100%; max-width: 500px; box-sizing: border-box; }\n";
        html = html + "button { padding: 8px 16px; margin-top: 12px; margin-right: 6px; }\n";
        html = html + "footer { text-align: center; color: #555; font-size: 13px; padding: 20px; }\n";
        html = html + "</style>\n</head>\n<body>\n";
        // The marker <!--NAV--> is replaced in send() by the login/logout links.
        html = html + "<header><a href=\"/\">Silsila</a><span class=\"nav\"><!--NAV--></span></header>\n";
        html = html + "<main>\n" + content + "\n</main>\n";
        html = html + "<footer>Silsila - a knowledge network for nikah and talaq</footer>\n";
        html = html + "</body>\n</html>\n";
        return html;
    }

    // The links at the top right of every page, depending on who is logged in.
    private static String navFor(User user) {
        if (user == null) {
            return "<a href=\"/login\">Log in</a>";
        }
        String nav = escape(user.getUsername()) + " (" + escape(user.getRole().toLowerCase()) + ")";
        if (user.isReviewer()) {
            nav = nav + " <a href=\"/review\">Review links</a>";
        }
        nav = nav + " <a href=\"/logout\">Log out</a>";
        return nav;
    }

    // Sends a page to the browser. "status" is 200 for OK, 404 for page not found.
    public static void send(HttpExchange exchange, int status, String html) throws IOException {
        // Put the right menu in the page: send() knows who is asking.
        html = html.replace("<!--NAV-->", navFor(SessionManager.getUser(exchange)));

        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream out = exchange.getResponseBody();
        out.write(bytes);
        out.close();
    }

    // Sends the browser to another address (status 303 = "go and look over there").
    public static void redirect(HttpExchange exchange, String address) throws IOException {
        exchange.getResponseHeaders().set("Location", address);
        exchange.sendResponseHeaders(303, -1);
        exchange.close();
    }
}
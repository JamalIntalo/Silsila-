package com.silsila.presentation;

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
        html = html + "header { background: #14532d; padding: 12px 20px; }\n";
        html = html + "header a { color: white; font-size: 22px; font-weight: bold; text-decoration: none; }\n";
        html = html + "main { max-width: 900px; margin: 0 auto; padding: 20px; }\n";
        html = html + ".box { background: white; border: 1px solid #ccd5d0; border-radius: 6px; padding: 10px 14px; margin: 8px 0; }\n";
        html = html + ".small { color: #555; font-size: 14px; }\n";
        html = html + "footer { text-align: center; color: #555; font-size: 13px; padding: 20px; }\n";
        html = html + "</style>\n</head>\n<body>\n";
        html = html + "<header><a href=\"/\">Silsila</a></header>\n";
        html = html + "<main>\n" + content + "\n</main>\n";
        html = html + "<footer>Silsila - a knowledge network for nikah and talaq</footer>\n";
        html = html + "</body>\n</html>\n";
        return html;
    }

    // Sends a page to the browser. "status" is 200 for OK, 404 for page not found.
    public static void send(HttpExchange exchange, int status, String html) throws IOException {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(status, bytes.length);
        OutputStream out = exchange.getResponseBody();
        out.write(bytes);
        out.close();
    }
}
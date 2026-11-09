package com.silsila.presentation;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

// Helper for reading what the browser sent us.
public class Request {

    // Reads one value from the address.
    // Example: for the address  /search?q=wali  and the name "q", it returns "wali".
    // If the value is missing it returns "" (an empty text).
    public static String getQueryParam(HttpExchange exchange, String name) {
        String query = exchange.getRequestURI().getRawQuery();
        if (query == null) {
            return "";
        }

        // The address part after "?" looks like  a=1&b=2  so we cut it at every "&".
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            // Each pair looks like  name=value
            String[] parts = pair.split("=", 2);
            if (parts[0].equals(name)) {
                if (parts.length < 2) {
                    return "";
                }
                // Browsers write a space as "+" or "%20"; this turns it back into a space.
                return URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
            }
        }
        return "";
    }

    // Reads what a filled-in form sent (a "POST" request).
    // The browser sends the fields in the body of the request, written like  username=ali&password=xyz
    // This returns them as a list of name -> value pairs.
    // It can only be called ONCE per request, because the body can be read only once.
    public static HashMap<String, String> getForm(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);

        HashMap<String, String> form = new HashMap<String, String>();
        if (body.equals("")) {
            return form;
        }
        for (String pair : body.split("&")) {
            String[] parts = pair.split("=", 2);
            String name = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
            String value = "";
            if (parts.length == 2) {
                value = URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
            }
            form.put(name, value);
        }
        return form;
    }
}
package com.silsila.presentation;

import com.sun.net.httpserver.HttpExchange;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
}
package com.silsila.dataaccess;

// A very small helper that reads one text value out of a JSON text.
// JSON is the format the websites send, for example:   {"chapter": 2, "text": "Hello"}
// getString(json, "text") returns Hello.
public class SimpleJson {

    // Finds the first "key" in the JSON and returns its text value (or null if not found).
    public static String getString(String json, String key) {
        return getStringFrom(json, key, 0);
    }

    // Same, but starts searching at position "from" in the JSON text.
    public static String getStringFrom(String json, String key, int from) {
        int keyPosition = json.indexOf("\"" + key + "\"", from);
        if (keyPosition < 0) {
            return null;
        }
        int colon = json.indexOf(":", keyPosition + key.length() + 2);
        if (colon < 0) {
            return null;
        }
        int start = json.indexOf("\"", colon);
        if (start < 0) {
            return null;
        }
        // Between the colon and the opening quote there must only be spaces.
        // If there is something else, the value is not a text (for example a number).
        if (!json.substring(colon + 1, start).trim().equals("")) {
            return null;
        }

        StringBuilder value = new StringBuilder();
        int i = start + 1;
        while (i < json.length()) {
            char c = json.charAt(i);
            if (c == '"') {
                break;                       // the closing quote: the text ends here
            }
            if (c == '\\' && i + 1 < json.length()) {
                // An escape such as \n (new line) or \" (a quote inside the text).
                i++;
                char next = json.charAt(i);
                if (next == 'n') {
                    value.append('\n');
                } else if (next == 't') {
                    value.append('\t');
                } else if (next == 'u' && i + 4 < json.length()) {
                    value.append((char) Integer.parseInt(json.substring(i + 1, i + 5), 16));
                    i = i + 4;
                } else {
                    value.append(next);      // for \" and \\ and \/
                }
            } else {
                value.append(c);
            }
            i++;
        }
        return value.toString();
    }
}
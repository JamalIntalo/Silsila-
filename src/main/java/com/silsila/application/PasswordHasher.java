package com.silsila.application;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

// Scrambles passwords so that the real password is never stored (requirement NFR-3).
//
// A "hash" is a one-way scramble: from "secret" you can compute the hash,
// but from the hash you cannot get "secret" back.
// To check a login we scramble what the person typed and compare the two hashes.
//
// A "salt" is a random text added before scrambling. Two people with the same
// password then get different hashes.
public class PasswordHasher {

    // Returns the text to store: the salt, a colon, and the hash.
    public static String hash(String password) {
        // Make a random salt of 8 bytes.
        byte[] saltBytes = new byte[8];
        new SecureRandom().nextBytes(saltBytes);
        String salt = toHex(saltBytes);

        return salt + ":" + scramble(salt, password);
    }

    // Checks a typed password against the stored text "salt:hash".
    public static boolean matches(String password, String stored) {
        String[] parts = stored.split(":");
        if (parts.length != 2) {
            return false;
        }
        String salt = parts[0];
        String expectedHash = parts[1];
        return scramble(salt, password).equals(expectedHash);
    }

    // Scrambles salt + password with SHA-256 and returns the result as hex text.
    private static String scramble(String salt, String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] result = digest.digest((salt + password).getBytes(StandardCharsets.UTF_8));
            return toHex(result);
        } catch (NoSuchAlgorithmException e) {
            // Every Java has SHA-256, so this cannot really happen.
            throw new RuntimeException(e);
        }
    }

    // Turns bytes into text made of 0-9 and a-f, for example 3fa9...
    private static String toHex(byte[] bytes) {
        StringBuilder text = new StringBuilder();
        for (byte b : bytes) {
            text.append(String.format("%02x", b));
        }
        return text.toString();
    }
}
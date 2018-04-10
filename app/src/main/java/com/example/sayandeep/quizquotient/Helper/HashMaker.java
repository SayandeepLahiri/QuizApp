package com.example.sayandeep.quizquotient.Helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Pronoy on 10-Apr-18.
 * This is the class to make the Hash for the password.
 */

public class HashMaker {
    static public MessageDigest messageDigest;
    static String algorithm = "SHA-256";

    /**
     * This is the method to make the make Hash for the Password.
     * This uses SHA-256 hashing algorithm.
     *
     * @param message: The password whose hash is needed.
     * @return: The final hash of the message using SHA-256 separated by '.'
     */
    public static String makeHash(String message) {
        StringBuilder finalHash = new StringBuilder();
        try {
            messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(message.getBytes(StandardCharsets.UTF_8));
            byte hash[] = messageDigest.digest();
            for (byte i : hash) {
                finalHash.append((i + 128) + ".");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return finalHash.toString();
    }
}

package com.example.aazar.cryptonite.controller;

import android.content.Context;
import android.widget.Toast;

import com.example.aazar.cryptonite.MainActivity;

/**
 * Created by Aazar on 30-Jan-16.
 */
public class Cipher {
    // working correctly
    public static class ShiftCipher {

        public static String decrypt(String cipherText, int KEY) {
            return encrypt(cipherText, 26 - KEY);
        }

        public static String encrypt(String plainText, int KEY) {
            KEY = KEY % 26 + 26;
            StringBuilder encryptedText = new StringBuilder();
            for (char i : plainText.toCharArray()) {
                if (Character.isLetter(i)) {
                    if (Character.isUpperCase(i)) {
                        encryptedText.append((char) ('A' + (i - 'A' + KEY) % 26));
                    } else {
                        encryptedText.append((char) ('a' + (i - 'a' + KEY) % 26));
                    }
                } else {
                    encryptedText.append(i);
                }
            }
            return encryptedText.toString();
        }
    }

    // working correctly
    public static class VigenereCipher {
        public static String encrypt(String plainText, final String key) {
            String encryptedText = "";
//            plainText = plainText.toUpperCase();
            for (int i = 0, j = 0; i < plainText.length(); i++) {
                char c = plainText.charAt(i);
                if (c < 'A' || c > 'Z') continue;
                encryptedText += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
                j = ++j % key.length();
            }
            return encryptedText;
        }

        public static String decrypt(String cipherText, final String key) {
            String decryptedText = "";
//            cipherText = cipherText.toUpperCase();
            for (int i = 0, j = 0; i < cipherText.length(); i++) {
                char c = cipherText.charAt(i);

                if (c < 'A' || c > 'Z')
                    continue;

                decryptedText += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
                j = ++j % key.length();
            }
            return decryptedText;
        }
    }

    // working correctly ( issue was in main logic line; added -'A' and +'A' in encrypt and decrypt respectively
    public static class AffineCipher {
        public static String encrypt(String plainText, int a, int b) {
            String encryptedText = "";

            for (int i = 0; i < plainText.length(); i++)
                encryptedText += (char) ((((a * (plainText.charAt(i)) - 'A') + b) % 26) + 'A');

            return encryptedText;
        }

        public static String decrypt(String cipherText, int a, int b) {
            String decryptedText = "";

            int a_inv = 0;
            int flag;

            // for inverse
            for (int i = 1; i < 26; i++) {
                flag = (a * i) % 26;

                if (flag == 1)
                    a_inv = i;
            }

            for (int i = 0; i < cipherText.length(); i++) {
                decryptedText += (char) (((a_inv * ((cipherText.charAt(i) + 'A') - b)) % 26) + 'A');
            }
            return decryptedText;
        }
    }

    // needs to be implemented
    public static class HillCipher {
    } // hill cipher end
} // cipher class end
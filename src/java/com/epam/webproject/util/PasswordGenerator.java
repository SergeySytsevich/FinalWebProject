
package com.epam.webproject.util;

import java.security.SecureRandom;
import java.util.ArrayList;

public class PasswordGenerator {
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyzабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String NUMBERS = "0123456789";
    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 25;
    private static final int NUMBERS_LENGTH = 10;
    private static final int ALPHABET_LENGTH = 59;

    private ArrayList<String> list;

    public PasswordGenerator() {
        list = new ArrayList<>();
        list.add(LOWER_CASE);
        list.add(UPPER_CASE);
        list.add(NUMBERS);
    }
    
    public String generatePassword() {
        int amount = 3;
        int digitsNumber = 2;
        int upperCaseNumber = 1;
        int lowerCaseNumber = 0;
        SecureRandom random = new SecureRandom();
        int length = MIN_LENGTH + random.nextInt(MAX_LENGTH - MIN_LENGTH + 1);
        int digitsAmount = 0;
        int lowerCaseAmount = 0;
        int upperCaseAmount = 0;
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length - digitsNumber; i++) {
            int nextInt = random.nextInt(amount);
            if (nextInt == digitsNumber) {
                password.append(this.list.get(nextInt).charAt(random.nextInt(NUMBERS_LENGTH)));
                digitsAmount++;
            } else if (nextInt == upperCaseNumber) {
                password.append(this.list.get(nextInt).charAt(random.nextInt(ALPHABET_LENGTH)));
                upperCaseAmount++;
            } else {
                password.append(this.list.get(nextInt).charAt(random.nextInt(ALPHABET_LENGTH)));
                lowerCaseAmount++;
            }
        }
        if (digitsAmount <= upperCaseNumber) {
            password.append(this.list.get(digitsNumber).charAt(random.nextInt(NUMBERS_LENGTH)));
        }
        if (lowerCaseAmount <= upperCaseNumber) {
            password.append(this.list.get(lowerCaseNumber).charAt(random.nextInt(ALPHABET_LENGTH)));
        }
        if (upperCaseAmount <= upperCaseNumber) {
            password.append(this.list.get(upperCaseNumber).charAt(random.nextInt(ALPHABET_LENGTH)));
        }
        return password.toString();
    }
}


package com.epam.webproject.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryptor {
    private static final Logger LOGGER = LogManager.getLogger(Encryptor.class);
    private final int signum = 1;
    private final int notation = 16;
    private final int passwordSize = 32;
    private final String passwordConstant = "0";

    public String encryptData(String oldValue) {
        MessageDigest messageDigest;
        byte[] bytes = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(oldValue.getBytes());
            bytes = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.ERROR, "Error with encryption");
        }
        BigInteger bigInteger = new BigInteger(signum, bytes);
        String encryptionResult = bigInteger.toString(notation);
        while (encryptionResult.length() < passwordSize) {
            encryptionResult = passwordConstant + encryptionResult;
        }
        LOGGER.log(Level.INFO, "Encryptor was successful");
        return encryptionResult;
    }
}

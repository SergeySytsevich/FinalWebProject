package com.epam.webproject.util;

import java.util.Random;

public class KeyGenerator {
     public int generateKey() {
        Random random = new Random(System.currentTimeMillis());
        return 1000 + random.nextInt(10001 - 1000);
    }
}

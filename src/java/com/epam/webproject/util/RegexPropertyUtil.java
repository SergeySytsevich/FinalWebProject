package com.epam.webproject.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class RegexPropertyUtil {
    private static final RegexPropertyUtil instance= new RegexPropertyUtil();
    
    // getBundle can throw MissingResourceException , must exists a file without suffix 
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("regexp", Locale.getDefault()); 
    
    private RegexPropertyUtil() {
        
    }

    public static RegexPropertyUtil getInstance() {
        return instance;
    }
    
     public String getProperty(String propertyName) {
        return resourceBundle.getString(propertyName);
    }
}

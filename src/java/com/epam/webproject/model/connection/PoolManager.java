
package com.epam.webproject.model.connection;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import com.epam.webproject.constant.DatabaseConstant;
import com.epam.webproject.constant.DatabaseConstant.DefaultDatabaseConstant;


public class PoolManager {
    private final static Logger LOGGER = LogManager.getLogger(PoolManager.class);
    private Properties properties;
    private String url;
    private ResourceBundle resourceBundle;
    
    public PoolManager() {
        try {
            resourceBundle = ResourceBundle.getBundle(DatabaseConstant.PATH);
            this.properties = new Properties();
            url = resourceBundle.getString(DatabaseConstant.URL);
            putProperties(DatabaseConstant.USER, DefaultDatabaseConstant.DEFAULT_USER, resourceBundle);
            putProperties(DatabaseConstant.PASSWORD, DefaultDatabaseConstant.DEFAULT_PASSWORD, resourceBundle);
            putProperties(DatabaseConstant.USE_UNICODE, DefaultDatabaseConstant.DEFAULT_USE_UNICODE, resourceBundle);
            putProperties(DatabaseConstant.CHARACTER_ENCODING, DefaultDatabaseConstant.DEFAULT_CHARACTER_ENCODING, resourceBundle);
            putProperties(DatabaseConstant.AUTO_RECONNECT, DefaultDatabaseConstant.DEFAULT_AUTO_RECONNECT, resourceBundle);
            putProperties(DatabaseConstant.USE_SSL, DefaultDatabaseConstant.DEFAULT_USE_SSL, resourceBundle);
            putProperties(DatabaseConstant.POOL_SIZE, DefaultDatabaseConstant.DEFAULT_POOL_SIZE, resourceBundle);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            LOGGER.log(Level.INFO, "PoolManager initialization is successful");
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, "Can't find database.properties " + e.getMessage());
            throw new RuntimeException("Can't find database.properties " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "Can't register driver " + e.getMessage());
            throw new RuntimeException("Can't register driver " + e.getMessage());
        }
    }
    
    int getPoolSize() {
        int poolSize;
        try {
            poolSize = Integer.parseInt(resourceBundle.getString(DatabaseConstant.POOL_SIZE));
        } catch (MissingResourceException e) {
            poolSize = Integer.parseInt(DefaultDatabaseConstant.DEFAULT_POOL_SIZE);
            LOGGER.log(Level.ERROR, "Can't find poolSize " + e.getMessage());
        }
        return poolSize;
    }

    String getURL() {
        return url;
    }

    Properties getProperties() {
        return properties;
    }

    void deregisterDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Can't deregister drivers ", e.getMessage());
            }
        }
    }

    private void putProperties(String propertyName, String defaultProperty, ResourceBundle resourceBundle) {
        try {
            properties.put(propertyName, resourceBundle.getString(propertyName));
        } catch (MissingResourceException e) {
            properties.put(propertyName, defaultProperty);
            LOGGER.log(Level.ERROR, "Can't find " + propertyName + " " + e.getMessage());
        }
    }
    
}

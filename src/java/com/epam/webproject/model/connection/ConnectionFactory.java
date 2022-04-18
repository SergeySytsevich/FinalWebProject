package com.epam.webproject.model.connection;

import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final Logger LOG = LogManager.getLogger();
    private static final Properties properties = new Properties();
    private static final String DATABASE_URL;
    private static final String DB_URL = "db.url";
    private static final String DB_DRIVER = "db.driver";
    private static final String RESOURCE = "db.properties";
    
    static {
        String driverName = null;
        try (InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(RESOURCE)) {
            properties.load(inputStream);
            driverName = (String) properties.get(DB_DRIVER);
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            LOG.fatal("driver cann´t be register: " + driverName, e);
            throw new RuntimeException("driver cann´t be register: " + driverName, e);
        } catch (IOException e) {
            LOG.fatal("cann't load properties: ", e);
            throw new RuntimeException("cann't load properties: ", e);
        }
        DATABASE_URL = (String) properties.get(DB_URL);
    }
    
    private ConnectionFactory() {
    }
    
    static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, properties);
    }

}

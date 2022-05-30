package com.epam.webproject.model.connection;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import java.util.Properties;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.sql.DriverManager;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static AtomicBoolean isCreated = new AtomicBoolean();
    private static Lock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connectionBlockingQueue;
    private PoolManager manager;

    private ConnectionPool() {
        initialize();
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                    LOGGER.log(Level.INFO, "Connection pool created successfully");
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void destroyPool() {
        for (int i = 0; i < manager.getPoolSize(); i++) {
            try {
                ProxyConnection proxyConnection = connectionBlockingQueue.take();
                proxyConnection.realClose();
                LOGGER.log(Level.INFO, "Connection is closed successfully");
            } catch (SQLException | InterruptedException e) {
                LOGGER.log(Level.ERROR, "Can't close the connection ", e.getMessage());
            }
            manager.deregisterDriver();
        }
    }

    public ProxyConnection takeConnection() {
        ProxyConnection proxyConnection = null;
        try {
            proxyConnection = connectionBlockingQueue.take();
            LOGGER.log(Level.INFO, "Connection is taken successfully");
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Can't take the connection " + e.getMessage());
        }
        return proxyConnection;
    }

    public void returnConnection(ProxyConnection proxyConnection) {
        try {
            connectionBlockingQueue.put(proxyConnection);
            LOGGER.log(Level.INFO, "Connection is returned successfully");
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, "Can't return connection " + e.getMessage());
        }
    }

    private void initialize() {
        manager = new PoolManager();
        int poolSize = manager.getPoolSize();
        String url = manager.getURL();
        Properties properties = manager.getProperties();
        connectionBlockingQueue = new ArrayBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            try {
                Connection connection = DriverManager.getConnection(url, properties);
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                connectionBlockingQueue.add(proxyConnection);
                LOGGER.log(Level.INFO, i + 1 + " connection initialise successfully");
            } catch (SQLException e) {
                LOGGER.log(Level.FATAL, "Error in connectionPool initialization " + e.getMessage());
                throw new RuntimeException();
            }
        }
    }
    
}

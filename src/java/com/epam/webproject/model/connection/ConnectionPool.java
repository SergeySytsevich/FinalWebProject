package com.epam.webproject.model.connection;

import java.sql.Connection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
    private static final Logger LOG = LogManager.getLogger();
    private static final int DEFAULT_POOL_SIZE = 5;
    private static ConnectionPool instance = new ConnectionPool();
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isAlreadyCreated = new AtomicBoolean(false);
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> usedConnections;
    
    private ConnectionPool() {
        
    }
    
    public static ConnectionPool getInstance() {
        if(!isAlreadyCreated.get()) {
            lock.lock();
            if(instance == null) {
                instance = new ConnectionPool();
                isAlreadyCreated.getAndSet(true);
            }
            lock.unlock();
        }
        return instance;
    }
    public void releaseConnection(Connection connection) {
        if (!(connection instanceof ProxyConnection)) {
            LOG.error("bad connection is detected");
            throw new RuntimeException("bad connection is detected : " + connection);
        }
        usedConnections.remove(connection);
        try {
            freeConnections.put((ProxyConnection) connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
}

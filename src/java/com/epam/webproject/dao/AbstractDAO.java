
package com.epam.webproject.dao;

import com.epam.webproject.entity.Entity;
import com.epam.webproject.model.connection.ProxyConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class AbstractDAO<T extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractDAO.class);
    protected final int successfulNumber = 1;
    ProxyConnection proxyConnection;

    public void setConnection(ProxyConnection proxyConnection) {
        this.proxyConnection = proxyConnection;
    }
}

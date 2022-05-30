package com.epam.webproject.receiver.impl;

import com.epam.webproject.constant.GeneralConstant.FooterConstant;
import com.epam.webproject.constant.GeneralConstant.RoomSQLConstant;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.dao.RoomDAO;
import com.epam.webproject.dao.TransactionManager;
import com.epam.webproject.entity.Room;
import com.epam.webproject.exception.DAOException;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.CommonReceiver;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CommonReceiverImpl implements CommonReceiver {
    private static final Logger LOGGER = LogManager.getLogger(CommonReceiverImpl.class);
    
    @Override
    public void changeLocale(RequestContent requestContent) throws ReceiverException {
        String locale = requestContent.getRequestParameters().get(FooterConstant.LOCALE)[0];
        if (FooterConstant.EN.equals(locale)) {
            requestContent.insertSessionAttributes(FooterConstant.LOCALE, FooterConstant.EN.toLowerCase());
        } else {
            requestContent.insertSessionAttributes(FooterConstant.LOCALE, FooterConstant.RU.toLowerCase());
        }
    }

    @Override
    public void openMainPage(RequestContent requestContent) throws ReceiverException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            RoomDAO roomDAO = new RoomDAO();
            transactionManager.beginTransaction(roomDAO);
            List<Room> rooms = roomDAO.showAllRooms();
            requestContent.getRequestAttributes().put(RoomSQLConstant.ALL_ROOMS, rooms);
            transactionManager.commit();
            LOGGER.log(Level.INFO, "Rooms were found successfully");
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        }
    }
    
}

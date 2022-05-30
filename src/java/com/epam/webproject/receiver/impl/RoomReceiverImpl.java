
package com.epam.webproject.receiver.impl;

import com.epam.webproject.constant.DatabaseConstant.RoomPageConstant;
import com.epam.webproject.constant.GeneralConstant.RoomSQLConstant;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.dao.RoomDAO;
import com.epam.webproject.dao.TransactionManager;
import com.epam.webproject.entity.Room;
import com.epam.webproject.exception.DAOException;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.RoomReceiver;
import com.epam.webproject.util.DateFormatter;
import java.util.List;
import java.sql.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RoomReceiverImpl implements RoomReceiver{
    private static final Logger LOGGER = LogManager.getLogger(RoomReceiverImpl.class);

    @Override
    public void findRooms(RequestContent requestContent) throws ReceiverException {
        TransactionManager transactionManager = new TransactionManager();
        try {
            int beds = Integer.valueOf(requestContent.getRequestParameters().get(RoomPageConstant.BED_NUMBER)[0]);
            int stars = Integer.valueOf(requestContent.getRequestParameters().get(RoomPageConstant.STAR_NUMBER)[0]);
            int price = Integer.valueOf(requestContent.getRequestParameters().get(RoomPageConstant.PRICE_NUMBER)[0]);
            boolean wifi = requestContent.getRequestParameters().get(RoomPageConstant.WIFI) != null;
            String arrivalTime = requestContent.getRequestParameters().get(RoomPageConstant.ARRIVING_DATE)[0];
            String leavingTime = requestContent.getRequestParameters().get(RoomPageConstant.LEAVING_DATE)[0];
            DateFormatter dateFormatter = new DateFormatter();
            Date arrivalDate = dateFormatter.getDate(arrivalTime);
            Date leavingDate = dateFormatter.getDate(leavingTime);
            RoomDAO roomDAO = new RoomDAO();
            transactionManager.beginTransaction(roomDAO);
            List<Room> rooms = roomDAO.findRooms(beds, stars, price, wifi, arrivalDate, leavingDate);
            requestContent.getRequestAttributes().put(RoomPageConstant.BED, beds);
            requestContent.getRequestAttributes().put(RoomPageConstant.STAR, stars);
            requestContent.getRequestAttributes().put(RoomPageConstant.PRICE, price);
            requestContent.getRequestAttributes().put(RoomPageConstant.WIFI_LABEL, wifi);
            requestContent.getRequestAttributes().put(RoomPageConstant.ARRIVE, arrivalTime);
            requestContent.getRequestAttributes().put(RoomPageConstant.LEAVE, leavingTime);
            requestContent.getRequestAttributes().put(RoomPageConstant.BED_NUMBER, beds);
            requestContent.getRequestAttributes().put(RoomPageConstant.STAR_NUMBER, stars);
            requestContent.getRequestAttributes().put(RoomPageConstant.PRICE_NUMBER, price);
            requestContent.getRequestAttributes().put(RoomPageConstant.ARRIVING_DATE, arrivalTime);
            requestContent.getRequestAttributes().put(RoomPageConstant.LEAVING_DATE, leavingTime);
            requestContent.getRequestAttributes().put(RoomSQLConstant.ALL_ROOMS, rooms);
            requestContent.getRequestAttributes().put(RoomPageConstant.ARRIVAL_TIME, arrivalDate);
            requestContent.getRequestAttributes().put(RoomPageConstant.LEAVING_TIME, leavingDate);
            transactionManager.commit();
            LOGGER.log(Level.INFO, "Rooms were found successfully");
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }
    
}

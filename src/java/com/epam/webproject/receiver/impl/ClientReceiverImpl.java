/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.webproject.receiver.impl;

import com.epam.webproject.constant.GeneralConstant;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.dao.ClientDAO;
import com.epam.webproject.dao.RoomDAO;
import com.epam.webproject.dao.TransactionManager;
import com.epam.webproject.entity.Entity;
import com.epam.webproject.entity.Room;
import com.epam.webproject.exception.DAOException;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.ClientReceiver;
import com.epam.webproject.util.DateFormatter;
import com.epam.webproject.util.RoleChecker;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientReceiverImpl implements ClientReceiver{
    private static final Logger LOGGER = LogManager.getLogger(ClientReceiverImpl.class);
    
    @Override
    public void openClientApplications(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String login = requestContent.getSessionAttributes().get(GeneralConstant.UserPageConstant.LOGIN).toString();
        TransactionManager transactionManager = new TransactionManager();
        try {
            ClientDAO clientDAO = new ClientDAO();
            transactionManager.beginTransaction(clientDAO);
            ArrayList<ArrayList<Entity>> list = clientDAO.findClientApps(login);
            requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_APPS, list);
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void bookRoom(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        int id = Integer.parseInt(requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.ID)[0]);
        String arrivalTime = requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.ARRIVAL_TIME)[0];
        String leavingTime = requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.LEAVING_TIME)[0];
        DateFormatter dateFormatter = new DateFormatter();
        Date arrivalDate = dateFormatter.getDate(arrivalTime);
        Date leavingDate = dateFormatter.getDate(leavingTime);
        String cost = requestContent.getRequestParameters().get(GeneralConstant.OrderSQLConstant.COST)[0];
        String login = requestContent.getSessionAttributes().get(GeneralConstant.UserPageConstant.LOGIN).toString();
        TransactionManager transactionManager = new TransactionManager();
        try {
            ClientDAO clientDAO = new ClientDAO();
            RoomDAO roomDAO = new RoomDAO();
            transactionManager.beginTransaction(clientDAO, roomDAO);
            if (clientDAO.createOrder(login, id, arrivalDate, leavingDate, new BigDecimal(cost))) {
                transactionManager.commit();
                List<Room> rooms = roomDAO.showAllRooms();
                transactionManager.commit();
                requestContent.getRequestAttributes().put(GeneralConstant.RoomSQLConstant.ALL_ROOMS, rooms);
                LOGGER.log(Level.INFO, "Order was created successfully");
            }
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void cancelRoom(RequestContent requestContent) throws ReceiverException {
         new RoleChecker().checkRole(requestContent);
        int id = Integer.parseInt(requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.ID)[0]);
        String login = requestContent.getSessionAttributes().get(GeneralConstant.UserPageConstant.LOGIN).toString();
        TransactionManager transactionManager = new TransactionManager();
        try {
            ClientDAO clientDAO = new ClientDAO();
            transactionManager.beginTransaction(clientDAO);
            if (clientDAO.cancelRoom(id)) {
                ArrayList<ArrayList<Entity>> list = clientDAO.findClientApps(login);
                requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_APPS, list);
                transactionManager.commit();
                LOGGER.log(Level.INFO, "Room in order was deleted successfully");
            } else {
                transactionManager.rollback();
            }
        } catch (DAOException e) {
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void openClientPendingApplications(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String login = requestContent.getSessionAttributes().get(GeneralConstant.UserPageConstant.LOGIN).toString();
        TransactionManager transactionManager = new TransactionManager();
        try {
            ClientDAO clientDAO = new ClientDAO();
            transactionManager.beginTransaction(clientDAO);
            ArrayList<ArrayList<Entity>> list = clientDAO.findPendingApps(login);
            transactionManager.commit();
            requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.PENDING_APPS, list);
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }
    
}

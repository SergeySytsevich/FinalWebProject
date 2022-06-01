package com.epam.webproject.receiver.impl;

import com.epam.webproject.constant.GeneralConstant;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.dao.AdminDAO;
import com.epam.webproject.dao.RoomDAO;
import com.epam.webproject.dao.TransactionManager;
import com.epam.webproject.dao.UserDAO;
import com.epam.webproject.entity.Entity;
import com.epam.webproject.entity.Room;
import com.epam.webproject.entity.RoomType;
import com.epam.webproject.entity.User;
import com.epam.webproject.entity.UserRole;
import com.epam.webproject.exception.DAOException;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.AdminReceiver;
import com.epam.webproject.util.RoleChecker;
import com.epam.webproject.validator.UserValidator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AdminReceiverImpl implements AdminReceiver {
    private static final Logger LOGGER = LogManager.getLogger(AdminReceiverImpl.class);

    @Override
    public void openIncomingApps(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        TransactionManager transactionManager = new TransactionManager();
        try {
            AdminDAO adminDAO = new AdminDAO();
            transactionManager.beginTransaction(adminDAO);
            ArrayList<ArrayList<Entity>> list = adminDAO.findIncomingApps();
            requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_APPS, list);
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void approveOrder(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String orderId = requestContent.getRequestParameters().get(GeneralConstant.OrderSQLConstant.ORDER_ID)[0];
        String login = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.LOGIN)[0];
        String cost = requestContent.getRequestParameters().get(GeneralConstant.OrderSQLConstant.COST)[0];
        TransactionManager transactionManager = new TransactionManager();
        AdminDAO adminDAO = new AdminDAO();
        transactionManager.beginTransaction(adminDAO);
        try {
            if (adminDAO.approveOrder(orderId)) {
                transactionManager.commit();
                if (adminDAO.deductFromBalance(login, new BigDecimal(cost))) {
                    transactionManager.commit();
                } else {
                    LOGGER.log(Level.ERROR, "Error in deduction from balance");
                    transactionManager.rollback();
                }
            } else {
                LOGGER.log(Level.ERROR, "Error in approving order");
                transactionManager.rollback();
            }
            ArrayList<ArrayList<Entity>> list = adminDAO.findIncomingApps();
            requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_APPS, list);
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            e.printStackTrace();
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void revokeOrder(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String orderId = requestContent.getRequestParameters().get(GeneralConstant.OrderSQLConstant.ORDER_ID)[0];
        TransactionManager transactionManager = new TransactionManager();
        AdminDAO adminDAO = new AdminDAO();
        transactionManager.beginTransaction(adminDAO);
        try {
            if (adminDAO.revokeOrder(orderId)) {
                transactionManager.commit();
            } else {
                transactionManager.rollback();
            }
            ArrayList<ArrayList<Entity>> list = adminDAO.findIncomingApps();
            requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_APPS, list);
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void addRoom(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        int beds = Integer.valueOf(requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.BED_NUMBER)[0]);
        int stars = Integer.valueOf(requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.STAR_NUMBER)[0]);
        BigDecimal price = new BigDecimal(requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.PRICE_NUMBER)[0]);
        boolean wifi = requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.WIFI) != null;
        String description = requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.DESCRIPTION)[0];
        RoomType roomType = RoomType.findByName(requestContent.getRequestParameters().get(GeneralConstant.RoomPageConstant.TYPE)[0]);
        Room room = new Room();
        room.setWifi(wifi);
        room.setCapacity(beds);
        room.setStarCount(stars);
        room.setPrice(price);
        room.setDescription(description);
        room.setType(roomType);
        TransactionManager transactionManager = new TransactionManager();
        try {
            AdminDAO adminDAO = new AdminDAO();
            transactionManager.beginTransaction(adminDAO);
            if (adminDAO.createRoom(room)) {
                transactionManager.commit();
                LOGGER.log(Level.INFO, "Room was added successfully");
            } else {
                transactionManager.rollback();
            }
            requestContent.getRequestAttributes().put(GeneralConstant.AdminConstant.ADD_ROOM_FORM, true);
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void openAllApps(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        TransactionManager transactionManager = new TransactionManager();
        AdminDAO adminDAO = new AdminDAO();
        transactionManager.beginTransaction(adminDAO);
        try {
            ArrayList list = adminDAO.findAllApps();
            requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_APPS, list);
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void openAllUsers(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        AdminDAO adminDAO = new AdminDAO();
        TransactionManager transactionManager = new TransactionManager();
        transactionManager.beginTransaction(adminDAO);
        try {
            ArrayList<User> list = adminDAO.findAllUsers();
            transactionManager.commit();
            requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_USERS, list);
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void blockUser(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String login = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.LOGIN)[0];
        TransactionManager transactionManager = new TransactionManager();
        AdminDAO adminDAO = new AdminDAO();
        transactionManager.beginTransaction(adminDAO);
        try {
            if (adminDAO.blockUnblockUser(login, true)) {
                ArrayList<User> list = adminDAO.findAllUsers();
                requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_USERS, list);
                transactionManager.commit();
            } else {
                transactionManager.rollback();
            }
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void unblockUser(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String login = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.LOGIN)[0];
        TransactionManager transactionManager = new TransactionManager();
        AdminDAO adminDAO = new AdminDAO();
        transactionManager.beginTransaction(adminDAO);
        try {
            if (adminDAO.blockUnblockUser(login, false)) {
                ArrayList<User> list = adminDAO.findAllUsers();
                requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.ALL_USERS, list);
                transactionManager.commit();
            } else {
                transactionManager.rollback();
            }
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void addRoomForm(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        requestContent.getRequestAttributes().put(GeneralConstant.AdminConstant.ADD_ROOM_FORM, true);
    }

    @Override
    public void openAddUserForm(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        requestContent.getRequestAttributes().put(GeneralConstant.AdminConstant.ADD_USER_FORM, true);
    }

    @Override
    public void addUser(RequestContent requestContent) throws ReceiverException {
       new RoleChecker().checkRole(requestContent);
        UserValidator userValidator = new UserValidator();
        String name = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.NAME)[0];
        String surname = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.SURNAME)[0];
        String country = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.COUNTRY)[0];
        String age = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.AGE)[0];
        String login = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.LOGIN)[0];
        String mail = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.MAIL)[0];
        String password = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.PASSWORD)[0];
        String passwordRepetition = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.PASSWORD_REPETITION)[0];
        String role = requestContent.getRequestParameters().get(GeneralConstant.UserPageConstant.SELECT_ROLE)[0];
        if (userValidator.validateUserInfo(name, surname, country, age, mail, login, password, passwordRepetition, requestContent)) {
            TransactionManager transactionManager = new TransactionManager();
            try {
                UserDAO userDAO = new UserDAO();
                transactionManager.beginTransaction(userDAO);
                if (!userDAO.findEmail(mail)) {
                    if (!userDAO.findLogin(login)) {
                        User user = new User();
                        user.setName(name);
                        user.setSurname(surname);
                        user.setCountry(country);
                        user.setAge(Integer.parseInt(age));
                        user.setLogin(login);
                        user.setMail(mail);
                        user.setPassword(password);
                        user.setRole(UserRole.findByName(role));
                        if (userDAO.create(user)) {
                            if (userDAO.updateIsAuthorized(login)) {
                                transactionManager.commit();
                            } else {
                                transactionManager.rollback();
                            }
                            requestContent.getRequestAttributes().put(GeneralConstant.AdminConstant.ADD_USER_FORM, true);
                            LOGGER.log(Level.INFO, "User was created successfully");
                        }
                    } else {
                        requestContent.getRequestAttributes().put(GeneralConstant.UserPageConstant.EXISTING_LOGIN, true);
                        transactionManager.rollback();
                        LOGGER.log(Level.ERROR, "Login exists");
                    }
                } else {
                    requestContent.getRequestAttributes().put(GeneralConstant.UserPageConstant.EXISTING_EMAIL, true);
                    transactionManager.rollback();
                    LOGGER.log(Level.ERROR, "Email exists");
                }
            } catch (DAOException e) {
                transactionManager.rollback();
                throw new ReceiverException(e.getMessage());
            } finally {
                transactionManager.endTransaction();
            }
        }
    }

    @Override
    public void openDeleteRoomForm(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        TransactionManager transactionManager = new TransactionManager();
        try {
            RoomDAO roomDAO = new RoomDAO();
            transactionManager.beginTransaction(roomDAO);
            List<Room> rooms = roomDAO.showAllRooms();
            transactionManager.commit();
            requestContent.getRequestAttributes().put(GeneralConstant.AdminConstant.ALL_ROOMS, rooms);
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void deleteRoom(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        int id = Integer.parseInt(requestContent.getRequestParameters().get(GeneralConstant.RoomSQLConstant.ROOM_ID)[0]);
        TransactionManager transactionManager = new TransactionManager();
        try {
            AdminDAO adminDAO = new AdminDAO();
            RoomDAO roomDAO = new RoomDAO();
            transactionManager.beginTransaction(adminDAO, roomDAO);
            if (adminDAO.deleteRoom(id)) {
                List<Room> rooms = roomDAO.showAllRooms();
                transactionManager.commit();
                requestContent.getRequestAttributes().put(GeneralConstant.AdminConstant.ALL_ROOMS, rooms);
            }
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }
    
}

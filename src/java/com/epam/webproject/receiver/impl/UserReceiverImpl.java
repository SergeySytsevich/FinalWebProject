
package com.epam.webproject.receiver.impl;

import com.epam.webproject.constant.GeneralConstant;
import com.epam.webproject.constant.GeneralConstant.UserPageConstant;
import static com.epam.webproject.constant.MailConstant.MailMessage.CONFIRM_MAIL_MESSAGE;
import static com.epam.webproject.constant.MailConstant.MailMessage.RESET_PASSWORD_MESSAGE;
import static com.epam.webproject.constant.MailConstant.MailTheme.CONFIRM_MAIL;
import static com.epam.webproject.constant.MailConstant.MailTheme.RESET_PASSWORD;
import com.epam.webproject.util.PasswordGenerator;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.dao.TransactionManager;
import com.epam.webproject.dao.UserDAO;
import com.epam.webproject.entity.User;
import com.epam.webproject.entity.UserRole;
import com.epam.webproject.exception.DAOException;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.UserReceiver;
import com.epam.webproject.util.Encryptor;
import com.epam.webproject.util.KeyGenerator;
import com.epam.webproject.util.MailSender;
import com.epam.webproject.util.RoleChecker;
import com.epam.webproject.validator.UserValidator;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.math.BigDecimal;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserReceiverImpl implements UserReceiver{
    private static final Logger LOGGER = LogManager.getLogger(UserReceiverImpl.class);
    
    @Override
    public void signUp(RequestContent requestContent) throws ReceiverException {
        UserValidator userValidator = new UserValidator();
        String name = requestContent.getRequestParameters().get(UserPageConstant.NAME)[0];
        String surname = requestContent.getRequestParameters().get(UserPageConstant.SURNAME)[0];
        String country = requestContent.getRequestParameters().get(UserPageConstant.COUNTRY)[0];
        String age = requestContent.getRequestParameters().get(UserPageConstant.AGE)[0];
        String login = requestContent.getRequestParameters().get(UserPageConstant.LOGIN)[0];
        String mail = requestContent.getRequestParameters().get(UserPageConstant.MAIL)[0];
        String password = requestContent.getRequestParameters().get(UserPageConstant.PASSWORD)[0];
        String passwordRepetition = requestContent.getRequestParameters().get(UserPageConstant.PASSWORD_REPETITION)[0];
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
                        user.setRole(UserRole.CLIENT);
                        if (userDAO.create(user)) {
                            requestContent.insertSessionAttributes(UserPageConstant.LOGIN, user.getLogin());
                            MailSender mailSender = new MailSender();
                            KeyGenerator keyGenerator = new KeyGenerator();
                            int key = keyGenerator.generateKey();
                            mailSender.sendMessage(user.getMail(), CONFIRM_MAIL, CONFIRM_MAIL_MESSAGE + key);
                            requestContent.getSessionAttributes().put(GeneralConstant.CommonConstant.KEY, key);
                            transactionManager.commit();
                            LOGGER.log(Level.INFO, "User was created successfully");
                        }
                    } else {
                        requestContent.getRequestAttributes().put(UserPageConstant.EXISTING_LOGIN, true);
                        transactionManager.rollback();
                        LOGGER.log(Level.ERROR, "Login exists");
                    }
                } else {
                    requestContent.getRequestAttributes().put(UserPageConstant.EXISTING_EMAIL, true);
                    transactionManager.rollback();
                    LOGGER.log(Level.ERROR, "Email exists");
                }
                requestContent.getRequestAttributes().put(UserPageConstant.NAME, name);
                requestContent.getRequestAttributes().put(UserPageConstant.SURNAME, surname);
                requestContent.getRequestAttributes().put(UserPageConstant.COUNTRY, country);
                requestContent.getRequestAttributes().put(UserPageConstant.AGE, age);
                requestContent.getRequestAttributes().put(UserPageConstant.MAIL, mail);
                requestContent.getRequestAttributes().put(UserPageConstant.LOGIN, login);
            } catch (DAOException | MessagingException e) {
                transactionManager.rollback();
                throw new ReceiverException(e.getMessage());
            } finally {
                transactionManager.endTransaction();
            }
        }
    }

    @Override
    public void signIn(RequestContent requestContent) throws ReceiverException {
        String login = requestContent.getRequestParameters().get(UserPageConstant.LOGIN)[0];
        String password = requestContent.getRequestParameters().get(UserPageConstant.PASSWORD)[0];
        TransactionManager transactionManager = new TransactionManager();
        try {
            UserDAO userDAO = new UserDAO();
            transactionManager.beginTransaction(userDAO);
            User user = userDAO.findUser(login, new Encryptor().encryptData(password));
            transactionManager.commit();
            if (!userDAO.findLogin(login)) {
                transactionManager.rollback();
                requestContent.getRequestAttributes().put(UserPageConstant.NON_EXISTING_LOGIN, true);
            } else if (user != null) {
                if (user.isBlocked()) {
                    transactionManager.rollback();
                    requestContent.getRequestAttributes().put(UserPageConstant.IS_BLOCKED, true);
                } else if (!user.isAuthorized()) {
                    transactionManager.rollback();
                    requestContent.getRequestAttributes().put(UserPageConstant.IS_NOT_AUTHORIZED, true);
                } else {
                    requestContent.insertSessionAttributes(UserPageConstant.ROLE, user.getRole());
                    requestContent.insertSessionAttributes(UserPageConstant.LOGIN, login);
                    transactionManager.commit();
                }
            } else {
                transactionManager.rollback();
                requestContent.getRequestAttributes().put(UserPageConstant.WRONG_PASSWORD, true);
            }
            requestContent.getRequestAttributes().put(UserPageConstant.LOGIN, login);
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void logOut(RequestContent requestContent) throws ReceiverException {
        requestContent.getSessionAttributes().remove(UserPageConstant.LOGIN);
        requestContent.getSessionAttributes().remove(UserPageConstant.PASSWORD);
        requestContent.getSessionAttributes().remove(UserPageConstant.ROLE);
        LOGGER.log(Level.INFO, "log out was successful");
    }

    @Override
    public void confirmMail(RequestContent requestContent) throws ReceiverException {
        String key = requestContent.getSessionAttributes().get(GeneralConstant.CommonConstant.KEY).toString();
        String userKey = requestContent.getRequestParameters().get(UserPageConstant.CONFIRM_MAIL)[0];
        String login = requestContent.getSessionAttributes().get(UserPageConstant.LOGIN).toString();
        if (key.equals(userKey)) {
            requestContent.getSessionAttributes().remove(GeneralConstant.CommonConstant.KEY);
            TransactionManager transactionManager = new TransactionManager();
            try {
                UserDAO userDAO = new UserDAO();
                transactionManager.beginTransaction(userDAO);
                if (userDAO.updateIsAuthorized(login)) {
                    transactionManager.commit();
                    UserRole userRole = userDAO.findRoleByLogin(login);
                    requestContent.getSessionAttributes().put(UserPageConstant.ROLE, userRole);
                } else {
                    transactionManager.rollback();
                }
            } catch (DAOException e) {
                transactionManager.rollback();
                throw new ReceiverException(e.getMessage());
            } finally {
                transactionManager.endTransaction();
            }
        } else {
            requestContent.getRequestAttributes().put(UserPageConstant.WRONG_KEY, true);
        }
    }

    @Override
    public void resetPassword(RequestContent requestContent) throws ReceiverException {
        String email = requestContent.getRequestParameters().get(UserPageConstant.MAIL)[0];
        TransactionManager transactionManager = new TransactionManager();
        try {
            UserDAO userDAO = new UserDAO();
            transactionManager.beginTransaction(userDAO);
            if (userDAO.findEmail(email)) {
                LOGGER.log(Level.INFO, "email was found");
                String password = new PasswordGenerator().generatePassword();
                if (userDAO.changePasswordByEmail(email, new Encryptor().encryptData(password))) {
                    MailSender mailSender = new MailSender();
                    mailSender.sendMessage(email, RESET_PASSWORD, RESET_PASSWORD_MESSAGE + password);
                    transactionManager.commit();
                    LOGGER.log(Level.INFO, "Password was sent and changed successfully in database");
                } else {
                    transactionManager.rollback();
                    LOGGER.log(Level.ERROR, "Error with changing password in database");
                }
            } else {
                transactionManager.rollback();
                requestContent.getRequestAttributes().put(UserPageConstant.NON_EXISTING_EMAIL, true);
                LOGGER.log(Level.ERROR, "Email doesn't exist");
            }
        } catch (DAOException | MessagingException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void sendEmailToAuthorize(RequestContent requestContent) throws ReceiverException {
        String email = requestContent.getRequestParameters().get(UserPageConstant.MAIL)[0];
        TransactionManager transactionManager = new TransactionManager();
        try {
            UserDAO userDAO = new UserDAO();
            transactionManager.beginTransaction(userDAO);
            if (userDAO.findEmail(email)) {
                LOGGER.log(Level.INFO, "email was found");
                String login = userDAO.findLoginByEmail(email);
                UserRole role = userDAO.findRoleByLogin(login);
                requestContent.insertSessionAttributes(UserPageConstant.ROLE, role);
                requestContent.insertSessionAttributes(UserPageConstant.LOGIN, login);
                MailSender mailSender = new MailSender();
                KeyGenerator keyGenerator = new KeyGenerator();
                int key = keyGenerator.generateKey();
                mailSender.sendMessage(email, CONFIRM_MAIL, CONFIRM_MAIL_MESSAGE + key);
                requestContent.getSessionAttributes().put(GeneralConstant.CommonConstant.KEY, key);
                transactionManager.commit();
                LOGGER.log(Level.INFO, "Key was sent");
            } else {
                transactionManager.rollback();
                requestContent.getRequestAttributes().put(UserPageConstant.NON_EXISTING_EMAIL, true);
                LOGGER.log(Level.ERROR, "Email doesn't exist");
            }
        } catch (DAOException | MessagingException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void openProfile(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String login = requestContent.getSessionAttributes().get(GeneralConstant.UserPageConstant.LOGIN).toString();
        TransactionManager transactionManager = new TransactionManager();
        UserDAO userDAO = new UserDAO();
        transactionManager.beginTransaction(userDAO);
        try {
            User user = userDAO.findUserByLogin(login);
            ArrayList<User> list = new ArrayList<>();
            list.add(user);
            requestContent.getRequestAttributes().put(GeneralConstant.CommonConstant.USER_INFO, list);
            transactionManager.commit();
        } catch (DAOException e) {
            transactionManager.rollback();
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
        
        
    }

    @Override
    public void openLoginSetting(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String login = requestContent.getSessionAttributes().get(UserPageConstant.LOGIN).toString();
        requestContent.getRequestAttributes().put(UserPageConstant.USER_LOGIN, login);
    }

    @Override
    public void changeLogin(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String oldLogin = requestContent.getSessionAttributes().get(UserPageConstant.LOGIN).toString();
        String newLogin = requestContent.getRequestParameters().get(UserPageConstant.LOGIN)[0];
        TransactionManager transactionManager = new TransactionManager();
        UserDAO userDAO = new UserDAO();
        transactionManager.beginTransaction(userDAO);
        try {
            if (!userDAO.findLogin(newLogin)) {
                if (userDAO.changeLogin(oldLogin, newLogin)) {
                    LOGGER.log(Level.INFO, "Login was changed successfully");
                    requestContent.getSessionAttributes().put(UserPageConstant.LOGIN, newLogin);
                    requestContent.getRequestAttributes().put(UserPageConstant.USER_LOGIN, newLogin);
                    transactionManager.commit();
                } else {
                    requestContent.getRequestAttributes().put(UserPageConstant.USER_LOGIN, oldLogin);
                    requestContent.getRequestAttributes().put(UserPageConstant.LOGIN, oldLogin);
                    transactionManager.rollback();
                }
            } else {
                LOGGER.log(Level.ERROR, "Login exists in database");
                transactionManager.rollback();
                requestContent.getRequestAttributes().put(UserPageConstant.USER_LOGIN, oldLogin);
                requestContent.getRequestAttributes().put(UserPageConstant.EXISTING_LOGIN, true);
            }
        } catch (DAOException e) {
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void openPasswordSetting(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        requestContent.getRequestAttributes().put(UserPageConstant.PASSWORD_SETTING, true);
    }

    @Override
    public void changePassword(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String login = requestContent.getSessionAttributes().get(UserPageConstant.LOGIN).toString();
        String oldPassword = requestContent.getRequestParameters().get(UserPageConstant.PASSWORD)[0];
        String newPassword = requestContent.getRequestParameters().get(UserPageConstant.NEW_PASSWORD)[0];
        TransactionManager transactionManager = new TransactionManager();
        try {
            UserDAO userDAO = new UserDAO();
            transactionManager.beginTransaction(userDAO);
            User user = userDAO.findUser(login, new Encryptor().encryptData(oldPassword));
            if (user != null) {
                if (userDAO.changePassword(login, new Encryptor().encryptData(newPassword))) {
                    transactionManager.commit();
                    requestContent.getRequestAttributes().put(UserPageConstant.PASSWORD_SETTING, true);
                    LOGGER.log(Level.INFO, "Password was changed successfully");
                } else {
                    transactionManager.rollback();
                    requestContent.getRequestAttributes().put(UserPageConstant.PASSWORD_SETTING, true);
                    LOGGER.log(Level.ERROR, "Error with changing password in database");
                }
            } else {
                transactionManager.rollback();
                requestContent.getRequestAttributes().put(UserPageConstant.WRONG_OLD_PASSWORD, true);
                requestContent.getRequestAttributes().put(UserPageConstant.PASSWORD_SETTING, true);
                LOGGER.log(Level.ERROR, "Password doesn't exist in database");
            }
        } catch (DAOException e) {
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }

    @Override
    public void openBalanceForm(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.BALANCE_FORM, true);
    }

    @Override
    public void topUpBalance(RequestContent requestContent) throws ReceiverException {
        new RoleChecker().checkRole(requestContent);
        String login = requestContent.getSessionAttributes().get(UserPageConstant.LOGIN).toString();
        BigDecimal money = new BigDecimal(requestContent.getRequestParameters().get(GeneralConstant.UserSQLConstant.BALANCE)[0]);
        TransactionManager transactionManager = new TransactionManager();
        try {
            UserDAO userDAO = new UserDAO();
            transactionManager.beginTransaction(userDAO);
            if (userDAO.topUpBalance(login, money)) {
                transactionManager.commit();
            } else {
                LOGGER.log(Level.ERROR, "Error in top up balance");
                transactionManager.rollback();
            }
            requestContent.getRequestAttributes().put(GeneralConstant.ClientUserPageConstant.BALANCE_FORM, true);
        } catch (DAOException e) {
            throw new ReceiverException(e.getMessage());
        } finally {
            transactionManager.endTransaction();
        }
    }
    
}


package com.epam.webproject.dao;

import com.epam.webproject.constant.SQLRequestConstant;
import com.epam.webproject.constant.GeneralConstant;
import com.epam.webproject.constant.GeneralConstant.UserSQLConstant;
import com.epam.webproject.entity.User;
import com.epam.webproject.entity.UserRole;
import com.epam.webproject.exception.DAOException;
import com.epam.webproject.util.Encryptor;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.math.BigDecimal;

public class UserDAO extends AbstractDAO<User> {
    private static final Logger LOGGER = LogManager.getLogger(UserDAO.class);

    public boolean create(User entity) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.CREATE_USER)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getSurname());
            preparedStatement.setString(3, entity.getCountry());
            preparedStatement.setInt(4, entity.getAge());
            preparedStatement.setString(5, entity.getLogin());
            preparedStatement.setString(6, new Encryptor().encryptData(entity.getPassword()));
            preparedStatement.setString(7, entity.getMail());
            preparedStatement.setString(8, String.valueOf(entity.getRole()));
            LOGGER.log(Level.INFO, "User was created successfully");
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException("Error with creating user" + e.getMessage());
        }
    }
    
    public User findUser(String login, String password) throws DAOException {
        User user = null;
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.FIND_USER)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setAuthorized(resultSet.getBoolean(UserSQLConstant.IS_AUTHORIZED));
                user.setBlocked(resultSet.getBoolean(UserSQLConstant.IS_BLOCKED));
                user.setRole(UserRole.findByName(resultSet.getString(GeneralConstant.UserPageConstant.ROLE)));
                LOGGER.log(Level.INFO, "User was found successfully");
            }
        } catch (SQLException e) {
            throw new DAOException("Find user error ", e);
        }
        return user;
    }
    
    public boolean findEmail(String email) throws DAOException {
        boolean answer = false;
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.FIND_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                answer = true;
                LOGGER.log(Level.INFO, "email exist in db");
            }
        } catch (SQLException e) {
            throw new DAOException("Find email error ", e);
        }
        return answer;
    }
    
    public boolean findLogin(String login) throws DAOException {
        boolean answer = false;
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.FIND_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                answer = true;
                LOGGER.log(Level.INFO, "login exist in db");
            }
        } catch (SQLException e) {
            throw new DAOException("Find login error ", e);
        }
        return answer;
    }
    
    public boolean updateIsAuthorized(String login) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.UPDATE_IS_AUTHORIZED)) {
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public String findLoginByEmail(String email) throws DAOException {
        String login = null;
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.FIND_LOGIN_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                login = resultSet.getString(GeneralConstant.UserPageConstant.LOGIN);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return login;
    }
    
    public UserRole findRoleByLogin(String login) throws DAOException {
        UserRole role = null;
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.FIND_ROLE_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = UserRole.findByName(resultSet.getString(GeneralConstant.UserPageConstant.ROLE));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return role;
    }
    
    public User findUserByLogin(String login) throws DAOException {
        User user = new User();
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.FIND_USER_INFO)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.setName(resultSet.getString(GeneralConstant.UserPageConstant.NAME));
                user.setSurname(resultSet.getString(GeneralConstant.UserPageConstant.SURNAME));
                user.setAge(resultSet.getInt(GeneralConstant.UserPageConstant.AGE));
                user.setCountry(resultSet.getString(GeneralConstant.UserPageConstant.COUNTRY));
                user.setLogin(resultSet.getString(GeneralConstant.UserPageConstant.LOGIN));
                user.setBalance(resultSet.getBigDecimal(UserSQLConstant.BALANCE));
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return user;
    }
    
    public boolean changeLogin(String oldLogin, String newLogin) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.CHANGE_LOGIN)) {
            preparedStatement.setString(1, newLogin);
            preparedStatement.setString(2, oldLogin);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public boolean changePassword(String login, String password) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.CHANGE_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, login);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public boolean topUpBalance(String login, BigDecimal money) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.TOP_UP_BALANCE)) {
            preparedStatement.setString(1, login);
            preparedStatement.setBigDecimal(2, money);
            preparedStatement.setString(3, login);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean changePasswordByEmail(String email, String password) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.CHANGE_PASSWORD_BY_EMAIL)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
}

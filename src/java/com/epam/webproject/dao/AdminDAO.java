package com.epam.webproject.dao;

import com.epam.webproject.constant.SQLRequestConstant;
import com.epam.webproject.constant.GeneralConstant;
import com.epam.webproject.entity.BusyDate;
import com.epam.webproject.entity.Entity;
import com.epam.webproject.entity.Order;
import com.epam.webproject.entity.Room;
import com.epam.webproject.entity.RoomType;
import com.epam.webproject.entity.User;
import com.epam.webproject.exception.DAOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDAO extends AbstractDAO<Entity>{
    
    public ArrayList<ArrayList<Entity>> findIncomingApps() throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.FIND_INCOMING_APPS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getAppsInfo(resultSet, false);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public boolean approveOrder(String order_id) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.APPROVE_ORDER)) {
            preparedStatement.setString(1, order_id);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public boolean deductFromBalance(String login, BigDecimal cost) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.DEDUCT_FROM_BALANCE)) {
            preparedStatement.setString(1, login);
            preparedStatement.setBigDecimal(2, cost);
            preparedStatement.setString(3, login);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public boolean revokeOrder(String order_id) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.REVOKE_ORDER)) {
            preparedStatement.setString(1, order_id);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
  
    public ArrayList<ArrayList<Entity>> findAllApps() throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.SHOW_ALL_APPS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return getAppsInfo(resultSet, true);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public ArrayList<User> findAllUsers() throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.SHOW_ALL_USERS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<User> list = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString(GeneralConstant.UserPageConstant.LOGIN));
                user.setBalance(resultSet.getBigDecimal(GeneralConstant.UserSQLConstant.BALANCE));
                user.setBlocked(resultSet.getBoolean(GeneralConstant.UserSQLConstant.IS_BLOCKED));// FIXME UserPageConstant && UserSQLConstant
                list.add(user);
            }
            return list;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public boolean blockUnblockUser(String login, boolean isToBlock) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.BLOCK_UNBLOCK_USER)) {
            preparedStatement.setBoolean(1, isToBlock);
            preparedStatement.setString(2, login);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    public boolean createRoom(Room room) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.CREATE_ROOM)) {
            preparedStatement.setInt(1, room.getCapacity());
            preparedStatement.setBigDecimal(2, room.getPrice());
            preparedStatement.setBoolean(3, room.isWifi());
            preparedStatement.setInt(4, room.getStarCount());
            preparedStatement.setString(5, room.getDescription());
            preparedStatement.setString(6, room.getType().getName());
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
    public boolean deleteRoom(int id) throws DAOException {
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.DELETE_ROOM)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() == successfulNumber;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }
    
   private ArrayList<ArrayList<Entity>> getAppsInfo(ResultSet resultSet, boolean isApproved) throws SQLException {
        ArrayList<ArrayList<Entity>> list = new ArrayList<>();
        while (resultSet.next()) {
            ArrayList<Entity> entities = new ArrayList<>();
            Room room = new Room();
            room.setCapacity(resultSet.getInt(GeneralConstant.RoomSQLConstant.ROOM_CAPACITY));
            room.setType(RoomType.findByName(resultSet.getString(GeneralConstant.RoomSQLConstant.TYPE)));
            room.setDescription(resultSet.getString(GeneralConstant.RoomSQLConstant.DESCRIPTION));
            room.setPrice(resultSet.getBigDecimal(GeneralConstant.RoomSQLConstant.PRICE));
            room.setStarCount(resultSet.getInt(GeneralConstant.RoomSQLConstant.STAR_COUNT));
            room.setWifi(resultSet.getBoolean(GeneralConstant.RoomSQLConstant.WIFI));
            entities.add(room);
            BusyDate busyDate = new BusyDate();//1
            busyDate.setArrivalTime(resultSet.getDate(GeneralConstant.BusyDateSQLConstant.ARRIVAL_TIME));
            busyDate.setReleaseDate(resultSet.getDate(GeneralConstant.BusyDateSQLConstant.RELEASE_TIME));
            entities.add(busyDate);
            User user = new User();//2
            user.setLogin(resultSet.getString(GeneralConstant.UserPageConstant.LOGIN));
            user.setBalance(resultSet.getBigDecimal(GeneralConstant.UserSQLConstant.BALANCE));
            entities.add(user);
            Order order = new Order();//3
            if (isApproved) {
                order.setApproved(resultSet.getBoolean(GeneralConstant.OrderSQLConstant.IS_APPROVED));
                order.setRejected(resultSet.getBoolean(GeneralConstant.OrderSQLConstant.IS_REJECTED));
            }
            
            order.setId(resultSet.getInt(GeneralConstant.OrderSQLConstant.ORDER_ID));
            entities.add(order);
            list.add(entities);
        }
        return list;
    }
}

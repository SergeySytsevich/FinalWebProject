
package com.epam.webproject.dao;

import com.epam.webproject.constant.GeneralConstant.RoomSQLConstant;
import com.epam.webproject.constant.SQLRequestConstant;
import com.epam.webproject.entity.Entity;
import com.epam.webproject.entity.Room;
import com.epam.webproject.entity.RoomType;
import com.epam.webproject.exception.DAOException;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.math.BigDecimal;


public class RoomDAO extends AbstractDAO<Entity>{
    
    public List<Room> showAllRooms() throws DAOException {
        List<Room> rooms;
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.SHOW_ALL_ROOMS)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            rooms = getRoomInfo(resultSet);

        } catch (SQLException e) {
            throw new DAOException("Find rooms error in searching all rooms: " + e.getMessage());
        }
        return rooms;
    }
    
    public List<Room> findRooms(int beds, int stars, int money, boolean wifi, Date arrivalDate, Date leavingDate) throws DAOException {
        List<Room> rooms;
        try (PreparedStatement preparedStatement = proxyConnection.prepareStatement(SQLRequestConstant.FIND_CORRECT_ROOMS)) {
            preparedStatement.setInt(1, beds);
            preparedStatement.setInt(2, money);
            preparedStatement.setBoolean(3, wifi);
            preparedStatement.setInt(4, stars);
            preparedStatement.setDate(5, leavingDate);
            preparedStatement.setDate(6, leavingDate);
            preparedStatement.setDate(7, arrivalDate);
            preparedStatement.setDate(8, arrivalDate);
            preparedStatement.setDate(9, leavingDate);
            preparedStatement.setDate(10, leavingDate);
            preparedStatement.setDate(11, arrivalDate);
            preparedStatement.setDate(12, arrivalDate);
            preparedStatement.setInt(13, beds);
            preparedStatement.setInt(14, money);
            preparedStatement.setBoolean(15, wifi);
            preparedStatement.setInt(16, stars);
            ResultSet resultSet = preparedStatement.executeQuery();
            rooms = getRoomInfo(resultSet);
            setCorrectPrice(rooms, arrivalDate, leavingDate);
        } catch (SQLException e) {
            throw new DAOException("Find rooms error in searching concrete room: " + e.getMessage());
        }
        return rooms;
    }
    
     private List<Room> getRoomInfo(ResultSet resultSet) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (resultSet.next()) {
            Room room = new Room();
            room.setCapacity(resultSet.getInt(RoomSQLConstant.ROOM_CAPACITY));
            room.setId(resultSet.getInt(RoomSQLConstant.ROOM_ID));
            room.setPrice(resultSet.getBigDecimal(RoomSQLConstant.PRICE));
            room.setStarCount(resultSet.getInt(RoomSQLConstant.STAR_COUNT));
            room.setType(RoomType.findByName(resultSet.getString(RoomSQLConstant.TYPE)));
            room.setWifi(resultSet.getBoolean(RoomSQLConstant.WIFI));
            room.setDescription(resultSet.getString(RoomSQLConstant.DESCRIPTION));
            rooms.add(room);
        }
        return rooms;
    }
     
    private void setCorrectPrice(List<Room> rooms, Date arrivalDate, Date leavingDate) {
        int dayCount = getDayCount(arrivalDate, leavingDate);
        for (Room room : rooms) {
            room.setPrice(room.getPrice().multiply(BigDecimal.valueOf(dayCount)));
        }
    }
    
    private int getDayCount(Date arrivalDate, Date leavingDate) {
        final int MILLISECONDS_IN_DAY = 86400000;
        return (int) (1 + (leavingDate.getTime() - arrivalDate.getTime()) / MILLISECONDS_IN_DAY);
    }
    
}

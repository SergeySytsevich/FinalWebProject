
package com.epam.webproject.constant;

public class SQLRequestConstant {
    public static final String CREATE_USER = "INSERT INTO room_booking.user (name, surname, country, age, login, password, " +
            "mail, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_USER = "SELECT user_id, is_authorized, role, is_blocked FROM room_booking.user WHERE login = ? AND password = ?";
    public static final String FIND_EMAIL = "SELECT user_id FROM room_booking.user WHERE mail = ?";
    public static final String SHOW_ALL_ROOMS = "SELECT * FROM room_booking.room";
    public static final String FIND_LOGIN = "SELECT user_id FROM room_booking.user WHERE login = ?";
    public static final String FIND_ROLE_BY_LOGIN = "SELECT role FROM room_booking.user WHERE login = ?";
    public static final String FIND_CORRECT_ROOMS = "SELECT DISTINCT room_capacity, room_id, type, price, wifi, star_count, description " +
            "FROM  room_booking.room, room_booking.order " +
            "WHERE((room_capacity = ? " +
            "AND price <= ? " +
            "AND wifi = ? " +
            "AND star_count = ? " +
            "AND ((room_booking.room.room_id IN " +
            "(room_booking.room.room_id  IN " +
            "(Select DISTINCT room_room_id FROM room_booking.order " +
            "WHERE((arrival_time > ? " +
            "AND release_time > ? ) " +
            "OR (arrival_time < ? " +
            "AND release_time  < ?))))) " +
            "OR(room_booking.room.room_id NOT IN " +
            "(Select DISTINCT room_room_id FROM room_booking.order " +
            "WHERE((arrival_time <= ?  " +
            "OR release_time<= ? ) " +
            "AND (arrival_time >= ? " +
            "OR release_time  >= ?)))))) " +
            "OR room_capacity = ? " +
            "AND price <= ? " +
            "AND wifi = ? " +
            "AND star_count = ? " +
            "AND room_booking.room.room_id NOT IN " +
            "(SELECT room_room_id FROM room_booking.order))";
    public static final String UPDATE_IS_AUTHORIZED = "UPDATE room_booking.user SET is_authorized = 1 WHERE login = ?";
    public static final String FIND_CLIENT_APPS = "SELECT room_capacity, order_id, room.type, cost, wifi, star_count, description, " +
            "arrival_time, release_time, is_approved, is_rejected " +
            "FROM room_booking.room, room_booking.user, room_booking.order " +
            "WHERE room_booking.user.login = ? " +
            "AND room_booking.order.user_user_id = room_booking.user.user_id " +
            "AND room_booking.order.room_room_id = room_booking.room.room_id ";
    public static final String CREATE_ORDER = "INSERT INTO room_booking.order (cost, arrival_time, release_time, user_user_id, room_room_id) " +
            "VALUES( ?, ?, ?, " +
            "(SELECT user_id from room_booking.user " +
            "WHERE room_booking.user.login = ?), ?)";
    public static final String CANCEL_ROOM_IN_ORDER = "DELETE FROM room_booking.order WHERE order_id = ?";
    public static final String FIND_INCOMING_APPS = "SELECT room_capacity, order_id, login, balance, room.type, cost, wifi, " +
            "star_count, description, arrival_time, release_time, user_user_id " +
            "FROM room_booking.room, room_booking.order, room_booking.user " +
            "WHERE room_booking.order.user_user_id = room_booking.user.user_id " +
            "AND room_booking.order.room_room_id = room_booking.room.room_id " +
            "AND room_booking.order.is_in_pending = 1";
    public static final String APPROVE_ORDER = "UPDATE room_booking.order SET is_approved = 1, is_in_pending = 0 " +
            "WHERE order_id = ?";
    public static final String DEDUCT_FROM_BALANCE = "UPDATE room_booking.user SET balance = " +
            "(SELECT balance WHERE login = ?) - ? " +
            "WHERE login = ?";
    public static final String REVOKE_ORDER = "UPDATE room_booking.order SET is_rejected = 1, is_in_pending = 0 " +
            "WHERE order_id = ?";
    public static final String SHOW_ALL_APPS = "SELECT room_capacity, order_id, login, balance, room.type, cost, wifi, " +
            "star_count, description, arrival_time, release_time, is_approved, is_rejected " +
            "FROM room_booking.room, room_booking.order, room_booking.user " +
            "WHERE room_booking.order.user_user_id = room_booking.user.user_id " +
            "AND room_booking.order.room_room_id = room_booking.room.room_id ";
    public static final String SHOW_ALL_USERS = "SELECT login, balance, is_blocked FROM room_booking.user WHERE role = 'client'";
    public static final String BLOCK_UNBLOCK_USER = "UPDATE room_booking.user SET is_blocked = ? WHERE login = ?";
    public static final String FIND_LOGIN_BY_EMAIL = "SELECT login FROM room_booking.user WHERE mail=?";
    public static final String FIND_USER_INFO = "SELECT name, surname, country, age, login, balance from room_booking.user WHERE login = ?";
    public static final String CHANGE_LOGIN = "UPDATE room_booking.user SET login = ? WHERE login = ?";
    public static final String CHANGE_PASSWORD = "UPDATE room_booking.user SET password =? WHERE login = ? ";
    public static final String SHOW_PENDING_APPS = "SELECT room_capacity, order_id, room.type, cost, wifi, star_count, description, " +
            "arrival_time, release_time, is_in_pending " +
            "FROM room_booking.room, room_booking.user, room_booking.order " +
            "WHERE room_booking.user.login = ? " +
            "AND room_booking.order.user_user_id = room_booking.user.user_id " +
            "AND room_booking.order.room_room_id = room_booking.room.room_id " +
            "AND is_in_pending = 1";
    public static final String TOP_UP_BALANCE = "UPDATE room_booking.user SET balance = " +
            "(SELECT balance WHERE login = ?) + ? " +
            "WHERE login = ?";
    public static final String CREATE_ROOM = "INSERT INTO room_booking.room (room_capacity, price, wifi, star_count, description, type) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    public static final String CHANGE_PASSWORD_BY_EMAIL = "UPDATE room_booking.user " +
            "SET password = ? " +
            "WHERE mail=?";
    public static final String DELETE_ROOM = "DELETE FROM room_booking.room WHERE room_id = ?";
}

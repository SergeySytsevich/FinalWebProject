package com.epam.webproject.constant;

public final class DatabaseConstant {
    public static final String PATH = "database";
    public static final String URL = "url";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String USE_UNICODE = "useUnicode";
    public static final String CHARACTER_ENCODING = "characterEncoding";
    public static final String AUTO_RECONNECT = "autoReconnect";
    public static final String USE_SSL = "useSSL";
    public static final String POOL_SIZE = "poolSize";

    public final class DefaultDatabaseConstant{
        public static final String DEFAULT_USER = "root";
        public static final String DEFAULT_PASSWORD = "192463k.nysqgthtekjr";
        public static final String DEFAULT_USE_UNICODE = "true";
        public static final String DEFAULT_CHARACTER_ENCODING = "utf-8";
        public static final String DEFAULT_AUTO_RECONNECT = "true";
        public static final String DEFAULT_POOL_SIZE = "10";
        public static final String DEFAULT_USE_SSL = "false";
    }
    
    public static class RoomPageConstant {
        public static final String BED_NUMBER = "rooms-number";
        public static final String STAR_NUMBER = "stars-number";
        public static final String PRICE_NUMBER = "price-number";
        public static final String WIFI = "wifi-number";
        public static final String ARRIVING_DATE = "arriving-date";
        public static final String LEAVING_DATE = "leaving-date";
        public static final String ARRIVAL_TIME = "arrivalTime";
        public static final String LEAVING_TIME = "leavingTime";
        public static final String ID = "id";
        public static final String BED = "beds";
        public static final String STAR = "stars";
        public static final String PRICE = "price";
        public static final String WIFI_LABEL = "wifi";
        public static final String ARRIVE = "arriveDate";
        public static final String LEAVE = "leaveDate";
        public static final String DESCRIPTION = "description";
        public static final String TYPE = "type";
    }

}

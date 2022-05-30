
package com.epam.webproject.constant;


public class GeneralConstant {
    public static class CommonConstant {
        public static final String URL = "thisRequest";
        public static final String KEY = "key";
        public static final String USER_INFO = "userInfo";
        public static final String PREVIOUS_PAGE = "previousPage";
    }
    
    public static class CommonPageConstant {
        public static final String MAIN_PAGE = "jsp/main.jsp";
        public static final String SIGN_UP_PAGE = "jsp/signUp.jsp";
        public static final String SIGN_IN_PAGE = "jsp/signIn.jsp";
        public static final String AFTER_SIGN_UP_PAGE = "jsp/afterSignUp.jsp";
        public static final String INDEX_PAGE = "index.jsp";
        public static final String USER_PROFILE = "jsp/client/clientProfile.jsp";
        public static final String ADMIN_PROFILE = "jsp/admin/adminProfile.jsp";
        public static final String ERROR_PAGE = "jsp/error/error.jsp";
        public static final String AUTHORISATION = "jsp/authorization.jsp";
    }
    
    public static class UserPageConstant {
        public static final String NAME = "name";
        public static final String SURNAME = "surname";
        public static final String COUNTRY = "country";
        public static final String AGE = "age";
        public static final String MAIL = "mail";
        public static final String LOGIN = "login";
        public static final String USER_LOGIN = "userLogin";
        public static final String PASSWORD = "password";
        public static final String PASSWORD_REPETITION = "repeat-password";
        public static final String NEW_PASSWORD = "newPassword";
        public static final String ROLE = "role";
        public static final String EXISTING_LOGIN = "existingLogin";
        public static final String NON_EXISTING_LOGIN = "nonExistingLogin";
        public static final String NON_EXISTING_EMAIL = "nonExistingEmail";
        public static final String EXISTING_EMAIL = "existingEmail";
        public static final String WRONG_OLD_PASSWORD = "wrongOldPassword";
        public static final String WRONG_EMAIL = "wrongEmail";
        public static final String WRONG_NAME = "wrongName";
        public static final String WRONG_SURNAME = "wrongSurname";
        public static final String WRONG_AGE = "wrongAge";
        public static final String WRONG_COUNTRY = "wrongCountry";
        public static final String WRONG_LOGIN = "wrongLogin";
        public static final String WRONG_PASSWORD = "wrongPassword";
        public static final String WRONG_PASSWORD_REPETITION = "wrongPasswordRepetition";
        public static final String CONFIRM_MAIL = "confirm-mail";
        public static final String WRONG_KEY = "wrongKey";
        public static final String IS_NOT_AUTHORIZED = "isNotAuthorized";
        public static final String IS_BLOCKED = "isBlocked";
        public static final String PASSWORD_SETTING = "openPass";
        public static final String SELECT_ROLE = "selectRole";
    }
    
    public static class UserSQLConstant {
        public static final String IS_AUTHORIZED = "is_authorized";
        public static final String IS_BLOCKED = "is_blocked";
        public static final String BALANCE = "balance";
    }
    
    public static class RoomSQLConstant {
        public static final String ROOM_ID = "room_id";
        public static final String ROOM_CAPACITY = "room_capacity";
        public static final String TYPE = "type";
        public static final String PRICE = "price";
        public static final String WIFI = "wifi";
        public static final String STAR_COUNT = "star_count";
        public static final String DESCRIPTION = "description";
        public static final String ALL_ROOMS = "allRooms";
    }
    
    public static class ErrorConstant {
        public static final String ERROR = "error";
    }
    
    public static class ClientUserPageConstant {
        public static final String ALL_APPS = "allApps";
        public static final String ALL_USERS = "allUsers";
        public static final String PENDING_APPS = "pendingApps";
        public static final String BALANCE_FORM = "balanceForm";
    }
    
    public static class FooterConstant {
        public static final String LOCALE = "locale";
        public static final String EN = "EN";
        public static final String RU = "RU";
    }
}
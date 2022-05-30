
package com.epam.webproject.constant;


public final class MailConstant {
    public static final String PATH = "mail";
    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String URL = "url";
    
    public final class DefaultMailConstant {
        public static final String DEFAULT_MAIL_SMTP_HOST = "smtp.gmail.com";
        public static final String DEFAULT_MAIL_SMTP_AUTH = "true";
        public static final String DEFAULT_MAIL_SMTP_STARTTLS_ENABLE = "true";
        public static final String DEFAULT_MAIL_SMTP_PORT = "587";
        public static final String DEFAULT_EMAIL = "rroombooking@gmail.com";
        public static final String DEFAULT_PASSWORD = "roombooking123";
        public static final String DEFAULT_NAME = "RoomBooking";
        public static final String DEFAULT_URL = "http://localhost:8081";
    }
    
    public final class MailTheme {
        public static final String CONFIRM_MAIL = "ПОДТВЕРЖДЕНИЕ E-MAIL";
        public static final String RESET_PASSWORD = "СБРОС ПАРОЛЯ";
    }
    
    public final class MailMessage {
        public static final String CONFIRM_MAIL_MESSAGE = "Поздравляем, вы успешно зарегистрировались на сайте roombooking.com!" +
                " Для подтверждения e-mail введите полученный код на сайте." +
                " Если вы не регистрировались на нашем сайте, пожалуйста, проигнорируйте это письмо. Код: ";
        public static final String RESET_PASSWORD_MESSAGE="Вы отправили запрос на сброс пароля на сайте roombooking.com. " +
                "Вы можете сменить пароль при входе в систему. " +
                "Ваш новый пароль:";
    }
}

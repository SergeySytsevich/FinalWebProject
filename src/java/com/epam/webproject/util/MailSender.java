
package com.epam.webproject.util;

import static com.epam.webproject.constant.MailConstant.*;
import static com.epam.webproject.constant.MailConstant.DefaultMailConstant.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.*;


public class MailSender {
    
    private static final Logger LOGGER = LogManager.getLogger(MailSender.class);
    private Properties properties;
    private Session session;
    private ResourceBundle resourceBundle;

    public MailSender() {
        this.properties = new Properties();
        initialize();
    }

    private void initialize() {
        resourceBundle = ResourceBundle.getBundle(PATH);
        putProperties(MAIL_SMTP_HOST, DEFAULT_MAIL_SMTP_HOST, resourceBundle);
        putProperties(MAIL_SMTP_AUTH, DEFAULT_MAIL_SMTP_AUTH, resourceBundle);
        putProperties(MAIL_SMTP_STARTTLS_ENABLE, DEFAULT_MAIL_SMTP_STARTTLS_ENABLE, resourceBundle);
        putProperties(MAIL_SMTP_PORT, DEFAULT_MAIL_SMTP_PORT, resourceBundle);
        putProperties(EMAIL, DEFAULT_EMAIL, resourceBundle);
        putProperties(PASSWORD, DEFAULT_PASSWORD, resourceBundle);
        putProperties(NAME, DEFAULT_NAME, resourceBundle);
        putProperties(URL, DEFAULT_URL, resourceBundle);
        session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(DEFAULT_EMAIL, DEFAULT_PASSWORD);
            }
        });
        LOGGER.log(Level.INFO, "MailSender was initialized successfully");
    }
    
    public void sendMessage(String recipient, String subject, String messageText) throws MessagingException {
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(DEFAULT_EMAIL));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        message.setSubject(subject);
        message.setText(messageText);

        Transport.send(message);
    }
    
    
    private void putProperties(String propertyName, String defaultProperty, ResourceBundle resourceBundle) {
        try {
            properties.put(propertyName, resourceBundle.getString(propertyName));
        } catch (MissingResourceException e) {
            properties.put(propertyName, defaultProperty);
            LOGGER.log(Level.ERROR, "Can't find " + propertyName + " " + e.getMessage());
        }
    }
}

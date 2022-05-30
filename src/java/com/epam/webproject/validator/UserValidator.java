
package com.epam.webproject.validator;

import com.epam.webproject.constant.GeneralConstant.UserPageConstant;
import com.epam.webproject.content.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

public class UserValidator {
    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);
    private static final String EMAIL_REGEX = "^[\\w-]+[.\\w-]*@[\\w-]+[.\\w-]+[a-zA-z]{2,}$";
    private static final String HAS_UPPERCASE_REGEX = "[A-ZА-Я]";
    private static final String HAS_LOWERCASE_REGEX = "[a-zа-я]";
    private static final String HAS_NUMBER_REGEX = "\\d";
    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MAX_PASSWORD_LENGTH = 25;
    private static final String WORD_REGEX = "([A-Za-z]|[А-Яа-я]){2,25}";
    private static final String LOGIN_REGEX = "\\w{2,25}";
    public boolean validateUserInfo(String name, String surname, String country, String age, String mail,
                                    String login, String password, String passwordRepetition, RequestContent content) {
        boolean isValidInfo = true;
        if (!checkWord(name, WORD_REGEX)) {
            isValidInfo = false;
            content.getRequestAttributes().put(UserPageConstant.WRONG_NAME, true);
        }
        if (!checkWord(surname, WORD_REGEX)) {
            isValidInfo = false;
            content.getRequestAttributes().put(UserPageConstant.WRONG_SURNAME, true);
        }
        if (!checkWord(country, WORD_REGEX)) {
            isValidInfo = false;
            content.getRequestAttributes().put(UserPageConstant.WRONG_COUNTRY, true);
        }
        if (!checkAge(age, content)) {
            isValidInfo = false;
        }
        if (!checkEmail(mail)) {
            isValidInfo = false;
            content.getRequestAttributes().put(UserPageConstant.WRONG_EMAIL, true);
        }
        if (!checkWord(login, LOGIN_REGEX)) {
            isValidInfo = false;
            content.getRequestAttributes().put(UserPageConstant.WRONG_LOGIN, true);
        }
        if (!checkPassword(password)) {
            isValidInfo = false;
            content.getRequestAttributes().put(UserPageConstant.WRONG_PASSWORD, true);
        }
        if (!checkPasswordEquality(password, passwordRepetition)) {
            isValidInfo = false;
            content.getRequestAttributes().put(UserPageConstant.WRONG_PASSWORD_REPETITION, true);
        }
        return isValidInfo;
    }

    boolean checkEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    boolean checkWord(String word, String regex) {
        return word.matches(regex);
    }

    private boolean checkAge(String age, RequestContent requestContent) {
        boolean answer;
        int number;
        try {
            number = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Age is not a number");
            requestContent.getRequestAttributes().put(UserPageConstant.WRONG_AGE, true);
            return false;
        }
        answer = (number >= MIN_AGE && number <= MAX_AGE);
        if (!answer) {
            requestContent.getRequestAttributes().put(UserPageConstant.WRONG_AGE, true);
        }
        return answer;
    }

    boolean checkPassword(String password) {
        boolean hasUppercase = Pattern.compile(HAS_UPPERCASE_REGEX).matcher(password).find();
        boolean hasLowercase = Pattern.compile(HAS_LOWERCASE_REGEX).matcher(password).find();
        boolean hasNumber = Pattern.compile(HAS_NUMBER_REGEX).matcher(password).find();
        boolean hasRightLength = (password.length() >= MIN_PASSWORD_LENGTH && password.length() <= MAX_PASSWORD_LENGTH);
        return (hasNumber && hasLowercase && hasUppercase && hasRightLength);
    }

    boolean checkPasswordEquality(String password, String passwordRepetition) {
        return password.equals(passwordRepetition);
    }
}

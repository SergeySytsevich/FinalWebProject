
package com.epam.webproject.receiver;

import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;


public interface UserReceiver extends Receiver {
    void signUp(RequestContent requestContent) throws ReceiverException;

    void signIn(RequestContent requestContent) throws ReceiverException;

    void logOut(RequestContent requestContent) throws ReceiverException;

    void confirmMail(RequestContent requestContent) throws ReceiverException;

    void resetPassword(RequestContent requestContent) throws ReceiverException;

    void sendEmailToAuthorize(RequestContent requestContent) throws ReceiverException;

    void openProfile(RequestContent requestContent) throws ReceiverException;

    void openLoginSetting(RequestContent requestContent) throws ReceiverException;

    void changeLogin(RequestContent requestContent) throws ReceiverException;

    void openPasswordSetting(RequestContent requestContent) throws ReceiverException;

    void changePassword(RequestContent requestContent) throws ReceiverException;

    void openBalanceForm(RequestContent requestContent) throws ReceiverException;

    void topUpBalance(RequestContent requestContent) throws ReceiverException;
}

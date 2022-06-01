package com.epam.webproject.receiver;

import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;


public interface ClientReceiver extends Receiver{
    void openClientApplications(RequestContent requestContent) throws ReceiverException;

    void bookRoom(RequestContent requestContent) throws ReceiverException;

    void cancelRoom(RequestContent requestContent) throws ReceiverException;

    void openClientPendingApplications(RequestContent requestContent) throws ReceiverException;
}


package com.epam.webproject.receiver;

import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;


public interface CommonReceiver extends Receiver{
    void changeLocale(RequestContent requestContent) throws ReceiverException;
    void openMainPage(RequestContent requestContent) throws ReceiverException;
}

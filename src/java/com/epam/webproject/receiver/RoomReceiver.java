package com.epam.webproject.receiver;

import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;


public interface RoomReceiver extends Receiver{
    void findRooms(RequestContent requestContent) throws ReceiverException;
}

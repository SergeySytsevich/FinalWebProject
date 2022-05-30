
package com.epam.webproject.receiver;

import com.epam.webproject.command.CommandType;
import com.epam.webproject.content.RequestContent;

public interface Receiver {
    default void doAction(CommandType commandType, RequestContent requestContent) throws ReceiverException {
        commandType.doReceiver(requestContent);
    }
    
}

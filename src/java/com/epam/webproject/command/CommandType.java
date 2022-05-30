
package com.epam.webproject.command;

import com.epam.webproject.command.AbstractCommand;
import com.epam.webproject.command.impl.user.LogOutCommand;
import com.epam.webproject.command.impl.user.SignInCommand;
import com.epam.webproject.command.impl.user.SignUpCommand;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.impl.UserReceiverImpl;

public enum CommandType {
    //DEFAULT,
    //GO_TO_LOGIN_PAGE_COMMAND,
    //GO_TO_ERROR_PAGE_COMMAND
    SIGN_IN(new SignInCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).signIn(content);
        }
    },
    SIGN_UP(new SignUpCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).signUp(content);
        }
    },
    LOG_OUT(new LogOutCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).logOut(content);
        }
    };
    
    private AbstractCommand command;
    
    CommandType(AbstractCommand command) {
        this.command = command;

    }

    public AbstractCommand getCommand() {
        return command;
    }
    
    public abstract void doReceiver(RequestContent content) throws ReceiverException;
}

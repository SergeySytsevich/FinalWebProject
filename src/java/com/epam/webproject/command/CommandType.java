
package com.epam.webproject.command;

import com.epam.webproject.command.AbstractCommand;
import com.epam.webproject.command.user.ChangeLoginCommand;
import com.epam.webproject.command.user.*;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.impl.UserReceiverImpl;

public enum CommandType {

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
    CHANGE_LOCALE(new ChangeLocaleCommand(new CommonReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((CommonReceiverImpl) getCommand().getReceiver()).changeLocale(content);
        }
    },
    LOG_OUT(new LogOutCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent content) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).logOut(content);
        }
    }, 
    CHANGE_LOGIN(new ChangeLoginCommand(new UserReceiverImpl())) {
        public void doReceiver(RequestContent requestContent) throws ReceiverException {
            ((UserReceiverImpl) getCommand().getReceiver()).changeLogin(requestContent);
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

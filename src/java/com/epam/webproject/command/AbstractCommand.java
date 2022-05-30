package com.epam.webproject.command;

import com.epam.webproject.content.RequestContent;
import com.epam.webproject.receiver.Receiver;
import com.epam.webproject.util.Router;


public abstract class AbstractCommand {
    protected Receiver receiver;

    public AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public Receiver getReceiver() {
        return this.receiver;
    }

    public abstract Router execute(RequestContent requestContent);
    
}

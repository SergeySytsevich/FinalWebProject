
package com.epam.webproject.command.impl.user;

import com.epam.webproject.command.AbstractCommand;
import com.epam.webproject.command.CommandType;
import com.epam.webproject.constant.GeneralConstant.CommonConstant;
import com.epam.webproject.constant.GeneralConstant.CommonPageConstant;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.Receiver;
import com.epam.webproject.util.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogOutCommand extends AbstractCommand {
    private static final Logger LOGGER = LogManager.getLogger(LogOutCommand.class);

    public LogOutCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public Router execute(RequestContent requestContent) {
        Router router = new Router();
        try {
            receiver.doAction(CommandType.LOG_OUT, requestContent);
            router.setPath(CommonPageConstant.INDEX_PAGE);
            router.setType(Router.RouterType.REDIRECT);
            requestContent.insertSessionAttributes(CommonConstant.PREVIOUS_PAGE, CommonPageConstant.INDEX_PAGE);
            LOGGER.log(Level.INFO, "Log out command was successful");
        } catch (ReceiverException e) {
            router.setPath(CommonPageConstant.ERROR_PAGE);
            router.setType(Router.RouterType.REDIRECT);
            LOGGER.log(Level.ERROR, "Error in log out command: " + e.getMessage());
        }
        return router;
    }
}

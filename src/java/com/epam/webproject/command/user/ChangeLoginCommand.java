
package com.epam.webproject.command.user;

import com.epam.webproject.command.AbstractCommand;
import com.epam.webproject.command.CommandType;
import com.epam.webproject.constant.GeneralConstant;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.Receiver;
import com.epam.webproject.util.Router;
import com.epam.webproject.util.Router.RouterType;
import java.util.Set;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ChangeLoginCommand extends AbstractCommand {
    private static final Logger LOGGER = LogManager.getLogger(ChangeLoginCommand.class);

    public ChangeLoginCommand(Receiver receiver) {
        super(receiver);
    }
    @Override
    public Router execute(RequestContent requestContent) {
        Router router = new Router();
        try {
            receiver.doAction(CommandType.CHANGE_LOGIN, requestContent);
            Set<String> set = requestContent.getRequestAttributes().keySet();
            if (!set.contains(GeneralConstant.ErrorConstant.ERROR)) {
                router.setPath(GeneralConstant.CommandConstant.OPEN_LOGIN_SETTING);
                router.setType(RouterType.REDIRECT);
                LOGGER.log(Level.INFO, "Change login command was successful");
            } else {
                router.setPath(GeneralConstant.CommonPageConstant.ERROR_PAGE);
                router.setType(RouterType.REDIRECT);
            }
        } catch (ReceiverException e) {
            router.setPath(GeneralConstant.CommonPageConstant.ERROR_PAGE);
            router.setType(RouterType.REDIRECT);
            LOGGER.log(Level.ERROR, "Error in change login command: " + e.getMessage());
        }
        return router;
    }
    
}

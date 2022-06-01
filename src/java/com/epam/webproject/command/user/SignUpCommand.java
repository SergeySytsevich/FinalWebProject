
package com.epam.webproject.command.user;

import com.epam.webproject.command.AbstractCommand;
import com.epam.webproject.command.CommandType;
import com.epam.webproject.constant.GeneralConstant.CommonConstant;
import com.epam.webproject.constant.GeneralConstant.CommonPageConstant;
import com.epam.webproject.constant.GeneralConstant.UserPageConstant;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.Receiver;
import com.epam.webproject.util.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class SignUpCommand extends AbstractCommand {
    private static final Logger LOGGER = LogManager.getLogger(SignUpCommand.class);

    public SignUpCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public Router execute(RequestContent requestContent) {
        Router router = new Router();
        try {
            receiver.doAction(CommandType.SIGN_UP, requestContent);
            Set<String> set = requestContent.getRequestAttributes().keySet();
            if (set.contains(UserPageConstant.WRONG_EMAIL) || set.contains(UserPageConstant.EXISTING_LOGIN)||set.contains(UserPageConstant.EXISTING_EMAIL)) {
                router.setPath(CommonPageConstant.SIGN_UP_PAGE);
                router.setType(Router.RouterType.FORWARD);
                requestContent.insertSessionAttributes(CommonConstant.PREVIOUS_PAGE, CommonPageConstant.SIGN_UP_PAGE);
            } else {
                router.setPath(CommonPageConstant.AFTER_SIGN_UP_PAGE);
                router.setType(Router.RouterType.FORWARD);
                requestContent.insertSessionAttributes(CommonConstant.PREVIOUS_PAGE, CommonPageConstant.AFTER_SIGN_UP_PAGE);
                LOGGER.log(Level.INFO, "Sign up command was successful");
            }
        } catch (ReceiverException e) {
            router.setPath(CommonPageConstant.ERROR_PAGE);
            router.setType(Router.RouterType.REDIRECT);
            LOGGER.log(Level.ERROR, "Error in sign up command: " + e.getMessage());
        }
        return router;
    }
}

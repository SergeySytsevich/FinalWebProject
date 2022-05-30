
package com.epam.webproject.command.impl.user;

import com.epam.webproject.command.AbstractCommand;
import com.epam.webproject.command.CommandType;
import com.epam.webproject.constant.GeneralConstant.CommonConstant;
import com.epam.webproject.constant.GeneralConstant.CommonPageConstant;
import com.epam.webproject.constant.GeneralConstant.UserPageConstant;
import com.epam.webproject.util.Router;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.exception.ReceiverException;
import com.epam.webproject.receiver.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import java.util.Set;

public class SignInCommand extends AbstractCommand {
    private static final Logger LOGGER = LogManager.getLogger(SignInCommand.class);

    public SignInCommand(Receiver receiver) {
        super(receiver);
    }
    
    @Override
    public Router execute(RequestContent requestContent) {
        Router router = new Router();
        try {
            receiver.doAction(CommandType.SIGN_IN, requestContent);
            Set<String> set = requestContent.getRequestAttributes().keySet();
            if (set.contains(UserPageConstant.NON_EXISTING_LOGIN) || set.contains(UserPageConstant.WRONG_PASSWORD) ||
                    set.contains(UserPageConstant.IS_BLOCKED) || set.contains(UserPageConstant.IS_NOT_AUTHORIZED)) {
                router.setPath(CommonPageConstant.SIGN_IN_PAGE);
                router.setType(Router.RouterType.FORWARD);
                requestContent.insertSessionAttributes(CommonConstant.PREVIOUS_PAGE, CommonPageConstant.SIGN_IN_PAGE);
            } else {
                router.setPath(CommonPageConstant.INDEX_PAGE);
                router.setType(Router.RouterType.REDIRECT);
                requestContent.insertSessionAttributes(CommonConstant.PREVIOUS_PAGE, CommonPageConstant.INDEX_PAGE);
                LOGGER.log(Level.INFO, "Sign in command was successful");
            }
            
        } catch (ReceiverException e) {
            router.setPath(CommonPageConstant.ERROR_PAGE);
            router.setType(Router.RouterType.REDIRECT);
            LOGGER.log(Level.ERROR, "Error in sign in " + e.getMessage());
        }
        
        return router;
    }
}

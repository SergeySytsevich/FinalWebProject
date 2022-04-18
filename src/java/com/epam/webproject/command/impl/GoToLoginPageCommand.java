package com.epam.webproject.command.impl;

import com.epam.webproject.command.Command;
import com.epam.webproject.command.PageNavigator;
import com.epam.webproject.command.RequestAttribute;
import com.epam.webproject.command.Router;
import com.epam.webproject.command.Router.RouterType;
import com.epam.webproject.util.RegexPropertyUtil;
import javax.servlet.http.HttpServletRequest;

public class GoToLoginPageCommand implements Command{
     private static final String REGEXP_PROPERTY_LOGIN = "regexp.login";
     private static final String REGEXP_PROPERTY_PASSWORD = "regexp.password";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        RegexPropertyUtil propertyUtil = RegexPropertyUtil.getInstance();
        
        final String REGEXP_LOGIN = propertyUtil.getProperty(REGEXP_PROPERTY_LOGIN);
        final String REGEXP_PASSWORD = propertyUtil.getProperty(REGEXP_PROPERTY_PASSWORD);
        
        request.setAttribute(RequestAttribute.REGEXP_LOGIN, REGEXP_LOGIN);
        request.setAttribute(RequestAttribute.REGEXP_PASSWORD, REGEXP_PASSWORD);
        
        request.setAttribute(RequestAttribute.ACTIVE_LOGIN, true);
        router = new Router(RouterType.FORWARD, PageNavigator.ACCOUNT_PAGE);
        
        return router;
    }
    
    
}

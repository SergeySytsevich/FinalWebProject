
package com.epam.webproject.command;

public class Router {  //FIXME Final class???
    public enum RouterType {
        FORWARD, REDIRECT
    }
    
    private final RouterType routerType;
    private final String pageNavigator;
    
    public Router(RouterType type, String page) {
        this.routerType = type;
        this.pageNavigator = page;
    }

    public RouterType getRouterType() {
        return routerType;
    }

    public String getPage() {
        return pageNavigator;
    }
}

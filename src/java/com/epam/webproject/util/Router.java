package com.epam.webproject.util;

public class Router {
    public enum RouterType {
        FORWARD, 
        REDIRECT
    }
    
    private String path;
    private RouterType type;
    
    public Router() {
        this.type = RouterType.FORWARD;
    }

    public Router(String path, RouterType type) {
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RouterType getType() {
        return type;
    }

    public void setType(RouterType type) {
        this.type = type;
    }
}

package com.epam.webproject.controller;

import com.epam.webproject.command.*;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@MultipartConfig
public class Controller extends HttpServlet{
    private static final Logger LOG = LogManager.getLogger();
    private final CommandProvider COMMAND_PROVIDER = CommandProvider.getInstance(); 
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        performRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        performRequest(request, response);
    }
    
    private void performRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameter.COMMAND);
        Command command = COMMAND_PROVIDER.getCommand(commandName);
        Router router = command.execute(request);
        switch (router.getRouterType()) {
            case REDIRECT:
                response.sendRedirect(router.getPage());
                break;
            case FORWARD:
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPage());
                dispatcher.forward(request, response);
                break;
            default:
                LOG.error("router type isn´t correct" + router.getRouterType());
                response.sendRedirect(PageNavigator.ERROR_PAGE);
        }
    }
}

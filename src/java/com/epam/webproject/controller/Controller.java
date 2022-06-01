package com.epam.webproject.controller;

import com.epam.webproject.command.*;
import com.epam.webproject.content.RequestContent;
import com.epam.webproject.util.Router;
import com.epam.webproject.util.Router.RouterType;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AbstractCommand abstractCommand;
        RequestContent requestContent = new RequestContent();
        requestContent.extractValues(request);
        abstractCommand = new CommandFactory().initializeCommand(requestContent);
        Router router = abstractCommand.execute(requestContent);
        requestContent.insertValues(request);
        if (RouterType.FORWARD.equals(router.getType())) {
            request.getRequestDispatcher(router.getPath()).forward(request, response);
        } else if (RouterType.REDIRECT.equals(router.getType())) {
            response.sendRedirect(router.getPath());
        } else {
            response.sendRedirect(router.getPath());
        }
    }
}

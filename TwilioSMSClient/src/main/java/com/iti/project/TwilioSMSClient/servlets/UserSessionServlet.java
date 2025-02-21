package com.iti.project.TwilioSMSClient.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/TwilioSMSClient/UserSessionServlet")
public class UserSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String username = (session != null && session.getAttribute("username") != null) 
                            ? (String) session.getAttribute("username") 
                            : null;

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"username\": \"" + (username != null ? username : "") + "\"}");
    }
}

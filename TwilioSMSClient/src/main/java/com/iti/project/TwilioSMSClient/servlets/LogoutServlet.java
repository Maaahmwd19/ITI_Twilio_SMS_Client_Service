package com.iti.project.TwilioSMSClient.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/TwilioSMSClient/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            System.out.println("🔴 Invalidating session: " + session.getId());
            session.invalidate();
        }
        response.sendRedirect("/TwilioSMSClient/pages/login1.html");
    }
}

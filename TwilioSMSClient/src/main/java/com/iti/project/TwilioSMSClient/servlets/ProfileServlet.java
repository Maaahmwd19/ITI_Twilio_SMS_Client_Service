package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login1.html");
            return;
        }

        User user = (User) session.getAttribute("user");

        request.setAttribute("name", user.getName());
        request.setAttribute("username", user.getUsername());
        request.setAttribute("phone", user.getPhoneNumber());
        request.setAttribute("email", user.getEmail());
        request.setAttribute("twilioAccountSid", user.getAccountSid());
        request.setAttribute("twilioAuthToken", user.getAuthToken());
        // Removed twilioSenderId attribute - no longer needed
        request.setAttribute("birthday", user.getBirthday());
        request.setAttribute("job", user.getJob());
        request.setAttribute("address", user.getAddress());

        
        
        request.getRequestDispatcher("/pages/Profile.jsp").forward(request, response);
    }
}
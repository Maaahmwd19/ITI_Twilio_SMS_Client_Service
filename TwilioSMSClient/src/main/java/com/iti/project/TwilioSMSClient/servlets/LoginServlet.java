package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.model.User;
import com.iti.project.TwilioSMSClient.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/TwilioSMSClient/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = UserDAO.validateUser(username, password);
            if (user != null) {
                // Check if the password matches
                if (user.getPassword().equals(password)) {
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    System.out.println("User: " + user);

                    session.setAttribute("username", user.getUsername()); 
                    System.out.println("username: " + user.getUsername());
                    if(user.getRole().equals("admin")){
                        response.sendRedirect("/TwilioSMSClient/pages/AdminHomePage.html");
                    }
                    else{
                        response.sendRedirect("/TwilioSMSClient/pages/HomePage.html");
                    }
                } else {
                    response.sendRedirect("/TwilioSMSClient/pages/login1.html?error=invalid_credentials");
                }
            } else {
                // request.setAttribute("errorMessage", "Invalid username or password.");
                response.sendRedirect("/TwilioSMSClient/pages/login1.html?errorMessage=invalid_credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
}

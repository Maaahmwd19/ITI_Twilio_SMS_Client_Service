// src/main/java/com/iti/project/TwilioSMSClient/servlets/LoginServlet.java
package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import com.iti.project.TwilioSMSClient.dao.UserDAO;

@WebServlet("/TwilioSMSClient/LoginServlet")
public class LoginServlet extends HttpServlet {

   

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = UserDAO.getUserByUsername(username);
            System.out.println("user getted");
            if (user != null && user.getPassword().equals(password)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                if ("admin".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect("/TwilioSMSClient/pages/AdminHome.html");
                } else {
                    response.sendRedirect("/TwilioSMSClient/pages/HomePage.html");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("/TwilioSMSClient/pages/login.html").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
        }
    }
}
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            User user = UserDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(password)) { // Replace with hashed password check
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("role", user.getRole());

                if ("admin".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect("/TwilioSMSClient/pages/adminHome.jsp");
                } else {
                    response.sendRedirect("/TwilioSMSClient/pages/HomePage.html");
                }
            } else {
                request.setAttribute("errorMessage", "Invalid username or password.");
                request.getRequestDispatcher("/TwilioSMSClient/pages/login.html").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
        }
    }
}

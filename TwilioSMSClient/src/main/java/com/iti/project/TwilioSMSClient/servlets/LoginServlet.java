/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.dao.UserDAO;
import com.iti.project.TwilioSMSClient.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mibrahim
 */
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            User user= UserDAO.validateUser(username,password);
            if(user!=null){
                HttpSession session = request.getSession();
                session.setAttribute("name", user.getName());
                session.setAttribute("role", user.getRole());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("id", user.getId());
                session.setAttribute("phoneNumber", user.getPhoneNumber());
                session.setAttribute("email", user.getEmail());

                if (user.getRole() == User.Role.ADMIN) {
                    response.sendRedirect("customerHome.html");
                } else if (user.getRole() == User.Role.CUSTOMER) {
                    response.sendRedirect("pages/customerHome.html");
                } else {
                    response.sendRedirect("pages/login.html");
                }

            }else {
                response.sendRedirect("pages/login.html");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

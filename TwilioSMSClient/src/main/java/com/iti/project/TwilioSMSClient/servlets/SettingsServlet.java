/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import com.iti.project.TwilioSMSClient.model.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mibrahim
 */
public class SettingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("/pages/login1.html");
            return;
        }
        

        String username = (String) session.getAttribute("username");

        User user = getUserData(username);
        request.setAttribute("user", user);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/Settings.jsp");
        dispatcher.forward(request, response);
    }

    private User getUserData(String username) {
        User user = null;
        try (
                
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setJob(rs.getString("job_title"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setAccountSid(rs.getString("twilio_account_sid"));
                user.setAuthToken(rs.getString("twilio_auth_token"));
            }
        } catch (SQLException e) {
        }
        return user;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.iti.project.TwilioSMSClient.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mibrahim
 */
public class UpdateUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("LoginPage.html");
            return;
        }

        int userId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String phone = request.getParameter("phone_number");
        String email = request.getParameter("email");
        String job = request.getParameter("job");
        String address = request.getParameter("address");
        String twilioSid = request.getParameter("twilio_account_sid");
        String twilioToken = request.getParameter("twilio_auth_token");

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = DatabaseUtil.getConnection();

            // Check if email or phone already exists for another user
            String checkQuery = "SELECT id FROM users WHERE (email = ? OR phone_number = ?) AND id != ?";
            stmt = conn.prepareStatement(checkQuery);
            stmt.setString(1, email);
            stmt.setString(2, phone);
            stmt.setInt(3, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                response.sendRedirect("/pages/Settings.html?error=Email or phone is already used.");
                return;
            }

            // Update query
            String updateQuery = "UPDATE users SET name = ?, phone_number = ?, email = ?, job = ?, address = ?, twilio_account_sid = ?, twilio_auth_token = ? WHERE id = ?";
            stmt = conn.prepareStatement(updateQuery);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, email);
            stmt.setString(4, job);
            stmt.setString(5, address);
            stmt.setString(6, twilioSid);
            stmt.setString(7, twilioToken);

            int updatedRows = stmt.executeUpdate();
            if (updatedRows > 0) {
                response.sendRedirect("Settings.html?success=Settings updated successfully.");
            } else {
                response.sendRedirect("Settings.html?error=Failed to update settings.");
            }

        } catch (IOException | SQLException e) {
            response.sendRedirect("Settings.html?error=Something went wrong.");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ignored) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ignored) {
            }
        
            }
        }
    }


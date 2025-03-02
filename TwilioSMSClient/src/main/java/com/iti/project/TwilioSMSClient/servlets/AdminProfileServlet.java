package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import com.iti.project.TwilioSMSClient.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/adminprofile")
public class AdminProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");


        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/pages/login.html");
            return;
        }
        int adminId=user.getId();
        editAdminForm(request, response, user.getId());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateProfile(request, response);
    }

    private void editAdminForm(HttpServletRequest request, HttpServletResponse response, int adminId) throws ServletException, IOException {
        Map<String, Object> adminData = new HashMap<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ? AND role = 'admin'";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, adminId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        adminData.put("id", rs.getInt("id"));
                        adminData.put("username", rs.getString("username"));
                        adminData.put("email", rs.getString("email"));
                        adminData.put("name", rs.getString("name"));
                        adminData.put("phone_number", rs.getString("phone_number"));
                        adminData.put("twilio_account_sid", rs.getString("twilio_account_sid"));
                        adminData.put("twilio_auth_token", rs.getString("twilio_auth_token"));
                        adminData.put("birthday", rs.getDate("birthday"));
                        adminData.put("job", rs.getString("job"));
                        adminData.put("address", rs.getString("address"));
                        adminData.put("is_valid", rs.getBoolean("is_valid"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("adminData", adminData);
        request.getRequestDispatcher("/pages/admin-edit.jsp").forward(request, response);
    }
    private void updateProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int adminId = Integer.parseInt(request.getParameter("id"));
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String name = request.getParameter("name");
            String phoneNumber = request.getParameter("phone_number");
            
            String twilioAccountSid = request.getParameter("twilio_account_sid");
            String twilioAuthToken = request.getParameter("twilio_auth_token");
            
            java.sql.Date sqlBirthday = null;
            String birthday = request.getParameter("birthday");
            if (birthday != null && !birthday.isEmpty()) {
                try {
                    sqlBirthday = java.sql.Date.valueOf(birthday);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            
            String job = request.getParameter("job");
            String address = request.getParameter("address");
            boolean isValid = request.getParameter("is_valid") != null;
    
            try (Connection conn = DatabaseUtil.getConnection()) {
                String sql = "UPDATE users SET username = ?, email = ?, name = ?, phone_number = ?, " +
                        "twilio_account_sid = ?, twilio_auth_token = ?, birthday = ?, job = ?, address = ?, is_valid = ? " +
                        "WHERE id = ? AND role = 'admin'";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, email);
                    pstmt.setString(3, name);
                    pstmt.setString(4, phoneNumber);
                    pstmt.setString(5, twilioAccountSid);
                    pstmt.setString(6, twilioAuthToken);
                    
                    if (sqlBirthday != null) {
                        pstmt.setDate(7, sqlBirthday);
                    } else {
                        pstmt.setNull(7, java.sql.Types.DATE);
                    }
                    
                    pstmt.setString(8, job);
                    pstmt.setString(9, address);
                    pstmt.setBoolean(10, isValid);
                    pstmt.setInt(11, adminId);
                    
                    int rowsUpdated = pstmt.executeUpdate();
                    System.out.println("Rows updated: " + rowsUpdated); // Add debugging
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Add better error handling here
                request.setAttribute("errorMessage", "Database error: " + e.getMessage());
                editAdminForm(request, response, adminId);
                return;
            }
            
            HttpSession session = request.getSession();
            session.setAttribute("successMessage", "Profile updated successfully");
            
            response.sendRedirect(request.getContextPath() + "/adminprofile");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}

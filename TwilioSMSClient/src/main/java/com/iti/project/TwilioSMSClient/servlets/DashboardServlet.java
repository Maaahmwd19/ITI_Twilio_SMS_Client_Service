package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Summary statistics
        Map<String, Object> stats = new HashMap<>();
        
        // Customer activity data for chart
        List<Map<String, Object>> customerActivity = new ArrayList<>();
        
        // SMS status distribution data for chart
        Map<String, Integer> smsStatusDistribution = new HashMap<>();
        
        // Recent SMS messages
        List<Map<String, Object>> recentSMS = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            // Get total count of users
            String userCountQuery = "SELECT COUNT(*) as total FROM users where role='customer'";
            try (PreparedStatement pstmt = conn.prepareStatement(userCountQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    stats.put("totalUsers", rs.getInt("total"));
                }
            }
            
            // Get total count of SMS messages
            String smsCountQuery = "SELECT COUNT(*) as total FROM sms";
            try (PreparedStatement pstmt = conn.prepareStatement(smsCountQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    stats.put("totalSMS", rs.getInt("total"));
                }
            }
            
            // Get SMS sent today
            String smsTodayQuery = "SELECT COUNT(*) as total FROM sms WHERE date = CURRENT_DATE";
            try (PreparedStatement pstmt = conn.prepareStatement(smsTodayQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    stats.put("smsSentToday", rs.getInt("total"));
                }
            }
            
            // Get SMS status distribution
            String statusDistributionQuery = "SELECT status, COUNT(*) as count FROM sms GROUP BY status";
            try (PreparedStatement pstmt = conn.prepareStatement(statusDistributionQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    smsStatusDistribution.put(rs.getString("status"), rs.getInt("count"));
                }
            }
            
            // Get top 5 users by SMS count
            String topUsersQuery = "SELECT u.id, u.username, COUNT(s.id) as smsCount " +
                         "FROM users u LEFT JOIN sms s ON u.id = s.user_id " +
                         "GROUP BY u.id, u.username " +
                         "ORDER BY smsCount DESC LIMIT 5";
            try (PreparedStatement pstmt = conn.prepareStatement(topUsersQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> userActivity = new HashMap<>();
                    userActivity.put("id", rs.getInt("id"));
                    userActivity.put("username", rs.getString("username"));
                    userActivity.put("smsCount", rs.getInt("smsCount"));
                    customerActivity.add(userActivity);
                }
            }
            
            // Get 10 most recent SMS messages
            String recentSmsQuery = "SELECT s.id,s.from_number, s.to_number, s.body, s.status, s.date, u.username " +
                          "FROM sms s JOIN users u ON s.user_id = u.id " +
                          "ORDER BY s.date DESC LIMIT 10";
            try (PreparedStatement pstmt = conn.prepareStatement(recentSmsQuery);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> sms = new HashMap<>();
                    sms.put("id", rs.getInt("id"));
                    sms.put("toNumber", rs.getString("to_number"));
                    sms.put("fromNumber", rs.getString("from_number"));
                    sms.put("body", rs.getString("body"));
                    sms.put("status", rs.getString("status"));
                    sms.put("date", rs.getDate("date"));
                    sms.put("username", rs.getString("username"));
                    recentSMS.add(sms);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Set attributes to be used in JSP
        request.setAttribute("stats", stats);
        request.setAttribute("customerActivity", customerActivity);
        request.setAttribute("smsStatusDistribution", smsStatusDistribution);
        request.setAttribute("recentSMS", recentSMS);
        
        // Forward to dashboard JSP
        request.getRequestDispatcher("/pages/dashboard.jsp").forward(request, response);
    }
}

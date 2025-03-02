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

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "listCustomers"; // Default action
        }
        
        switch (action) {
            case "viewCustomer":
                viewCustomer(request, response);
                break;
            case "editCustomerForm":
                editCustomerForm(request, response);
                break;
            case "deleteCustomer":
                deleteCustomer(request, response);
                break;
            case "listCustomers":
            default:
                listCustomers(request, response);
                break;
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "listCustomers"; // Default action
        }
        
        switch (action) {
            case "updateCustomer":
                updateCustomer(request, response);
                break;
            default:
                listCustomers(request, response);
                break;
        }
    }
    
    private void listCustomers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> customers = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT u.id, u.username, COUNT(s.id) AS smsCount " +
            "FROM users u " +
            "LEFT JOIN sms s ON u.id = s.user_id " +
            "WHERE u.role = 'customer' " +
            "GROUP BY u.id, u.username";

            
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    Map<String, Object> customer = new HashMap<>();
                    customer.put("id", rs.getInt("id"));
                    customer.put("username", rs.getString("username"));
                    customer.put("smsCount", rs.getInt("smsCount"));
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("customers", customers);
        request.getRequestDispatcher("/pages/adminHome.jsp").forward(request, response);
    }
    
    private void viewCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("id"));
        Map<String, Object> customer = new HashMap<>();
        List<Map<String, Object>> smsList = new ArrayList<>();
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            // Get customer details
            String customerSql = "SELECT * FROM users WHERE id = ? ";
            try (PreparedStatement pstmt = conn.prepareStatement(customerSql)) {
                pstmt.setInt(1, customerId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        customer.put("id", rs.getInt("id"));
                        customer.put("username", rs.getString("username"));
                        customer.put("email", rs.getString("email"));
                        customer.put("phone_number", rs.getString("phone_number"));

                    }
                }
            }
            
            String smsSql = "SELECT id,from_number,to_number, body, status, date FROM sms WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(smsSql)) {
                pstmt.setInt(1, customerId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> sms = new HashMap<>();
                        System.out.println(rs.getString("from_number"));
                        sms.put("id", rs.getInt("id"));
                        sms.put("from", rs.getString("from_number"));
                        sms.put("to", rs.getString("to_number"));
                        sms.put("message", rs.getString("body"));
                        sms.put("status", rs.getString("status"));
                        sms.put("sentDate", rs.getTimestamp("date"));
                        smsList.add(sms);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("customer", customer);
        request.setAttribute("smsList", smsList);
        request.getRequestDispatcher("/pages/customer-view.jsp").forward(request, response);
    }
    
    private void editCustomerForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("id"));
        Map<String, Object> customer = new HashMap<>();
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ? and role='customer'";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, customerId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        customer.put("id", rs.getInt("id"));
                        customer.put("username", rs.getString("username"));
                        customer.put("email", rs.getString("email"));
                        customer.put("name", rs.getString("name"));
                        customer.put("phone_number", rs.getString("phone_number"));
                        customer.put("twilio_account_sid", rs.getString("twilio_account_sid"));
                        customer.put("twilio_auth_token", rs.getString("twilio_auth_token"));
                        customer.put("birthday", rs.getDate("birthday"));
                        customer.put("job", rs.getString("job"));
                        customer.put("address", rs.getString("address"));
                        customer.put("is_valid", rs.getBoolean("is_valid"));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/pages/customer-edit.jsp").forward(request, response);
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phone_number");
        String twilioAccountSid = request.getParameter("twilio_account_sid");
        String twilioAuthToken = request.getParameter("twilio_auth_token");
        String birthday = request.getParameter("birthday");
        String job = request.getParameter("job");
        String address = request.getParameter("address");
        boolean isValid = request.getParameter("is_valid") != null; 
    
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "UPDATE users SET username = ?, email = ?, name = ?, phone_number = ?, twilio_account_sid = ?, " +
                         "twilio_auth_token = ?, birthday = ?, job = ?, address = ?, is_valid = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                pstmt.setString(3, name);
                pstmt.setString(4, phoneNumber);
                pstmt.setString(5, twilioAccountSid);
                pstmt.setString(6, twilioAuthToken);
                pstmt.setString(7, birthday);
                pstmt.setString(8, job);
                pstmt.setString(9, address);
                pstmt.setBoolean(10, isValid);
                pstmt.setInt(11, customerId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        response.sendRedirect(request.getContextPath() + "/admin?action=viewCustomer&id="+customerId);
    }
    
    
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int customerId = Integer.parseInt(request.getParameter("id"));
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            // First, delete all related SMS records
            String deleteSMSSql = "DELETE FROM sms WHERE user_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSMSSql)) {
                pstmt.setInt(1, customerId);
                pstmt.executeUpdate();
            }
            
            // Then, delete the customer
            String deleteUserSql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteUserSql)) {
                pstmt.setInt(1, customerId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Redirect back to the list after deletion
        response.sendRedirect(request.getContextPath() + "/admin?action=listCustomers");
    }
    
}
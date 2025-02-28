/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.project.TwilioSMSClient.dao;

import com.iti.project.TwilioSMSClient.model.User;
import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import java.sql.*;
import java.lang.*;

/**
 *
 * @author mibrahim
 */
public class UserDAO {

    public static User validateUser(String username, String password) throws SQLException {
        User user = null;
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setAccountSid(rs.getString("twilio_account_sid"));
                user.setAuthToken(rs.getString("twilio_auth_token"));
                // user.setSenderId(rs.getString("sender_id"));
                user.setVerified(rs.getInt("is_valid") == 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }
    public static User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                User    user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setAccountSid(rs.getString("twilio_account_sid"));
                user.setAuthToken(rs.getString("twilio_auth_token"));
                // user.setSenderId(rs.getString("sender_id"));
                user.setVerified(rs.getInt("is_valid") == 1);
                    return user;
                }
            }
        }
        return null;
    }


    public static User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setRole(rs.getString("role"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setEmail(rs.getString("email"));
                    user.setAccountSid(rs.getString("twilio_account_sid"));
                    user.setAuthToken(rs.getString("twilio_auth_token"));
                    // user.setSenderId(rs.getString("sender_id"));
                    user.setVerified(rs.getInt("is_valid") == 1);
                    return user;
                    


                }
            }
        }
        return null;
    }

   public boolean updateUser(User user) {
        String query = "UPDATE users SET name = ?, phone_number = ?, email = ?, "
                + "twilio_account_sid = ?, twilio_auth_token = ?, twilio_sender_id = ?, "
                + "birthday = ?, job = ?, address = ? WHERE username = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getAccountSid());
            pstmt.setString(5, user.getAuthToken());
            pstmt.setString(6, user.getSenderId());
            pstmt.setDate(7, (Date) user.getBirthday());
            pstmt.setString(8, user.getJob());
            pstmt.setString(9, user.getAddress());
            pstmt.setString(10, user.getUsername());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0; // Return true if at least one row was updated

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

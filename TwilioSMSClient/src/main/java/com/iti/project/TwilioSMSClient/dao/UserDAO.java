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
    public static User validateUser(String username, String password) throws SQLException{
        User user = null;
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try(Connection conn = DatabaseUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setRole(rs.getString("role"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setAccountSid(rs.getString("account_sid"));
                user.setAuthToken(rs.getString("auth_token"));
                user.setSenderId(rs.getString("sender_id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;

    }

    public static User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setUsername(rs.getString("username"));
                    user.setBirthday(rs.getDate("birthday"));
                    user.setPassword(rs.getString("password"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setJob(rs.getString("job"));
                    user.setEmail(rs.getString("email"));
                    user.setAddress(rs.getString("address"));
                    user.setAccountSid(rs.getString("account_sid"));
                    user.setAuthToken(rs.getString("auth_token"));
                    user.setSenderId(rs.getString("sender_id"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        }
        return null;
    }
    public static User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setName(rs.getString("name"));
                    user.setBirthday(rs.getDate("birthday"));
                    user.setPassword(rs.getString("password"));
                    user.setPhoneNumber(rs.getString("phone_number"));
                    user.setJob(rs.getString("job"));
                    user.setEmail(rs.getString("email"));
                    user.setAddress(rs.getString("address"));
                    user.setAccountSid(rs.getString("twilio_account_sid"));
                    user.setAuthToken(rs.getString("twilio_auth_token"));
                    user.setSenderId(rs.getString("twilio_sender_id"));
                    user.setRole(rs.getString("role"));
                    return user;
                }
            }
        }
        return null;
    }    
}

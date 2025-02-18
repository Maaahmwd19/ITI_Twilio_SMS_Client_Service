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
                user.setRole(User.Role.valueOf(rs.getString("role").toUpperCase()));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setEmail(rs.getString("email"));
                user.setTwilioAccountSid(rs.getString("twilio_account_sid"));
                user.setTwilioAuthToken(rs.getString("twilio_auth_token"));
                user.setTwilioSenderId(rs.getString("twilio_sender_id"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;

    }
}

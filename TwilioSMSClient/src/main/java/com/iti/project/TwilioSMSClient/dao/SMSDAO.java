/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.project.TwilioSMSClient.dao;

import com.iti.project.TwilioSMSClient.model.SMS;
import com.iti.project.TwilioSMSClient.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author mibrahim
 */
public class SMSDAO {
    public static void saveSMS(int userId, String fromNumber, String toNumber, String body, String status) {
        String sql = "INSERT INTO sms (user_id, from_number, to_number, body, status) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection conn = DatabaseUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, fromNumber);
            stmt.setString(3, toNumber);
            stmt.setString(4, body);
            stmt.setString(5, status);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

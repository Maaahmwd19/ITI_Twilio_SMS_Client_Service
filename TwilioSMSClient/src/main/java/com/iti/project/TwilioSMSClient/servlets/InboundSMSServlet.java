package com.iti.project.TwilioSMSClient.servlets;

import com.twilio.exception.ApiException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.iti.project.TwilioSMSClient.service.SMSService;
import com.iti.project.TwilioSMSClient.model.User;
import com.iti.project.TwilioSMSClient.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/inbound-sms")
public class InboundSMSServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromNumber = request.getParameter("From");
        String toNumber = request.getParameter("To");
        String body = request.getParameter("Body");

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO inbound_sms (from_number, to_number, body, received_at) VALUES (?, ?, ?, NOW())";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, fromNumber);
                pstmt.setString(2, toNumber);
                pstmt.setString(3, body);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("text/plain");
        response.getWriter().write("Inbound SMS received");
    }
}

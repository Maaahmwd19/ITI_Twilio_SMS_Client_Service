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
import java.sql.ResultSet;
import java.util.*;




@WebServlet("/view-inbound-sms")
public class ViewInboundSMSServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> inboundSMS = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT from_number, to_number, body, received_at FROM inbound_sms WHERE to_number = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                pstmt.setString(1, user.getPhoneNumber());

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> sms = new HashMap<>();
                        sms.put("fromNumber", rs.getString("from_number"));
                        sms.put("toNumber", rs.getString("to_number"));
                        sms.put("body", rs.getString("body"));
                        sms.put("receivedAt", rs.getTimestamp("received_at"));
                        inboundSMS.add(sms);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("inboundSMS", inboundSMS);
        request.getRequestDispatcher("/pages/inbound-sms.jsp").forward(request, response);
    }
}

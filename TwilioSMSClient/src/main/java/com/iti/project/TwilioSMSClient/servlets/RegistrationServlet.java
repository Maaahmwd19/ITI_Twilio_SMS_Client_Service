package com.iti.project.TwilioSMSClient.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.iti.project.TwilioSMSClient.util.DatabaseUtil;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        String name = request.getParameter("name");
        String username = request.getParameter("uname");
        String birthday = request.getParameter("birthday");
        String job = request.getParameter("job");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String twilioSid = request.getParameter("twilio_sid");
        String twilioToken = request.getParameter("twilio_token");
        
        int verificationCode = new Random().nextInt(900000) + 100000;
        session.setAttribute("verificationCode", verificationCode);
        session.setAttribute("phone", phone);
        session.setAttribute("attempts", 0);  //The attempts attribute in the session is used to track the number of times a user enters the verification code incorrectly. 
        
        try (Connection conn = DatabaseUtil.getConnection()) {
            String checkUserSql = "SELECT * FROM users WHERE phone = ? OR email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkUserSql);
            checkStmt.setString(1, phone);
            checkStmt.setString(2, email);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                out.println("User with this phone number or email already exists.");
            } else {
                String sql = "INSERT INTO users (name, username, birthday, job, phone, email, address, password, twilio_sid, twilio_token, verification_code, verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, name);
                stmt.setString(2, username);
                stmt.setString(3, birthday);
                stmt.setString(4, job);
                stmt.setString(5, phone);
                stmt.setString(6, email);
                stmt.setString(7, address);
                stmt.setString(8, password);
                stmt.setString(9, twilioSid);
                stmt.setString(10, twilioToken);
                stmt.setInt(11, verificationCode);
                stmt.executeUpdate();
                
                // Send SMS verification
                Twilio.init(twilioSid, twilioToken);
                Message.creator(new PhoneNumber(phone), new PhoneNumber("YourTwilioSenderID"), "Your verification code: " + verificationCode).create();
                
                response.sendRedirect("/TwilioSMSClient/pages/phoneNumberValidation.html");
            }
        } catch (SQLException e) {
            out.println("Database error: " + e.getMessage());
        }
    }
}

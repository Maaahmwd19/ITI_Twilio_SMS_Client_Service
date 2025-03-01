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

        // 🌟 Get form parameters
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String birthday = request.getParameter("birthday");
        String job = request.getParameter("job");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String address = request.getParameter("address");
        String password = request.getParameter("password");
        String twilioSid = request.getParameter("twilio_sid");
        String twilioToken = request.getParameter("twilio_token");
        String twilioSenderId = request.getParameter("twilio_sender_id");

        // 🌟 Generate verification code
        int verificationCode = new Random().nextInt(900000) + 100000;
        session.setAttribute("verificationCode", verificationCode);
        session.setAttribute("phone", phone);
        session.setAttribute("attempts", 0);

        // 🌟 Establish database connection
        try (Connection conn = DatabaseUtil.getConnection()) {
            if (conn == null) {
                System.err.println("❌ ERROR: Database connection failed!");
                out.println("<script>alert('Database connection error.'); window.location='/TwilioSMSClient/pages/register1.html';</script>");
                return;
            }

            conn.setAutoCommit(false); // Disable auto-commit

            // 🌟 Check if user already exists
            String checkUserSql = "SELECT * FROM users WHERE phone_number = ? OR email = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserSql)) {
                checkStmt.setString(1, phone);
                checkStmt.setString(2, email);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    System.out.println("⚠️ User already exists: " + email + " or " + phone);
                    out.println("<script>alert('User already exists!'); window.location='/TwilioSMSClient/pages/register1.html';</script>");
                    return;
                }
            }

            // 🌟 Insert new user
            String sql = "INSERT INTO users (username, password, name, phone_number, email, role, twilio_account_sid, twilio_auth_token, twilio_sender_id, birthday, job, address, is_valid) " +
                         "VALUES (?, ?, ?, ?, ?, 'customer', ?, ?, ?, ?, ?, ?, 0)";

            System.out.println("🟢 Preparing SQL statement...");
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, name);
                stmt.setString(4, phone);
                stmt.setString(5, email);
                stmt.setString(6, twilioSid);
                stmt.setString(7, twilioToken);
                stmt.setString(8, twilioSenderId);
                stmt.setString(9, birthday);
                stmt.setString(10, job);
                stmt.setString(11, address);

                int rowsInserted = stmt.executeUpdate();
                System.out.println("🟢 Rows inserted: " + rowsInserted);

                if (rowsInserted > 0) {
                    conn.commit(); // 🌟 Commit transaction if insert was successful
                    System.out.println("✅ User registered successfully.");

                    // 🌟 Send verification SMS (Handled separately to avoid blocking)
                    try {
                        Twilio.init(twilioSid, twilioToken);
                        Message.creator(
                                new PhoneNumber(phone),
                                new PhoneNumber(twilioSenderId != null ? twilioSenderId : "YourTwilioNumber"),
                                "Your verification code: " + verificationCode
                        ).create();
                    } catch (Exception e) {
                        System.err.println("⚠️ Twilio error: " + e.getMessage());
                    }

                    // 🌟 Show success alert & Redirect
                    out.println("<script>alert('Registration successful!'); window.location='/TwilioSMSClient/pages/login1.html';</script>");
                } else {
                    conn.rollback(); // Rollback if insert failed
                    System.err.println("❌ Error: User not inserted.");
                    out.println("<script>alert('Error occurred while registering. Please try again.'); window.location='/TwilioSMSClient/pages/register1.html';</script>");
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Database error: " + e.getMessage());
            e.printStackTrace();
            out.println("<script>alert('Database error: " + e.getMessage() + "'); window.location='/TwilioSMSClient/pages/register1.html';</script>");
        }
    }
}

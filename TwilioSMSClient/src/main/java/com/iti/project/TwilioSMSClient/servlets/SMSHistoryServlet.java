package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/smsHistory")
public class SMSHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        String username = (session != null) ? (String) session.getAttribute("username") : null;

        if (username == null) {
            response.getWriter().println("[]");
            return;
        }

        try (PrintWriter out = response.getWriter();
             Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "SELECT s.id, u1.username AS sender, u2.username AS receiver, s.body AS message_body, s.date AS sent_date " +
                 "FROM sms s " +
                 "JOIN users u1 ON s.from_number = u1.phone_number " +
                 "JOIN users u2 ON s.to_number = u2.phone_number " +
                 "WHERE u1.username = ? OR u2.username = ?")) {

            ps.setString(1, username);
            ps.setString(2, username);

            try (ResultSet rs = ps.executeQuery()) {
                StringBuilder json = new StringBuilder("[");
                boolean first = true;

                while (rs.next()) {
                    if (!first) json.append(",");
                    json.append("{");
                    json.append("\"id\":\"").append(rs.getInt("id")).append("\", ");
                    json.append("\"from\":\"").append(rs.getString("sender")).append("\", ");
                    json.append("\"to\":\"").append(rs.getString("receiver")).append("\", ");
                    json.append("\"body\":\"").append(rs.getString("message_body")).append("\", ");
                    json.append("\"date\":\"").append(rs.getString("sent_date")).append("\"");
                    json.append("}");
                    first = false;
                }

                json.append("]");
                out.println(json.toString());
            }
        } catch (Exception e) {
            response.getWriter().println("[]");
        }
    }
}

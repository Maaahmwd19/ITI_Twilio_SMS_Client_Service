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
import com.iti.project.TwilioSMSClient.model.*;

@WebServlet("/smsHistory")
public class SMSHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        Integer userId = user.getId();

       
        try (PrintWriter out = response.getWriter();
             Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 "select * from sms where user_id = ?")) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                StringBuilder json = new StringBuilder("[");
                boolean first = true;

                while (rs.next()) {
                    if (!first) json.append(",");
                    json.append("{");
                    System.out.println(rs.getInt("id"));
                    json.append("\"id\":\"").append(rs.getInt("id")).append("\", ");
                    json.append("\"from\":\"").append(rs.getString("from_number")).append("\", ");
                    json.append("\"to\":\"").append(rs.getString("to_number")).append("\", ");
                    json.append("\"body\":\"").append(rs.getString("body")).append("\", ");
                    json.append("\"date\":\"").append(rs.getDate("date")).append("\", ");
                    json.append("\"status\":\"").append(rs.getString("status")).append("\"");
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

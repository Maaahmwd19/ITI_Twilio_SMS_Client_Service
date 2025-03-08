package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteSMSServlet extends HttpServlet {
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");

    String smsIdStr = request.getParameter("id");
    System.out.println("Received DELETE request for SMS ID: " + smsIdStr);

    if (smsIdStr == null || smsIdStr.isEmpty()) {
        response.getWriter().write("Invalid SMS ID");
        return;
    }

    int smsId;
    try {
        smsId = Integer.parseInt(smsIdStr);
    } catch (NumberFormatException e) {
        response.getWriter().write("Invalid SMS ID format");
        return;
    }

    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement("DELETE FROM sms WHERE id = ?")) {

        ps.setInt(1, smsId);
        int rowsAffected = ps.executeUpdate();

        System.out.println("Rows affected: " + rowsAffected);

        if (rowsAffected > 0) {
            response.getWriter().write("SMS Deleted Successfully");
        } else {
            response.getWriter().write("SMS Not Found or Cannot be Deleted");
        }
    } catch (Exception e) {
        e.printStackTrace();
        response.getWriter().write("Error deleting SMS: " + e.getMessage());
    }
}

}
package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class DeleteSMSServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject jsonResponse = new JSONObject();

        String smsId = request.getParameter("smsId");
        System.out.println("Received delete request for SMS ID: " + smsId);

        if (smsId == null || smsId.isEmpty()) {
            System.out.println("Invalid SMS ID received!");
            jsonResponse.put("success", false);
            jsonResponse.put("error", "Invalid SMS ID");
            out.print(jsonResponse.toString());
            return;
        }

        try {
            try (Connection conn = DatabaseUtil.getConnection()) {
                String query = "DELETE FROM sms WHERE id = ?";  // Ensure correct table name
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, Integer.parseInt(smsId));
                    
                    int affectedRows = stmt.executeUpdate();
                    if (affectedRows > 0) {
                        jsonResponse.put("success", true);
                        System.out.println("SMS deleted successfully.");
                    } else {
                        jsonResponse.put("success", false);
                        jsonResponse.put("error", "SMS not found");
                        System.out.println("No SMS found with ID: " + smsId);
                    }
                }
            } // Ensure correct table name // Ensure correct table name
        } catch (NumberFormatException | SQLException | JSONException e) {
            jsonResponse.put("success", false);
            jsonResponse.put("error", "Database error: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
    }
}

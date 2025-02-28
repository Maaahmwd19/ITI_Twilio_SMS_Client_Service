/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author mibrahim
 */
public class DeleteSMSServlet extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws IOException
     */
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String smsId = request.getParameter("smsId");
        System.out.println(smsId);
        if (smsId == null || smsId.isEmpty()) {
            response.getWriter().write("{\"success\": false, \"error\": \"SMS ID is missing\"}");
            return;
        }

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM sms WHERE id = ?")) {
             
            ps.setInt(1, Integer.parseInt(smsId));
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                response.getWriter().write("{\"success\": true}");
            } else {
                response.getWriter().write("{\"success\": false, \"error\": \"SMS not found\"}");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("{\"success\": false, \"error\": \"Invalid SMS ID format\"}");
        } catch (Exception e) {
            response.getWriter().write("{\"success\": false, \"error\": \"Database error\"}");
        }
    }
}

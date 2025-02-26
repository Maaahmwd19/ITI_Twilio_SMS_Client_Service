/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.iti.project.TwilioSMSClient.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mibrahim
 */
public class RevealTokenServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.getWriter().write("{\"success\": false, \"error\": \"Not authenticated\"}");
            return;
        }

        String authToken = (String) session.getAttribute("auth_token");
        response.setContentType("application/json");
        response.getWriter().write("{\"success\": true, \"token\": \"" + authToken + "\"}");
    }
}
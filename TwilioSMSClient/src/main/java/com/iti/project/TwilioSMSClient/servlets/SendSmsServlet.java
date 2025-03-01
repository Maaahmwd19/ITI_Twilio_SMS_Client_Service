
package com.iti.project.TwilioSMSClient.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.iti.project.TwilioSMSClient.service.SMSService;
import com.twilio.exception.ApiException;
/**
 *
 * @author XPRISTO
 */
@WebServlet(name = "SendSmsServlet", urlPatterns = {"/SendSmsServlet"})
public class SendSmsServlet extends HttpServlet {

 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String accountSid = (String) session.getAttribute("account_sid");
        String authToken = (String) session.getAttribute("auth_token");
        String from = (String) session.getAttribute("phoneNumber"); 

        if (accountSid == null || authToken == null || from == null) {
            response.getWriter().write("Twilio credentials not found in session.");
            return;
        }

        String to = request.getParameter("to");
        String body = request.getParameter("body");

        if (to == null || to.isEmpty() || body == null || body.isEmpty()) {
            response.getWriter().write("Please enter both recipient's phone number and message body.");
           response.sendRedirect("/pages/HomaPage.html");

        }
        int userId = (int) session.getAttribute("userId");
        try {
            SMSService.sendSMS(accountSid, authToken, from, to, body, userId);
            //smsDao.saveSMS();
            response.sendRedirect("pages/smsHistory.html");

        } catch (ApiException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to send SMS: " + e.getMessage());
             response.sendRedirect("/pages/HomaPage.html");

        }
    }   
 


}

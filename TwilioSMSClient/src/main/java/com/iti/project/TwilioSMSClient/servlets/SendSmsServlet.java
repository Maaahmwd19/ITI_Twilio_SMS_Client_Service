
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
import com.iti.project.TwilioSMSClient.model.User;
/**
 *
 * @author XPRISTO
 */
@WebServlet(name = "SendSmsServlet", urlPatterns = {"/SendSmsServlet"})
public class SendSmsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        String from_number = (String) request.getParameter("from_number");
        String to = request.getParameter("to_number");
        String body = request.getParameter("body");
        if (user == null) {
            response.sendRedirect("/TwilioSMSClient/pages/login.html");
            return;
        }
        String accountSid = (String) user.getAccountSid();
        String authToken = (String) user.getAuthToken();
       // String from = (String) session.getAttribute("phoneNumber"); 
        Integer userId = (Integer) user.getId();
        System.out.println("accountSid: " + accountSid);
        System.out.println("authToken: " + authToken);
        System.out.println("userId: " + userId);
        System.out.println("from_number: " + from_number);
        System.out.println(user.isVerified());
        if(!user.isVerified()){
            response.sendRedirect("/TwilioSMSClient/pages/phoneNumberValidation.html");
            return;
        }
    

        
        if (accountSid == null || authToken == null || from_number == null || userId == null || to == null || body == null) {
            response.getWriter().write("Twilio credentials not found in session.");
            response.sendRedirect("/TwilioSMSClient/pages/SendSMS.html");
            return;
        }

    


        try {
            SMSService.sendSMS(accountSid, authToken, from_number, to, body, userId);
            response.sendRedirect("/TwilioSMSClient/pages/smsHistory.html");
        } catch (ApiException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to send SMS: " + e.getMessage());
            response.sendRedirect("/TwilioSMSClient/pages/smsHistory.html");
        }
    }
}
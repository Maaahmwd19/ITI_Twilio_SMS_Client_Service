package com.iti.project.TwilioSMSClient.servlets;

import com.twilio.exception.ApiException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.iti.project.TwilioSMSClient.service.SMSService;
import com.iti.project.TwilioSMSClient.model.User;

@WebServlet("/SendVerificationCodeServlet")
public class SendVerificationCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null) {
            response.sendRedirect("/TwilioSMSClient/pages/login.html");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/TwilioSMSClient/pages/login.html");
            return;
        }
        
        int userId = user.getId();
        // String accountSid = user.getAccountSid();
        // String authToken = user.getAuthToken();
        //String from = user.getSenderId();
        String accountSid=user.getAccountSid();
        String authToken=user.getAuthToken();
        String from=user.getPhoneNumber();
        String phoneNumber = request.getParameter("phoneNumber");
        int verificationCode = SMSService.sendVerificationCode(accountSid, authToken, from, phoneNumber, userId);
        session.setAttribute("verificationCode", verificationCode);

        // Validate credentials
        if (accountSid == null || accountSid.isEmpty() || 
            authToken == null || authToken.isEmpty()) {
            response.getWriter().write("Twilio credentials not properly configured.");
            return;
        }


        if (phoneNumber == null || phoneNumber.isEmpty()) {
            response.getWriter().write("Please enter a valid phone number.");
            return;
        }
        
        try {
          
            session.setAttribute("attempts", 0);
            response.sendRedirect("/TwilioSMSClient/pages/enterVerificationCode.html");
        } catch (ApiException e) {
           // response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //response.getWriter().write("Failed to send verification code: " + e.getMessage());
            
            response.sendRedirect("/TwilioSMSClient/pages/enterVerificationCode.html");
        }
    }
}
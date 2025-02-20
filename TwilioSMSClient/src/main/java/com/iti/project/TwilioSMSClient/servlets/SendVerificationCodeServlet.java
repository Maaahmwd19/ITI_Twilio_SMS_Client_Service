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
        User user=(User) session.getAttribute("user");
        int userId=user.getId();

//        String accountSid = (String) session.getAttribute("account_sid");
//        String authToken = (String) session.getAttribute("auth_token");
//        String from = (String) session.getAttribute("phoneNumber"); 
        
    String accountSid="AC678155c90723b11bab7f47605d59195e";
    String authToken="9f59780a875c0ab154d0946b6204e09d";
    String from="+18314329014";
        


        String phoneNumber = request.getParameter("phoneNumber");

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            response.getWriter().write("Please enter a valid phone number.");
            return;
        }

        try {
            int verificationCode = SMSService.sendVerificationCode(accountSid, authToken, from, phoneNumber,userId);
            session.setAttribute("verificationCode", verificationCode);
            session.setAttribute("attempts", 0);
            response.sendRedirect("/TwilioSMSClient/pages/enterVerificationCode.html");
        } catch (ApiException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to send verification code: " + e.getMessage());
        }
    }
}
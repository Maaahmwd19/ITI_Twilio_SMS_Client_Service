package com.iti.project.TwilioSMSClient.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/verifyCode")
public class VerifyCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer expectedCode = (Integer) session.getAttribute("verificationCode");
        String userEnteredCode = request.getParameter("code");
        Integer attempts = (Integer) session.getAttribute("attempts");

        if (attempts == null) {
            attempts = 0;
        }

        if (expectedCode != null && userEnteredCode != null && expectedCode.toString().equals(userEnteredCode)) {
            session.removeAttribute("verificationCode");
            session.removeAttribute("attempts");
            response.sendRedirect("/TwilioSMSClient/pages/HomePage.html");
        } else {
            attempts++;
            session.setAttribute("attempts", attempts);

            if (attempts >= 3) {
                session.removeAttribute("verificationCode");
                session.removeAttribute("attempts");
                response.sendRedirect("/TwilioSMSClient/pages/phoneNumberValidation.html");
            } else {
                request.setAttribute("errorMessage", "Invalid verification code. Please try again.");
                request.getRequestDispatcher("/TwilioSMSClient/pages/phoneNumberValidation.html").forward(request, response);
            }
        }
    }
}
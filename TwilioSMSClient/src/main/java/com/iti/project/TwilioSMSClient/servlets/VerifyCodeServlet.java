package com.iti.project.TwilioSMSClient.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.iti.project.TwilioSMSClient.dao.UserDAO;
import java.sql.SQLException;
import com.iti.project.TwilioSMSClient.model.User;
@WebServlet("/verifyCode")
public class VerifyCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer expectedCode = (Integer) session.getAttribute("verificationCode");
        String userEnteredCode = request.getParameter("code");
        Integer attempts = (Integer) session.getAttribute("attempts");
        User user = (User) session.getAttribute("user");
        int userId = user.getId();

        if (attempts == null) {
            attempts = 0;
        }

        if (expectedCode != null && userEnteredCode != null && expectedCode.toString().equals(userEnteredCode)) {
            session.removeAttribute("verificationCode");
            session.removeAttribute("attempts");
            try {
                UserDAO.updateUserValidation(userId, true);
                User updatedUser = UserDAO.getUserById(userId);
                session.setAttribute("user", updatedUser);
                response.sendRedirect("/TwilioSMSClient/pages/HomePage.html");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("/TwilioSMSClient/pages/enterVerificationCode.html?error=database_error");
            }
        } else {
            attempts++;
            session.setAttribute("attempts", attempts);
            response.sendRedirect("/TwilioSMSClient/pages/enterVerificationCode.html");
           
        }
    }
}
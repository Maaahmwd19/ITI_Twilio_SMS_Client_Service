package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.dao.UserDAO;
import com.iti.project.TwilioSMSClient.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;


public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login1.html");
            return;
        }

        // Get parameters from the form
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String accountSid = request.getParameter("twilio_account_sid");
        String authToken = request.getParameter("twilio_auth_token");
        // Removed getSenderId - no longer needed
        String birthdayStr = request.getParameter("birthday");
        String job = request.getParameter("job");
        String address = request.getParameter("address");
        
        // Debugging: Print values received
        System.out.println("Updating user: " + name);

        // Update user object
        currentUser.setName(name);
        currentUser.setPhoneNumber(phone);
        currentUser.setEmail(email);
        currentUser.setAccountSid(accountSid);
        currentUser.setAuthToken(authToken);
        // Removed setSenderId - no longer needed
        currentUser.setJob(job);
        currentUser.setAddress(address);

        // Convert birthday
        if (birthdayStr != null && !birthdayStr.isEmpty()) {
            try {
                currentUser.setBirthday(Date.valueOf(birthdayStr));
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format.");
            }
        }

        // Update user in database
        UserDAO userDAO = new UserDAO();
        boolean updateSuccess = userDAO.updateUser(currentUser);

        if (updateSuccess) {
            session.setAttribute("user", currentUser); // Update 
            request.setAttribute("successMessage", "Profile updated successfully.");

            response.sendRedirect(request.getContextPath() + "/ProfileServlet");
        } else {
            request.setAttribute("errorMessage", "Profile update failed.");
            response.sendRedirect(request.getContextPath() + "/ProfileServlet");
        }
    }
}
package com.iti.project.TwilioSMSClient.servlet;

import com.iti.project.TwilioSMSClient.dao.UserDAO;
import com.iti.project.TwilioSMSClient.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UpdateProfileServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.getWriter().write("Session expired. Please log in again.");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        String job = request.getParameter("job");
        String address = request.getParameter("address");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();

        if (userDAO.isEmailTaken(email, currentUser.getId())) {
            response.getWriter().write("Email is already in use.");
            return;
        }

        if (userDAO.isPhoneTaken(phone, currentUser.getId())) {
            response.getWriter().write("Phone number is already in use.");
            return;
        }

        currentUser.setName(name);
        currentUser.setPhoneNumber(phone);
        currentUser.setEmail(email);
        currentUser.setBirthday(java.sql.Date.valueOf(birthday));
        currentUser.setJob(job);
        currentUser.setAddress(address);

        boolean success = userDAO.updateUser(currentUser);
        if (success) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("Failed to update profile.");
        }
    }
}

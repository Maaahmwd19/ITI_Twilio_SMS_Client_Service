package com.iti.project.TwilioSMSClient.servlets;

import com.iti.project.TwilioSMSClient.util.DatabaseUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        if (username != null && !username.trim().isEmpty()) {
            try (Connection conn = DatabaseUtil.getConnection()) {
                
                conn.setAutoCommit(false);

               
                String deleteSmsSql = "DELETE FROM sms WHERE user_id = (SELECT id FROM users WHERE username = ?)";
                try (PreparedStatement smsStmt = conn.prepareStatement(deleteSmsSql)) {
                    smsStmt.setString(1, username);
                    smsStmt.executeUpdate();
                }

               
                String deleteUserSql = "DELETE FROM users WHERE username = ?";
                try (PreparedStatement userStmt = conn.prepareStatement(deleteUserSql)) {
                    userStmt.setString(1, username);
                    int rowsAffected = userStmt.executeUpdate();

                    if (rowsAffected > 0) {
                        conn.commit(); 
                        response.sendRedirect("register.html?success=true");
                    } else {
                        conn.rollback();  
                        response.sendRedirect("adminHome.html?error=notfound");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("register.html?error=sqlerror");
            }
        } else {
            response.sendRedirect("register.html?error=invalidusername");
        }
    }
}

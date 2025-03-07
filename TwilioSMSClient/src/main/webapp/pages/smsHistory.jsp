<%@ page import="java.sql.*, java.util.*, java.text.SimpleDateFormat, com.iti.project.TwilioSMSClient.util.DatabaseUtil, javax.servlet.http.HttpSession, com.iti.project.TwilioSMSClient.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View Twilio SMS History</title>
        <link rel="icon" type="image/png" href="../images/message.png">
        <link rel="stylesheet" href="../Styles/headerAndFooterStyle.css">
        <link rel="stylesheet" href="../Styles/SMShistory.css">
        <script defer src="../Scripts/SMShistory.js"></script>
        <style>
            .delete-button {
                background-color: #ff4d4d;
                color: white;
                border: none;
                padding: 8px 12px;
                cursor: pointer;
                border-radius: 5px;
                font-size: 14px;
                transition: background 0.3s ease;
            }

            .delete-button:hover {
                background-color: #cc0000;
            }
        </style>
    </head>
    <body>
        <header class="header">
            <nav>
                <ul>
                    <li><a href="HomePage.html">Home</a></li>
                    <li><a href="SendSMS.html">Send Message</a></li>
                    <li><a href="smsHistory.html" class="active">SMS History</a></li>
                    <li><a href="/TwilioSMSClient/view-inbound-sms">Inbound Messages</a></li>
                    <li><a href="/TwilioSMSClient/ProfileServlet">Profile</a></li>
                    <li><a href="/TwilioSMSClient/LogoutServlet">Logout</a></li>
                </ul>
            </nav>
        </header>

        <div class="container">
            <h1>SMS History</h1>
            <div class="table-frame">
                <table id="smsTable">
                    <thead>
                        <tr>
                            <th>From</th>
                            <th>To</th>
                            <th>Body</th>
                            <th>Date/Time</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            HttpSession sessionObj = request.getSession(false);
                            User user = (User) sessionObj.getAttribute("user");

                            if (user != null) {
                                Integer userId = user.getId();
                                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a"); // MM/DD/YYYY hh:mm AM/PM

                                try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT * FROM sms WHERE user_id = ?")) {

                                    ps.setInt(1, userId);
                                    try (ResultSet rs = ps.executeQuery()) {
                                        while (rs.next()) {
                        %>
                        <tr data-sms-id="<%= rs.getInt("id")%>">
                            <td><%= rs.getString("from_number")%></td>
                            <td><%= rs.getString("to_number")%></td>
                            <td><%= rs.getString("body")%></td>
                            <td><%= dateTimeFormat.format(rs.getTimestamp("date"))%></td>
                            <td>
                                <button class="delete-button" onclick="deleteSMS(this)">Delete</button>
                            </td>
                        </tr>
                        <%
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

        <footer class="footer">
            <p style="color: white; opacity: 100%;">All rights reserved to ITI 2025 Intake 45 Telecom Track</p>
        </footer>

    </body>
</html>

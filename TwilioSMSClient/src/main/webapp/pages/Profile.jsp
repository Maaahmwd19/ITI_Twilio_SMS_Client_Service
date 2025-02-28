<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.iti.project.TwilioSMSClient.model.User" %>

<%
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj == null || sessionObj.getAttribute("user") == null) {
        response.sendRedirect("login.html");
        return;
    }

    User user = (User) sessionObj.getAttribute("user");
%>

<html>
    <head>
        <title>Profile</title>
        <link rel="icon" type="image/png" href="../images/settings.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../Styles/Settings.css">
        <style>
            /* Flex container for two-column layout */
            .form-row {
                display: flex;
                justify-content: space-between;
                gap: 15px;
                margin-bottom: 15px;
            }
            .field {
                flex: 1;
                display: flex;
                flex-direction: column;
            }
            .field input, .field textarea {
                width: 100%;
                padding: 10px;
                font-size: 1em;
                border: 1px solid #ccc;
                border-radius: 5px;
                background: #f5f5f5;
                outline: none;
                transition: border 0.3s;
            }
            .field input:focus {
                border: 1px solid #007bff;
            }
        </style>
    </head>
    <body>

        <header class="header">
            <nav>
                <ul>
                    <li><a href="HomePage.html">Home</a></li>
                    <li><a href="SendSMS.html">Send Message</a></li>
                    <li><a href="smsHistory.html">SMS History</a></li>
                    <li><a href="Profile.jsp" class="active">Profile</a></li>
                    <li><a href="/TwilioSMSClient/LogoutServlet">Logout</a></li>
                </ul>
            </nav>
        </header>

        <div class="container">
            <h2>User Profile</h2>

            <!-- Profile Form -->
            <form action="<%= request.getContextPath()%>/UpdateProfileServlet" method="POST" id="profileForm">
                
                <div class="form-row">
                    <div class="field">
                        <label>Name:</label>
                        <input type="text" name="name" value="<%= user.getName()%>" readonly id="name" />
                    </div>
                    <div class="field">
                        <label>User Name:</label>
                        <input type="text" name="username" value="<%= user.getUsername()%>" readonly id="username" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>Phone:</label>
                        <input type="text" name="phone" value="<%= user.getPhoneNumber()%>" readonly id="phone" />
                    </div>
                    <div class="field">
                        <label>Email:</label>
                        <input type="email" name="email" value="<%= user.getEmail()%>" readonly id="email" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>Twilio Account SID:</label>
                        <input type="text" name="twilio_account_sid" value="<%= user.getAccountSid()%>" readonly id="twilioAccountSid" />
                    </div>
                    <div class="field">
                        <label>Twilio Auth Token:</label>
                        <input type="text" name="twilio_auth_token" value="<%= user.getAuthToken()%>" readonly id="twilioAuthToken" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>Twilio Sender ID:</label>
                        <input type="text" name="twilio_sender_id" value="<%= user.getSenderId()%>" readonly id="twilioSenderId" />
                    </div>
                    <div class="field">
                        <label>Birthday:</label>
                        <input type="date" name="birthday" value="<%= user.getBirthday()%>" readonly id="birthday" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>Job:</label>
                        <input type="text" name="job" value="<%= user.getJob()%>" readonly id="job" />
                    </div>
                    <div class="field">
                        <label>Address:</label>
                        <input type="text"  name="address"  value="<%=user.getAddress()%>" readonly id="address"/>
                    </div>
                </div>

                <!-- Password Field (Hidden initially) -->
                <div id="passwordField" class="form-row" style="display: none;">
                    <div class="field">
                        <label>Enter Password to Save Changes:</label>
                        <input type="password" name="password" required id="password" />
                    </div>
                </div>

                <!-- Submit Button (Hidden initially) -->
                <div id="saveButton" style="display: none;">
                    <button type="submit">Save Changes</button>
                </div>

                <!-- Edit Button -->
                <button type="button" id="editButton" onclick="enableEditing()">Edit</button>
            </form>
        </div>

        <footer class="footer">
            <p>All rights reserved to ITI 2024 Intake 45 Telecom Track</p>
        </footer>

        <script src="../Scripts/Profile.js"></script>

        <script>
            // Function to enable editing the profile
            function enableEditing() {
                let fields = ['name', 'username', 'phone', 'email', 'twilioAccountSid', 'twilioAuthToken', 
                              'twilioSenderId', 'birthday', 'job', 'address'];
                fields.forEach(field => document.getElementById(field).removeAttribute('readonly'));

                // Show password field and save button
                document.getElementById('passwordField').style.display = 'flex';
                document.getElementById('saveButton').style.display = 'block';

                // Hide the Edit button
                document.getElementById('editButton').style.display = 'none';
            }
        </script>

    </body>
</html>

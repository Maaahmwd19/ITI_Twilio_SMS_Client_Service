<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.iti.project.TwilioSMSClient.model.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>



<html>
    <head>
        <title>Profile</title>
        <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/settings.png">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Styles/Settings.css">
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
                    <li><a href="${pageContext.request.contextPath}/pages/HomePage.html">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/pages/SendSMS.html">Send Message</a></li>
                    <li><a href="${pageContext.request.contextPath}/pages/smsHistory.html">SMS History</a></li>
                    <li><a href="/TwilioSMSClient/view-inbound-sms">Inbound Messages</a></li>
                    <li><a href="/TwilioSMSClient/ProfileServlet" class="active">Profile</a></li>
                    <li><a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a></li>
                </ul>
            </nav>
        </header>

        <div class="container">
            <h2>User Profile</h2>
            <c:if test="${not empty errorMessage}">
                <div style="background-color: #ffdddd; color: #ff0000; padding: 10px; margin-bottom: 20px; border-radius: 5px;">
                    ${errorMessage}
                </div>
            </c:if>
            <c:if test="${not empty successMessage}">
                <div style="background-color: #ddffdd; color: #008000; padding: 10px; margin-bottom: 20px; border-radius: 5px;">
                    ${successMessage}
                </div>
            </c:if>
            <!-- Profile Form -->
            <form action="${pageContext.request.contextPath}/UpdateProfileServlet" method="POST" id="profileForm">
                
                <div class="form-row">
                    <div class="field">
                        <label>Name:</label>
                        <input type="text" name="name" value="${name}" readonly id="name" />
                    </div>
                    <div class="field">
                        <label>User Name:</label>
                        <input type="text" name="username" value="${username}" readonly id="username" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>Phone:</label>
                        <input type="text" name="phone" value="${phone}" readonly id="phone" />
                    </div>
                    <div class="field">
                        <label>Email:</label>
                        <input type="email" name="email" value="${email}" readonly id="email" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>Twilio Account SID:</label>
                        <input type="text" name="twilio_account_sid" value="${twilioAccountSid}" readonly id="twilioAccountSid" />
                    </div>
                    <div class="field">
                        <label>Twilio Auth Token:</label>
                        <input type="text" name="twilio_auth_token" value="${twilioAuthToken}" readonly id="twilioAuthToken" />
                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>Birthday:</label>
                        <input type="date" name="birthday" value="${birthday}" readonly id="birthday" />                    </div>
                    <div class="field">
                        <label>Job:</label>
                        <input type="text" name="job" value="${job}" readonly id="job" />                    </div>
                </div>

                <div class="form-row">
                    <div class="field">
                        <label>Address:</label>
                        <input type="text" name="address" value="${address}" readonly id="address"/>
                    </div>
                    <!-- Empty field to maintain the two-column layout -->
                    <!-- <div class="field" style="visibility: hidden;">
                        <label>&nbsp;</label>
                        <input type="text" readonly />
                    </div> -->
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

        <script>
            // Function to enable editing the profile
            function enableEditing() {
                let fields = ['name', 'phone', 'email', 'twilioAccountSid', 'twilioAuthToken', 
                              'birthday', 'job', 'address'];
                fields.forEach(field => document.getElementById(field).removeAttribute('readonly'));

                // Keep username readonly
                document.getElementById('username').setAttribute('readonly', 'readonly');

                // Show password field and save button
                document.getElementById('passwordField').style.display = 'flex';
                document.getElementById('saveButton').style.display = 'block';

                // Hide the Edit button
                document.getElementById('editButton').style.display = 'none';
            }
        </script>

    </body>
</html>
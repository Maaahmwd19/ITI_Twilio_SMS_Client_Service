<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Details</title>
    <link rel="icon" type="image/png" href="../images/message.png">

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(to right, #007bff, #ff7f00);
            color: white;
            text-align: center;

        }
        
        /* Main Container */
        .container {
            max-width: 90%;
            margin: 100px auto 40px;
            background: rgba(255, 255, 255, 0.95);
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.3);
            color: black;
            text-align: center;

        }
        
        /* Header Navigation */
        .header {
            width: 100%;
            background: linear-gradient(to right, #007bff, #ff7f00);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            position: fixed;
            top: 0;
            left: 0;
            z-index: 100;
            padding: 15px 0;
        }
        
        nav ul {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            justify-content: center;
            gap: 25px;
        }
        
        nav ul li a {
            display: block;
            padding: 12px 20px;
            font-size: 1.1em;
            text-decoration: none;
            color: white;
            font-weight: bold;
            border-radius: 5px;
            transition: all 0.3s ease-in-out;
        }
        
        nav ul li a:hover {
            background: rgba(255, 255, 255, 0.2);
            transform: scale(1.1);
        }
        
        nav ul li a.active {
            background: rgba(255, 255, 255, 0.3);
            border-bottom: 3px solid white;
        }
        .customer-info {
    font-size: 1.2em;
    font-weight: bold;
    margin: 20px 0;
    padding: 20px;
    background: #f8f9fa;
    color: black;
    border-radius: 8px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.info-row {
    display: flex;
    justify-content: space-between;
    border-bottom: 1px solid #ddd;
    padding: 12px 0;
}

.info-label {
    font-weight: bold;
    font-size: 1.3em;
    color: #007bff;
}

        
        /* Table Styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
            color: black;
            border-radius: 8px;
            overflow: hidden;
        }
        a{
            margin-top: 1rem;
        }
        th, td {
            padding: 14px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        th {
            background-color: #f2f2f2;
            font-weight: bold;
            text-transform: uppercase;
        }
        
        tr:hover {
            background-color: #f9f9f9;
        }

        .status-sent {
            color: green;
            font-weight: bold;
        }

        .status-failed {
            color: red;
            font-weight: bold;
        }

        .status-pending {
            color: orange;
            font-weight: bold;
        }

        /* Buttons */
        .btn {
            display: inline-block;
            padding: 10px 14px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 14px;
            cursor: pointer;
            text-align: center;
            color: white;
            font-weight: bold;
            transition: all 0.3s ease-in-out;
        }
        
        .btn-view { background-color: #007bff; }
        .btn-edit { background-color: #28a745; }
        .btn-delete { background-color: #dc3545; }
        .btn-back { background-color: #6c757d; }
        
        .btn:hover {
            opacity: 0.8;
        }
        
        /* Footer Styling */
        .footer {
            width: 100%;
            background: linear-gradient(to right, #e47b13, #579ce6);
            opacity: 80%;
            color: black;
            box-shadow: 0px -4px 15px rgba(0, 0, 0, 0.3);
            text-align: center;
            padding: 15px 0;
            bottom: 0;
            left: 0;
            font-size: 1.1em;
            font-weight: bold;
            position: fixed;
        }
        
        /* Responsive Design */
        @media screen and (max-width: 768px) {
            .container {
                width: 95%;
                padding: 20px;
            }
        
            nav ul {
                flex-direction: column;
                gap: 10px;
            }
        
            nav ul li a {
                padding: 10px;
                font-size: 1em;
            }
        }
        
    </style>
</head>
<body>
    <header class="header">
        <nav>
            <ul>
                <li><a href="/TwilioSMSClient/pages/AdminHomePage.html" >Home</a></li>
                <li><a href="/TwilioSMSClient/admin?action=listCustomers" class="active">View Customers</a></li>
                <li><a href="/TwilioSMSClient/dashboard">DashBoard</a></li>
                <li><a href="/TwilioSMSClient/adminprofile">Profile</a></li>
                <li><a href="/TwilioSMSClient/LogoutServlet">Logout</a></li>
            </ul>
        </nav>
    </header>

    <div class="container">
        <h1>Customer Details</h1>
        
        <div class="customer-info">
            <div><strong>ID:</strong> ${customer.id}</div>
            <div><strong>Username:</strong> ${customer.username}</div>
            <div><strong>Email:</strong> ${customer.email}</div>
            <div><strong>Phone Number:</strong> ${customer.phone_number}</div>
            <div><strong>Total SMS:</strong> ${smsList.size()}</div>
        </div>
        
        <h2>SMS History</h2>
        
        <c:choose>
            <c:when test="${not empty smsList}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Message</th>
                            <th>Status</th>
                            <th>Date Sent</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="sms" items="${smsList}">
                            <tr>
                                <td>${sms.id}</td>
                                <td>${sms.from}</td>
                                <td>${sms.to}</td>
                                <td title="${sms.message}">${sms.message}</td>
                                <td class="${sms.status == 'success' ? 'status-sent' : (sms.status == 'failed' ? 'status-failed' : 'status-pending')}">${sms.status}</td>
                                <td><fmt:formatDate value="${sms.sentDate}" pattern="yyyy-MM-dd" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p>No SMS records found for this customer.</p>
            </c:otherwise>
        </c:choose>
        
        <a href="admin" class="btn btn-back">Back to List</a>
        <a href="admin?action=editCustomerForm&id=${customer.id}" class="btn btn-edit">Edit Customer</a>
    </div>
</body>
<footer class="footer" >
    <p>All rights reserved to ITI 2024 Intake 45 Telecom Track</p>
</footer>
</html>

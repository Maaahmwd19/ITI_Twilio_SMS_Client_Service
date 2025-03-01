<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Panel - Customer Management</title>
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

th, td {
    padding: 14px;
    text-align: center;
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

/* Buttons */
.actions {
    /* display: relative; */
    gap: 20px;
    justify-content: center;
    text-align: center;

}

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
    width: 4rem;
}

.btn-view { background-color: #007bff; }
.btn-edit { background-color: #28a745; }
.btn-delete { background-color: #dc3545; }

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
                <li><a href="Settings.html">Settings</a></li>
                <li><a href="/TwilioSMSClient/LogoutServlet">Logout</a></li>
            </ul>
        </nav>
    </header>
    <div class="container">
        <h1>Customer Management</h1>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th class="stats">SMS Count</th>
                    <th class="actions">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="customer" items="${customers}">
                    <tr>
                        <td>${customer.id}</td>
                        <td>${customer.username}</td>
                        <td class="stats">${customer.smsCount}</td>
                        <td class="actions">
                            <a href="admin?action=viewCustomer&id=${customer.id}" class="btn btn-view">View</a>
                            <a href="admin?action=editCustomerForm&id=${customer.id}" class="btn btn-edit">Edit</a>
                            <a href="admin?action=deleteCustomer&id=${customer.id}" onclick="return confirm('Are you sure you want to delete this customer?')">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <footer class="footer">
        <p>All rights reserved to ITI 2024 Intake 45 Telecom Track</p>
    </footer>
</body>
</html>
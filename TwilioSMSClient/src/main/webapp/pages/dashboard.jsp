<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>SMS Dashboard</title>
    <link rel="icon" type="image/png" href="../images/message.png">

    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            color: #333;
        }
        
        .dashboard {
            padding: 20px;
        }
        
      
/* Header Navigation */
 
.header {
         background: linear-gradient(to right, #007bff, #ff7f00);
            color: white;
            padding: 15px 20px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
            
        }
        
        .header h1 {
            margin: 0;
            font-size: 24px;
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

        
        .stats-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background-color: #fff;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
        }
        
        .stat-card:hover {
            transform: translateY(-5px);
        }
        
        .stat-title {
            font-size: 16px;
            color: #6c757d;
            margin-bottom: 10px;
        }
        
        .stat-value {
            font-size: 28px;
            font-weight: bold;
            color: #0056b3;
        }
        
        .grid-container {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .card {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .card-header {
            background-color: #f8f9fa;
            padding: 15px 20px;
            border-bottom: 1px solid #eee;
        }
        
        .card-title {
            margin: 0;
            font-size: 18px;
            color: #212529;
        }
        
        .card-body {
            padding: 20px;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
        }
        
        th, td {
            padding: 12px 15px;
            text-align: left;
        }
        
        th {
            background-color: #f8f9fa;
            color: #495057;
            font-weight: 600;
        }
        
        tbody tr {
            border-bottom: 1px solid #eee;
        }
        
        tbody tr:hover {
            background-color: #f8f9fa;
        }
        
        .message-cell {
            max-width: 250px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        
        .status-sent {
            color: #28a745;
            font-weight: 600;
        }
        
        .status-failed {
            color: #dc3545;
            font-weight: 600;
        }
        
        .status-pending {
            color: #ffc107;
            font-weight: 600;
        }
        
        .chart-container {
            height: 300px;
            position: relative;
        }
        
        .navigation {
            margin-top: 20px;
            text-align: center;

        }
        
        .nav-link {
            display: inline-block;
            padding: 10px 20px;
            background-color: #0056b3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
            transition: background-color 0.3s;
            width: 25%;
            font-size: larger;
            font-style: revert-layer;
            font-weight: bold;
        }
        
        .nav-link:hover {
            background-color: #004494;
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
    <!-- Include Chart.js for creating charts -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
   
    <div class="header">
        <header >
            <nav>
                <ul>
                    <li><a href="/TwilioSMSClient/pages/AdminHomePage.html" >Home</a></li>
                    <li><a href="/TwilioSMSClient/admin?action=listCustomers" >View Customers</a></li>
                    <li><a href="/TwilioSMSClient/dashboard" class="active">DashBoard</a></li>
                    <li><a href="/TwilioSMSClient/adminprofile" >Profile</a></li>
                    <li><a href="/TwilioSMSClient/LogoutServlet">Logout</a></li>
                </ul>
            </nav>
        </header>    </div>
    
    <div class="dashboard">
        <!-- Summary Statistics -->
        <div class="stats-container">
            <div class="stat-card">
                <div class="stat-title">Total Users</div>
                <div class="stat-value">${stats.totalUsers}</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-title">Total SMS Messages</div>
                <div class="stat-value">${stats.totalSMS}</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-title">SMS Sent Today</div>
                <div class="stat-value">${stats.smsSentToday}</div>
            </div>
            
            <div class="stat-card">
                <div class="stat-title">Success Rate</div>
                <div class="stat-value">
                    <c:if test="${stats.totalSMS > 0 && smsStatusDistribution['success'] != null}">
                        <fmt:formatNumber type="number" 
                                        maxFractionDigits="1" 
                                        value="${(smsStatusDistribution['success'] / stats.totalSMS) * 100}" />%
                    </c:if>
                    <c:if test="${stats.totalSMS == 0 || smsStatusDistribution['success'] == null}">
                        0%
                    </c:if>
                </div>
            </div>
        </div>
        
        <div class="grid-container">
            <!-- Customer Activity Chart -->
            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">Top 5 Users by SMS Count</h2>
                </div>
                <div class="card-body">
                    <div class="chart-container">
                        <canvas id="customerActivityChart"></canvas>
                    </div>
                </div>
            </div>
            
            <!-- SMS Status Distribution Chart -->
            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">SMS Status Distribution</h2>
                </div>
                <div class="card-body">
                    <div class="chart-container">
                        <canvas id="smsStatusChart"></canvas>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Recent SMS Table -->
        <div class="card">
            <div class="card-header">
                <h2 class="card-title">Recent SMS Messages</h2>
            </div>
            <div class="card-body">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>User</th>
                            <th>From</th>
                            <th>To</th>
                            <th>Message</th>
                            <th>Status</th>
                            <th>Date Sent</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="sms" items="${recentSMS}">
                            <tr>
                                <td>${sms.id}</td>
                                <td>${sms.username}</td>
                                <td>${sms.fromNumber}</td>
                                <td>${sms.toNumber}</td>
                                <!-- <td>${sms.body}</td> -->
                                <td class="message-cell" title="${sms.body}">${sms.body}</td>
                                <td class="${sms.status == 'success' ? 'status-sent' : (sms.status == 'failed' ? 'status-failed' : 'status-pending')}">${sms.status}</td>
                                <td><fmt:formatDate value="${sms.date}" pattern="yyyy-MM-dd" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        
        <div class="navigation">
            <a href="admin" class="nav-link">Manage Customers</a>
        </div>
    </div>
    <footer class="footer">
        <p>All rights reserved to ITI 2024 Intake 45 Telecom Track</p>
    </footer>
    <script>
        // Set up Customer Activity Chart
        const userLabels = [
            <c:forEach var="user" items="${customerActivity}" varStatus="status">
                "${user.username}"${!status.last ? ',' : ''}
            </c:forEach>
        ];
        
        const smsData = [
            <c:forEach var="user" items="${customerActivity}" varStatus="status">
                ${user.smsCount}${!status.last ? ',' : ''}
            </c:forEach>
        ];
        
        const ctx1 = document.getElementById('customerActivityChart').getContext('2d');
        new Chart(ctx1, {
            type: 'bar',
            data: {
                labels: userLabels,
                datasets: [{
                    label: 'Number of SMS',
                    data: smsData,
                    backgroundColor: 'rgba(54, 162, 235, 0.6)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
        
        // Set up SMS Status Distribution Chart
        const smsStatusDistribution = [
        <c:forEach var="entry" items="${smsStatusDistribution}" varStatus="loop">
        { status: "${entry.key}", count: ${entry.value} }<c:if test="${!loop.last}">,</c:if>
        </c:forEach>
    ];

    // Extract labels (statuses) and data (counts) dynamically
    const statusLabels = smsStatusDistribution.map(entry => entry.status);
    const statusData = smsStatusDistribution.map(entry => entry.count);

    const backgroundColors = [
        'rgba(40, 167, 69, 0.6)',  // Green for SENT
        'rgba(220, 53, 69, 0.6)',  // Red for FAILED
    ];

    const ctx2 = document.getElementById('smsStatusChart').getContext('2d');
    new Chart(ctx2, {
        type: 'doughnut',
        data: {
            labels: statusLabels,
            datasets: [{
                data: statusData,
                backgroundColor: backgroundColors.slice(0, statusLabels.length),
                borderColor: backgroundColors.map(color => color.replace('0.6', '1')),
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });
    </script>
</body>
</html>
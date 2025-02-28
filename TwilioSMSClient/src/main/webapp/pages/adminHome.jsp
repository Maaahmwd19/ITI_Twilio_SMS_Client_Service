<%-- 
    Document   : adminHome
    Created on : Feb 27, 2025, 5:48:51 PM
    Author     : mibrahim
--%>

<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Page</title>
    <link rel="stylesheet" href="../Styles/admin.css">
</head>
<body>
    <div class="container">
        <h2>Registered Users</h2>
        <table>
            <tr>
                <th>Username</th>
                <th>Actions</th>
            </tr>
            <% 
                List<String> users = (List<String>) request.getAttribute("users");
                if (users != null) {
                    for (String username : users) { 
            %>
            <tr>
                <td><%= username %></td>
                <td>
                    <form action="DeleteUserServlet" method="post">
                        <input type="hidden" name="username" value="<%= username %>">
                        <button type="submit" class="delete-btn">Delete</button>
                    </form>
                    <form action="UserHistoryServlet" method="get">
                        <input type="hidden" name="username" value="<%= username %>">
                        <button type="submit" class="history-btn">View History</button>
                    </form>
                </td>
            </tr>
            <% 
                    }
                } else { 
            %>
            <tr>
                <td colspan="2">No users found.</td>
            </tr>
            <% } %>
        </table>
    </div>
</body>
</html>

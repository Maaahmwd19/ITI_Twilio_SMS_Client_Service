# /usr/bin/python3
import shutil
import os

# Define project structure
project_name = "BalanceQueryService_NetBeans"
base_path = f"/mnt/data/{project_name}"

folders = [
    "Web Pages",
    "Source Packages/com/example/balancequery/servlet",
    "Source Packages/com/example/balancequery/dao",
    "Source Packages/com/example/balancequery/model",
    "Source Packages/com/example/balancequery/util",
    "Asterisk-AGI",
    "asterisk-config",
    "Configuration Files",
    "nbproject"
]

files = {
    "Web Pages/index.html": "<!DOCTYPE html>\n<html>\n<head>\n<title>Balance Query</title>\n</head>\n<body>\n<h2>Enter MSISDN</h2>\n<input type='text' id='msisdn'><button onclick='queryBalance()'>Check</button>\n<p id='result'></p>\n<script src='balance-query.js'></script>\n</body>\n</html>",
    "Web Pages/balance-query.js": "function queryBalance() { let msisdn = document.getElementById('msisdn').value; fetch('/BalanceQueryService/BalanceServlet?msisdn=' + msisdn).then(response => response.text()).then(data => { document.getElementById('result').innerText = data; }); }",
    "Web Pages/style.css": "body { font-family: Arial; text-align: center; }",
    "Source Packages/com/example/balancequery/servlet/BalanceServlet.java": """package com.example.balancequery.servlet;
import com.example.balancequery.dao.BalanceDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BalanceServlet")
public class BalanceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String msisdn = request.getParameter("msisdn");
        String balance = BalanceDAO.getBalance(msisdn);
        response.getWriter().write(balance);
    }
}""",
    "Source Packages/com/example/balancequery/dao/BalanceDAO.java": """package com.example.balancequery.dao;
import com.example.balancequery.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BalanceDAO {
    public static String getBalance(String msisdn) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT balance FROM user_balance WHERE msisdn = ?");
            stmt.setString(1, msisdn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("balance");
        } catch (Exception e) { e.printStackTrace(); }
        return "Balance not found";
    }
}""",
    "Source Packages/com/example/balancequery/model/UserBalance.java": """package com.example.balancequery.model;
public class UserBalance {
    private String msisdn;
    private String balance;
    public UserBalance(String msisdn, String balance) { this.msisdn = msisdn; this.balance = balance; }
    public String getMsisdn() { return msisdn; }
    public String getBalance() { return balance; }
}""",
    "Source Packages/com/example/balancequery/util/DBConnection.java": """package com.example.balancequery.util;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/balance_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";
    public static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}""",
    "Asterisk-AGI/balance-query.agi": """#!/usr/bin/python3
import sys, requests

print("Content-type: text/plain\n")

msisdn = sys.argv[1] if len(sys.argv) > 1 else ''
response = requests.get(f"http://localhost:8080/BalanceQueryService/BalanceServlet?msisdn={msisdn}")
print(response.text)""",
    "asterisk-config/extensions.conf": "[sip-users]\nexten => 100,1,Answer()\nexten => 100,n,Playback(welcome)\nexten => 100,n,Read(msisdn,10)\nexten => 100,n,AGI(balance-query.agi,${msisdn})\nexten => 100,n,Hangup()",
    "asterisk-config/sip.conf": "[100]\ntype=friend\ncontext=sip-users\nhost=dynamic\ndefaultuser=100\nsecret=1234",
    "Configuration Files/web.xml": """<web-app>\n<servlet>\n<servlet-name>BalanceServlet</servlet-name>\n<servlet-class>com.example.balancequery.servlet.BalanceServlet</servlet-class>\n</servlet>\n<servlet-mapping>\n<servlet-name>BalanceServlet</servlet-name>\n<url-pattern>/BalanceServlet</url-pattern>\n</servlet-mapping>\n</web-app>""",
    "Configuration Files/application.properties": "db.url=jdbc:postgresql://localhost:5432/balance_db\ndb.user=postgres\ndb.password=password",
    "README.md": "# Balance Query Service\nA SIP-based balance query system using Java Servlets, PostgreSQL, and Asterisk.",
}

# Create directories
for folder in folders:
    os.makedirs(os.path.join(base_path, folder), exist_ok=True)

# Create files
for file_path, content in files.items():
    full_path = os.path.join(base_path, file_path)
    with open(full_path, "w") as file:
        file.write(content)

# Zip the project
zip_path = f"/mnt/data/{project_name}.zip"
shutil.make_archive(zip_path.replace(".zip", ""), 'zip', base_path)

zip_path

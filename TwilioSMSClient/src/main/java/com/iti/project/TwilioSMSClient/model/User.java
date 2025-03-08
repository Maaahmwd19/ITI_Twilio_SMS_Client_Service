// src/main/java/com/iti/project/TwilioSMSClient/model/User.java
package com.iti.project.TwilioSMSClient.model;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String username;
    private Date birthday;
    private String password;
    private String phoneNumber;
    private String job;
    private String email;
    private String address;
    private String accountSid;
    private String authToken;
    private String senderId;
    private String role;
    private boolean isVerified;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }

    public String getAuthToken() { return authToken; }
    public void setAuthToken(String authToken) { this.authToken = authToken; }

    // public String getSenderId() { return senderId; }
    // public void setSenderId(String senderId) { this.senderId = senderId; }

    public boolean isVerified() { return isVerified; }
    public void setVerified(boolean isVerified) { this.isVerified = isVerified; }

    public String getSenderId() { return senderId; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
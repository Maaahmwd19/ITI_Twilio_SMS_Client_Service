/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.project.TwilioSMSClient.model;

/**
 * Represents a user in the system.
 *
 * @author mibrahim
 */
public class User {
    private int id;
    private String email;
    private String username;
    private String password;
    private String name;
    private String phoneNumber;
    private Role role;
    private String twilioAccountSid;
    private String twilioAuthToken;
    private String twilioSenderId;

    /**
     * Enum representing the roles a user can have.
     */
    public enum Role {
        ADMIN,
        CUSTOMER;
    }

    /**
     * Default constructor initializing all fields to default values.
     */
    public User() {
        this.id = 0;
        this.email = "";
        this.username = "";
        this.password = "";
        this.name = "";
        this.phoneNumber = "";
        this.role = Role.CUSTOMER;
        this.twilioAccountSid = "";
        this.twilioAuthToken = "";
        this.twilioSenderId = "";
    }

    /**
     * Parameterized constructor to initialize all fields.
     *
     * @param id The user's ID.
     * @param email The user's email.
     * @param username The user's username.
     * @param password The user's password.
     * @param name The user's name.
     * @param phoneNumber The user's phone number.
     * @param role The user's role.
     * @param twilioAccountSid The Twilio account SID.
     * @param twilioAuthToken The Twilio authentication token.
     * @param twilioSenderId The Twilio sender ID.
     */
    public User(int id, String email, String username, String password, String name, String phoneNumber, Role role, String twilioAccountSid, String twilioAuthToken, String twilioSenderId) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.twilioAccountSid = twilioAccountSid;
        this.twilioAuthToken = twilioAuthToken;
        this.twilioSenderId = twilioSenderId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setTwilioAccountSid(String twilioAccountSid) {
        this.twilioAccountSid = twilioAccountSid;
    }

    public void setTwilioAuthToken(String twilioAuthToken) {
        this.twilioAuthToken = twilioAuthToken;
    }

    public void setTwilioSenderId(String twilioSenderId) {
        this.twilioSenderId = twilioSenderId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public String getTwilioAccountSid() {
        return twilioAccountSid;
    }

    public String getTwilioAuthToken() {
        return twilioAuthToken;
    }

    public String getTwilioSenderId() {
        return twilioSenderId;
    }
}
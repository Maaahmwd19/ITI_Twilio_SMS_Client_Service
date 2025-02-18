/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.iti.project.TwilioSMSClient.model;

/**
 *
 * @author mibrahim
 */
public class SMS {
    private int id;
    private  int user_id;
    private String from_number;
    private String to_number;
    private String body;
    private String date;
    private Status status;

    public enum Status {
        Success,
        Failed
    }
    public SMS() {
        id = 0;
        user_id = 0;
        from_number = "";
        to_number = "";
        body = "";
        date = "";
        status = Status.Failed;
    }
    public SMS(int id, int user_id, String from_number, String to_number, String body, String date, Status status) {
        this.id = id;
        this.user_id = user_id;
        this.from_number = from_number;
        this.to_number = to_number;
        this.body = body;
        this.date = date;
        this.status = status;
    }
    //setters
    public void setId(int id) {
        this.id = id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setFrom_number(String from_number) {
        this.from_number = from_number;
    }
    public void setTo_number(String to_number) {
        this.to_number = to_number;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    //getters
    public int getId() {
        return id;
    }
    public int getUser_id() {
        return user_id;
    }
    public String getFrom_number() {
        return from_number;
    }
    public String getTo_number() {
        return to_number;
    }
    public String getBody() {
        return body;
    }
    public String getDate() {
        return date;
    }
    public Status getStatus() {
        return status;
    }

}

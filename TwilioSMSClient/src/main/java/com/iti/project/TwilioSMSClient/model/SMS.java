// src/main/java/com/iti/project/TwilioSMSClient/model/SMS.java
package com.iti.project.TwilioSMSClient.model;

import java.util.Date;

public class SMS {
    private int id;
    private int userId;
    private String from;
    private String to;
    private String body;
    private Date dateSent;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public Date getDateSent() { return dateSent; }
    public void setDateSent(Date dateSent) { this.dateSent = dateSent; }
}
package com.iti.project.TwilioSMSClient.service;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import java.util.Random;
import com.iti.project.TwilioSMSClient.dao.SMSDAO;
import com.iti.project.TwilioSMSClient.model.SMS;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;


public class SMSService{
    
    
     public static int sendVerificationCode(String accountSid, String authToken, String from, String to,int userId) throws ApiException {
        Random rand = new Random();
        int verificationCode = 100000 + rand.nextInt(900000);

        String body = "Your verification code is: " + verificationCode;
        sendSMS(accountSid, authToken, from, to, body,userId);

        return verificationCode;
    }
     
      public static void sendSMS(String accountSid, String authToken, String from, String to, String body,int userId) throws ApiException {

        Message message = null;
        try {
            Twilio.init(accountSid, authToken);
             message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(from),
                    body
            ).create();
            System.out.println("SMS Sent: " + message.getSid());
        } catch (ApiException e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            throw e;
        }finally{
            if (message != null) {
                SMSDAO.saveSMS(userId, from, to, body, message.getStatus().toString().toLowerCase());
            } else {
                SMSDAO.saveSMS(userId, from, to, body, "failed");
            }        }
    }
}



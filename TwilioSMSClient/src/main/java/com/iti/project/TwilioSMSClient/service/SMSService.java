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

public class SMSService {
    
    public static int sendVerificationCode(String accountSid, String authToken, String from, String to, int userId) throws ApiException {
        Random rand = new Random();
        Message message = null;
        String status = "failed";
        int verificationCode = 100000 + rand.nextInt(900000);
        System.out.println("verificationCode: " + verificationCode);
        String body = "Your verification code is: " + verificationCode;

        // Validate the 'to' phone number format
        if (!isValidPhoneNumber(to)) {
            throw new ApiException("Invalid 'To' Phone Number: " + to);
        }

        try {
            Twilio.init(accountSid, authToken);
            message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(from),
                    body
            ).create();
            
            System.out.println("SMS Sent: " + message.getSid());
            status = message.getStatus().toString().toLowerCase();
        } catch (ApiException e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            throw e;
        } 
        return verificationCode;
    }
     
    public static void sendSMS(String accountSid, String authToken, String from, String to, String body, int userId) throws ApiException {
        Message message = null;
        String status = "failed";
        
        // Validate the 'to' phone number format
        if (!isValidPhoneNumber(to)) {
            throw new ApiException("Invalid 'To' Phone Number: " + to);
        }

        try {
            Twilio.init(accountSid, authToken);
            message = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(from),
                    body
            ).create();
            
            System.out.println("SMS Sent: " + message.getSid());
            status = "success";
        } catch (ApiException e) {
            System.err.println("Failed to send SMS: " + e.getMessage());
            throw e;
        } finally {
            SMSDAO.saveSMS(userId, from, to, body, status);
        }
    }

    // Helper method to validate phone number format
    private static boolean isValidPhoneNumber(String phoneNumber) {
        // Simple regex to check if the phone number is valid (this can be adjusted as needed)
        return phoneNumber != null && phoneNumber.matches("\\+?[0-9]{10,15}");
    }
}

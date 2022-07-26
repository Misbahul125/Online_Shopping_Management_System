/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.EmailVerification;

import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * https://mvnrepository.com/artifact/javax.mail/javax.mail-api -->
 * <dependency>
 * <groupId>com.sun.mail</groupId>
 * <artifactId>javax.mail</artifactId>
 * <version>1.6.2</version>
 * </dependency>
 *
 * @author Misbahul Haque
 */
public class EmailHelper {

    String type = "";

    public EmailHelper() {
    }

    public EmailHelper(String type) {
        this.type = type;
    }

    //generate vrification code
    public String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    //send email to the user email
    public boolean sendEmail(TemporaryUser user) {
        boolean test = false;

        final String toEmail = user.getEmail();
        final String fromEmail = "misbahulhaque2001@gmail.com";
        final String password = "";

        try {

            // your host email smtp server details
            Properties pr = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");
            pr.put("mail.smtp.socketFactory.port", "587");
            pr.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            //get session to authenticate the host email address and password
            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            //set email message details
            Message message = new MimeMessage(session);

            //set from email address
            message.setFrom(new InternetAddress(fromEmail));

            //set to email address or destination email address
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            
            StringBuffer sb = new StringBuffer();

            if (type.matches(Constants.RESET.toString())) {
                
                sb.append("Your OTP (One Time Password) to reset password at OSMS is :-  <strong>").append(user.getCode()).append("</strong>.");
                
                sb.append("<br/>");
                sb.append("It is a system generated email. Please do not reply.");
                
                sb.append("<br/>");
                sb.append("<br/>");
                sb.append("Thank you.");
                
                sb.append("<br/>");
                sb.append("<br/>");
                sb.append("Regards,");
                sb.append("<br/>");
                sb.append("Team OSMS");
                
                //set email subject
                message.setSubject("OSMS - Reset Password");

                //set message text
                message.setContent(sb.toString(), "text/html;charset=UTF-8");
            
            } else {
                
                sb.append("Your OTP (One Time Password) to register at OSMS is :-  <strong>").append(user.getCode()).append("</strong>.");
                
                sb.append("<br/>");
                sb.append("It is a system generated email. Please do not reply.");
                
                sb.append("<br/>");
                sb.append("<br/>");
                sb.append("Thank you.");
                
                sb.append("<br/>");
                sb.append("<br/>");
                sb.append("Regards,");
                sb.append("<br/>");
                sb.append("Team OSMS");
                
                //set email subject
                message.setSubject("OSMS - User Verification");

                //set message text
                message.setContent(sb.toString(), "text/html;charset=UTF-8");
            
            }

            //send the message
            Transport.send(message);

            test = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return test;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.EmailVerification;

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
        final String password = "plxmjcyteimnfptq";

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
            Message mess = new MimeMessage(session);

            //set from email address
            mess.setFrom(new InternetAddress(fromEmail));
            //set to email address or destination email address
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            //set email subject
            mess.setSubject("OSMS - User Verification");

            //set message text
            mess.setText("Your OTP (One Time Password) to register at OSMS is :-  " + user.getCode()+" \nIt is a system generated email. Please do not reply. \n \nThank you. \n \nRegards, \nTeam OSMS");
            //send the message
            Transport.send(mess);

            test = true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return test;
    }
}

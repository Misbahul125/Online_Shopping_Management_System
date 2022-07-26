/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.helper.PasswordHelper;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 *
 * @author Misbahul Haque
 */
public class PasswordHelper {
    
    public static void main(String[] args) {
        Encoder encoder = Base64.getEncoder();
        String originalString = "YOUR_SECRETE_KEY";
        String encodedString = encoder.encodeToString(originalString.getBytes());
 
        System.out.println("Encrypted Value :: " +encodedString);
        Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(encodedString);
                 
        System.out.println("Decrypted Value :: " +new String(bytes));
    }
 
}
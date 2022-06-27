/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.helper;

/**
 *
 * @author Misbahul Haque
 */
public class SentenceHelper {
    
    public static String get10Words(String longText) {
        
        String str[] = longText.split(" ");
        
        if(str.length > 10) {
            
            String res = "";
            
            for(int i=0 ; i<10 ; i++) {
                res = res+str[i]+" ";
            }
            
            return res+"...";
            
        }
        else {
            return longText+"...";
        }
        
    }
    
}

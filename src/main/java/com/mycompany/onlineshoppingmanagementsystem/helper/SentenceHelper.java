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

    public SentenceHelper() {
    }

    public String getWords(String longText, int state) {

        String str[] = longText.split(" ");

        if (str.length > 10) {

            String res = "";

            //if show less is toggled
            if (state == 0) {

                for (int i = 0; i < 10; i++) {
                    res = res + str[i] + " ";
                }

                return res+" ";
            }
            else if(state == 1) {
                for (int i = 10; i < str.length; i++) {
                    res = res + str[i] + " ";
                }

                return res;
            }

        } else {
            return longText;
        }
        
        return "";

    }

}
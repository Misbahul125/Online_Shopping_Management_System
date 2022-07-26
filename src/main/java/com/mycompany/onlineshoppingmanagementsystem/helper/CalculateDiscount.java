/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.helper;

/**
 *
 * @author Misbahul Haque
 */
public class CalculateDiscount {

    public CalculateDiscount() {
    }
    
    //function to calculate discount
    public int getPriceAfterApplyingDiscount(int mp , int disc) {
        
        int a = (int)((disc/100.0)*mp);
        
        return mp-a;
        
    }
    
}
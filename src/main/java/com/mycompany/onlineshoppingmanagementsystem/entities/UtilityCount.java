/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Misbahul Haque
 */

@Entity
public class UtilityCount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10 , name = "utility_count_id")
    private int utilityID;
    
    @Column(name = "user_count")
    private int userCount;
    
    @Column(name = "category_count")
    private int categoryCount;
    
    @Column(name = "product_count")
    private int productCount;
    
    @Column(name = "order_count")
    private int orderCount;

    public UtilityCount(int utilityID, int userCount, int categoryCount, int productCount, int orderCount) {
        this.utilityID = utilityID;
        this.userCount = userCount;
        this.categoryCount = categoryCount;
        this.productCount = productCount;
        this.orderCount = orderCount;
    }

    public UtilityCount(int userCount, int categoryCount, int productCount, int orderCount) {
        this.userCount = userCount;
        this.categoryCount = categoryCount;
        this.productCount = productCount;
        this.orderCount = orderCount;
    }

    public UtilityCount() {
    }
    
    public int getUtilityID() {
        return utilityID;
    }

    public void setUtilityID(int utilityID) {
        this.utilityID = utilityID;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    @Override
    public String toString() {
        return "UtilityCount{" + "utilityID=" + utilityID + ", userCount=" + userCount + ", categoryCount=" + categoryCount + ", productCount=" + productCount + ", orderCount=" + orderCount + '}';
    }
        
}

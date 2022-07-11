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
import javax.persistence.ManyToOne;

/**
 *
 * @author Misbahul Haque
 */
@Entity
public class Orders {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10, name = "order_id")
    private int orderId;
    
    @Column(length = 20, name = "actual_order_id") 
    private String actualOrderId;
    
    @Column(length = 30, name = "payment_method")
    private int paymentMethod;
    
    @Column(name = "razorpay_order_id")
    private int razorpayOrderId;
    
    @Column(name = "razorpay_payment_id")
    private int razorpayPaymentId;
    
    @Column(name = "razorpay_signature")
    private int razorpaySignature;
    
    @Column(length = 30, name = "order_status")
    private String orderStatus;
    
    @Column(name = "delivery_date")
    private String deliveryDate;
    
    @Column(name = "user_address")
    private String userAddress;
    
    @ManyToOne
    private Product product;
    
    @ManyToOne
    private User user;
    
    @ManyToOne
    private Address address;

    public Orders(int orderId, String actualOrderId, int paymentMethod, int razorpayOrderId, int razorpayPaymentId, int razorpaySignature, String orderStatus, String deliveryDate, String userAddress, Product product, User user, Address address) {
        this.orderId = orderId;
        this.actualOrderId = actualOrderId;
        this.paymentMethod = paymentMethod;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
        this.orderStatus = orderStatus;
        this.deliveryDate = deliveryDate;
        this.userAddress = userAddress;
        this.product = product;
        this.user = user;
        this.address = address;
    }

    public Orders(String actualOrderId, int paymentMethod, int razorpayOrderId, int razorpayPaymentId, int razorpaySignature, String orderStatus, String deliveryDate, String userAddress, Product product, User user, Address address) {
        this.actualOrderId = actualOrderId;
        this.paymentMethod = paymentMethod;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
        this.orderStatus = orderStatus;
        this.deliveryDate = deliveryDate;
        this.userAddress = userAddress;
        this.product = product;
        this.user = user;
        this.address = address;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getActualOrderId() {
        return actualOrderId;
    }

    public void setActualOrderId(String actualOrderId) {
        this.actualOrderId = actualOrderId;
    }

    public int getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(int razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public int getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(int razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public int getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(int razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
}

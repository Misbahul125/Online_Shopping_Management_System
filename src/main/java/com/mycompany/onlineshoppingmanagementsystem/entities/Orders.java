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
    private String paymentMethod;
    
    @Column(length = 50, name = "transaction_id")
    private int transactionId;
    
    @Column(length = 30, name = "order_status")
    private String orderStatus;
    
    @Column(length = 30, name = "delivery_date")
    private String deliveryDate;
    
    @ManyToOne
    private Product product;
    
    @ManyToOne
    private User user;
    
    @ManyToOne
    private Address address;

    public Orders(int orderId, String actualOrderId, String paymentMethod, int transactionId, String orderStatus, String deliveryDate, Product product, User user, Address address) {
        this.orderId = orderId;
        this.actualOrderId = actualOrderId;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.orderStatus = orderStatus;
        this.deliveryDate = deliveryDate;
        this.product = product;
        this.user = user;
        this.address = address;
    }

    public Orders(String actualOrderId, String paymentMethod, int transactionId, String orderStatus, String deliveryDate, Product product, User user, Address address) {
        this.actualOrderId = actualOrderId;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.orderStatus = orderStatus;
        this.deliveryDate = deliveryDate;
        this.product = product;
        this.user = user;
        this.address = address;
    }

    public Orders() {
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
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

    @Override
    public String toString() {
        return "Orders{" + "orderId=" + orderId + ", actualOrderId=" + actualOrderId + ", paymentMethod=" + paymentMethod + ", transactionId=" + transactionId + ", orderStatus=" + orderStatus + ", deliveryDate=" + deliveryDate + ", product=" + product + ", user=" + user + ", address=" + address + '}';
    }
    
}

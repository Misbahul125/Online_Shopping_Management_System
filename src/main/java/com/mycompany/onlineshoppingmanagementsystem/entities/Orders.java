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

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "product_price")
    private int productPrice;
    
    @Column(length = 10, name = "total")
    private int total;

    @Column(length = 10, name = "net_amount")
    private int netAmount;

    @Column(length = 30, name = "payment_method")
    private String paymentMethod;

    @Column(name = "razorpay_order_id")
    private String razorpayOrderId;

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentId;

    @Column(name = "razorpay_signature_id")
    private String razorpaySignatureId;

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

    public Orders(int orderId, String actualOrderId, String orderDate, int quantity, int productPrice, int total, int netAmount, String paymentMethod, String razorpayOrderId, String razorpayPaymentId, String razorpaySignatureId, String orderStatus, String deliveryDate, String userAddress, Product product, User user, Address address) {
        this.orderId = orderId;
        this.actualOrderId = actualOrderId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.total = total;
        this.netAmount = netAmount;
        this.paymentMethod = paymentMethod;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignatureId = razorpaySignatureId;
        this.orderStatus = orderStatus;
        this.deliveryDate = deliveryDate;
        this.userAddress = userAddress;
        this.product = product;
        this.user = user;
        this.address = address;
    }

    public Orders(String actualOrderId, String orderDate, int quantity, int productPrice, int total, int netAmount, String paymentMethod, String razorpayOrderId, String razorpayPaymentId, String razorpaySignatureId, String orderStatus, String deliveryDate, String userAddress, Product product, User user, Address address) {
        this.actualOrderId = actualOrderId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.total = total;
        this.netAmount = netAmount;
        this.paymentMethod = paymentMethod;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignatureId = razorpaySignatureId;
        this.orderStatus = orderStatus;
        this.deliveryDate = deliveryDate;
        this.userAddress = userAddress;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setActualOrderId(String actualOrderId) {
        this.actualOrderId = actualOrderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(int netAmount) {
        this.netAmount = netAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getRazorpaySignatureId() {
        return razorpaySignatureId;
    }

    public void setRazorpaySignatureId(String razorpaySignatureId) {
        this.razorpaySignatureId = razorpaySignatureId;
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

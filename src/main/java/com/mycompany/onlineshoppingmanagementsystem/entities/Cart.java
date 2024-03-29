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
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10, name = "cart_id")
    private int cartId;
    
    @Column(name = "quantity")
    private int quantity;
    
    @Column(length = 10, name = "total")
    private int total;
    
    @ManyToOne
    private Product product;
    
    @ManyToOne
    private User user;

    public Cart(int cartId, int quantity, int total, Product product, User user) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.total = total;
        this.product = product;
        this.user = user;
    }

    public Cart(int quantity, int total, Product product, User user) {
        this.quantity = quantity;
        this.total = total;
        this.product = product;
        this.user = user;
    }

    public Cart() {
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    @Override
    public String toString() {
        return "Cart{" + "cartId=" + cartId + ", quantity=" + quantity + ", total=" + total + ", product=" + product + ", user=" + user + '}';
    }
    
}
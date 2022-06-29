/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Misbahul Haque
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10, name = "product_id")
    private int productId;

    @Column(length = 100, name = "product_name")
    private String productName;
    
    @Column(length = 1500, name = "product_description")
    private String productDescription;
    
    @Column(name = "product_price")
    private int productPrice;
    
    @Column(name = "product_discount")
    private int productDiscount;
    
    @Column(name = "product_quantity")
    private int productQuantity;
    
    @Column(length = 100, name = "product_pic")
    private String productPic;
    
    @ManyToOne
    private Category category;
    
    @OneToMany
    private List<Cart> carts;
    
    @OneToMany(mappedBy = "product")
    private List<Orders> orders;

    public Product(int productId, String productName, String productDescription, int productPrice, int productDiscount, int productQuantity, Category category, String productPic, List<Cart> carts) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productQuantity = productQuantity;
        this.category = category;
        this.productPic = productPic;
        this.carts = carts;
    }

    public Product(String productName, String productDescription, int productPrice, int productDiscount, int productQuantity, Category category, String productPic, List<Cart> carts) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productQuantity = productQuantity;
        this.category = category;
        this.productPic = productPic;
        this.carts = carts;
    }

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName=" + productName + ", productDescription=" + productDescription + ", productPrice=" + productPrice + ", productDiscount=" + productDiscount + ", productQuantity=" + productQuantity + ", productPic=" + productPic + ", category=" + category + ", carts=" + carts + ", orders=" + orders + '}';
    }

    
    //function to calculate discount
    public int getPriceAfterApplyingDiscount() {
        
        int d = (int)((this.getProductDiscount()/100.0)*this.getProductPrice());
        
        return this.getProductPrice()-d;
        
    }
    
}

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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10, name = "product_id")
    private int productId;

    @Column(length = 100, name = "product_name")
    private String productName;
    
    @Column(length = 1500, name = "product_description")
    private String productDescription;
    
    @Column(length = 100, name = "product_pic")
    private String productPic;
    
    @Column(name = "product_price")
    private float productPrice;
    
    @Column(name = "product_discount")
    private float productDiscount;
    
    @Column(name = "product_quantity")
    private int productQuantity;
    
    @ManyToOne
    private Category category;

    public Product(int productId, String productName, String productDescription, String productPic, float productPrice, float productDiscount, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPic = productPic;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productQuantity = productQuantity;
    }

    public Product(String productName, String productDescription, String productPic, float productPrice, float productDiscount, int productQuantity, Category category) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPic = productPic;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productQuantity = productQuantity;
        this.category = category;
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

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public float getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(float productDiscount) {
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

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName=" + productName + ", productDescription=" + productDescription + ", productPic=" + productPic + ", productPrice=" + productPrice + ", productDiscount=" + productDiscount + ", productQuantity=" + productQuantity + '}';
    }
    
}

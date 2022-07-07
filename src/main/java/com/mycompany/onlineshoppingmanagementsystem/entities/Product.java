/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    
    @Column(name = "product_marked_price")
    private int productMarkedPrice;
    
    @Column(name = "product_discount")
    private int productDiscount;
    
    @Column(name = "product_selling_price")
    private int productSellingPrice;
    
    @Column(name = "product_quantity")
    private int productQuantity;
    
    @Column(length = 100, name = "product_pic")
    private String productPic;
    
    @ManyToOne
    private Category category;
    
    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY)
    private List<Cart> carts;
    
    @OneToMany(mappedBy = "product" , fetch = FetchType.LAZY)
    private List<Orders> orders;

    public Product(int productId, String productName, String productDescription, int productMarkedPrice, int productDiscount, int productSellingPrice, int productQuantity, String productPic, Category category, List<Cart> carts, List<Orders> orders) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productMarkedPrice = productMarkedPrice;
        this.productDiscount = productDiscount;
        this.productSellingPrice = productSellingPrice;
        this.productQuantity = productQuantity;
        this.productPic = productPic;
        this.category = category;
        this.carts = carts;
        this.orders = orders;
    }

    public Product(String productName, String productDescription, int productMarkedPrice, int productDiscount, int productSellingPrice, int productQuantity, String productPic, Category category, List<Cart> carts, List<Orders> orders) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.productMarkedPrice = productMarkedPrice;
        this.productDiscount = productDiscount;
        this.productSellingPrice = productSellingPrice;
        this.productQuantity = productQuantity;
        this.productPic = productPic;
        this.category = category;
        this.carts = carts;
        this.orders = orders;
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

    public int getProductMarkedPrice() {
        return productMarkedPrice;
    }

    public void setProductMarkedPrice(int productMarkedPrice) {
        this.productMarkedPrice = productMarkedPrice;
    }

    public int getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }

    public int getProductSellingPrice() {
        return productSellingPrice;
    }

    public void setProductSellingPrice(int productSellingPrice) {
        this.productSellingPrice = productSellingPrice;
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
    
}

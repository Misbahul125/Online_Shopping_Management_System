/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Misbahul Haque
 */
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 10, name = "category_id")
    private int categoryId;

    @Column(length = 100, name = "category_title")
    private String categoryTitle;

    @Column(length = 1500, name = "category_description")
    private String categoryDescription;

    @Column(length = 100, name = "category_pic")
    private String categoryPic;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    public Category(int categoryId, String categoryTitle, String categoryDescription, String categoryPic, List<Product> products) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
        this.categoryPic = categoryPic;
        this.products = products;
    }

    public Category(int categoryId, String categoryTitle, String categoryDescription, String categoryPic) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
        this.categoryPic = categoryPic;
    }
    

    public Category(String categoryTitle, String categoryDescription, String categoryPic, List<Product> products) {
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
        this.categoryPic = categoryPic;
        this.products = products;
    }

    public Category() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryPic() {
        return categoryPic;
    }

    public void setCategoryPic(String categoryPic) {
        this.categoryPic = categoryPic;
    }
    
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Category{" + "categoryId=" + categoryId + ", categoryTitle=" + categoryTitle + ", categoryDescription=" + categoryDescription + ", categoryPic=" + categoryPic + ", products=" + products + '}';
    }

}
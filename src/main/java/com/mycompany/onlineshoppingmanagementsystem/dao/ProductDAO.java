/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.Category;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Misbahul Haque
 */
public class ProductDAO {
    
    private SessionFactory sessionFactory;

    public ProductDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    //add product
    public int createProduct(String productName , String productDescription ,
                        float productPrice , float productDiscount , int productQuantity ,
                        String productImage , int categoryId) {

        Session session = null;
        Transaction transaction = null;
        int productId = 0;

        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            CategoryDAO categoryDAO = new CategoryDAO(this.sessionFactory);
            Category category = categoryDAO.getCategoryById(categoryId);
            
            if(category != null) {
            
                Product product = new Product(productName, productDescription, productPrice, productDiscount, productQuantity, category, productImage);
                
                productId = (int) session.save(product);
                transaction.commit();
                
            }
            
        } catch (Exception e) {
            
            transaction.rollback();
            productId = 0;
            e.printStackTrace();
            
        } finally {
            
            session.close();
            return productId;
        }

    }

    
}

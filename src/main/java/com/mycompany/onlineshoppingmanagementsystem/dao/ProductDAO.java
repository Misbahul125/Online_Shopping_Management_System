/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.Category;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
                        int productMarkedPrice , int productDiscount , int productSellingPrice , int productQuantity ,
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
            
                Product product = new Product(productName, productDescription, productMarkedPrice, productDiscount, productSellingPrice, productQuantity, productImage, category, null, null);
                
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
    
    //get product by id
    public Product getProductById(int productId) {
        
        Session session = null;
        Product product = null;

        try {            
            session = this.sessionFactory.openSession();
            product = session.get(Product.class, productId);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        } finally {
            session.close();
            return product;
        }
        
    }

    //get all products
    public List<Product> getAllProducts() {
        
        Session session = null;
        List<Product> products = null;

        try {            
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from Product");
            products = query.list();
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        } finally {
            session.close();
            return products;
        }
        
    }
    
    
    //get all products with category id
    public List<Product> getAllProductsByCategoryId(int categoryId) {
        
        Session session = null;
        List<Product> products = null;

        try {
            
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from Product as p where p.category.categoryId =: id");
            query.setParameter("id", categoryId);
            products = query.list();
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        } finally {
            
            session.close();
            return products;
        }
        
    }
}

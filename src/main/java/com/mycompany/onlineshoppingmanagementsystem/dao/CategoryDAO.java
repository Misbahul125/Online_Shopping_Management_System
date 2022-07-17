/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.Category;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Misbahul Haque
 */
public class CategoryDAO {

    private SessionFactory sessionFactory;

    public CategoryDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //add category
    public int createCategory(String categoryTitle, String categoryDescription) {

        Session session = null;
        Transaction transaction = null;
        int categoryId = 0;

        try {
            
            Category category = new Category();
            category.setCategoryTitle(categoryTitle);
            category.setCategoryDescription(categoryDescription);

            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            categoryId = (int) session.save(category);
            transaction.commit();
            
        } catch (Exception e) {
            
            transaction.rollback();
            categoryId = 0;
            e.printStackTrace();
            
        } finally {
            
            session.close();
            return categoryId;
        }

    }
    
    public List<Category> getCategories() {
        
        Session session = null;
        List<Category> categories = null;

        try {
            
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from Category");
            categories = query.list();
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        } finally {
            
            session.close();
            return categories;
        }
        
    }
    
    public Category getCategoryById(int categoryId) {
        
        Session session = null;
        Category category = null;

        try {
            
            session = this.sessionFactory.openSession();
            category = session.get(Category.class, categoryId);
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
        } finally {
            
            session.close();
            return category;
        }
        
    }
    
    //update category
    public int updateCategory(int categoryId, String categoryTitle, String categoryDescription) {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            
            Category category = new Category();
            category.setCategoryId(categoryId);
            category.setCategoryTitle(categoryTitle);
            category.setCategoryDescription(categoryDescription);

            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            session.saveOrUpdate(category);
            transaction.commit();
            
            status = 1;
            
        } catch (Exception e) {
            
            transaction.rollback();
            status = 0;
            e.printStackTrace();
            
        } finally {
            
            session.close();
            return status;
        }

    }

}

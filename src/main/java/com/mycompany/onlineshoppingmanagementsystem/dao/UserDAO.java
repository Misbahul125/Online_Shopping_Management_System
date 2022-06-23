/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Misbahul Haque
 */
public class UserDAO {
    
    private SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    public User getUserByEmailAndPassword(String email, String password) {
        
        User user = null;
        
        Session session = null;
        
        try {
            
            String query = "from User where userEmail =: e and userPassword =: p";
            
            session = this.sessionFactory.openSession();
            Query q = session.createQuery(query);
            q.setParameter("e", email);
            q.setParameter("p", password);
            
            user = (User) q.uniqueResult();
            
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        
        return user;
        
    }
    
}

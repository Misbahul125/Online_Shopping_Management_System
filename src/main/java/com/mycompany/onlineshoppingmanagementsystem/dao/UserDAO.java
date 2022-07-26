/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

    public int createUserWithEmailAndPassword(String userName, String userEmail, String userPassword,
            String userPhone, String userImage, String userAddress, String userType) {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {

            User user = new User(userName, userEmail, userPassword, userPhone, userImage, userAddress, 0, userType);

            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            status = (int) session.save(user);

            if (status > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
            }

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            status = 0;
        }

        return status;
        
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;

    }

    public User getUserByEmail(String email) {

        User user = null;

        Session session = null;

        try {

            String query = "from User where userEmail =: e";

            session = this.sessionFactory.openSession();
            Query q = session.createQuery(query);
            q.setParameter("e", email);
            user = (User) q.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;

    }

    public int updateCartCountByUserId(User user, int count) {

        int status = 0;

        Session session = null;
        Transaction tx = null;

        try {

            session = this.sessionFactory.openSession();
            tx = session.beginTransaction();

            String q = "update User set userCartCount =: n where userId =: u";

            Query query = session.createQuery(q);
            query.setParameter("n", count);
            query.setParameter("u", user.getUserId());
            status = query.executeUpdate();
            //user.setUserCartCount(count);
            //session.saveOrUpdate(user);

            if (status > 0) {
                tx.commit();
            } else {
                tx.rollback();
            }

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            status = 0;
        } finally {
            session.close();
        }

        return status;

    }

    public int resetPasswordByEmail(String userEmail, String userPassword) {

        int status = 0;

        Session session = null;
        Transaction tx = null;

        try {

            session = this.sessionFactory.openSession();
            tx = session.beginTransaction();

            String query = "update User set userPassword =: p where userEmail =: e";

            Query q = session.createQuery(query);
            q.setParameter("p", userPassword);
            q.setParameter("e", userEmail);
            status = q.executeUpdate();

            if(status > 0) {
                tx.commit();
            }
            else {
                tx.rollback();
            }

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
            status = 0;
        } finally {
            session.close();
        }

        return status;

    }

}
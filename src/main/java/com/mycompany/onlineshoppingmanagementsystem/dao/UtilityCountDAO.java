/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.UtilityCount;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Misbahul Haque
 */
public class UtilityCountDAO {

    private SessionFactory sessionFactory;

    public UtilityCountDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    //get counts
    public UtilityCount getAllUtilityCounts() {

        Session session = null;
        UtilityCount utilityCount = null;

        try {
            session = this.sessionFactory.openSession();
            utilityCount = session.get(UtilityCount.class ,1);

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            session.close();
            return utilityCount;
        }

    }
    
    //update user count
    public int updateUserCount() {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            
            int uCount = getAllUtilityCounts().getUserCount();

            String query = "update UtilityCount set userCount =: c where utilityID =: i";

            transaction = session.beginTransaction();

            Query q = session.createQuery(query);
            q.setParameter("c", uCount+1);
            q.setParameter("i", 1);
            status = q.executeUpdate();

            if (status > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
            }

        } catch (Exception e) {

            transaction.rollback();
            status = 0;
            e.printStackTrace();

        } finally {
            session.close();
            return status;
        }

    }
    
    //update category count
    public int updateCategoryCount() {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            
            int cCount = getAllUtilityCounts().getCategoryCount();

            String query = "update UtilityCount set categoryCount =: c where utilityID =: i";

            transaction = session.beginTransaction();

            Query q = session.createQuery(query);
            q.setParameter("c", cCount+1);
            q.setParameter("i", 1);
            status = q.executeUpdate();

            if (status > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
            }

        } catch (Exception e) {

            transaction.rollback();
            status = 0;
            e.printStackTrace();

        } finally {
            session.close();
            return status;
        }

    }
    
    //update product count
    public int updateProductCount() {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            
            int pCount = getAllUtilityCounts().getProductCount();

            String query = "update UtilityCount set productCount =: c where utilityID =: i";

            transaction = session.beginTransaction();

            Query q = session.createQuery(query);
            q.setParameter("c", pCount+1);
            q.setParameter("i", 1);
            status = q.executeUpdate();

            if (status > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
            }

        } catch (Exception e) {

            transaction.rollback();
            status = 0;
            e.printStackTrace();

        } finally {
            session.close();
            return status;
        }

    }
    
    //update order count
    public int updateOrderCount() {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            
            int oCount = getAllUtilityCounts().getOrderCount();

            String query = "update UtilityCount set orderCount =: c where utilityID =: i";

            transaction = session.beginTransaction();

            Query q = session.createQuery(query);
            q.setParameter("c", oCount+1);
            q.setParameter("i", 1);
            status = q.executeUpdate();

            if (status > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
            }

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

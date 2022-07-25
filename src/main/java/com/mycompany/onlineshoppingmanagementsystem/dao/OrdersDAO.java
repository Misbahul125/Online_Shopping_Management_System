/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.Cart;
import com.mycompany.onlineshoppingmanagementsystem.entities.Orders;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Misbahul Haque
 */
public class OrdersDAO {
    
    private SessionFactory sessionFactory;

    public OrdersDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
    //add single order
    public int addSingleOrder(Orders order) {
        
        Session session = null;
        Transaction transaction = null;
        int orderId = 0;

        try {
            
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            orderId = (int) session.save(order);
            
            if (orderId > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
                return 0;
            }

        } catch (Exception e) {

            transaction.rollback();
            orderId = 0;
            e.printStackTrace();

        } finally {

            session.close();
            return orderId;
        }

    }

    //add multiple orders
    public int addMultipleOrders(List<Orders> orders) {

        Session session = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();

            if(orders != null && !orders.isEmpty()) {
                
                for(Orders o : orders) {
                    
                    int s = addSingleOrder(o);
                    
                    if(s > 0) {
                        status = 1;
                        continue;
                    }
                    else {
                        status = 0;
                        break;
                    }
                    
                }
                
            }
            else {
                status = 0;
            }

        } catch (Exception e) {

            status = 0;
            e.printStackTrace();

        } finally {
            session.close();
            return status;
        }

    }
    
    //get orders by actual order id
    public List<Orders> getSingleOrderByActualId(String orderId) {

        Session session = null;
        List<Orders> orders = null;

        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from Orders as o where o.actualOrderId =: oid");
            query.setParameter("oid", orderId);
            orders = query.list();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            session.close();
            return orders;
        }

    }
    
    //get orders of an user by user ID
    public List<Orders> getOrdersOfAnUser(int userId) {

        Session session = null;
        List<Orders> orders = null;

        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from Orders as o where o.user.userId =: uid order by orderId desc");
            query.setParameter("uid", userId);
            orders = query.list();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            session.close();
            return orders;
        }

    }
}

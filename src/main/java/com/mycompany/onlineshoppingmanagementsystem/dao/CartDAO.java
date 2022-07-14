/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.Cart;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author Misbahul Haque
 */
public class CartDAO {

    private SessionFactory sessionFactory;

    public CartDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //add new item into cart
    public int addNewItemToCart(User user, Product product) {
        
        Session session = null;
        Transaction transaction = null;
        int cartId = 0;
        int status = 0;

        try {
            
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            Cart cart = new Cart();
            cart.setQuantity(1);
            cart.setTotal(product.getProductSellingPrice());
            cart.setProduct(product);
            cart.setUser(user);

            cartId = (int) session.save(cart);
            
            int c = (getCartItemsForUser(user.getUserId())).size();
            System.out.println("cart items : "+c);
            
//            UserDAO userDAO = new UserDAO(this.sessionFactory);
//            status = userDAO.updateCartCountByUserId(user, c);

            //update cart count column in user table
            user.setUserCartCount(c+1);
            session.saveOrUpdate(user);
            status = 1;
            
            if (status > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
                return 0;
            }

        } catch (Exception e) {

            transaction.rollback();
            cartId = 0;
            status = 0;
            e.printStackTrace();

        } finally {

            session.close();
            return status;
        }

    }

    //get the cart item for a particular user and a particular product (will return only 1 row)
    public List<Cart> getCartItemWithProductAndUserId(int productId, int userId) {

        Session session = null;
        //here we have taken list as because we don't know the primary key of the cart item and hence, 
        //we cannot use get() method
        List<Cart> carts = null;

        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from Cart as c where c.product.productId =: pid and c.user.userId =: uid");
            query.setParameter("pid", productId);
            query.setParameter("uid", userId);
            carts = query.list();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            session.close();
            return carts;
        }

    }

    //get all the cart items for a particular user, with user id
    public List<Cart> getCartItemsForUser(int userId) {

        Session session = null;
        List<Cart> carts = null;

        try {
            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from Cart as c where c.user.userId =: uid");
            query.setParameter("uid", userId);
            carts = query.list();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            session.close();
            return carts;
        }

    }

    public int updateCartQuantity(User user, Cart cart, String action) {

        Session session = null;
        Transaction transaction = null;
        int quantity = 0;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            int total = 0;

            if (action.matches(Constants.CART_INCREMENT.toString())) {

                quantity = (cart.getQuantity()) + 1;
                total = (cart.getTotal()) + (cart.getProduct().getProductSellingPrice());

            } else {
                quantity = (cart.getQuantity()) - 1;
                total = (cart.getTotal()) - (cart.getProduct().getProductSellingPrice());
            }

            cart.setQuantity(quantity);
            cart.setTotal(total);

            //check if after calculating either quantity or price becomes 0,
            //then delete the item
            if ((cart.getQuantity() == 0) || (cart.getTotal() == 0)) {
                session.delete(cart);

            } else {
                session.saveOrUpdate(cart);
            }
            
            status = 1;

//            int c = (getCartItemsForUser(user.getUserId())).size();
//
//            //update cart count column in user table
//            user.setUserCartCount(c);
//            session.saveOrUpdate(user);
//            status = 1;
            
            if (status > 0) {
                transaction.commit();
                
            } else {
                transaction.rollback();
                return 0;
            }

        } catch (Exception e) {

            transaction.rollback();
            e.printStackTrace();
            return status = 0;

        } finally {

            session.close();
        }

        return quantity;

    }

    public int deleteCartItem(User user, Cart cart) {

        Session session = null;
        Transaction transaction = null;
        int cartId = 0;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            cartId = cart.getCartId();

            session.delete(cart);

            int c = (getCartItemsForUser(user.getUserId())).size();

            //update cart count column in user table
            user.setUserCartCount(c-1);
            session.saveOrUpdate(user);
            status = 1;

            if (status > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
                return 0;
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
    
    //delete multiple cart items especially after placing order from cart
    public int deleteCartItemsAfterOrder(int userId) {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();
            
            String q1 = "delete from Cart as c where c.user.userId =: u";
            
            Query query = session.createQuery(q1);
            query.setParameter("u", userId);
            status = query.executeUpdate();
            
            if (status > 0) {
                transaction.commit();
            } else {
                transaction.rollback();
                status = 0;
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

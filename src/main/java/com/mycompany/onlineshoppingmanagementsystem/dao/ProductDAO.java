/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.dao;

import com.mycompany.onlineshoppingmanagementsystem.entities.Cart;
import com.mycompany.onlineshoppingmanagementsystem.entities.Category;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
import java.util.Arrays;
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
    public int createProduct(Product product, int categoryId) {

        Session session = null;
        Transaction transaction = null;
        int productId = 0;

        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            CategoryDAO categoryDAO = new CategoryDAO(this.sessionFactory);
            Category category = categoryDAO.getCategoryById(categoryId);

            if (category != null) {

                product.setCategory(category);

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

    //to check if product available and have sufficient quantity
    public int getProductQuantity(int productId) {

        Session session = null;
        int q = 0;

        try {
            session = this.sessionFactory.openSession();
            q = session.get(Product.class, productId).getProductQuantity();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            session.close();
            return q;
        }

    }

    //decrease single product quantity
    public int decreaseSingleProductQuantity(int productId, int newQuantity) {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();

            String query = "update Product set productQuantity =: q where productId =: p";

            transaction = session.beginTransaction();

            Query q = session.createQuery(query);
            q.setParameter("q", newQuantity);
            q.setParameter("p", productId);
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

    //decrease multiple product quantity
    public int decreaseMultipleProductsQuantity(List<Cart> carts) {

        Session session = null;
        int status = 0;

        try {

            session = this.sessionFactory.openSession();

            if (carts != null && !carts.isEmpty()) {

                for (Cart c : carts) {

                    int newQuantity = (c.getProduct().getProductQuantity()) - (c.getQuantity());
                    int s = decreaseSingleProductQuantity(c.getProduct().getProductId(), newQuantity);

                    if (s > 0) {
                        status = 1;
                        continue;
                    } else {
                        status = 0;
                        break;
                    }

                }

            } else {
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

    //get products with search key
    public List<Product> getProductsWithSearchKey(String searchKey) {

        Session session = null;
        List<Product> products = null;

        try {

            session = this.sessionFactory.openSession();
            Query query = session.createQuery("from Product p where p.productName like :pname or p.category.categoryTitle like :cname");
            query.setParameter("pname", "%"+searchKey+"%");
            query.setParameter("cname", "%"+searchKey+"%");
            products = query.list();

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            session.close();
            return products;
        }

    }
    
    //edit product
    public int updateProduct(Product product, int categoryId) {

        Session session = null;
        Transaction transaction = null;
        int status = 0;

        try {
            session = this.sessionFactory.openSession();
            transaction = session.beginTransaction();

            CategoryDAO categoryDAO = new CategoryDAO(this.sessionFactory);
            Category category = categoryDAO.getCategoryById(categoryId);

            if (category != null) {

                product.setCategory(category);

                session.saveOrUpdate(product);
                transaction.commit();
                status = 1;

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
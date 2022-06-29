package com.mycompany.onlineshoppingmanagementsystem.helper;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryProvider {
    private static SessionFactory sessionFactory;
    
    public static SessionFactory getFactory() {
        try{
            if (sessionFactory == null) {
                System.out.println("if fp");
                sessionFactory = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .buildSessionFactory();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.out.println("catch fp");
        }
        
        return sessionFactory;
    }
}

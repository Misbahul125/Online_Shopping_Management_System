/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.helper.payment;

import com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.razorpay.*;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Misbahul Haque
 */
public class PaymentOperationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @return
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws com.razorpay.RazorpayException
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RazorpayException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            System.out.println("payment initiated-2");

            int amount = Integer.parseInt(request.getParameter("amount"));
            String info = request.getParameter("info");

            System.out.println(amount);
            System.out.println(info);

            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_XZmCccoknEaBos", "mRoXJ7MdsPxG7bGjdH3SOuAZ");

                //generate transaction id
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("amount", amount * 100);
                jSONObject.put("currency", "INR");
                
                String receiptId = "OSMSRID"+(System.currentTimeMillis());
                jSONObject.put("receipt", receiptId);

                //send order request to the razorpay server
                Order order = razorpayClient.orders.create(jSONObject);
                System.out.println(order);

                if (order != null) {

                    //save order details to the database
                    System.out.println("order created successfully...");

                    response.getWriter().append(order.toString());

                }
                

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (RazorpayException ex) {
            Logger.getLogger(PaymentOperationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (RazorpayException ex) {
            Logger.getLogger(PaymentOperationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

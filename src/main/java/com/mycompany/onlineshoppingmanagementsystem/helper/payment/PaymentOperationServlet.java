/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.helper.payment;

import com.mycompany.onlineshoppingmanagementsystem.dao.CartDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.Cart;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import com.mycompany.onlineshoppingmanagementsystem.helper.RequestBodyHelper;
import com.mycompany.onlineshoppingmanagementsystem.helper.ResponseHelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.razorpay.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
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
    HttpSession httpSession = null;
    
    ResponseHelper responseHelper = new ResponseHelper();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, RazorpayException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            System.out.println("payment initiated-2");

            String data = new RequestBodyHelper().getData(request);

            if (data != null) {

                JSONObject ob1 = new JSONObject(data);
                int productId = (int) ob1.get("productId");
                int amount = (int) ob1.get("amount");
                int paymentMethod = (int) ob1.get("paymentMethod");
                System.out.println(amount);

                ProductDAO productDAO = new ProductDAO(FactoryProvider.getFactory());

                //check if single product
                if (productId != 0 && productId > 0) {

                    //check if the product quantity is not equal to 0
                    if (productDAO.getProductQuantity(productId) > 0) {

                        generateRazorpayOrderID(response, amount);

                    } else {
                        responseHelper.sendFalseResponse(response, "Oops! Product is not available in stock at this moment. Please try again after some time.");
                    }

                } //else multiple products
                else {
                    httpSession = request.getSession();
                    User user = (User) httpSession.getAttribute("current-user");

                    if (user != null) {

                        CartDAO cartDAO = new CartDAO(FactoryProvider.getFactory());
                        List<Cart> carts = cartDAO.getCartItemsForUser(user.getUserId());
                        
                        boolean isCartQuantityOverflow = false;
                        
                        String overflowMsg = "";
                        
                        for(Cart c : carts) {
                            
                            if(c.getProduct().getProductQuantity() == 0) {
                                isCartQuantityOverflow = true;
                                overflowMsg = "Oops! The product, "+c.getProduct().getProductName()+" is currently unavailable.";
                                break;
                            }
                            else if(c.getQuantity() > c.getProduct().getProductQuantity()) {
                                isCartQuantityOverflow = true;
                                overflowMsg = "Oops! The quantity of "+c.getProduct().getProductName()+" is too large to place your order. The available stock is "+c.getProduct().getProductQuantity()+". Please decrease your cart item's quantity and try again.";
                                break;
                            }
                            
                        }
                        
                        if(isCartQuantityOverflow) {
                            responseHelper.sendFalseResponse(response, overflowMsg);
                        }
                        else {
                            generateRazorpayOrderID(response, amount);
                        }

                    }
                    else {
                        responseHelper.sendFalseResponse(response, "User not found. Please login and try again.");
                    }

                }

            } else {
                responseHelper.sendFalseResponse(response, "Unable to initiate your order. Please login and try again.");
            }

        }
    }

    private void generateRazorpayOrderID(HttpServletResponse response, int amount) throws IOException, RazorpayException {

        final String KEY_ID = "rzp_test_XZmCccoknEaBos";
        final String SECRET_KEY = "mRoXJ7MdsPxG7bGjdH3SOuAZ";
        //initiate razorpay
        RazorpayClient razorpayClient = new RazorpayClient(KEY_ID, SECRET_KEY);

        //generate order id
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("amount", amount * 100);
        jSONObject.put("currency", "INR");

        String receiptId = "OSMSRID" + (System.currentTimeMillis());
        jSONObject.put("receipt", receiptId);

        //send order request to the razorpay server
        Order order = razorpayClient.orders.create(jSONObject);
        System.out.println(order);

        if (order != null) {

            //go for payment with the razorpay order id
            System.out.println("order created successfully...");

            response.getWriter().append(order.toString());
            //responseHelper.sendTrueResponse(response, order.toString());

        }
        else {
            responseHelper.sendFalseResponse(response, "Something went wrong.Unable to process your payment. Please try again.");
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
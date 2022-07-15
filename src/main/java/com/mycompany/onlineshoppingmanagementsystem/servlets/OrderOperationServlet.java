/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.CartDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.OrdersDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.UserDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.Cart;
import com.mycompany.onlineshoppingmanagementsystem.entities.Orders;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import com.mycompany.onlineshoppingmanagementsystem.helper.RequestBodyHelper;
import com.mycompany.onlineshoppingmanagementsystem.helper.ResponseHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Misbahul Haque
 */
public class OrderOperationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    HttpSession httpSession = null;
    Product product = null;
    User user = null;

    ProductDAO productDAO = new ProductDAO(FactoryProvider.getFactory());
    CartDAO cartDAO = new CartDAO(FactoryProvider.getFactory());

    List<Cart> carts = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            System.out.println("Order initiated 2");

            String data = new RequestBodyHelper().getData(request);

            ResponseHelper responseHelper = new ResponseHelper();

            //check if payment data is not null
            if (data != null) {

                //parsing string data into json
                JSONObject jSONObject = new JSONObject(data);

                //check if payment method is COD
                //then tally product quantity with inventory
                int pid = (int) jSONObject.get("pid");
                String paymentMethod = "";

                String deliveryDate = (String) jSONObject.get("deliveryDate");

                String razorpayOrderId = null;
                String razorpayPaymentId = null;
                String razorpaySignatureId = null;
                int netAmount = 0;
                if (((int) jSONObject.get("paymentMethod")) == 1) {
                    paymentMethod = Constants.OP.toString();
                    razorpayOrderId = (String) jSONObject.get("razorpay_order_id");
                    razorpayPaymentId = (String) jSONObject.get("razorpay_payment_id");
                    razorpaySignatureId = (String) jSONObject.get("razorpay_signature");
                    netAmount = (int) jSONObject.get("amount");
                } else {
                    paymentMethod = Constants.COD.toString();
                    boolean isAvailable = isProductQuantityAvailable(request, response, responseHelper, pid);
                    if (isAvailable) {
                        netAmount = (int) jSONObject.get("amount");
                    } else {
                        return;
                    }

                }

                httpSession = request.getSession();
                if (user == null) {

                    user = (User) httpSession.getAttribute("current-user");
                }

                String orderId = "OID" + (System.currentTimeMillis());

                OrdersDAO ordersDAO = new OrdersDAO(FactoryProvider.getFactory());

                //check if single product
                if (pid != 0 && pid > 0) {

                    if (product == null) {
                        product = productDAO.getProductById(pid);
                    }

                    int newQuantity = product.getProductQuantity() - 1;
                    int status = productDAO.decreaseSingleProductQuantity(pid, newQuantity);

                    if (status > 0) {

                        //insert data into order table
                        Orders order = new Orders();
                        order.setActualOrderId(orderId);
                        order.setQuantity(1);
                        order.setTotal(product.getProductSellingPrice());
                        order.setNetAmount(netAmount);

                        order.setPaymentMethod(paymentMethod);
                        order.setRazorpayOrderId(razorpayOrderId);
                        order.setRazorpayPaymentId(razorpayPaymentId);
                        order.setRazorpaySignatureId(razorpaySignatureId);

                        order.setOrderStatus(Constants.IN_PROGRESS.toString());
                        order.setDeliveryDate(deliveryDate);
                        order.setUserAddress(user.getUserAddress());
                        order.setProduct(product);
                        order.setUser(user);
                        order.setAddress(null);

                        int s = ordersDAO.addSingleOrder(order);
                        if (s > 0) {
                            httpSession.removeAttribute("current-user");
                            httpSession.setAttribute("current-user", user);
                            responseHelper.sendTrueResponse(response, "Order placed successfully. You can check its status in My Orders.");
                        } else {
                            responseHelper.sendFalseResponse(response, "Something went wrong. Unable to process your order.");
                        }

                    } else {
                        responseHelper.sendFalseResponse(response, "Something went wrong. Unable to process your order.");
                    }
                } //else multiple products
                else {

                    if (carts == null) {
                        carts = cartDAO.getCartItemsForUser(user.getUserId());
                    }

                    int status = productDAO.decreaseMultipleProductsQuantity(carts);

                    if (status > 0) {

                        //insert data into order table
                        List<Orders> orders = new ArrayList<>();
                        for (Cart c : carts) {
                            Orders order = new Orders();
                            order.setActualOrderId(orderId);
                            order.setQuantity(c.getQuantity());
                            order.setTotal(c.getTotal());
                            order.setNetAmount(netAmount);

                            order.setPaymentMethod(paymentMethod);
                            order.setRazorpayOrderId(razorpayOrderId);
                            order.setRazorpayPaymentId(razorpayPaymentId);
                            order.setRazorpaySignatureId(razorpaySignatureId);

                            order.setOrderStatus(Constants.IN_PROGRESS.toString());
                            order.setDeliveryDate(deliveryDate);
                            order.setUserAddress(user.getUserAddress());
                            order.setProduct(c.getProduct());
                            order.setUser(user);
                            order.setAddress(null);

                            orders.add(order);
                        }

                        int s1 = ordersDAO.addMultipleOrders(orders);

                        if (s1 > 0) {
                            //finally delete from cart
                            int s2 = cartDAO.deleteCartItemsAfterOrder(user.getUserId());
                            if (s2 > 0) {

                                //lastly update user cart count to 0
                                int s3 = new UserDAO(FactoryProvider.getFactory()).updateCartCountByUserId(user, 0);
                                if (s3 > 0) {
                                    httpSession.removeAttribute("current-user");
                                    httpSession.setAttribute("current-user", user);
                                    responseHelper.sendTrueResponse(response, "Order placed successfully. You can check its status in My Orders.");
                                } else {
                                    httpSession.removeAttribute("current-user");
                                    httpSession.setAttribute("current-user", user);
                                    responseHelper.sendTrueResponse(response, "We are unable to update your cart quantity. But luckily your order have been placed successfully. You can check its status in My Orders.");
                                }

                            } else {
                                httpSession.removeAttribute("current-user");
                                httpSession.setAttribute("current-user", user);
                                responseHelper.sendTrueResponse(response, "We are unable to remove your cart item(s). But luckily your order have been placed successfully. You can check its status in My Orders.");
                            }

                        } else {
                            responseHelper.sendFalseResponse(response, "Something went wrong. Unable to process your order.");
                        }

                    } else {
                        responseHelper.sendFalseResponse(response, "Something went wrong. Unable to process your order.");
                    }

                }

            } else {
                responseHelper.sendFalseResponse(response, "Something went wrong. Unable to fetch your payment details.");
            }

        }
    }

    private boolean isProductQuantityAvailable(HttpServletRequest request, HttpServletResponse response, ResponseHelper responseHelper, int pid) throws IOException {

        //if single product
        if (pid != 0 && pid > 0) {
            product = productDAO.getProductById(pid);

            //check if the product quantity is not equal to 0
            if (product.getProductQuantity() > 0) {
                return true;
            } else {
                responseHelper.sendFalseResponse(response, "Oops! Product is not available in stock at this moment. Please try again after some time.");
                return false;
            }
        } else {
            httpSession = request.getSession();
            user = (User) httpSession.getAttribute("current-user");

            carts = cartDAO.getCartItemsForUser(user.getUserId());

            boolean isCartQuantityOverflow = false;

            String overflowMsg = "";

            for (Cart c : carts) {

                if (c.getQuantity() > c.getProduct().getProductQuantity()) {
                    isCartQuantityOverflow = true;
                    overflowMsg = "Oops! the quantity of " + c.getProduct().getProductName() + " is too large to place your order. The available stock is " + c.getProduct().getProductQuantity() + ". Please decrease your cart item's quantity and try again.";
                    break;
                }

            }

            if (isCartQuantityOverflow) {
                responseHelper.sendFalseResponse(response, overflowMsg);
                return false;
            } else {
                return true;
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
        processRequest(request, response);
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
        processRequest(request, response);
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

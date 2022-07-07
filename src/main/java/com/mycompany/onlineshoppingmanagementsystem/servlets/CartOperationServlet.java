/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.CartDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.Cart;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Misbahul Haque
 */
public class CartOperationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            User user = (User) request.getSession().getAttribute("current-user");
            int productId = Integer.parseInt(request.getParameter("productId"));
            String action = request.getParameter("action");

            ProductDAO productDAO = new ProductDAO(FactoryProvider.getFactory());
            Product product = productDAO.getProductById(productId);
            
            HttpSession httpSession = request.getSession();

//            System.out.println(product.getProductName());
//            System.out.println(user.getUserName());
            if (user != null && product != null) {

                CartDAO cartDAO = new CartDAO(FactoryProvider.getFactory());
                List<Cart> carts = cartDAO.getCartItemWithProductAndUserId(productId, user.getUserId());
                //System.out.println(carts);

                //check if particular item exists for the particular user in cart or not , if exists then increase/decrease the quantity
                if (carts != null && !carts.isEmpty()) {

                    //check if user wants to either remove or update quantity of cart item
                    if (action.matches(Constants.CART_DELETE.toString())) {

                        int cartId = cartDAO.deleteCartItem(user , carts.get(0));

                        if (cartId > 0) {

                            httpSession.setAttribute("positiveMessage", "Cart item have been deleted.");
                            response.sendRedirect("cart.jsp");

                        } else {

                            httpSession.setAttribute("negativeMessage", "Something went wrong! Please try again later.");
                            response.sendRedirect("cart.jsp");

                        }

                    } //update cart quantity
                    else {

                        int q = cartDAO.updateCartQuantity(user , carts.get(0), action);
                        //System.out.println(carts.get(0));

                        if (action.matches(Constants.CART_INCREMENT.toString())) {

                            httpSession.setAttribute("positiveMessage", "Cart item have been increased to " + q);
                            response.sendRedirect("cart.jsp");

                        } else if (action.matches(Constants.CART_DECREMENT.toString())) {

                            httpSession.setAttribute("negativeMessage", "Cart item have been decreased to " + q);
                            response.sendRedirect("cart.jsp");

                        } else {

                            httpSession.setAttribute("negativeMessage", "Something went wrong! Please try again later.");
                            response.sendRedirect("cart.jsp");

                        }

                    }

                } //no item with particular user id exists in cart, add new row(with user id and product id in cart table)
                else {
                    
                    System.out.println("Add1");

                    int cartId = cartDAO.addNewItemToCart(user , product);
                    //System.out.println("Cart id is : " + cartId);
                    System.out.println("Add15");

                    if (cartId > 0) {
                        
                        System.out.println("Add16");

                        httpSession.setAttribute("positiveMessage", "Item added to cart. ");
                        response.sendRedirect("cart.jsp");

                    } else {
                        
                        System.out.println("Add17");

                        httpSession.setAttribute("negativeMessage", "Something went wrong! Please try again later.");
                        response.sendRedirect("cart.jsp");

                    }

                }

            } else {

                httpSession.setAttribute("negativeMessage", "Something went wrong! Please try again later.");
                response.sendRedirect("cart.jsp");

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

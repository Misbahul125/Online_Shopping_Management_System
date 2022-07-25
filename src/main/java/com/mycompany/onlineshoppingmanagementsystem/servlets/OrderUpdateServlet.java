/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.OrdersDAO;
import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Misbahul Haque
 */
public class OrderUpdateServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    HttpSession httpSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        httpSession = request.getSession();

        String orderId = request.getParameter("orderId");
        String value = request.getParameter("value");
        String orderStatus = null;

        if (value.matches("1")) {
            orderStatus = Constants.IN_PROGRESS.toString();
        } else if (value.matches("2")) {
            orderStatus = Constants.DELIVERED.toString();
        } else if (value.matches("3")) {
            orderStatus = Constants.CANCELLED.toString();
        }

        OrdersDAO ordersDAO = new OrdersDAO(FactoryProvider.getFactory());
        int status = ordersDAO.updateOrderStatus(orderId, orderStatus);

        if (status > 0) {
            httpSession.setAttribute("positiveMessage", "Order with "+orderId+" have been updated successfully.");
            response.sendRedirect("viewOrders.jsp");
        } else {
            httpSession.setAttribute("negativeMessage", "Something went wrong. Please try again later");
            response.sendRedirect("viewOrders.jsp");
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

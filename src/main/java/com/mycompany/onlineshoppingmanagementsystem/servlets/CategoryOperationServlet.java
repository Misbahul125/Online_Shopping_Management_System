/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO;
import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Misbahul Haque
 */
@WebServlet(name = "CategoryOperationServlet", urlPatterns = {"/CategoryOperationServlet"})
public class CategoryOperationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    javax.servlet.http.HttpSession httpSession = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            httpSession = request.getSession();

            String operationType = request.getParameter("categoryOperation");

            //fetching category data
            String categoryTitle = request.getParameter("category_title");
            String categoryDescription = request.getParameter("category_description");
            
            int status = 0;
            
            CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());

            if (operationType.matches(Constants.EDIT.toString())) {

                int cid = Integer.parseInt(request.getParameter("categoryId"));

                status = categoryDAO.updateCategory(cid, categoryTitle, categoryDescription);
                
                if (status != 0) {
                    httpSession.setAttribute("positiveMessage", "Category is updated successfully.");
                    response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                    return;
                } else {
                    System.out.println("Unable to update category in database");
                    httpSession.setAttribute("negativeMessage", "Unable to update category! Please try again later.");
                    response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                    return;
                }

            } else if (operationType.matches(Constants.ADD.toString())) {

                status = categoryDAO.createCategory(categoryTitle, categoryDescription);

                if (status != 0) {
                    httpSession.setAttribute("positiveMessage", "Category is added successfully.");
                    response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                    return;
                } else {
                    System.out.println("Unable to addd category in database");
                    httpSession.setAttribute("negativeMessage", "Unable to add category! Please try again later.");
                    response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                    return;
                }

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

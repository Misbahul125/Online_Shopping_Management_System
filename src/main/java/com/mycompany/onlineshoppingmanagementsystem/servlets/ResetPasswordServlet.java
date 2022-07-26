/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.UserDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import com.mycompany.onlineshoppingmanagementsystem.helper.PasswordHelper.AESHelper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Misbahul Haque
 */
public class ResetPasswordServlet extends HttpServlet {

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

            String userEmail = request.getParameter("user_email");
            String userNewPassword = request.getParameter("user_new_password");
            String userConfirmPassword = request.getParameter("user_confirm_password");

            HttpSession httpSession = request.getSession();

            String encryptedPassword = null;

            try {
                encryptedPassword = AESHelper.encrypt(userConfirmPassword);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            UserDAO userDAO = new UserDAO(FactoryProvider.getFactory());
            int status = userDAO.resetPasswordByEmail(userEmail, encryptedPassword);

            if (status > 0) {

                httpSession.setAttribute("positiveMessage", "Password reset is successful.! Please login with your new password to continue.");
                response.sendRedirect("login.jsp");

            } else {
                httpSession.setAttribute("negativeMessage", "Something went wrong. Please try again later");
                response.sendRedirect("login.jsp");
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
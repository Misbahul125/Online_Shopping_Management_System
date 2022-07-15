/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.EmailVerification;

import com.mycompany.onlineshoppingmanagementsystem.dao.UserDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
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
public class SendOTPServlet extends HttpServlet {

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            String email = request.getParameter("user_email");

            httpSession = request.getSession();

            UserDAO userDAO = new UserDAO(FactoryProvider.getFactory());
            User user = userDAO.getUserByEmail(email);

            //check if user already exists or not with the same email
            if (user != null) {
                
                httpSession.setAttribute("negativeMessage", "User already exists. Please try with other Email ID.");
                response.sendRedirect("signup1.jsp");
            
            } else {

                //create instance object of the SendEmail Class
                EmailHelper emailHelper = new EmailHelper();

                //get the 6-digit code
                String code = emailHelper.getRandom();

                //save details to user class
                TemporaryUser tempUser = new TemporaryUser(email, code);
                
                //call the send email method
                boolean isEmailSent = emailHelper.sendEmail(tempUser);

                //check if the email send successfully
                if (isEmailSent) {
                    httpSession.setAttribute("temp-user", tempUser);
                    httpSession.setAttribute("positiveMessage", "OTP sent successfully! Please check your email.");
                    response.sendRedirect("signup2.jsp");
                } else {
                    httpSession.setAttribute("negativeMessage", "Something went wong! Unable to send OTP.");
                    response.sendRedirect("signup1.jsp");
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

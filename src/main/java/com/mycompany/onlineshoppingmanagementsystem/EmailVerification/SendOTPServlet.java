/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.EmailVerification;

import com.mycompany.onlineshoppingmanagementsystem.dao.UserDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
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

    TemporaryUser tempUser = null;

    //create instance object of the EmailHelper Class
    EmailHelper emailHelper = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            String source = request.getParameter("source");

            String email = request.getParameter("user_email");

            httpSession = request.getSession();

            UserDAO userDAO = new UserDAO(FactoryProvider.getFactory());
            User user = userDAO.getUserByEmail(email);

            emailHelper = new EmailHelper(source);

            //coming from reset password page
            if (source.matches(Constants.RESET.toString())) {

                //check if user exists or not
                if (user != null) {

                    boolean isEmailSent = sendEmail(email);
                    //check if the email send successfully
                    if (isEmailSent) {
                        httpSession.setAttribute("temp-user", tempUser);
                        httpSession.setAttribute("positiveMessage", "Reset OTP sent successfully! Please check your email.");
                        response.sendRedirect("verifyOtp.jsp?source="+source);
                    } else {
                        httpSession.setAttribute("negativeMessage", "Something went wong! Unable to send reset OTP.");
                        response.sendRedirect("reset_password1.jsp");
                    }

                } //user doesn't exits
                else {

                    httpSession.setAttribute("negativeMessage", "User doesn't exists. Invalid Email ID.");
                    response.sendRedirect("reset_password1.jsp");

                }

            } //coming from signup page
            else {

                //check if user exists or not
                if (user != null) {

                    httpSession.setAttribute("negativeMessage", "User already exists. Please try with other Email ID.");
                    response.sendRedirect("signup1.jsp");

                } //user doesn't exits
                else {
                    boolean isEmailSent = sendEmail(email);
                    //check if the email send successfully
                    if (isEmailSent) {
                        httpSession.setAttribute("temp-user", tempUser);
                        httpSession.setAttribute("positiveMessage", "OTP sent successfully! Please check your email.");
                        response.sendRedirect("verifyOtp.jsp?source="+source);
                    } else {
                        httpSession.setAttribute("negativeMessage", "Something went wong! Unable to send OTP.");
                        response.sendRedirect("signup1.jsp");
                    }
                }

            }

        }

    }

    private boolean sendEmail(String email) {

        //get the 6-digit code
        String code = emailHelper.getRandom();

        //save details to user class
        tempUser = new TemporaryUser(email, code);

        //call the send email method
        boolean status = emailHelper.sendEmail(tempUser);

        return status;

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
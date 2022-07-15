/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.EmailVerification;

import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
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
public class ResendOtpServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private HttpSession httpSession = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        httpSession = request.getSession();

        String source = request.getParameter("source");

        TemporaryUser oldTemporaryUser = (TemporaryUser) httpSession.getAttribute("temp-user");

        //create instance object of the SendEmail Class
        EmailHelper emailHelper = new EmailHelper();

        //get the 6-digit code
        String code = emailHelper.getRandom();

        TemporaryUser newTemporaryUser = new TemporaryUser(oldTemporaryUser.getEmail(), code);

        //call the send email method
        boolean isEmailSent = emailHelper.sendEmail(newTemporaryUser);

        //check if the email send successfully
        if (isEmailSent) {
            httpSession.removeAttribute("temp-user");
            httpSession.setAttribute("temp-user", newTemporaryUser);
            httpSession.setAttribute("positiveMessage", "OTP sent again successfully! Please check your email.");
            response.sendRedirect("verifyOtp.jsp?source=" + source);
        } else {

            if (source.matches(Constants.RESET.toString())) {
                httpSession.setAttribute("negativeMessage", "Something went wong! Unable to resend OTP.");
                response.sendRedirect("reset_password1.jsp");
            } else {

                httpSession.setAttribute("negativeMessage", "Something went wong! Unable to resend OTP.");
                response.sendRedirect("signup1.jsp");

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

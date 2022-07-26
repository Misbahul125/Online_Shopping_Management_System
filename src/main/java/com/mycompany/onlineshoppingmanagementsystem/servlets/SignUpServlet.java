package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.UserDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.UtilityCountDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.User;
import com.mycompany.onlineshoppingmanagementsystem.helper.PasswordHelper.AESHelper;
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
public class SignUpServlet extends HttpServlet {

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

            HttpSession httpSession = request.getSession();

            String userName = request.getParameter("user_name");
            String userEmail = request.getParameter("user_email");
            String userPassword = request.getParameter("user_password");
            String userPhone = request.getParameter("user_phone");
            String userImage = "default.png";
            String userAddress = request.getParameter("user_address");
            String userType = null;
            String encryptedPassword = null;

            try {
                encryptedPassword = AESHelper.encrypt(userPassword);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (userEmail.contains("@uem.edu.in")) {
                userType = Constants.ADMIN_USER.toString();
            } else {
                userType = Constants.NORMAL_USER.toString();
            }

            UserDAO userDAO = new UserDAO(FactoryProvider.getFactory());
            int s1 = userDAO.createUserWithEmailAndPassword(userName, userEmail, encryptedPassword, userPhone, userImage, userAddress, userType);

            if (s1 > 0) {

                int s2 = new UtilityCountDAO(FactoryProvider.getFactory()).updateUserCount();

                if (s2 > 0) {

                    User user = userDAO.getUserByEmailAndPassword(userEmail, encryptedPassword);

                    if (user != null) {

                        //if user signup is succssful, log in user
                        httpSession.setAttribute("current-user", user);

                        if (userType.matches(Constants.ADMIN_USER.toString())) {
                            httpSession.removeAttribute("temp-user");
                            response.sendRedirect("admin_home.jsp");
                            return;
                        } else if (userType.matches(Constants.NORMAL_USER.toString())) {
                            httpSession.removeAttribute("temp-user");
                            response.sendRedirect("client_home.jsp");
                            return;
                        } else {
                            httpSession.removeAttribute("temp-user");
                            httpSession.setAttribute("negativeMessage", "You have been registered successfully. But something went wong! Please try to login.");
                            response.sendRedirect("login.jsp");
                        }

                    } else {
                        httpSession.removeAttribute("temp-user");
                        httpSession.setAttribute("negativeMessage", "You have been registered successfully. But something went wong! Please try to login.");
                        response.sendRedirect("login.jsp");
                    }

                } else {

                    httpSession.removeAttribute("temp-user");
                    httpSession.setAttribute("negativeMessage", "You have been registered successfully. But something went wong! Please try to login.");
                    response.sendRedirect("login.jsp");

                }

            } else {

                //if something goes wrong
                httpSession.removeAttribute("temp-user");
                httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
                response.sendRedirect("signup.jsp");

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
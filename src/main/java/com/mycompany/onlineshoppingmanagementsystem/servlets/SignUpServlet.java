package com.mycompany.onlineshoppingmanagementsystem.servlets;

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
import org.hibernate.Session;
import org.hibernate.Transaction;

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
    Session hibernateSession;
    Transaction t;
    HttpSession httpSession;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            httpSession = request.getSession();

            try {
                String userName = request.getParameter("user_name");
                String userEmail = request.getParameter("user_email");
                String userPassword = request.getParameter("user_password");
                String userPhone = request.getParameter("user_phone");
                String userAddress = request.getParameter("user_address");
                String userType = null;

                hibernateSession = FactoryProvider.getFactory().openSession();
                t = hibernateSession.beginTransaction();

                //check user exists or not
                UserDAO userDAO = new UserDAO(FactoryProvider.getFactory());
                User user = userDAO.getUserByEmailAndPassword(userEmail, userPassword);
                System.out.println(user);

                if (user != null) {
                    // user already exits

                    httpSession.setAttribute("negativeMessage", "User already exists. Please try to login");
                    response.sendRedirect("login.jsp");
                } else {
                    //new user

                    if (userEmail.contains("@uem.edu.in")) {
                        userType = Constants.ADMIN_USER.toString();
                    } else {
                        userType = Constants.NORMAL_USER.toString();
                    }

                    user = new User(userName, userEmail, userPassword, userPhone, "default.png", userAddress, userType);

                    int userId = (int) hibernateSession.save(user);

                    t.commit();

                    httpSession.setAttribute("current-user", user);

                    if (user.getUserType().matches(Constants.ADMIN_USER.toString())) {
                        System.out.println("2");
                        response.sendRedirect("admin/admin_home.jsp");
                        return;
                    } else if (user.getUserType().matches(Constants.NORMAL_USER.toString())) {
                        System.out.println("3");
                        response.sendRedirect("client/client_home.jsp");
                        return;
                    } else {
                        t.rollback();
                        httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
                        httpSession.removeAttribute("current-user");
                        response.sendRedirect("signup.jsp");
                    }

//                out.println("Signup successful");
//                out.println("<br> User Id is "+userId);
                }

            } catch (Exception e) {
                t.rollback();
                httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
                response.sendRedirect("signup.jsp");

                e.printStackTrace();
            } finally {
                hibernateSession.close();
                System.out.println("closed");
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

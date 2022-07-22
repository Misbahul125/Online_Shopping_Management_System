/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.UtilityCountDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.Category;
import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import com.mycompany.onlineshoppingmanagementsystem.helper.ImageHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Misbahul Haque
 */

@MultipartConfig
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
    HttpSession httpSession = null;
    Part part = null;
    FileOutputStream fos = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            httpSession = request.getSession();

            String operationType = request.getParameter("categoryOperation");

            //fetching category data
            String categoryTitle = request.getParameter("category_title");
            String categoryDescription = request.getParameter("category_description");

            part = request.getPart("category_image");
            String categoryImageName = part.getSubmittedFileName();

            String addImagePath = getServletContext().getRealPath("pictures") + File.separator + "categories" + File.separator + categoryImageName;

            Category category = null;

            ImageHelper imageHelper = new ImageHelper();

            int s1 = 0;

            //upload image
            CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());

            if (operationType.matches(Constants.EDIT.toString())) {

                int cid = Integer.parseInt(request.getParameter("categoryId"));

                String cImage = categoryDAO.getCategoryById(cid).getCategoryPic();

                Path deleteImagePath = Paths.get(getServletContext().getRealPath("pictures") + File.separator + "categories" + File.separator + cImage);

                if (imageHelper.deleteImage(deleteImagePath)) {

                    if (imageHelper.addImage(part, addImagePath)) {

                        category = new Category(cid, categoryTitle, categoryDescription, categoryImageName);

                        //upload data
                        s1 = categoryDAO.updateCategory(category);

                        if (s1 != 0) {
                            httpSession.setAttribute("positiveMessage", "Category is updated successfully.");
                            response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                            return;
                        } else {
                            System.out.println("Unable to update category in database");
                            httpSession.setAttribute("negativeMessage", "Unable to update category! Please try again later.");
                            response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                            return;
                        }

                    } else {
                        System.out.println("Unable to add image");
                        httpSession.setAttribute("negativeMessage", "Unable to update category! Please try again later.");
                        response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                        return;
                    }

                } else {
                    System.out.println("The product image is not deleted");
                    httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
                    response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                    return;
                }

            } else if (operationType.matches(Constants.ADD.toString())) {

                if (imageHelper.addImage(part, addImagePath)) {

                    category = new Category(categoryTitle, categoryDescription, categoryImageName, null);

                    s1 = categoryDAO.createCategory(category);

                    if (s1 > 0) {

                        int s2 = new UtilityCountDAO(FactoryProvider.getFactory()).updateCategoryCount();

                        if (s2 > 0) {
                            httpSession.setAttribute("positiveMessage", "Category is added successfully with ID : " + s1);
                            response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                            return;
                        } else {
                            System.out.println("Unable to update category count in database");
                            httpSession.setAttribute("negativeMessage", "Category added successfully! Something went wrong. Unable to update category count");
                            response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                            return;
                        }

                    } else {

                        System.out.println("Unable to add category in database");
                        httpSession.setAttribute("negativeMessage", "Unable to add category! Please try again later.");
                        response.sendRedirect("category.jsp?action=" + Constants.NONE.toString());
                        return;

                    }

                } else {

                    System.out.println("The category image is not added");
                    httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
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

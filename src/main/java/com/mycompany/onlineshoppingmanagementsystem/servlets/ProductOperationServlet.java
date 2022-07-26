/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.UtilityCountDAO;
import com.mycompany.onlineshoppingmanagementsystem.entities.Product;
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
public class ProductOperationServlet extends HttpServlet {

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

            String operationType = request.getParameter("productOperation");

            //fetching product data
            String productName = request.getParameter("product_name");
            String productDescription = request.getParameter("product_description");
            int productMarkedPrice = Integer.parseInt(request.getParameter("product_marked_price"));
            int productDiscount = Integer.parseInt(request.getParameter("product_discount"));
            int productSellingPrice = Integer.parseInt(request.getParameter("product_selling_price"));
            int productQuantity = Integer.parseInt(request.getParameter("product_quantity"));
            int productCategory = Integer.parseInt(request.getParameter("productCategories"));

            part = request.getPart("product_image");
            String productImageName = part.getSubmittedFileName();

            String addImagePath = getServletContext().getRealPath("pictures") + File.separator + "products" + File.separator + productImageName;

            Product product = null;

            ImageHelper imageHelper = new ImageHelper();

            int s1 = 0;

            //upload image
            ProductDAO productDAO = new ProductDAO(FactoryProvider.getFactory());

            if (operationType.matches(Constants.EDIT.toString())) {

                int pid = Integer.parseInt(request.getParameter("productId"));

                String pImage = productDAO.getProductById(pid).getProductPic();

                Path deleteImagePath = Paths.get(getServletContext().getRealPath("pictures") + File.separator + "products" + File.separator + pImage);

                if (imageHelper.deleteImage(deleteImagePath)) {

                    if (imageHelper.addImage(part, addImagePath)) {

                        product = new Product(pid, productName, productDescription, productMarkedPrice, productDiscount, productSellingPrice, productQuantity, productImageName, null, null, null);

                        //upload data
                        s1 = productDAO.updateProduct(product, productCategory);

                        if (s1 != 0) {
                            httpSession.setAttribute("positiveMessage", "Product is updated successfully.");
                            response.sendRedirect("product.jsp?action=" + Constants.NONE.toString());
                            return;
                        } else {
                            System.out.println("Unable to update product in database");
                            httpSession.setAttribute("negativeMessage", "Unable to update product! Please try again later.");
                            response.sendRedirect("product.jsp?action=" + Constants.NONE.toString());
                            return;
                        }

                    } else {
                        System.out.println("Unable to add image");
                        httpSession.setAttribute("negativeMessage", "Unable to update product! Please try again later.");
                        response.sendRedirect("product.jsp?action=" + Constants.NONE.toString());
                        return;
                    }

                } else {
                    System.out.println("The product image is not deleted");
                    httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
                    response.sendRedirect("product.jsp?action=" + Constants.NONE.toString());
                    return;
                }

            } else if (operationType.matches(Constants.ADD.toString())) {

                if (imageHelper.addImage(part, addImagePath)) {

                    product = new Product(productName, productDescription, productMarkedPrice, productDiscount, productSellingPrice, productQuantity, productImageName, null, null, null);

                    s1 = productDAO.createProduct(product, productCategory);

                    if (s1 > 0) {

                        int s2 = new UtilityCountDAO(FactoryProvider.getFactory()).updateProductCount();

                        if (s2 > 0) {
                            httpSession.setAttribute("positiveMessage", "Product is added successfully.");
                            response.sendRedirect("product.jsp?action=" + Constants.NONE.toString());
                            return;
                        } else {
                            System.out.println("Unable to update product count in database");
                            httpSession.setAttribute("negativeMessage", "Product added successfully! Something went wrong. Unable to update category count");
                            response.sendRedirect("product.jsp?action=" + Constants.NONE.toString());
                            return;
                        }

                    } else {

                        System.out.println("Unable to add product in database");
                        httpSession.setAttribute("negativeMessage", "Unable to add product! Please try again later.");
                        response.sendRedirect("product.jsp?action=" + Constants.NONE.toString());
                        return;

                    }
                } else {

                    System.out.println("The product image is not added");
                    httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
                    response.sendRedirect("product.jsp?action=" + Constants.NONE.toString());
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
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.onlineshoppingmanagementsystem.servlets;

import com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO;
import com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO;
import com.mycompany.onlineshoppingmanagementsystem.helper.CalculateDiscount;
import com.mycompany.onlineshoppingmanagementsystem.helper.Constants;
import com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
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
            String productImage = part.getSubmittedFileName();
            
            int status = 0;

            //upload image
            ProductDAO productDAO = new ProductDAO(FactoryProvider.getFactory());

            if (operationType.matches(Constants.EDIT.toString())) {

                int pid = Integer.parseInt(request.getParameter("productId"));

                String pImage = productDAO.getProductById(pid).getProductPic();

                if (deleteImage(pImage)) {

                    if (addImage(productImage)) {

                        //upload data
                        status = productDAO.updateProduct(pid, productName, productDescription, productMarkedPrice, productDiscount, productSellingPrice, productQuantity, productImage, productCategory);

                        if (status != 0) {
                            httpSession.setAttribute("positiveMessage", "Product is updated successfully.");
                            response.sendRedirect("product.jsp?action="+Constants.NONE.toString());
                            return;
                        } else {
                            System.out.println("Unable to update product in database");
                            httpSession.setAttribute("negativeMessage", "Unable to update product! Please try again later.");
                            response.sendRedirect("product.jsp?action="+Constants.NONE.toString());
                            return;
                        }

                    } else {
                        System.out.println("Unable to add image");
                        httpSession.setAttribute("negativeMessage", "Unable to update product! Please try again later.");
                        response.sendRedirect("product.jsp?action="+Constants.NONE.toString());
                        return;
                    }

                } else {
                    System.out.println("The product image is not deleted");
                    httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
                    response.sendRedirect("product.jsp?action="+Constants.NONE.toString());
                    return;
                }

            } else if (operationType.matches(Constants.ADD.toString())) {

                if (addImage(productImage)) {
                    status = productDAO.createProduct(productName, productDescription, productMarkedPrice, productDiscount, productSellingPrice, productQuantity, productImage, productCategory);

                    if (status != 0) {
                        httpSession.setAttribute("positiveMessage", "Product is added successfully with ID : " + status);
                        response.sendRedirect("product.jsp?action="+Constants.NONE.toString());
                        return;
                    } else {
                        System.out.println("Unable to add product in database");
                        httpSession.setAttribute("negativeMessage", "Unable to update product! Please try again later.");
                        response.sendRedirect("product.jsp?action="+Constants.NONE.toString());
                        return;
                    }
                } else {

                    System.out.println("The product image is not added");
                    httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
                    response.sendRedirect("product.jsp?action="+Constants.NONE.toString());
                    return;

                }

            }
        }

//            if (operationType.trim().matches("add_category")) {
//
//                //fetching category data
//                String categoryTitle = request.getParameter("category_title");
//                String categoryDescription = request.getParameter("category_description");
//
//                CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());
//                int status = categoryDAO.createCategory(categoryTitle, categoryDescription);
//
//                if (status != 0) {
//                    httpSession.setAttribute("positiveMessage", "Category is added successfully with ID : " + status);
//                    response.sendRedirect("admin_home.jsp");
//                    return;
//                } else {
//                    httpSession.setAttribute("negativeMessage", "Something went wong! Please try again later.");
//                    response.sendRedirect("admin_home.jsp");
//                    return;
//                }
//
//            } 
    }

    private boolean deleteImage(String pImage) {

        Path imgPath = Paths.get(getServletContext().getRealPath("pictures") + File.separator + "products" + File.separator + pImage);

        try {
            Files.delete(imgPath);
            System.out.println("deleted");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private boolean addImage(String pImage) throws IOException {

        try {
            String path = getServletContext().getRealPath("pictures") + File.separator + "products" + File.separator + pImage;
            System.out.println(path);

            fos = new FileOutputStream(path);
            InputStream is = part.getInputStream();

            byte data[] = new byte[is.available()];
            is.read(data);
            fos.write(data);
            fos.close();

            return true;
        } catch (Exception e) {
            if (fos != null) {
                fos.close();
            }
            e.printStackTrace();

            return false;
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

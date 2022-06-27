<%-- 
    Document   : index
    Created on : 20-Jun-2022, 10:58:34 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.SentenceHelper"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Product"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - Home</title>

        <%@include file="components/common_css_js.jsp" %>
    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container-fluid text-center">

            <div class="row mt-4 mx-2">

                <%  String categoryId = request.getParameter("categoryId");

                    ProductDAO productDAO = new ProductDAO(FactoryProvider.getFactory());

                    List<Product> products = null;

                    if (categoryId == null || categoryId.trim().matches("all")) {
                        products = productDAO.getAllProducts();
                    } else {
                        int cid = Integer.parseInt(categoryId);
                        products = productDAO.getAllProductsByCategoryId(cid);
                    }

                    CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());
                    List<Category> categories = categoryDAO.getCategories();
                %>

                <!-- display categories in this column -->
                <div class="col-md-2">

                    <div class="list-group mt-4">

                        <a href="index.jsp?categoryId=all" class="list-group-item list-group-item-action active">All Categories</a>

                        <%
                            for (Category c : categories) {
                        %>

                        <a href="index.jsp?categoryId=<%= c.getCategoryId()%>" class="list-group-item list-group-item-action"><%= c.getCategoryTitle()%></a>

                        <%
                            }
                        %>

                    </div>

                </div>




                <!-- display products in this column -->
                <div class="col-md-10">

                    <div class="row mt-4">

                        <div class="col-md-12">

                            <%
                                //check if a particular category has more than 0 products, then display specific message
                                if(session.getAttribute("negativeMessage") != null) {
                            %>
                                    <%@include file="components/negativeMessage.jsp" %>
                            <%
                                return;
                                }
                            %>

                            <div class="card-columns">

                                <%  for (Product p : products) {
                                %>

                                <div class="card">

                                    <div class="container text-center">
                                        <img src="pictures/products/<%= p.getProductPic()%>" style="max-height: 200px; max-width: 100%; width: auto" class="card-img-top m-2" alt="">
                                    </div>

                                    <div class="card-body">

                                        <<h5 class="card-title"><%= p.getProductName()%></h5>

                                        <p class="card-text"><%= SentenceHelper.get10Words(p.getProductDescription())%></p>

                                    </div>

                                    <div class="card-footer">

                                        <button class="btn custom-bg text-white">Add to Cart</button>
                                        <button class="btn btn-outline-success"> &#8377; <%= String.valueOf(p.getProductPrice())%></button>

                                    </div>

                                </div>

                                <%
                                    }

                                    if (products.size() == 0) {
                                        session.setAttribute("negativeMessage", "No product(s) found in this category");
                                        response.sendRedirect("index.jsp");
                                    }
                                %>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </div>

</body>
</html>

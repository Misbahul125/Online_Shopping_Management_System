<%-- 
    Document   : index
    Created on : 20-Jun-2022, 10:58:34 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.SentenceHelper"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Product"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>

<%
    if (session.getAttribute("current-user") != null) {
        User u = (User) session.getAttribute("current-user");
        if (u.getUserType().matches(Constants.ADMIN_USER.toString())) {
            response.sendRedirect("admin_home.jsp");
            return;
        } else {
            response.sendRedirect("client_home.jsp");
            return;
        }
    }
%>

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
                        System.out.println("c-all");
                        products = productDAO.getAllProducts();
                    } else {
                        System.out.println(categoryId);
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
                                if (session.getAttribute("negativeMessage") != null) {
                            %>
                            <%@include file="components/negativeMessage.jsp" %>
                            <%                                    return;
                                }
                            %>

                            <div class="card-columns">

                                <%  SentenceHelper sh = new SentenceHelper();
                                    for (Product p : products) {
                                %>

                                <div class="card product-card">
                                    
                                    <%
                                        if(p.getProductQuantity() <= 10) {
                                    %>

                                    <div class="row">

                                        <div class="less-stock-alert">

                                            <h6>Hurry! Only few left</h6>

                                        </div>
                                    </div>
                                    
                                    <%
                                        }
                                    %>


                                    <br>

                                    <div class="container text-center">
                                        <img src="pictures/products/<%= p.getProductPic()%>" style="max-height: 150px; max-width: 100%; width: auto" class="card-img-top" alt="">
                                    </div>

                                    <div class="card-body">

                                        <<h5 class="card-title"><%= p.getProductName()%></h5>

                                        <p id="wholeText" data-id="<%= p.getProductId()%>" class="card-text whole-text">
                                            <%= sh.getWords(p.getProductDescription(), 0)%>
                                            <span class="dots">...</span>
                                            <span class="more-text">
                                                <%= sh.getWords(p.getProductDescription(), 1)%>
                                            </span>
                                            <button data-target-id="<%= p.getProductId()%>" onclick="changeText(event)" type="button" id="readMoreBtn" class="btn read-more">Read More</button>

                                        </p>





                                    </div>

                                    <div class="card-footer text-center">
                                        <!-- this button is for adding a product into a cart -->
                                        <button class="btn custom-bg text-white custom-btn" data-toggle="modal" data-target="#cart-login-modal">Add to Cart</button>

                                        <button class="btn btn-success" data-toggle="modal" data-target="#buy-login-modal"> 
                                            <span class="custom-btn">&#8377; <%= p.getProductSellingPrice()%></span>
                                            /- <span class="marked-price discount">
                                                &#8377; <%= p.getProductMarkedPrice()%>
                                            </span>
                                            <span class="discount">
                                                <%= p.getProductDiscount()%>% off
                                            </span>
                                        </button>

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


        <%
            if (session.getAttribute("current-user") == null) {
        %>

        <!-- Modal -->
        <div class="modal fade" id="cart-login-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Oops! User not found</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Please login or signup to facilitate cart.
                    </div>
                    <div class="modal-footer">

                        <a href="login.jsp">
                            <button type="button" class="btn btn-primary">
                                Login
                            </button>
                        </a>

                        <a href="signup1.jsp">
                            <button type="button" class="btn custom-bg text-white">
                                Signup
                            </button>
                        </a>

                    </div>
                </div>
            </div>
        </div>


        <div class="modal fade" id="buy-login-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLongTitle">Oops! User not found</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        Please login or signup to buy a product.
                    </div>
                    <div class="modal-footer">

                        <a href="login.jsp">
                            <button type="button" class="btn btn-primary">
                                Login
                            </button>
                        </a>

                        <a href="signup1.jsp">
                            <button type="button" class="btn custom-bg text-white">
                                Signup
                            </button>
                        </a>

                    </div>
                </div>
            </div>
        </div>

        <%        }
        %>


    </body>
</html>

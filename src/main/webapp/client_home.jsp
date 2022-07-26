<%-- 
    Document   : client_home
    Created on : 23-Jun-2022, 6:10:32 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Cart"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CartDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.SentenceHelper"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Product"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.User"%>
<%
    User user = (User) session.getAttribute("current-user");
    if (user != null) {
        if (user.getUserType().matches(Constants.ADMIN_USER.toString())) {
            session.setAttribute("negativeMessage", "You are not a valid user to access this page.");
            response.sendRedirect("login.jsp");
            return;
        }
    } else {
        session.setAttribute("negativeMessage", "Please login to continue !!");
        response.sendRedirect("login.jsp");
        return;
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
                        products = productDAO.getAllProducts();
                    } else {
                        int cid = Integer.parseInt(categoryId);
                        products = productDAO.getAllProductsByCategoryId(cid);
                    }

                    CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());
                    List<Category> categories = categoryDAO.getCategories();

                    CartDAO cartDAO = new CartDAO(FactoryProvider.getFactory());
                %>

                <!-- display categories in this column -->
                <div class="col-md-2">

                    <div class="list-group mt-4">

                        <a href="client_home.jsp?categoryId=all" class="list-group-item list-group-item-action active">All Categories</a>

                        <%
                            for (Category c : categories) {
                        %>

                        <a href="client_home.jsp?categoryId=<%= c.getCategoryId()%>" class="list-group-item list-group-item-action"><%= c.getCategoryTitle()%></a>

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

                                <%
                                    SentenceHelper sh = new SentenceHelper();
                                    for (Product p : products) {
                                %>

                                <div class="card product-card">

                                    <%
                                        if (p.getProductQuantity() <= 10) {
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
                                        <img src="pictures/products/<%= p.getProductPic()%>" style="max-height: 150px; max-width: 100%; width: auto" class="card-img-top m-2" alt="">
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

                                        <%
                                            List<Cart> carts = cartDAO.getCartItemWithProductAndUserId(p.getProductId(), user.getUserId());
                                            if (carts != null && !carts.isEmpty()) {
                                        %>


                                        <a href="CartOperationServlet?productId=<%=p.getProductId()%>&action=none">
                                            <button class="btn btn-go-to-cart custom-btn">Go to Cart</button>
                                        </a>

                                        <%
                                        } else {
                                        %> 

                                        <a href="CartOperationServlet?productId=<%=p.getProductId()%>&action=<%= Constants.CART_INCREMENT.toString()%>">
                                            <button class="btn custom-bg text-white custom-btn">Add to Cart</button>
                                        </a>

                                        <%
                                            }
                                        %>

                                        <a href="checkout.jsp?productId=<%=p.getProductId()%>&source=client_home">
                                            <button id="amt" class="btn btn-success" value="<%= p.getProductSellingPrice()%>">
                                                <span class="custom-btn">&#8377; <%= p.getProductSellingPrice()%></span>
                                                /- <span class="marked-price">
                                                    &#8377; <%= p.getProductMarkedPrice()%>
                                                </span>
                                                <span class="discount">
                                                    <%= p.getProductDiscount()%>% off
                                                </span>
                                            </button>
                                        </a>

                                    </div>

                                </div>

                                <%
                                    }

                                    if (products.size() == 0) {
                                        session.setAttribute("negativeMessage", "No product(s) found in this category");
                                        response.sendRedirect("client_home.jsp");
                                    }
                                %>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>


        <!-- Modal -->
        <!--        <div class="modal fade" id="buyModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h3 class="modal-title" id="exampleModalLabel">Buy</h3>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
        
                            <div class="modal-body">
                                <h5>Do you want to buy this product?</h5>
                            </div>
        
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
        
                                
        
                            </div>
                        </div>
                    </div>
                </div>-->

    </body>
</html>
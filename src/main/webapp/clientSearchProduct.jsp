<%-- 
    Document   : search
    Created on : 15-Jul-2022, 9:36:27 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CartDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Cart"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.SentenceHelper"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Product"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>

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
    
    String searchKey = request.getParameter("search");
    ProductDAO productDAO1 = new ProductDAO(FactoryProvider.getFactory());
    List <Product> products = productDAO1.getProductsWithSearchKey(searchKey);
    System.out.println(products);
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - Search</title>

        <%@include file="components/common_css_js.jsp" %>
    </head>

    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container-fluid text-center">

            <div class="row mt-4 mx-2">

                <!-- display products in this column -->
                <div class="col-md-10 offset-md-2">

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

                                    <div class="container text-center">
                                        <img src="pictures/products/<%= p.getProductPic()%>" style="max-height: 200px; max-width: 100%; width: auto" class="card-img-top" alt="">
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
                                            List<Cart> carts = new CartDAO(FactoryProvider.getFactory()).getCartItemWithProductAndUserId(p.getProductId(), user.getUserId());
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
                                        session.setAttribute("negativeMessage", "No search item(s) found");
                                        response.sendRedirect("clientSearchProduct.jsp");
                                    }
                                %>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </body>
    
</html>
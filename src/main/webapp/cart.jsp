<%-- 
    Document   : cart
    Created on : 06-Jul-2022, 2:20:31 am
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.DateHelper"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.SentenceHelper"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Cart"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CartDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
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

    int tItem = 0, tUnit = 0, tAmount = 0, netAmount = 0;

    DateHelper dh = new DateHelper();
    String deliveryDate = dh.getDeliveryDate();

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - Cart</title>

        <link rel="stylesheet" href="css/style.css">
        <%@include file="components/cart_css_js.jsp" %>
    </head>

    <body class="bg-light">

        <%@include file="components/navbar.jsp" %>

        <%            //fetch cart items
            CartDAO cartDAO = new CartDAO(FactoryProvider.getFactory());
            List<Cart> carts = cartDAO.getCartItemsForUser(user.getUserId());
        %>

        <div class="container-fluid">

            <%@include file="components/positiveMessage.jsp" %>
            <%@include file="components/negativeMessage.jsp" %>

            <div class="row">

                <%                    if (carts.size() != 0 && carts != null) {
                %>

                <div class="col-md-10 col-11 mx-auto">

                    <div class="row mt-5 gx-3">

                        <!-- left side div -->
                        <div class="col-md-12 col-lg-8 col-11 mx-auto main_cart mb-lg-0 mb-5 shadow">

                            <%
                                if (user.getUserCartCount() == 1) {
                            %>

                            <h2 class="py-4 font-weight-bold">Cart (<%= user.getUserCartCount()%> item)</h2>

                            <%
                            } else {
                            %>

                            <h2 class="py-4 font-weight-bold">Cart (<%= user.getUserCartCount()%> items)</h2>

                            <%
                                }
                            %>

                            <!--start loop-->

                            <%
                                SentenceHelper sh = new SentenceHelper();
                                for (Cart c : carts) {
                                    ++tItem;
                                    tUnit = tUnit + c.getQuantity();
                                    tAmount = tAmount + c.getTotal();
                            %>

                            <div class="card p-4">

                                <div class="row">

                                    <!-- cart images div -->
                                    <div class="col-md-5 col-11 mx-auto bg-light d-flex justify-content-center align-items-center shadow product_img">
                                        <img src="pictures/products/<%= c.getProduct().getProductPic()%>" class="img-fluid" alt="cart img">
                                    </div>

                                    <!-- cart product details -->
                                    <div class="col-md-7 col-11 mx-auto px-4 mt-2">
                                        <div class="row">
                                            <!-- product name  -->
                                            <div class="col-6 card-title">
                                                <h1 class="mb-4 product_name"><%= c.getProduct().getProductName()%></h1>

                                                <p id="wholeText" data-id="<%= c.getProduct().getProductId()%>" class="card-text whole-text">
                                                    <%= sh.getWords(c.getProduct().getProductDescription(), 0)%>
                                                    <span class="dots">...</span>
                                                    <span class="more-text">
                                                        <%= sh.getWords(c.getProduct().getProductDescription(), 1)%>
                                                    </span>
                                                    <button data-target-id="<%= c.getProduct().getProductId()%>" onclick="changeText(event)" type="button" id="readMoreBtn" class="btn read-more">Read More</button>

                                                </p>

                                            </div>
                                            <!-- quantity inc dec -->
                                            <div class="col-6">

                                                <div class="pagination justify-content-end">

                                                    <a href="CartOperationServlet?productId=<%=c.getProduct().getProductId()%>&action=<%= Constants.CART_DECREMENT.toString()%>">
                                                        <button class="page-link ">
                                                            <i class="fas fa-minus"></i> 
                                                        </button>
                                                    </a>

                                                    <span class="page-item"><%= c.getQuantity()%></span>

                                                    <a href="CartOperationServlet?productId=<%=c.getProduct().getProductId()%>&action=<%= Constants.CART_INCREMENT.toString()%>">
                                                        <button class="page-link"> 
                                                            <i class="fas fa-plus"></i>
                                                        </button>
                                                    </a>

                                                </div>

                                                <!--                                                <ul class="pagination justify-content-end set_quantity">
                                                                                                    
                                                                                                    <li class="page-item">
                                                                                                        <button class="page-link " onclick="decreaseNumber('textbox', 'itemval')">
                                                                                                            <i class="fas fa-minus"></i> </button>
                                                                                                    </li>
                                                
                                                                                                    <li class="page-item">
                                                <%= c.getQuantity()%>
                                            </li>

                                            <li class="page-item">
                                                <button class="page-link" onclick="increaseNumber('textbox', 'itemval')"> <i class="fas fa-plus"></i></button>
                                            </li>

                                        </ul>-->
                                            </div>
                                        </div>
                                        <!-- //remover move and price -->
                                        <div class="row">

                                            <div class="col-8 d-flex justify-content-between remove_wish">
                                                <h3 id="itemval"> &#8377; <%= c.getProduct().getProductSellingPrice()%>
                                                    /- <span class="cart-marked-price cart-discount">
                                                        &#8377; <%= c.getProduct().getProductMarkedPrice()%>
                                                    </span>
                                                    <span class="cart-discount">
                                                        <%= c.getProduct().getProductDiscount()%>% off
                                                    </span>
                                                </h3>
                                            </div>

                                            <div class="col-4 d-flex justify-content-end price_money">

                                                <a href="CartOperationServlet?productId=<%=c.getProduct().getProductId()%>&action=<%= Constants.CART_DELETE.toString()%>">
                                                    <button class="btn btn-danger">
                                                        <i class="fas fa-trash-alt"></i>
                                                        REMOVE ITEM
                                                    </button>
                                                </a>
                                            </div>

                                        </div>

                                    </div>

                                </div>

                            </div>

                            <hr/>

                            <%
                                }

                                netAmount = tAmount + 50;
                            %>

                        </div>

                        <!-- right side div -->
                        <div class="col-md-12 col-lg-4 col-11 mx-auto mt-lg-0 mt-md-5">

                            <div class="right_side p-3 shadow bg-white">

                                <h2 class="product_name font-weight-bold mb-5">The Total Amount</h2>

                                <div class="price_indiv d-flex justify-content-between">
                                    <p>Total items</p>
                                    <p><%= tItem%></p>
                                </div>

                                <div class="price_indiv d-flex justify-content-between">
                                    <p>Total units</p>
                                    <p><%= tUnit%></p>
                                </div>

                                <div class="price_indiv d-flex justify-content-between">
                                    <p>Total amount</p>
                                    <p>&#8377; <span id="product_total_amt"><%= tAmount%>.00</span></p>
                                </div>

                                <div class="price_indiv d-flex justify-content-between">
                                    <p>Shipping Charge</p>
                                    <p>&#8377; <span id="shipping_charge">50.00</span></p>
                                </div>

                                <hr />

                                <div class="total-amt d-flex justify-content-between font-weight-bold">
                                    <p>Net payable amount (including GST)</p>
                                    <p>&#8377; <span id="total_cart_amt"><%= netAmount%>.00</span></p>
                                </div>

                                <a href="checkout.jsp?source=cart">
                                    <button class="btn custom-bg text-white text-uppercase">Checkout</button>
                                </a>

                            </div>
                            <!-- discount code part -->
                            <!--                            <div class="discount_code mt-3 shadow">
                                                            <div class="card">
                                                                <div class="card-body">
                                                                    <a class="d-flex justify-content-between" data-toggle="collapse" href="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                                                                        Add a discount code (optional)
                                                                        <span><i class="fas fa-chevron-down pt-1"></i></span>
                                                                    </a>
                                                                    <div class="collapse" id="collapseExample">
                                                                        <div class="mt-3">
                                                                            <input type="text" id="discount_code1" class="form-control font-weight-bold" placeholder="Enter the discount code">
                                                                            <small id="error_trw" class="text-dark mt-3">code is thapa</small>
                                                                        </div>
                                                                        <button class="btn btn-primary btn-sm mt-3" onclick="discount_code()">Apply</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>-->

                            <!-- discount code ends -->
                            <div class="mt-3 shadow p-3 bg-white">
                                <div class="pt-4">
                                    <h5 class="mb-4">Expected delivery date</h5>
                                    <p><%= deliveryDate%></p>
                                </div>

                            </div>

                        </div>

                    </div>

                </div>

                <%
                } else {
                %>

                <div class="container-fluid text-center">

                    <h1>You have no products to show in your cart.</h1>
                    <h4>Please add products in your cart</h4>
                    <a href="client_home.jsp">
                        <button class="btn btn-primary custom-bg">View Products</button>
                    </a>

                </div>

                <%
                    }
                %>

            </div>

        </div>

    </body>

</html>
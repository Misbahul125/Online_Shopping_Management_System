<%-- 
    Document   : checkout
    Created on : 08-Jul-2022, 3:40:07 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Product"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Cart"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CartDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.DateHelper"%>
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

    String src = request.getParameter("source");

    if (src != null && src.matches("cart")) {
        CartDAO cartDAO = new CartDAO(FactoryProvider.getFactory());
        List<Cart> carts = cartDAO.getCartItemsForUser(user.getUserId());
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
        <title>OSMS - Checkout</title>

        <%@include file="components/checkout_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container">

            <!-- 1. Order summary -->
            <div class="row mt-5">

                <div class="card">
                    <div class="container cards-heading">
                        <h4>1. ORDER SUMMARY</h4>
                    </div>

                    <!-- ror for table -->
                    <div class="container mt-2">

                        <div class="row">

                            <div class="col-md-12">

                                <div>

                                    <%                                        //this if condition is to check from where the user is coming
                                        //and send that corresponing value to js function for furher processing
                                        //"1" single , "2" for multiple products
                                        if (src.matches("cart")) {
                                    %>

                                    <input type="hidden" id="source" value="2" />

                                    <%
                                    } else {
                                    %>

                                    <input type="hidden" id="source" value="1" />
                                    <input type="hidden" id="pid" value="<%= request.getParameter("productId")%>" />

                                    <%
                                        }
                                    %>

                                    <table class="table">

                                        <thead class="thead-light">

                                            <tr class="text-center">
                                                <th>Sl. No.</th>
                                                <th>Product</th>
                                                <th>Name</th>
                                                <th>Quantity</th>
                                                <th>Price</th>
                                                <th>Sub-Total</th>
                                            </tr>

                                        </thead>

                                        <%                                            //check if coming from cart page
                                            if (src.matches("cart")) {
                                                CartDAO cartDAO = new CartDAO(FactoryProvider.getFactory());
                                                List<Cart> carts = cartDAO.getCartItemsForUser(user.getUserId());
                                                for (Cart c : carts) {
                                                    ++tItem;
                                                    tUnit = tUnit + c.getQuantity();
                                                    tAmount = tAmount + c.getTotal();
                                        %>

                                        <tr class="text-center">

                                            <td class="align-middle">
                                                <%= tItem%>
                                            </td>

                                            <td class="align-middle">
                                                <img style="max-height: 100px; max-width: 100%; width: auto" src="pictures/products/<%= c.getProduct().getProductPic()%>" class="img-fluid" alt="cart img">
                                            </td>

                                            <td class="align-middle">
                                                <%= c.getProduct().getProductName()%>
                                            </td>

                                            <td class="align-middle">
                                                <%= c.getQuantity()%>
                                            </td>

                                            <td class="align-middle">
                                                <%= c.getProduct().getProductSellingPrice()%>
                                            </td>

                                            <td class="align-middle">
                                                <%= c.getTotal()%>
                                            </td>

                                        </tr>

                                        <%
                                            }

                                            netAmount = tAmount + 50;

                                        } //single product coming from client home page
                                        else {
                                            String pid = request.getParameter("productId");
                                            ProductDAO pdao = new ProductDAO(FactoryProvider.getFactory());
                                            Product p = pdao.getProductById(Integer.parseInt(pid));

                                            tUnit = 1;
                                            tAmount = p.getProductSellingPrice();
                                            netAmount = tAmount + 50;
                                        %>

                                        <tr class="text-center">

                                            <td class="align-middle">
                                                <%= ++tItem%>
                                            </td>

                                            <td class="align-middle">
                                                <img style="max-height: 100px; max-width: 100%; width: auto" src="pictures/products/<%= p.getProductPic()%>" class="img-fluid" alt="cart img">
                                            </td>

                                            <td class="align-middle">
                                                <%= p.getProductName()%>
                                            </td>

                                            <td class="align-middle">
                                                <%= 1%>
                                            </td>

                                            <td class="align-middle">
                                                <%= p.getProductSellingPrice()%>
                                            </td>

                                            <td class="align-middle">
                                                <%= p.getProductSellingPrice()%>
                                            </td>

                                        </tr>

                                        <%
                                            }
                                        %>

                                    </table>

                                    <hr>

                                    <div class="container">

                                        <div class="text-left">

                                            <h5 class="order-footer">Total Items                                     :   <%= tItem%></h5>

                                            <h5 class="order-footer">Total Quantity                                :   <%= tUnit%></h5>

                                            <h5 class="order-footer">Total Amount                                 :   &#8377; <%= tAmount%></h5>

                                            <h5 class="order-footer">Shipping Charge                            :   &#8377; 50</h5>

                                            <h5 class="order-footer" style="font-weight: bold">Net Amount Payable (Incl. GST)  :   &#8377; <%= netAmount%></h5>

                                            <input type="hidden" id="npa" value="<%= netAmount%>" />

                                        </div>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>

                </div>

            </div>


            <!-- 2. Delivery details -->

            <div class="row mt-5">

                <div class="card">
                    <div class="container cards-heading">
                        <h4>2. DELIVERY DETAILS</h4>
                    </div>

                    <div class="container">

                        <div class="text-left">

                            <h5 class="order-footer">Name                     :   <%= user.getUserName()%></h5>

                            <h5 class="order-footer">Email-ID                 :   <%= user.getUserEmail()%></h5>

                            <h5 class="order-footer">Mobile number      :   +91 <%= user.getUserPhone()%></h5>

                            <h5 class="order-footer">Shipping Address   :   <%= user.getUserAddress()%></h5>

                            <h5 class="order-footer">Delivery Between   :   <%= deliveryDate%></h5>
                            
                            <input type="hidden" id="dDate" value="<%= deliveryDate %>" />

<!--                            <div class="container text-center" style="margin-top: 5px; margin-bottom: 3px;">
                                <a href="#">
                                    <button class="btn btn-change-address">Change Address</button>
                                </a>
                            </div>-->

                        </div>

                    </div>

                </div>

            </div>


            <!-- 3. Payment method -->
            <div class="row mt-5">

                <div class="card">
                    <div class="container cards-heading">
                        <h4>3. PAYMENT METHOD</h4>
                    </div>

                    <div class="container">

                        <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio" id="radioIP" name="customRadioInline1" value="<%= Constants.OP.toString()%>" class="custom-control-input" checked="">
                            <label class="custom-control-label active" for="radioIP" style="font-weight: bold">Internet Payment</label>
                        </div>
                        <div class="custom-control custom-radio custom-control-inline">
                            <input type="radio" id="radioCOD" name="customRadioInline1" value="<%= Constants.COD.toString()%>" class="custom-control-input">
                            <label class="custom-control-label" for="radioCOD" style="font-weight: bold">Cash-On Delivery</label>
                        </div>

                    </div>

                </div>

            </div>


            <div class="row place-order">

                <div class="col-md-12">

                    <div class="container text-center">

                        <a href="#">
                            <button class="btn custom-bg text-white custom-btn" style="width: 50%;" data-toggle="modal" data-target="#orderModal">Place Order</button>
                        </a>

                    </div>

                </div>

            </div>


        </div>


        <!-- Modal -->
        <div class="modal fade" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="exampleModalLabel">Please Confirm</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>

                    <div class="modal-body">
                        <h5>Do you want to place the order?</h5>
                    </div>

                    <div class="modal-footer">
                        <a href="client_home.jsp">
                            <button type="button" class="btn btn-secondary">No</button>
                        </a>

                        <a href="#">
                            <button onclick="checkPaymentMethod()" type="button" class="btn btn-primary">Yes</button>
                        </a>

                        <script>
                            function checkPaymentMethod() {

                                $('#orderModal').modal('hide');

                                //if user chooses online payment
                                let npa = Number(document.getElementById("npa").value);
                                let dDate = document.getElementById("dDate").value;
                                if (document.getElementById("radioIP").checked) {

                                    //if single product send productid
                                    if (document.getElementById("source").value === "1") {
                                        let pid = Number(document.getElementById("pid").value);
                                        initiateOrder(pid, npa, 1, dDate);

                                    } else {
                                        initiateOrder(0, npa, 1, dDate);
                                    }

                                }
                                //if user chooses COD
                                else if (document.getElementById("radioCOD").checked) {

                                    //if single product send productid
                                    if (document.getElementById("source").value === "1") {
                                        let pid = Number(document.getElementById("pid").value);
                                        initiateOrder(pid, npa, 0, dDate);

                                    } else {
                                        initiateOrder(0, npa, 0, dDate);
                                    }

                                }

                            }

                        </script>

                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
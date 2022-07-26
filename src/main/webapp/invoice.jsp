<%-- 
    Document   : invoice
    Created on : 08-Jul-2022, 1:59:29 am
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.SentenceHelper"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Orders"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.OrdersDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.User"%>
<%
    User user = (User) session.getAttribute("current-user");
    if (user == null) {
        session.setAttribute("negativeMessage", "Please login to continue !!");
        response.sendRedirect("login.jsp");
        return;
    }

    String oid = request.getParameter("order_id");
    OrdersDAO ordersDAO = new OrdersDAO(FactoryProvider.getFactory());
    List<Orders> orders = ordersDAO.getSingleOrderByActualId(oid);
    String paynentMethod = orders.get(0).getPaymentMethod();
    if (paynentMethod.matches(Constants.COD.toString())) {
        paynentMethod = "Cash-On Delivery (COD)";
    } else {
        paynentMethod = "Internet Payment";
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <%@include file="components/invoice_css_js.jsp" %>

    </head>
    <body>
        <div class="my-5 page" size="A4">
            <div class="p-5">
                <section class="top-content bb d-flex justify-content-between">
                    <div class="logo">
                        <img src="pictures/invoice/logo.png" alt="" class="img-fluid">
                    </div>
                    <div class="top-left">
                        <div class="graphic-path">
                            <p>Invoice</p>
                        </div>
                        <div class="position-relative">
                            <p>Order ID : <span><%= oid%></span></p>
                        </div>
                    </div>
                </section>

                <section class="store-user mt-5">
                    <div class="col-10">
                        <div class="row bb pb-3">
                            <div class="col-7">
                                <p>Supplier,</p>
                                <h2>OSMS</h2>
                                <p class="address"> New Town, <br> Action Area - III, <br>Kolkata-700156 </p>
                                <!--                                <div class="txn mt-2">TXN: XXXXXXX</div>-->
                            </div>
                            <div class="col-5">
                                <p>Client,</p>
                                <h2><%= orders.get(0).getUser().getUserName()%></h2>
                                <p class="address"> <%= orders.get(0).getUser().getUserAddress()%> <br> Abington MA 2351, <br>Vestavia Hills AL </p>
                                <!--                                <div class="txn mt-2">TXN: XXXXXXX</div>-->
                            </div>
                        </div>
                        <div class="row extra-info pt-3">
                            <div class="col-7">
                                <p>Order Date : <span><%= orders.get(0).getOrderDate()%></span></p>
                                <p>Payment Method : <span><%= paynentMethod%></span></p>

                                <%
                                    if (orders.get(0).getPaymentMethod().matches(Constants.OP.toString())) {
                                %>

                                <p>Payment ID : <span><%= orders.get(0).getRazorpayOrderId()%></span></p>
                                <p>Transaction ID : <span><%= orders.get(0).getRazorpayPaymentId()%></span></p>

                                <%
                                    }
                                %>
                            </div>

                            <div class="col-5">
                                <p>Order Status : <span><%= orders.get(0).getOrderStatus() %></span></p>
                                <p>Delivery Between : <span><%= orders.get(0).getDeliveryDate()%></span></p>
                            </div>

                        </div>

                    </div>

                </section>

                <section class="product-area mt-4">
                    <table class="table table-hover">
                        <thead>
                            <tr class="head-row">
                                <td>Item Description</td>
                                <td>Price</td>
                                <td>Quantity</td>
                                <td>Total</td>
                            </tr>
                        </thead>
                        <tbody>

                            <%
                                SentenceHelper sh = new SentenceHelper();
                                int tItem = 0, tUnit = 0, sTotal = 0;
                                for (Orders o : orders) {
                                    ++tItem;
                                    tUnit = tUnit + o.getQuantity();
                                    sTotal = sTotal + o.getTotal();
                            %>

                            <tr>

                                <td>

                                    <div class="media">

                                        <img class="mr-3 img-fluid" src="pictures/products/<%= o.getProduct().getProductPic()%>" alt="Product 01">

                                        <div class="media-body">
                                            <p class="mt-0 title"><%= o.getProduct().getProductName()%></p>
                                            <%= sh.getWords(o.getProduct().getProductDescription(), 0)%>
                                        </div>

                                    </div>

                                </td>

                                <td><%= o.getProductPrice()%></td>
                                <td><%= o.getQuantity()%></td>
                                <td><%= o.getTotal()%></td>

                            </tr>

                            <%
                                }
                            %>

                        </tbody>

                    </table>

                </section>

                <section class="balance-info">
                    <div class="row">
                        <div class="col-8">
                            <p class="m-0 font-weight-bold"> Note: </p>
                            <p>The goods sold are intended for end user consumption and not for resale.</p>
                        </div>
                        <div class="col-4">
                            <table class="table border-0 table-hover">
                                <tr>

                                    <td>Total Items :</td>
                                    <td class="space">          <%= tItem%></td>

                                </tr>

                                <tr>

                                    <td>Total Units :</td>
                                    <td class="space">          <%= tUnit%></td>

                                </tr>

                                <tr>

                                    <td>Sub Total :</td>
                                    <td>&#8377; <%= sTotal%></td>

                                </tr>

                                <tr>

                                    <td>(Incl. of all taxes)</td>

                                </tr>

                                <tr>

                                    <td>Delivery charge :</td>
                                    <td class="space">    +&#8377; 50</td>

                                </tr>

                                <tfoot>

                                    <tr>

                                        <td>Net payable amt.</td>
                                        <td>&#8377; <%= orders.get(0).getNetAmount()%></td>
                                    </tr>


                                </tfoot>

                            </table>

                            <!-- Signature -->
                            <div class="col-12">

                                <img src="pictures/invoice/signature.png" class="img-fluid" alt="">
                                <p style="margin-left: 40px;" class="text-center"> Director Signature </p>

                            </div>

                        </div>

                    </div>

                </section>

                <!-- Cart BG -->
                <img src="pictures/invoice/cart.jpg" class="img-fluid cart-bg" alt="">

                <footer>

                    <hr>

                    <p class="m-0 text-center">
                        View THis Invoice Online At - <a href="invoice.jsp?order_id=<%= oid%>"> invoice.jsp </a>
                    </p>

                    <div class="social pt-3">

                        <span class="pr-2">
                            <i class="fas fa-mobile-alt"></i>
                            <span>033-12345678</span>
                        </span>

                        <span class="pr-2">
                            <i class="fas fa-envelope"></i>
                            <span>misbahulhaque2001@outlook.com</span>
                        </span>

                        <span class="pr-2">
                            <i class="fab fa-linkedin"></i>
                            <span>https://www.linkedin.com/in/misbahul-haque-5bb5b2202</span>
                        </span>

                        <span class="pr-2">
                            <i class="fab fa-github"></i>
                            <span>https://github.com/Misbahul125</span>
                        </span>

                    </div>

                </footer>

            </div>

        </div>

        <div class="container text-center mb-5">
            <button id="printBtn" onclick="window.print();" class="btn custom-bg text-white" type="button">Print Invoice</button>
        </div>

    </body>

</html>
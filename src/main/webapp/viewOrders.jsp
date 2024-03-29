<%-- 
    Document   : viewOrders
    Created on : 23-Jul-2022, 8:00:49 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Orders"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.OrdersDAO"%>
<%
    User user = (User) session.getAttribute("current-user");

    if (user == null) {
        session.setAttribute("negativeMessage", "Please login to continue !!");
        response.sendRedirect("login.jsp");
        return;
    }

    boolean isAdmin = false;

    OrdersDAO ordersDAO = new OrdersDAO(FactoryProvider.getFactory());
    List<Orders> orders = null;

    if (user.getUserType().matches(Constants.ADMIN_USER.toString())) {
        orders = ordersDAO.getAllOrders();
        isAdmin = true;
    } else {
        orders = ordersDAO.getOrdersOfAnUser(user.getUserId());
    }

    int ordersListSize = orders.size();

    int tItem = 0, tUnit = 0, subTotal = 0, i = 0, j = 0;
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - Orders</title>

        <%@include file="components/checkout_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container">

            <div class="container-fluid mt-3">
                <%@include file="components/positiveMessage.jsp" %>
                <%@include file="components/negativeMessage.jsp" %>
            </div>

            <div class="row mt-5">

                <%                    Orders o1 = null;
                    Orders o2 = null;
                    for (i = j; i < ordersListSize; i++) {

                        o1 = orders.get(i);

                        tItem = 0;
                        tUnit = 0;
                        subTotal = 0;

                %>

                <div class="card mt-4">
                    <div class="container cards-heading">

                        <div class="row">

                            <div class="col-md-6">

                                <h4>ORDER ID :- <%= o1.getActualOrderId()%></h4>

                            </div>

                            <%
                                if (isAdmin) {
                            %>


                            <div class="col-md-6 text-right">

                                <div class="btn-group">
                                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Update Status
                                    </button>
                                    <div class="dropdown-menu">

                                        <a class="dropdown-item" href="OrderUpdateServlet?orderId=<%= o1.getActualOrderId()%>&value=1">In - progress</a>
                                        <a class="dropdown-item" href="OrderUpdateServlet?orderId=<%= o1.getActualOrderId()%>&value=2">Delivered</a>
                                        <a class="dropdown-item" href="OrderUpdateServlet?orderId=<%= o1.getActualOrderId()%>&value=3">Cancelled</a>

                                    </div>
                                </div>

                            </div>

                            <%
                                }
                            %>

                        </div>

                    </div>

                    <!-- row for table -->
                    <div class="container mt-2">

                        <%
                            if (isAdmin) {
                        %>

                        <div class="row mt-2 m-1">

                            <div class="col-md-4">

                                <h6 class="order-user-details">Name : <%= o1.getUser().getUserName()%></h6>

                            </div>

                            <div class="col-md-4">

                                <h6 class="order-user-details">Email : <%= o1.getUser().getUserEmail()%></h6>

                            </div><!-- comment --><div class="col-md-4">

                                <h6 class="order-user-details">Phone : +91 <%= o1.getUser().getUserPhone()%></h6>

                            </div>

                        </div>

                        <%
                            }
                        %>

                        <div class="row">

                            <div class="col-md-12">

                                <div>

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

                                        <%
                                            for (j = i; j < ordersListSize; j++) {

                                                o2 = orders.get(j);

                                                if (o1.getActualOrderId().matches(o2.getActualOrderId())) {
                                                    ++tItem;
                                                    tUnit = tUnit + o2.getQuantity();
                                                    subTotal = subTotal + o2.getTotal();
                                        %>


                                        <tr class="text-center">

                                            <td class="align-middle">
                                                <%= tItem%>
                                            </td>

                                            <td class="align-middle">
                                                <img style="max-height: 100px; max-width: 100%; width: auto" src="pictures/products/<%= o2.getProduct().getProductPic()%>" class="img-fluid" alt="cart img">
                                            </td>

                                            <td class="align-middle">
                                                <%= o2.getProduct().getProductName()%>
                                            </td>

                                            <td class="align-middle">
                                                <%= o2.getQuantity()%>
                                            </td>

                                            <td class="align-middle">
                                                <%= o2.getProductPrice()%>
                                            </td>

                                            <td class="align-middle">
                                                <%= o2.getTotal()%>
                                            </td>

                                        </tr>

                                        <%
                                                } else {
                                                    o2 = orders.get(j - 1);
                                                    i = j - 1;//as i's value will incement inside the for header, so we are decresing it by 1 here
                                                    break;
                                                }
                                            }
                                        %>

                                    </table>

                                    <hr>

                                    <div class="container">

                                        <div class="row">

                                            <div class="col-md-4">

                                                <div class="text-left">

                                                    <h5 class="order-footer">Total Items                                     :   <%= tItem%></h5>

                                                    <h5 class="order-footer">Total Quantity                                :   <%= tUnit%></h5>

                                                    <h5 class="order-footer">Total Amount                                 :   &#8377; <%= subTotal%></h5>

                                                    <h5 class="order-footer">Shipping Charge                            :   &#8377; 50</h5>

                                                    <h5 class="order-footer" style="font-weight: bold">Net Amount Payable (Incl. GST)  :   &#8377; <%= o2.getNetAmount()%></h5>

                                                </div>

                                            </div>

                                            <div class="col-md-8">

                                                <div class="text-left">

                                                    <h5 class="order-footer">               Order Date             :   <%= o2.getOrderDate()%></h5>

                                                    <h5 class="order-footer">               Payment Method   :   <%= o2.getPaymentMethod()%></h5>

                                                    <%
                                                        if (o2.getRazorpayOrderId() != null && o2.getRazorpayPaymentId() != null) {
                                                    %>

                                                    <h5 class="order-footer">               Payment ID             :   <%= o2.getRazorpayOrderId()%></h5>

                                                    <h5 class="order-footer">               Transaction ID        :   <%= o2.getRazorpayPaymentId()%></h5>

                                                    <%
                                                    } else {
                                                    %>

                                                    <h5 class="order-footer">               Current Status        :   <%= o2.getOrderStatus()%></h5>

                                                    <h5 class="order-footer">               Delivery Between   :   <%= o2.getDeliveryDate()%></h5>

                                                    <%
                                                        }
                                                    %>

                                                </div>

                                            </div>

                                        </div>

                                        <%
                                            if (o2.getRazorpayOrderId() != null && o2.getRazorpayPaymentId() != null) {
                                        %>

                                        <div class="row mt-2">

                                            <div class="col-md-4">

                                                <div class="text-left">

                                                    <h5 class="order-footer">Current Status   :   <%= o2.getOrderStatus()%></h5>

                                                </div>

                                            </div>

                                            <div class="col-md-8">

                                                <div class="text-left">

                                                    <h5 class="order-footer">               Delivery Between   :   <%= o2.getDeliveryDate()%></h5>

                                                </div>

                                            </div>

                                        </div>

                                        <%
                                            }
                                        %>

                                    </div>

                                </div>

                            </div>

                        </div>

                    </div>

                    <div class="container my-2">
                        <a href="invoice.jsp?order_id=<%= o1.getActualOrderId()%>">
                            <button style="width: 100%;" class="btn custom-btn">View Invoice</button>
                        </a>
                    </div>

                </div>

                <%
                    }

                %>                        

            </div>

        </div>

    </body>

</html>
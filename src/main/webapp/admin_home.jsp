<%-- 
    Document   : admin_home
    Created on : 23-Jun-2022, 6:10:18 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.UtilityCountDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.UtilityCount"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.User"%>
<%
    User user = (User) session.getAttribute("current-user");
    if (user != null) {
        if (user.getUserType().matches(Constants.NORMAL_USER.toString())) {
            session.setAttribute("negativeMessage", "You are not a valid user to access this page.");
            session.removeAttribute("current-user");
            response.sendRedirect("login.jsp");
            return;
        }
    } else {
        session.setAttribute("negativeMessage", "Please login to continue !!");
        response.sendRedirect("login.jsp");
        return;
    }

    UtilityCountDAO ucDAO = new UtilityCountDAO(FactoryProvider.getFactory());
    UtilityCount uc = ucDAO.getAllUtilityCounts();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Home</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container admin mb-5">

            <!-- show message for adding category -->
            <div class="container-fluid mt-3">
                <%@include file="components/positiveMessage.jsp" %>
                <%@include file="components/negativeMessage.jsp" %>
            </div>

            <div class="row mt-4">

                <div class="col-md-4">

                    <div class="card">

                        <div class="card-body text-center">

                            <div class="container">

                                <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/team.png" alt="users_icon">

                            </div>

                            <h1><%= uc.getUserCount()%>k+</h1>
                            <h1 class="text-uppercase text-muted">Users</h1>

                        </div>

                    </div>

                </div>


                <div class="col-md-4">

                    <div class="card">

                        <div class="card-body text-center">

                            <div class="container">

                                <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/list.png" alt="users_icon">

                            </div>

                            <h1><%= uc.getCategoryCount()%>k+</h1>
                            <h1 class="text-uppercase text-muted">Categories</h1>

                        </div>

                    </div>

                </div>


                <div class="col-md-4">

                    <div class="card">

                        <div class="card-body text-center">

                            <div class="container">

                                <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/products.png" alt="users_icon">

                            </div>

                            <h1><%= uc.getProductCount()%>k+</h1>
                            <h1 class="text-uppercase text-muted">Products</h1>

                        </div>

                    </div>

                </div>

            </div>

            <div class="row mt-4">

                <div class="col-md-4">

                    <div class="card">

                        <a style="text-decoration: none;" href="admin_view_category.jsp">

                            <div class="card-body text-center">

                                <div class="container">

                                    <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/options.png" alt="users_icon">

                                </div>

                                <h1 class="text-uppercase text-muted">View Categories</h1>

                            </div>

                        </a>

                    </div>

                </div>

                <div class="col-md-4">

                    <div class="card">

                        <a style="text-decoration: none;" href="viewOrders.jsp">

                            <div class="card-body text-center">

                                <div class="container">

                                    <img style="max-width: 120px;" class="img-fluid rounded-circle" src="pictures/order.png" alt="users_icon">

                                </div>

                                <h1 style="color: black"><%= uc.getOrderCount()%>k+</h1>
                                <h1 class="text-uppercase text-muted">View Orders</h1>

                            </div>

                        </a>

                    </div>

                </div>

                <div class="col-md-4">

                    <div class="card" >

                        <a style="text-decoration: none;" href="admin_view_products.jsp">

                            <div class="card-body text-center">

                                <div class="container">

                                    <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/new-product.png" alt="users_icon">

                                </div>

                                <h1 class="text-uppercase text-muted">View Products</h1>

                            </div>

                        </a>

                    </div>

                </div>

            </div>

        </div>

    </body>

</html>
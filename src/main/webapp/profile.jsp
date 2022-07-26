<%-- 
    Document   : profile
    Created on : 22-Jul-2022, 5:19:40 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.User"%>
<%
    User user = (User) session.getAttribute("current-user");

    if (user == null) {
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

        <title>OSMS - My Account</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>
        <!--NAVBAR
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <a class="navbar-brand" href="#">O.S.M.S</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href='about_us'>About</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Services
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item" href="study_material">Study Meterials</a>
                            <a class="dropdown-item" href="exam">Online Tests</a>

                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link disabled" href="contactData">Contact</a>
                    </li>
                </ul>
                <form class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </form>
            </div>
        </nav>
         ------------------------------------------------------ -->


        <%@include file="components/navbar.jsp" %>

        <%            if (user.getUserType().matches(Constants.ADMIN_USER.toString())) {
        %>

        <section style="background-color: #eee;">

            <div class="container py-5">

                <div class="row">

                    <div class="col">

                        <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                            <ol class="breadcrumb mb-0">

                                <li class="breadcrumb-item active" aria-current="page">My Account</li>
                            </ol>
                        </nav>

                    </div>

                </div>

                <div class="row">

                    <div class="col-lg-4">

                        <div class="card mb-4">

                            <div class="card-body text-center">

                                <img src="pictures/user.png" alt="avatar"
                                     class="rounded-circle img-fluid" style="width: 150px;">
                                <h5 class="my-3"><span>Name : </span><%= user.getUserName()%></h5>

                                <p class="text-muted mb-1"><span>Email : </span><%= user.getUserEmail()%></p>
                                <p class="text-muted mb-4"><span>Mobile : +91 </span><%= user.getUserPhone()%></p>

                            </div>

                        </div>

                    </div>

                    <div class="col-lg-8">

                        <div class="card mb-4">

                            <div class="card-body">

                                <div class="row">
                                    <h5 class="container">Manage Category</h5>
                                </div>

                                <hr class="mt-1">

                                <a href ="admin_view_category.jsp">View all categories</a>
                            </div>

                        </div>

                        <div class="card mb-4">

                            <div class="card-body">

                                <div class="row">
                                    <h5 class="container">Manage Product</h5>
                                </div>

                                <hr class="mt-1">

                                <a href ="admin_view_products.jsp">View all products</a>
                            </div>

                        </div>

                        <div class="card mb-4">

                            <div class="card-body">

                                <div class="row">
                                    <h5 class="container">Manage Orders</h5>
                                </div>

                                <hr class="mt-1">

                                <a href ="viewOrders.jsp">Show all orders</a>
                                
                            </div>

                        </div>

                        <div class="card mb-4">

                            <div class="card-body">

                                <div class="row">
                                    <h5 class="container">Account Settings</h5>
                                </div>

                                <hr class="mt-1">

                                <a href ="#">Change password</a>
                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </section>

        <%
        } else {
        %>

        <section style="background-color: #eee;">

            <div class="container py-5">

                <div class="row">

                    <div class="col">

                        <nav aria-label="breadcrumb" class="bg-light rounded-3 p-3 mb-4">
                            <ol class="breadcrumb mb-0">

                                <li class="breadcrumb-item active" aria-current="page">My Account</li>
                            </ol>
                        </nav>

                    </div>

                </div>

                <div class="row">

                    <div class="col-lg-4">

                        <div class="card mb-4">

                            <div class="card-body text-center">

                                <img src="pictures/user.png" alt="avatar"
                                     class="rounded-circle img-fluid" style="width: 150px;">
                                <h5 class="my-3"><span>Name : </span><%= user.getUserName()%></h5>

                                <p class="text-muted mb-1"><span>Email : </span><%= user.getUserEmail()%></p>
                                <p class="text-muted mb-4"><span>Mobile : +91 </span><%= user.getUserPhone()%></p>

                            </div>

                        </div>

                    </div>

                    <div class="col-lg-8">

                        <div class="card mb-4">

                            <div class="card-body">

                                <div class="row">
                                    <h5 class="container">My Cart</h5>
                                </div>

                                <hr class="mt-1">

                                <a href ="cart.jsp">Show my cart items</a>
                            </div>

                        </div>

                        <div class="card mb-4">

                            <div class="card-body">

                                <div class="row">
                                    <h5 class="container">Orders</h5>
                                </div>

                                <hr class="mt-1">

                                <a href ="viewOrders.jsp">Show my orders</a>
                            </div>

                        </div>

                        <div class="card mb-4">

                            <div class="card-body">

                                <div class="row">
                                    <h5 class="container">Account Settings</h5>
                                </div>

                                <hr class="mt-1">

                                <a href ="LogoutServlet?source=changePassword">Change password</a>
                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </section>

        <%
            }
        %>

    </body>

</html>
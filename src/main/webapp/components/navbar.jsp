<%-- 
    Document   : navbar
    Created on : 21-Jun-2022, 7:01:43 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.User"%>
<%
    User user1 = (User) session.getAttribute("current-user");
%>

<nav class="navbar navbar-expand-lg navbar-dark custom-bg">
    <div class="container">

        <%
            if (user1 != null) {
        %>

        <a style="font-size: 30px;" class="navbar-brand" href="index.jsp">OSMS</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <%
            if (user1.getUserType().matches(Constants.ADMIN_USER.toString())) {
        %>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="admin_home.jsp">Home <span class="sr-only">(current)</span></a>
                </li>

                <li class="nav-item active dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Categories
                    </a>

                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="category.jsp?action=<%= Constants.ADD.toString()%>">
                            <i class="fas fa-plus"></i>
                            <span class="ml-1">Add Category</span>
                        </a>

                        <div class="dropdown-divider"></div>

                        <a class="dropdown-item" href="admin_view_category.jsp">
                            <i class="fas fa-pen"></i>
                            <span class="ml-1">Edit Category</span>
                        </a>

                    </div>
                </li>

                <li class="nav-item active dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Products
                    </a>

                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">

                        <a class="dropdown-item" href="product.jsp?action=<%= Constants.ADD.toString()%>">
                            <i class="fas fa-plus"></i>
                            <span class="ml-1">Add Product</span>
                        </a>

                        <div class="dropdown-divider"></div>

                        <a class="dropdown-item" href="admin_view_products.jsp">
                            <i class="fas fa-pen"></i>
                            <span class="ml-1">Edit Product</span>
                        </a>

                    </div>
                </li>

            </ul>

            <ul class="navbar-nav ml-auto">

                <li class="nav-item active">
                    <a class="nav-link" href="adminProfile.jsp"><%=user1.getUserName()%> </a>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="LogoutServlet">Log Out </a>
                </li>

            </ul>

            <form class="form-inline my-2 my-lg-0" action="adminSearchProduct.jsp" method="post">
                <input class="form-control mr-sm-2" name="search" type="search" placeholder="Search anything..." aria-label="Search">
                <button style="background-color: lightgrey; color: black" class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>

        </div>
        <%
        } else {
        %>


        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="client_home.jsp">Home <span class="sr-only">(current)</span></a>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="#">My Orders <span class="sr-only">(current)</span></a>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="profile.jsp"><%=user1.getUserName()%> </a>
                </li>

            </ul>

            <ul class="navbar-nav ml-auto">

                <li class="nav-item active">
                    <a class="nav-link" href="cart.jsp"> 
                        <i class="fas fa-cart-plus"></i> 
                        <span class="ml-0">My Cart ( <%= user1.getUserCartCount()%> )
                        </span> 
                    </a>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="LogoutServlet">Log Out </a>
                </li>

            </ul>

            <form class="form-inline my-2 my-lg-0" action="clientSearchProduct.jsp" method="post">
                <input class="form-control mr-sm-2" name="search" type="search" placeholder="Search anything..." aria-label="Search">
                <button style="background-color: lightgrey; color: black" class="btn my-2 my-sm-0" type="submit">Search</button>
            </form>

        </div>
        <%                    }
        %>



        <%
        } else {
        %>

        <a class="navbar-brand" href="index.jsp">OSMS</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="index.jsp">Home <span class="sr-only">(current)</span></a>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="login.jsp">Login </a>
                </li>

                <li class="nav-item active">
                    <a class="nav-link" href="signup1.jsp">Signup </a>
                </li>


            </ul>

            <form class="form-inline my-2 my-lg-0" action="search.jsp" method="post">
                <input class="form-control mr-sm-2" name="search" type="search" placeholder="Search anything..." aria-label="Search">
                <button style="background-color: lightgrey; color: black" class="btn  custom-bg text-white my-2 my-sm-0" type="submit">Search</button>
            </form>

        </div>


        <%                    }
        %>

    </div>
</nav>

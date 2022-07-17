<%-- 
    Document   : product
    Created on : 17-Jul-2022, 2:11:06 am
    Author     : Misbahul Haque
--%>

<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Category"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Product"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.ProductDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.User"%>
<%
    User user = (User) session.getAttribute("current-user");
    if (user != null) {
        if (user.getUserType().matches(Constants.NORMAL_USER.toString())) {
            session.setAttribute("negativeMessage", "You are not a valid user to access this page.");
            response.sendRedirect("login.jsp");
            return;
        }
    } else {
        session.setAttribute("negativeMessage", "Please login to continue !!");
        response.sendRedirect("login.jsp");
        return;
    }

    String action = request.getParameter("action");

    Product product = null;
    if (action.matches(Constants.EDIT.toString())) {
        ProductDAO productDAO = new ProductDAO(FactoryProvider.getFactory());
        product = productDAO.getProductById(Integer.parseInt(request.getParameter("productId")));

    }


%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - <%= action %> Product</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container-fluid">

            <div class="row mt-5">

                <div class="col-md-8 offset-md-2">

                    <div class="card">

                        <div class="card-header custom-bg text-white">

                            <h3>Please enter product details</h3>

                        </div>

                        <div class="card-body">

                            <%@include file="components/positiveMessage.jsp" %>
                            <%@include file="components/negativeMessage.jsp" %>

                            <!<!-- if edit product then pre fill the fields -->
                            <%                                if (action.matches(Constants.EDIT.toString())) {
                            %>

                            <form action="ProductOperationServlet" method="post" enctype="multipart/form-data">

                                <input type="hidden" name="productOperation" value="<%= Constants.EDIT.toString()%>" />
                                <input type="hidden" name="productId" value="<%= product.getProductId()%>" />

                                <div class="form-group">
                                    <label for="productImage">Image</label>
                                    <div class="container text-center">
                                        <img id="imgFile " style="max-height: 250px; max-width: 100%;" src="pictures/products/<%= product.getProductPic()%>"/>
                                    </div>
                                    <input value="<%= product.getProductPic()%>" type="file" onchange="readURL(this)" class="form-control mt-1" name="product_image" required/>
                                </div>

                                <div class="form-group">
                                    <label for="productName">Name</label>
                                    <input type="text" value="<%= product.getProductName()%>" class="form-control" name="product_name" placeholder="Enter product name" required/>
                                </div>

                                <div class="form-group">
                                    <label for="productDescription">Description</label>
                                    <textarea style="height: 150px" class="form-control" name="product_description" placeholder="Enter product description" required><%= product.getProductDescription()%></textarea>
                                </div>

                                <div class="form-group">
                                    <label for="productMarkedPrice">Marked Price</label>
                                    <input type="number" value="<%= product.getProductMarkedPrice()%>" id="mp" class="form-control" name="product_marked_price" placeholder="Enter product marked price" required/>
                                    <small id="emailHelp" class="form-text text-muted">*Please input Marked Price without decimal.</small>
                                </div>

                                <div class="form-group">
                                    <label for="productDiscount">Discount</label>
                                    <input type="number" value="<%= product.getProductDiscount()%>" id="disc" class="form-control" name="product_discount" placeholder="Enter product discount" required/>
                                    <small id="emailHelp" class="form-text text-muted">*Please input Discount without decimal & % sign.</small>
                                </div>

                                <div class="form-group">
                                    <label for="productSellingPrice">Selling Price</label>
                                    <input type="number" value="<%= product.getProductSellingPrice()%>" id="sp" class="form-control" name="product_selling_price" placeholder="Enter product selling price" readonly required/>
                                </div>

                                <script>
                                    $("#disc").keyup(function () {
                                        console.log("discount");
                                        var m = Number($("#mp").val());
                                        var d = Number($(this).val());
                                        var s = Math.trunc(((d / 100.0) * m));
                                        $("#sp").val(m - s);
                                    });
                                </script>

                                <div class="form-group">
                                    <label for="productQuantity">Quantity</label>
                                    <input type="number" value="<%= product.getProductQuantity()%>" class="form-control" name="product_quantity" placeholder="Enter product quantity" required/>
                                </div>

                                <%                                CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());
                                    List<Category> categories = categoryDAO.getCategories();
                                %>

                                <div class="form-group">
                                    <label for="product_category">Category</label>
                                    <select name="productCategories" class="form-control" id="product_category">

                                        <%
                                            for (Category category : categories) {
                                        %>

                                        <option value="<%= category.getCategoryId()%>"><%= category.getCategoryTitle()%></option>

                                        <%
                                            }
                                        %>

                                    </select>
                                </div>

                                <div class="container text-center">
                                    <button class="btn btn-outline-success">Update Changes</button>
                                    
                                    <a href="admin_view_products.jsp">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    </a>
                                </div>

                            </form>

                            <%
                            } else {
                            %>

                            <form action="ProductOperationServlet" method="post" enctype="multipart/form-data">

                                <input type="hidden" name="productOperation" value="<%= Constants.ADD.toString()%>" />

                                <div class="form-group">
                                    <label for="productName">Name</label>
                                    <input type="text" class="form-control" name="product_name" placeholder="Enter product name" required/>
                                </div>

                                <div class="form-group">
                                    <label for="productDescription">Description</label>
                                    <textarea style="height: 150px" class="form-control" name="product_description" placeholder="Enter product description" required></textarea>
                                </div>

                                <div class="form-group">
                                    <label for="productMarkedPrice">Marked Price</label>
                                    <input type="number" id="mp" class="form-control" name="product_marked_price" placeholder="Enter product marked price" required/>
                                    <small id="emailHelp" class="form-text text-muted">*Please input Marked Price without decimal.</small>
                                </div>

                                <div class="form-group">
                                    <label for="productDiscount">Discount</label>
                                    <input type="number" id="disc" class="form-control" name="product_discount" placeholder="Enter product discount" required/>
                                    <small id="emailHelp" class="form-text text-muted">*Please input Discount without decimal & % sign.</small>
                                </div>

                                <div class="form-group">
                                    <label for="productSellingPrice">Selling Price</label>
                                    <input type="number" id="sp" class="form-control" name="product_selling_price" placeholder="Enter product selling price" readonly required/>
                                </div>

                                <div class="form-group">
                                    <label for="productQuantity">Quantity</label>
                                    <input type="number" class="form-control" name="product_quantity" placeholder="Enter product quantity" required/>
                                </div>

                                <%                                CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());
                                    List<Category> categories = categoryDAO.getCategories();
                                %>

                                <div class="form-group">
                                    <label for="product_category">Category</label>
                                    <select name="productCategories" class="form-control" id="product_category">

                                        <%
                                            for (Category category : categories) {
                                        %>

                                        <option value="<%= category.getCategoryId()%>"><%= category.getCategoryTitle()%></option>

                                        <%
                                            }
                                        %>

                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="productImage">Image</label>
                                    <input type="file" onchange="readURL(this)" class="form-control" name="product_image" required/>
                                    <div class="container text-center">
                                        <img id="imgFile" style="max-height: 250px; max-width: 100%;"/>
                                    </div>
                                </div>

                                <div class="container text-center">
                                    <button class="btn btn-outline-success">Add Product</button>

                                    <a href="admin_view_products.jsp">
                                        <button type="button" class="btn btn-secondary">Cancel</button>
                                    </a>
                                </div>

                            </form>

                            <%
                                }
                            %>

                        </div>

                    </div>

                </div>

            </div>

        </div>

        <script>
            $("#disc").keyup(function () {
                console.log("discount");
                var m = Number($("#mp").val());
                var d = Number($(this).val());
                var s = Math.trunc(((d / 100.0) * m));
                $("#sp").val(m - s);
            });

            function readURL(input) {
                if (input.files && input.files[0]) {
                    let reader = new FileReader();
                    reader.onload = function (e) {
                        $('#imgFile').attr('src', e.target.result).width(100).height(150);
                    };
                    reader.readAsDataURL(input.files[0]);
                }
            }

        </script>

    </body>

</html>

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

        <div class="container admin">

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

                            <h1><%= uc.getUserCount() %>k+</h1>
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

                            <h1><%= uc.getCategoryCount() %>k+</h1>
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

                            <h1><%= uc.getProductCount() %>k+</h1>
                            <h1 class="text-uppercase text-muted">Products</h1>

                        </div>

                    </div>

                </div>

            </div>

            <div class="row mt-4">

                <div class="col-md-6">

                    <div class="card">

                        <div class="card-body text-center">

                            <div class="container">

                                <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/options.png" alt="users_icon">

                            </div>
                            
                            <a style="text-decoration: none;" href="admin_view_category.jsp"><h1 class="text-uppercase text-muted">View Categories</h1></a>

                        </div>

                    </div>

                </div>

                <div class="col-md-6">

                    <div class="card" >

                        <div class="card-body text-center">

                            <div class="container">

                                <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/new-product.png" alt="users_icon">

                            </div>

                            <a style="text-decoration: none;" href="admin_view_products.jsp"><h1 class="text-uppercase text-muted">View Products</h1></a>

                        </div>

                    </div>

                </div>

            </div>

        </div>


        <!--add category modal-->

        <!-- Modal -->
        <div class="modal fade" id="add-category-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header custom-bg text-white">
                        <h5 class="modal-title" id="exampleModalLabel">Category Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form action="ProductOperationServlet" method="post">

                            <input type="hidden" name="productOperation" value="add_category" />

                            <div class="form-group">
                                <label for="categoryTitle">Title</label>
                                <input type="text" class="form-control" name="category_title" placeholder="Enter category title" required/>
                            </div>

                            <div class="form-group">
                                <label for="categoryDescription">Description</label>
                                <textarea style="height: 150px" class="form-control" name="category_description" placeholder="Enter category description" required></textarea>
                            </div>

                            <div class="container text-center">
                                <button class="btn btn-outline-success">Add Category</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>

                        </form>

                    </div>

                </div>
            </div>
        </div>

        <!--end of add category modal-->



        <!--add product modal-->

        <!-- Modal -->
        <div class="modal fade" id="add-product-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header custom-bg text-white">
                        <h5 class="modal-title" id="exampleModalLabel">Product Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">

                        <form action="ProductOperationServlet" method="post" enctype="multipart/form-data">

                            <input type="hidden" name="productOperation" value="add_product" />

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
                                <input type="file" class="form-control" name="product_image" required/>
                            </div>

                            <div class="container text-center">
                                <button class="btn btn-outline-success">Add Product</button>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            </div>

                        </form>

                    </div>

                </div>
            </div>
        </div>

        <!--end of add product modal-->

    </body>
</html>
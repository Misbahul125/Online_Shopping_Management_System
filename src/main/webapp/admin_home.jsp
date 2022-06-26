<%-- 
    Document   : admin_home
    Created on : 23-Jun-2022, 6:10:18 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.User"%>
<%
    User user = (User) session.getAttribute("current-user");
    if (user != null) {
        System.out.println("1");
        if (user.getUserType().matches(Constants.NORMAL_USER.toString())) {
            session.setAttribute("negativeMessage", "You are not a valid user to access this page.");
            session.removeAttribute("current-user");
            response.sendRedirect("login.jsp");
            return;
        }
    } else {
        System.out.println("2");
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

                            <h1>2.4k+</h1>
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

                            <h1>100+</h1>
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

                            <h1>20k+</h1>
                            <h1 class="text-uppercase text-muted">Products</h1>

                        </div>

                    </div>

                </div>

            </div>

            <div class="row mt-4">

                <div class="col-md-6">

                    <div class="card" data-toggle="modal" data-target="#add-category-modal">

                        <div class="card-body text-center">

                            <div class="container">

                                <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/add.png" alt="users_icon">

                            </div>

                            <p class="mt-2">Click here to add new category</p>
                            <h1 class="text-uppercase text-muted">Add Category</h1>

                        </div>

                    </div>

                </div>

                <div class="col-md-6">

                    <div class="card" data-toggle="modal" data-target="#add-product-modal">

                        <div class="card-body text-center">

                            <div class="container">

                                <img style="max-width: 120px" class="img-fluid rounded-circle" src="pictures/new-product.png" alt="users_icon">

                            </div>

                            <p class="mt-2">Click here to add new Product</p>

                            <h1 class="text-uppercase text-muted">Add Product</h1>

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
                                <label for="productPrice">Price</label>
                                <input type="number" class="form-control" name="product_price" placeholder="Enter product price" required/>
                            </div>
                            
                            <div class="form-group">
                                <label for="productDiscount">Discount</label>
                                <input type="number" class="form-control" name="product_discount" placeholder="Enter product discount" required/>
                            </div>
                            
                            <div class="form-group">
                                <label for="productQuantity">Quantity</label>
                                <input type="number" class="form-control" name="product_quantity" placeholder="Enter product quantity" required/>
                            </div>
                            
                            <%
                                CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());
                                List<Category> categories = categoryDAO.getCategories();
                            %>
                            
                            <div class="form-group">
                                <label for="product_category">Category</label>
                                <select name="productCategories" class="form-control" id="product_category">
                                    
                                    <%
                                        for(Category category : categories) {
                                    %>
                                    
                                    <option value="<%= category.getCategoryId() %>"><%= category.getCategoryTitle()%></option>
                                    
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
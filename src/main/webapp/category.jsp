<%-- 
    Document   : category
    Created on : 17-Jul-2022, 2:11:22 am
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

    Category category = null;
    if (action.matches(Constants.EDIT.toString())) {
        CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());
        category = categoryDAO.getCategoryById(Integer.parseInt(request.getParameter("categoryId")));

    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - <%= action%> Category</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container-fluid">

            <div class="row mt-5">

                <div class="col-md-8 offset-md-2">

                    <div class="card">

                        <div class="card-header custom-bg text-white">

                            <h3>Please enter category details</h3>

                        </div>

                        <div class="card-body">

                            <%@include file="components/positiveMessage.jsp" %>
                            <%@include file="components/negativeMessage.jsp" %>

                            <!-- if edit product then pre fill the fields -->
                            <%                                if (action.matches(Constants.EDIT.toString())) {
                            %>

                            <form action="CategoryOperationServlet" method="post" enctype="multipart/form-data">

                                <input type="hidden" name="categoryOperation" value="<%= Constants.EDIT.toString()%>" />
                                <input type="hidden" name="categoryId" value="<%= category.getCategoryId() %>" />

                                <div class="form-group">
                                    <label for="categoryImage">Image</label>
                                    <div class="container text-center">
                                        <img id="imgFile " style="max-height: 250px; max-width: 100%;" src="pictures/categories/<%= category.getCategoryPic()%>"/>
                                    </div>
                                    <input value="<%= category.getCategoryPic()%>" type="file" onchange="readURL(this)" class="form-control mt-1" name="category_image" required/>
                                </div>

                                <div class="form-group">
                                    <label for="categoryTitle">Title</label>
                                    <input type="text" class="form-control" name="category_title" value="<%= category.getCategoryTitle()%>" placeholder="Enter category title" required/>
                                </div>

                                <div class="form-group">
                                    <label for="categoryDescription">Description</label>
                                    <textarea style="height: 150px" class="form-control" name="category_description" placeholder="Enter category description" required><%= category.getCategoryDescription()%></textarea>
                                </div>

                                <div class="container text-center">
                                    <button class="btn btn-outline-success">Update Changes</button>
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                </div>

                            </form>
                            <%
                            } else {
                            %>

                            <form action="CategoryOperationServlet" method="post" enctype="multipart/form-data">

                                <input type="hidden" name="categoryOperation" value="<%= Constants.ADD.toString()%>" />

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

                                <div class="form-group">
                                    <label for="categoryImage">Image</label>
                                    <input value="<%= category.getCategoryPic()%>" type="file" onchange="readURL(this)" class="form-control mt-1" name="category_image" required/>
                                    <div class="container text-center">
                                        <img id="imgFile " style="max-height: 250px; max-width: 100%;" src="pictures/categories/<%= category.getCategoryPic()%>"/>
                                    </div>
                                    
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
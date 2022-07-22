<%-- 
    Document   : admin_view_category
    Created on : 21-Jul-2022, 10:36:00 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.dao.CategoryDAO"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.SentenceHelper"%>
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
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - Admin</title>

        <%@include file="components/admin_css_js.jsp" %>
    </head>

    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container-fluid text-center">

            <div class="row mt-4 mx-2">

                <%  
                    CategoryDAO categoryDAO = new CategoryDAO(FactoryProvider.getFactory());
                    List<Category> categories = categoryDAO.getCategories();

                %>

                <!-- display categories in this column -->
                <div class="col-md-10 offset-md-3">

                    <div class="row mt-4">

                        <div class="col-md-10">

                            <%                                //check if a particular category has more than 0 products, then display specific message
                                if (session.getAttribute("negativeMessage") != null) {
                            %>
                            <%@include file="components/negativeMessage.jsp" %>
                            <%                                    return;
                                }
                            %>

                            <div class="card-columns">

                                <%
                                    SentenceHelper sh = new SentenceHelper();
                                    for (Category c : categories) {
                                %>

                                <div class="card product-card">

                                    <div class="container text-center">
                                        <img src="pictures/categories/<%= c.getCategoryPic() %>" style="max-height: 150px; max-width: 100%; width: auto" class="card-img-top m-2" alt="">
                                    </div>

                                    <div class="card-body">

                                        <h5 class="card-title"><%= c.getCategoryTitle() %></h5>

                                        <p class="card-text whole-text">
                                            <%= c.getCategoryDescription() %>
                                            </p>

                                    </div>

                                    <div class="card-footer text-center">

                                        <a href="category.jsp?action=<%= Constants.EDIT.toString()%>&categoryId=<%= c.getCategoryId() %>">
                                            <button style="width: 50%;" class="btn custom-bg text-white" value="<%= c.getCategoryId() %>">
                                                EDIT CATEGORY
                                            </button>
                                        </a>

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

        </div>

    </body>
    
</html>
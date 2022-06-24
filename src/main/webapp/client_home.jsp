<%-- 
    Document   : client_home
    Created on : 23-Jun-2022, 6:10:32 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page import="com.mycompany.onlineshoppingmanagementsystem.entities.User"%>
<%
    User user = (User) session.getAttribute("current-user");
    if (user != null) {
        if (user.getUserType().matches(Constants.ADMIN_USER.toString())) {
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
        <title>Client Home</title>
        
        <%@include file="../components/common_css_js.jsp" %>
        
    </head>
    <body>
        
        <%@include file="../components/navbar.jsp" %>
        
        <h1>Hello World!</h1>
    </body>
</html>

<%-- 
    Document   : admin_home
    Created on : 23-Jun-2022, 6:10:18 pm
    Author     : Misbahul Haque
--%>

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
        
        <%@include file="../components/common_css_js.jsp" %>
        
    </head>
    <body>
        
        <%@include file="../components/navbar.jsp" %>
        
        <h1>Hello Admin!</h1>
    </body>
</html>
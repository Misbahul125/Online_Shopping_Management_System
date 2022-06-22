<%-- 
    Document   : index
    Created on : 20-Jun-2022, 10:58:34 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.FactoryProvider"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - Home</title>

        <%@include file="components/common_css_js.jsp" %>
    </head>
    <body>
        
        <%@include file="components/navbar.jsp" %>
        
        <h1>Hello World!</h1>
        <h1>Creating session factory object</h1>

        <%
            out.println(FactoryProvider.getFactory());
        %>
    </body>
</html>

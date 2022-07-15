<%-- 
    Document   : signup
    Created on : 21-Jun-2022, 7:23:31 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.EmailVerification.TemporaryUser"%>
<%
    TemporaryUser tempUser = (TemporaryUser) session.getAttribute("temp-user");
    if (tempUser == null) {
        session.setAttribute("negativeMessage", "You are not permitted to access this page.");

        if (session.getAttribute("current-user") != null) {
            session.removeAttribute("current-user");
        }

        response.sendRedirect("login.jsp");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - Sign Up</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container-fluid">

            <div class="row mt-5">

                <div class="col-md-4 offset-md-4">

                    <div class="card">

                        <div class="card-header custom-bg text-white">

                            <h3>Please enter your details</h3>

                        </div>

                        <div class="card-body">

                            <%@include file="components/positiveMessage.jsp" %>
                            <%@include file="components/negativeMessage.jsp" %>

                            <form onsubmit="return validateSignup()" action="SignUpServlet" method="post">

                                <div class="form-group">
                                    <label style="font-weight: bold;" for="email">User Email</label>
                                    <input name="user_email" value="<%= tempUser.getEmail()%>" required type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email" readonly>
                                </div>

                                <div class="form-group">
                                    <label style="font-weight: bold;" for="name">User Name</label>
                                    <input name="user_name" required type="text" class="form-control" id="name" aria-describedby="emailHelp" placeholder="Enter name">
                                </div>

                                <div class="form-group">
                                    <label style="font-weight: bold;" for="password">User Password</label>
                                    <input name="user_password" required type="password" class="form-control" id="password" aria-describedby="emailHelp" placeholder="Enter password">
                                    <span id="message1" style="color: red;">*Password should be of atleast 8 characters</span>
                                </div> 

                                <div class="form-group">
                                    <label style="font-weight: bold;" for="phone">User phone number</label>
                                    <input name="user_phone" required type="number" class="form-control" id="phone" aria-describedby="emailHelp" placeholder="Enter phone number">
                                </div>

                                <div class="form-group">
                                    <label style="font-weight: bold;" for="name">User Address</label>
                                    <textarea name="user_address" required style="height: 80px;" class="form-control" id="address" aria-describedby="emailHelp" placeholder="Enter address"></textarea>
                                </div>

                                <div class="container text-center">
                                    <button type="submit" class="btn custom-bg text-white">Sign Up</button>
                                    <button type="reset" class="btn btn-outline-warning">Reset</button>
                                </div>

                            </form>

                            <div class="container mt-4">

                                <h6 class="login-footer">Already have an account?   <a href="login.jsp" class="mb-2">Click here to login</a></h6>

                            </div>

                        </div>
                    </div>

                </div>

            </div>

        </div>

    </body>
</html>

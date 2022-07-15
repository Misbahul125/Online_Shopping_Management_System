<%-- 
    Document   : signup1
    Created on : 14-Jul-2022, 9:26:35 pm
    Author     : Misbahul Haque
--%>

<%@page import="com.mycompany.onlineshoppingmanagementsystem.helper.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OSMS - Verification</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container">

            <div class="row">

                <div class ="col-md-6 offset-md-3">

                    <div class="card mt-3">

                        <div class="text-center card-header custom-bg text-white">

                            <h3>Please enter your email</h3>

                        </div>

                        <div class="card-body">

                            <%@include file="components/positiveMessage.jsp" %>
                            <%@include file="components/negativeMessage.jsp" %>

                            <form action="SendOTPServlet" method="post">
                                <div class="form-group">

                                    <label style="font-weight: bold;" class="heading" for="exampleInputEmail1">Email address</label>
                                    <input name="user_email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email" required>
                                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                                    
                                    <input type="hidden" name="source" value="<%= Constants.SIGNUP.toString() %>">

                                </div>

                                <div class="container text-center">
                                    <button type="submit" class="btn custom-bg text-white border-0">Send OTP</button>
                                    <button type="reset" class="btn btn-primary border-0">Reset</button>
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
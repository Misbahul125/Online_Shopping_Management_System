<%-- 
    Document   : forgot_password
    Created on : 09-Jul-2022, 4:07:57 pm
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
        <title>OSMS - Reset Password</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container">

            <div class="row">

                <div class ="col-md-6 offset-md-3">

                    <div class="card mt-3">

                        <div class="card-header custom-bg text-white">

                            <h3>Please enter your credentials</h3>

                        </div>

                        <div class="card-body">

                            <%@include file="components/positiveMessage.jsp" %>
                            <%@include file="components/negativeMessage.jsp" %>

                            <form onsubmit="return validatePassword()" action="ResetPasswordServlet" method="post">
                                <div class="form-group">
                                    <label style="font-weight: bold;" class="heading" for="exampleInputEmail1">Email address</label>
                                    <input name="user_email" value="<%= tempUser.getEmail()%>" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email" readonly required>
                                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                                </div>

                                <div class="form-group">
                                    <label style="font-weight: bold;" for="newPassword">New Password</label>
                                    <input name="user_new_password" type="password" class="form-control" id="newPassword" placeholder="Password" required>
                                    <span id="message1" style="color: red;">*Password should be of atleast 8 characters</span>
                                </div>

                                <div class="form-group">
                                    <label style="font-weight: bold;" for="confirmPassword">Confirm New Password</label>
                                    <input name="user_confirm_password" type="password" class="form-control" id="confirmPassword" placeholder="Password" required>
                                    <span id="message2" style="color: red;">*This should be same as the new password</span>
                                </div>
                                
                                <div class="container text-center">
                                    <button type="submit" class="btn btn-danger border-0">Reset Password</button>
                                </div>

                            </form>

                            <div class="container mt-4">

                                <h6 class="login-footer">Don't want to reset password?   <a href="login.jsp" class="mb-2">Click here to login</a></h6>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </body>
</html>
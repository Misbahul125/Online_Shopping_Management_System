<%-- 
    Document   : signup2
    Created on : 14-Jul-2022, 9:26:57 pm
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

                            <h3>Please enter your OTP</h3>

                        </div>

                        <div class="card-body">

                            <%@include file="components/positiveMessage.jsp" %>
                            <%@include file="components/negativeMessage.jsp" %>

                            <form action="VerifyOTPServlet" method="post">
                                <div class="form-group">

                                    <label style="font-weight: bold;" class="heading" for="exampleInputEmail1">OTP (One Time Password)</label>
                                    <input name="otp" type="number" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter OTP" required>
                                    <small id="emailHelp" class="form-text text-muted">Don't share your OTP with anyone else.</small>

                                </div>

                                <div class="container text-center">
                                    <button type="submit" class="btn custom-bg text-white border-0">Verify OTP</button>
                                    <button type="reset" class="btn btn-primary border-0">Reset</button>
                                </div>

                            </form>

                            <div class="container mt-4">

                                <h6 class="login-footer">Didn't receive an OTP?   <a href="ResendOtpServlet" class="mb-2">Click here to resend</a></h6>

                            </div>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </body>
</html>
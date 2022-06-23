<%-- 
    Document   : login
    Created on : 21-Jun-2022, 7:23:57 pm
    Author     : Misbahul Haque
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container">

            <div class="row">

                <div class ="col-md-6 offset-md-3">

                    <div class="card mt-3">

                        <div class="card-header custom-bg text-white">

                            <h3>Please enter your login credentials</h3>

                        </div>

                        <div class="card-body">
                            
                            <%@include file="components/positiveMessage.jsp" %>
                            <%@include file="components/negativeMessage.jsp" %>

                            <form action="LogInServlet" method="post">
                                <div class="form-group">
                                    <label for="exampleInputEmail1">Email address</label>
                                    <input name="user_email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
                                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                                </div>
                                
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Password</label>
                                    <input name="user_password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                                </div>
                                
                                <a href="signup.jsp" class="text-center d-block mb-2">If not registered, click here</a>
                                
                                <div class="container text-center">
                                    <button type="submit" class="btn btn-primary border-0">Submit</button>
                                    <button type="reset" class="btn btn-primary border-0">Reset</button>
                                </div>
                                
                            </form>

                        </div>

                    </div>

                </div>

            </div>

        </div>

    </body>
</html>

<%-- 
    Document   : signup
    Created on : 21-Jun-2022, 7:23:31 pm
    Author     : Misbahul Haque
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign Up</title>

        <%@include file="components/common_css_js.jsp" %>

    </head>
    <body>

        <%@include file="components/navbar.jsp" %>

        <div class="container-fluid">

            <div class="row mt-5">

                <div class="col-md-4 offset-md-4">

                    <div class="card">
                        
                        <%@include file="components/message.jsp" %>
                        
                        <div class="card-body px-5">

                            <h3 class="text-center my-3">Please Fill Your Details !!</h3>

                            <form action="SignUpServlet" method="post">

                                <div class="form-group">
                                    <label for="name">User Name</label>
                                    <input name="user_name" required type="text" class="form-control" id="name" aria-describedby="emailHelp" placeholder="Enter name">
                                </div>

                                <div class="form-group">
                                    <label for="email">User Email</label>
                                    <input name="user_email" required type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email">
                                </div>

                                <div class="form-group">
                                    <label for="password">User Password</label>
                                    <input name="user_password" required type="password" class="form-control" id="password" aria-describedby="emailHelp" placeholder="Enter password">
                                </div> 

                                <div class="form-group">
                                    <label for="phone">User phone number</label>
                                    <input name="user_phone" required type="number" class="form-control" id="phone" aria-describedby="emailHelp" placeholder="Enter phone number">
                                </div>

                                <div class="form-group">
                                    <label for="name">User Address</label>
                                    <textarea name="user_address" required style="height: 80px;" class="form-control" id="address" aria-describedby="emailHelp" placeholder="Enter address"></textarea>
                                </div>

                                <div class="container text-center">
                                    <button type="submit" class="btn btn-outline-success">Sign Up</button>
                                    <button type="reset" class="btn btn-outline-warning">Reset</button>
                                </div>

                            </form>

                        </div>
                    </div>

                </div>

            </div>

        </div>

    </body>
</html>

<%-- 
    Document   : message
    Created on : 22-Jun-2022, 3:41:19 am
    Author     : Misbahul Haque
--%>

<%
    String message = (String) session.getAttribute("message");

    if (message != null) {

        //out.println(message);
        if (message.equals("Signup successful !! ")) {
%>

<div class="alert alert-success alert-dismissible fade show" role="alert">
    <strong><%=message%></strong>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>

<%
} else {
    message = "Something went wong! Please try again later.";
%>
<div class="alert alert-warning alert-dismissible fade show" role="alert">
    <strong><%=message%></strong>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>

<%
        }
        session.removeAttribute("message");
    }
%>

<%-- 
    Document   : message
    Created on : 22-Jun-2022, 3:41:19 am
    Author     : Misbahul Haque
--%>

<%
    String m1 = (String) session.getAttribute("positiveMessage");

    if (m1 != null) {
%>

<div class="alert alert-success alert-dismissible fade show" role="alert">
    <strong><%=m1%></strong>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>

<%
        session.removeAttribute("positiveMessage");
    }
%>
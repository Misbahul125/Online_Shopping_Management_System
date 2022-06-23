<%-- 
    Document   : negativeMessage
    Created on : 23-Jun-2022, 5:06:41 pm
    Author     : Misbahul Haque
--%>

<%
    String m2 = (String) session.getAttribute("negativeMessage");

    if (m2 != null) {
%>

<div class="alert alert-warning alert-dismissible fade show" role="alert">
    <strong><%=m2%></strong>
    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
</div>

<%
        session.removeAttribute("negativeMessage");
    }
%>
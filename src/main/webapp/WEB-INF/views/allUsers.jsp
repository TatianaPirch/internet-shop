<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<jsp:useBean id="greeting" scope="request" type="java.lang.String"/>
<%--
  Created by IntelliJ IDEA.
  User: tyty
  Date: 16.09.2019
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
Hello, ${greeting}! Welcome to the all users page

Users:
<table border="1" bgcolor="#ff8c00" >
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}" />
            </td>
            <td>
                <c:out value="${user.name}" />
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

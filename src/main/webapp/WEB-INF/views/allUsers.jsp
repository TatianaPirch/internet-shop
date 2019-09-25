<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<jsp:useBean id="users" scope="request" type="java.util.List<mate.academy.internetshop.model.User>"/>
<jsp:useBean id="greeting" scope="request" type="java.lang.String"/>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
</head>
<body>
Hello, ${greeting}! Welcome to the all users page!
<p>Users</p>
<table border="1" bgcolor="#bdb76b">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Login</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}" />
            </td>
            <td>
                <c:out value="${user.name}" />
            </td>
            <td>
                <c:out value="${user.surname}" />
            </td>
            <td>
                <c:out value="${user.login}" />
            </td>
            <td>
                <a href="/internet_shop_war_exploded/servlet/deleteUser?user_id=${user.id}">DELETE</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/internet_shop_war_exploded/registration">RETURN TO REGISTRATION</a>
<hr>
<a href="/internet_shop_war_exploded/logout">LOG OUT</a>
</body>
</html>

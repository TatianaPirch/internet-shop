<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="items" scope="request" type="java.util.List<mate.academy.internetshop.model.Item>"/>
<jsp:useBean id="user" scope="request" type="mate.academy.internetshop.model.User"/>

<html>
<head>
    <title>Bucket</title>
</head>
<body>
<p>Bucket of ${user.login} with ID ${user.id} </p>
<hr>
<a href="/internet_shop_war_exploded/servlet/completeOrder">COMPLETE ORDER</a>
<hr>
<a href="/internet_shop_war_exploded/servlet/getAllItems">GO TO ITEM LIST</a>
<table border="1" bgcolor="#f0e68c">
    <tr>
        <th>ID</th>
        <th>Item</th>
        <th>Price</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>
                <c:out value="${item.id}" />
            </td>
            <td>
                <c:out value="${item.name}" />
            </td>
            <td>
                <c:out value="${item.price}" />
            </td>
            <td>
                <a href="/internet_shop_war_exploded/servlet/deleteBucketItem?item_id=${item.id}">REMOVE ITEM FROM BUCKET</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/internet_shop_war_exploded/logout">LOG OUT</a>
</body>
</html>

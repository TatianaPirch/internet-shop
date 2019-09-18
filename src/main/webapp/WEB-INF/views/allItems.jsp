<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Items</title>
</head>
<body>
<p>Items</p>
<hr>
<a href="/internet_shop_war_exploded/servlet/getAllBucketItems?user_id=${user_id}&item_id=${item.id}&bucket_id=${bucket_id}">GO TO BUCKET</a>
<table border="1" bgcolor="#afeeee">
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
                <a href="/internet_shop_war_exploded/servlet/addBucketItem?item_id=${item.id}">ADD ITEM TO BUCKET</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

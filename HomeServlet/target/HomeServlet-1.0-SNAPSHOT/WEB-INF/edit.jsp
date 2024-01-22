<%--
  Created by IntelliJ IDEA.
  User: mahmoud
  Date: 2024-01-19
  Time: 13:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Page</title>
</head>
<body align="center">
<h2>Edit Page</h2>

<c:if test="${not empty message}">
    <p>${message}</p>
</c:if>

<jsp:include page="/edit" />
</body>
</html>

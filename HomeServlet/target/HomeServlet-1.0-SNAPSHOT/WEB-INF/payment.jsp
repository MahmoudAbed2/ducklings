<%--
  Created by IntelliJ IDEA.
  User: mahmoud
  Date: 2024-01-19
  Time: 13:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<html>
<head>
    <title>Payment Page</title>
</head>
<body align="center">
<h2>Payment Page</h2>
<form action='payment' method='post'>
    Title: <input type='text' name='title'><br>
    Date: <input type='text' name='date' value='<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>' readonly><br>
    Description: <input type='text' name='description'><br>
    Category: <input type='text' name='category'><br>
    Amount: <input type='text' name='amount'><br>
    <input type='submit' value='Submit Payment'>
</form>

<c:if test="${not empty message}">
    <p>${message}</p>
</c:if>
</body>
</html>

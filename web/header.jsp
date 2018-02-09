<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/headerStyle.css">
</head>
<body>
    <div class="header">
        <div class="title">
            ToDo
        </div>
        <div class="authentication">
            <c:if test="${user != null}">
                ${user.login}, <a href="logOut">logout</a>
            </c:if>
        </div>
    </div>
</body>
</html>

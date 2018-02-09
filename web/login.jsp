<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="js/authScript.js"></script>
    <script src="js/sha256.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/authStyle.css"/>
</head>
<body>
    <c:import url="header.jsp"/>
    <div id="login-block" class="form-class">
        <div class="form-header">Login</div>
        <form name="login-form" onsubmit="return loginSubmit();">
            <table>
                <tr>
                    <td>
                        <div>Login</div>
                    </td>
                    <td>
                        <input class="field" type="text" id="login-login">
                    </td>
                </tr>
                <tr>
                    <td>
                        <div>Password</div>
                    </td>
                    <td>
                        <input class="field" type="password" id="login-password">
                    </td>
                </tr>
            </table>
            <input class="button" type="submit">
            <div class="qu">Don't you have an account? <a href="signup.jsp">Sign Up</a>
            </div>
        </form>
    </div>
    <c:if test="${info != null}">
        <div class="info-block">
                ${info}
        </div>
    </c:if>
</body>
</html>
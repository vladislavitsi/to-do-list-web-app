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
    <div id="signup-block" class="form-class">
        <div class="form-header">Sign up</div>
        <form name="signup-form" onsubmit="return signupSubmit();">
            <table>
                <tr>
                    <td>
                        <div>Login</div>
                    </td>
                    <td>
                        <input class="field" type="text" id="signup-login">
                    </td>
                </tr>
                <tr>
                    <td>
                        <div>Password</div>
                    </td>
                    <td>
                        <input class="field" type="text" id="signup-password">
                    </td>
                </tr>
                <tr>
                    <td>
                        <div>Repeat password</div>
                    </td>
                    <td>
                        <input class="field" type="text" id="signup-password2">
                    </td>
                </tr>
            </table>
            <input type="submit" class="button">
            <div class="qu">Already signed up? <a href="login.jsp">Log in</a></div>
        </form>
    </div>
    <c:if test="${info != null}">
        <div class="info-block">
                ${info}
        </div>
    </c:if>
    </body>
</html>

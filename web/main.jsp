<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ToDo</title>
    <script type="text/javascript" src="js/utils.js"></script>
    <script src="js/app.js"></script>
</head>
<body>
    <c:import url="header.jsp"/>
    <c:import url="static/appContent.html"/>
    <c:import url="static/modalTask.html"/>
    <c:import url="static/modalFile.html"/>
    <script>
        window.initialize();
    </script>
</body>
</html>

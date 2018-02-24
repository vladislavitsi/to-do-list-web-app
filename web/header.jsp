<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="css/headerStyle.css">
<div class="header">
    <div class="title">
        ToDo
    </div>
    <div class="menu">
        <c:if test="${user != null}">
            <a href="JavaScript:window.tabs.goToTab(1)">
                <img class="menu-button hamb" src="icon/hamb.png">
                <img class="menu-button hamb is-active" src="icon/hamb_selected.png">
            </a>
            <a href="JavaScript:window.tabs.goToTab(2)">
                <img class="menu-button done is-active" src="icon/done.png">
                <img class="menu-button done" src="icon/done_selected.png">
            </a>
            <a href="JavaScript:window.tabs.goToTab(3)">
                <img class="menu-button bin is-active" src="icon/bin.png">
                <img class="menu-button bin" src="icon/bin_selected.png">
            </a>
        </c:if>
    </div>
    <div class="authentication">
        <c:if test="${user != null}">
            ${user.login}, <a href="logOut">logout</a>
            <div id="userId" hidden>${user.id}</div>
        </c:if>
    </div>
</div>
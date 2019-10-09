<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("UTF-8");%>
<html>
<head>
    <title>User Management Application</title>
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=IBM+Plex+Sans:300&display=swap" rel="stylesheet">
    <style>
        <%@include file="general.css"%>
        <%@include file="form.css"%>
        #bottom_link {
            width: 160px;
            float: left;
            border-radius: 4px;
        }
    </style>
</head>
<body>

<c:set var="btm_text" value="Back to Login"/>
<%--<c:if test="${loggedUser.role != null}">--%>
<%--    <c:set var="btm_text" value="User info"/>--%>
<%--</c:if>--%>
<%--<c:if test="${loggedUser.role.equals('admin')}">--%>
<%--    <c:set var="btm_text" value="User list"/>--%>
<%--</c:if>--%>

<header>
    <div class="content">
        <h1 id="header_text">${empty user.id ? "Register new user" : "Edit user"}</h1>
        <%--        <c:if test="${loggedUser.id != null}">--%>
        <%--            <a id="logout_link" class="link_button" href="logout">Logout</a>--%>
        <%--        </c:if>--%>
    </div>
</header>

<div class="content">
    <form action=${empty user.id ? "new" : "edit"} method="POST">
        <c:if test="${!empty user.id}">
            <input type="hidden" name="id" value="${user.id}"/>
        </c:if>
        <input type="hidden" name="role" value="${user.role}"/>
        <label>E-mail
            <input type="text" name="email" size="60"
                   value="${user.email}">
        </label>
        <label>Password
            <input type="text" name="password" size="60"
                   value="${user.password}">
        </label>
        <label>First name
            <input type="text" name="firstName" size="60"
                   value="${user.firstName}">
        </label>
        <label>Last name
            <input type="text" name="lastName" size="60"
                   value="${user.lastName}">
        </label>
        <label>Country
            <input type="text" name="country" size="60"
                   value="${user.country}">
        </label>
        <input type="submit" value="save">
    </form>
    <a id="bottom_link" class="link_button" href="${pageContext.request.contextPath}">${btm_text}</a>
</div>
</body>
</html>

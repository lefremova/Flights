<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="i18n.Messages" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script type="text/javascript" src="js/script.js"></script>
        <title><fmt:message key="auth.title" /></title>
    </head>

    <body>

        <form name="form" class="center" action="AuthServlet" method="POST" onsubmit="return LoginPasswordIsEmpty(form)">
            <fmt:message key="uname" /> : &nbsp; <input type="text" name="uname"> <br> <br>
            <fmt:message key="upass" /> : &nbsp; <input type="password" name="upass"> <br> <br>
            <input class="enter" type="submit" value=<fmt:message key="enter" /> > <br> <br>
        </form>

        <c:if test="${not empty requestScope.message}">
            <div class="error">${requestScope.message}</div>
        </c:if>

    </body>

</html>

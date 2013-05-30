<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setBundle basename="i18n.Messages" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="../../css/style.css">
        <title><fmt:message key="import.title" /></title>
    </head>

    <body>

        <div class="center"><a href="../../LogOutServlet"><fmt:message key="logout" /></a></div> <br> <br>
        <div class="center"><a href="../MainPageServlet"><fmt:message key="main" /></a></div>

        <form class="center" action="XMLImportServlet" method="POST" enctype="multipart/form-data">
            <input type="file" name="xml" > <br> <br>
            <input class="enter" type="submit" value=<fmt:message key="submit" />>
        </form>

        <c:if test="${not empty requestScope.successMessage}">
            <div class="success">${requestScope.successMessage}</div>
        </c:if>

    </body>
</html>

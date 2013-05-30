<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setBundle basename="i18n.Messages" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <title><fmt:message key="main.title" /></title>
    </head>

    <body>
        <div class="center"><a href="../LogOutServlet"><fmt:message key="logout" /></a></div>

        <form class="center" action="MainPageServlet" method="POST">
            <center>
            <table class="tableFlights">
                <tr>
                    <td><fmt:message key="flight" /></td>
                    <td><fmt:message key="numTick" /></td>
                    <td><fmt:message key="date" /></td>
                    <td><fmt:message key="book" /></td>
                </tr>
                <c:forEach var="flight" items="${requestScope.data}">
                    <tr>
                        <td>${flight.number}</td>
                        <td>${flight.ticketsCount}</td>
                        <td><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${flight.date}" /></td>
                        <td> <input type="checkbox" name="chBox" value="${flight.id}"> </td>
                    </tr>
                </c:forEach>
            </table>
            <center>
            <p>
                <input class="enter" type="submit" value=<fmt:message key="submit" />>
            </p>
        </form>

        <div class="center"><a href="admin/import.jsp"><fmt:message key="admin" /></a></div>

        <c:if test="${not empty requestScope.successMessage}">
            <div class="success">${requestScope.successMessage}</div>
        </c:if>

    </body>
</html>

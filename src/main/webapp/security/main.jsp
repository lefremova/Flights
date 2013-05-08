<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="../css/style.css">
        <title>Main table</title> 
    </head>

    <body>
        <div class="center"><a href="../LogOutServlet">Log out</a></div>

        <form class="center" action="MainPageServlet" method="POST">
            <table class="tableFlights">
                <tr>
                    <td>Flight</td>
                    <td>Number of tickets</td>
                    <td>Date</td>
                    <td>Book a flight</td>
                </tr>
                <c:forEach var="flight" items="${requestScope.data}">
                    <tr>
                        <td>${flight.number}</td>
                        <td>${flight.ticketsCount}</td>
                        <td>${flight.date}</td>
                        <td> <input type="checkbox" name="chBox" value="${flight.id}"> </td>
                    </tr>
                </c:forEach>
            </table>
            <p>
                <input class="enter" type="submit" value="Enter">
            </p>
        </form>

        <div class="center"><a href="admin/import.jsp">admin</a></div>

        <c:if test="${not empty requestScope.successMessage}">
            <div class="success">${requestScope.successMessage}</div>
        </c:if>

    </body>
</html>

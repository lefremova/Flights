<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>Main table</title> 
    </head>

    <body>
        <table>
            <c:forEach var="flight" items="${requestScope.data}">
                <tr>
                    <td>${flight.flight_number}</td>
                    <td>${flight.tickets_count}</td>
                    <td>${flight.date}</td>
                </tr>
            </c:forEach>
        </table>
    </body>

</html>
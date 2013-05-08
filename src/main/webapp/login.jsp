<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>Authorisation form</title> 
    </head>
    <body>

    <form class="center" action="AuthServlet" method="POST">
        Enter username: &nbsp; <input type="text" name="uname"> <br> <br>
        Enter password: &nbsp; <input type="password" name="upass"> <br> <br>
        <input class="enter" type="submit" value="Enter"> <br> <br>
    </form>

    <c:if test="${not empty requestScope.message}">
        <div class="error">${requestScope.message}</div>
    </c:if>

    </body>
</html>
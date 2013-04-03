<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <title>Authorisation form</title> 
    </head>
    <body>

    <form action="Auth" method="POST">
        Enter username: &nbsp; <input type="text" name="uname"> <br> <br>
        Enter password: &nbsp; <input type="password" name="upass" size=21> <br> <br>
        <input type="submit" value="Enter"> <br> <br>

        <c:if test="${not empty requestScope.message}">
            <div style="color:red;">${requestScope.message}</div>
        </c:if>

    </form>

    <%-- <div style="error">${message}</div> --%>

    </body>
</html>
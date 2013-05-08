<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="../../css/style.css">
    <title>Adding to MySQL</title>
    </head>

    <body>

        <div class="center"><a href="../../LogOutServlet">Log out</a></div> <br> <br>

        <form class="center" action="XMLImportServlet" method="POST" enctype="multipart/form-data">
            <input type="file" name="xml"> <br> <br>
            <input class="enter" type="submit" value="Enter">
        </form>

    </body>
</html>

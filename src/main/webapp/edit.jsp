<%-- 
    Document   : edit
    Created on : Feb 27, 2016, 12:57:25 PM
    Author     : tjcel
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Author</title>
        <link rel="stylesheet" 
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        <h2 align="center">Edit Author Here</h2>
        <form align ="center" method="post" action="AuthorController?taskType=save">
            <input hidden="true" type="text" readonly name = authorId value="${author.authorId}" />
            <input type="text" name="authorName" value="${author.authorName}"/>
            <input hidden="true" type="date" readonly name="dateAdded" value="${author.dateAdded}" />
            <input class="btn btn-success" type="submit" value="Save" />
            <input class="btn btn-danger" type="button" value="Cancel" onclick="location.href='AuthorController?taskType=cancel'"/>
        </form>
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>

<%-- 
    Document   : add
    Created on : Feb 24, 2016, 5:23:12 PM
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
        <title>Author List</title>
    </head>
    <body>
        <h1>Author List</h1>
        
        <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add Author</title>
    </head>
    <body>
        <form method="post" action="AuthorController?taskType=new">
            <label>Enter new Author Name</label>
            <input type="text" name="authorName" />
            <input type="submit" value="save" />
            <input type="button" value="cancel" onclick="location.href='AuthorController?taskType=cancel'"/>
        </form>
    </body>
</html>
    </body>
</html>
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
        <title>Add Author</title>
        <link rel="stylesheet" 
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
        
    <body>
    <center><h2>Add Author Here</h2></center>
    <center><form method="post" action=<%= response.encodeURL("AuthorController?taskType=new")%>>
            <label>Enter New Author Name</label>
            <input type="text" name="authorName" />
            <input class="btn btn-success" type="submit" value="Save" />
            <input class="btn btn-danger"type="button" value="Cancel" onclick="location.href='AuthorController?taskType=cancel'"/>
        </form></center>
         <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>
    
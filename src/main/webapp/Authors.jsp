<%-- 
    Document   : Authors
    Created on : Feb 8, 2016, 3:35:56 PM
    Author     : Thomas
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="edu.wctc.tjd.bookwebapp.model.Author"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Authors</title>
        <link rel="stylesheet" 
              href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        <h1>Here are some authors</h1>
        <div class ="row" >
            <div class="col-sm-8">

            <table border = "1" class="table table-hover" >
                <th>Author Id</th>
                <th>Author Name</th>
                <th>Date Added</th>

                <c:forEach items ="${requestScope.authorList}" var="author">
                    <tr>
                        <td>${author.authorId}</td>
                        <td>${author.authorName}</td>
                        <td>${author.dateAdded}</td>
                    </tr>

                </c:forEach>
            </table>
            
        </div>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>

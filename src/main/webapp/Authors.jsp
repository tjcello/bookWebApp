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
    <style>
        body{
            background-color:yellow;
        }
    </style>
    <body>
        <h1>Here are some authors</h1>
        <%--<div class ="row" >
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
        --%>

        
            <input type="submit" value="Add" name="add" onclick ="location.href='AuthorController?taskType=add'" />
            
            <br><br>
            <table width="500" border="2" cellspacing="0" cellpadding="4" padding="10">
                <tr style="background-color: blue;color:white;">
                    
                    <th align="left" class="tableHead">Author Name</th>
                    <th align="right" class="tableHead">Date Added</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach var="a" items="${authors}" varStatus="rowCount">

                   
                    <td align="left">${a.authorName}</td>
                    <td align="right">
                        <fmt:formatDate pattern="M/d/yyyy" value="${a.dateAdded}"></fmt:formatDate>
                        </td>
                        
                        <td><input type="submit" value="Edit" name="edit" onclick ="location.href='AuthorController?taskType=edit&id=${a.authorId}'" />&nbsp;</td>
                        <td><input type="submit" value="Delete" name="delete" onclick ="location.href='AuthorController?taskType=deleteAuthor&id=${a.authorId}'" />&nbsp;</td>
                        
                        
                        </tr>
                </c:forEach>
            </table>
            <br>
            
        
        <p>Return to <a href="index.html">Home Page</a></p>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>

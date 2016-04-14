<%-- 
    Document   : index
    Created on : Apr 13, 2016, 6:17:33 PM
    Author     : tjcel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
        <title>Authors and Books</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <style>
            #taskMenu{
                width:2%;
               
                margin-left: 44.7%;
                
            }
            #taskMenu2{
                width:2%;
               
                margin-left: 44.7%;
                
            }
        </style>
         
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    </head>
    <body>
        <div>
            <h2 align="center">Select a Task</h2>


            <form method="POST" action="<%= response.encodeURL("AuthorController?taskType=viewAuthor")%>">
                    <button type="submit" name="authors" class="btn btn-primary">View Authors</button>
                </form>
                    <form method="POST" action="<%= response.encodeURL("BookController?taskType=viewBooks")%>">
                    <button type="submit" name="books" class="btn btn-primary">View Books</button>
                </form>
        
            
        </div>
        
        
        <script src="https://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </body>
</html>
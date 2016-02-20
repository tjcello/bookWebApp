package edu.wctc.tjd.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthorService {
   private AuthorDaoStrategy dao = new AuthorDao();
   
   public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException{
       return dao.deleteAuthorById(id);
   }
   
   public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
       return dao.getAuthorList();
       
   }
   
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
    }
}


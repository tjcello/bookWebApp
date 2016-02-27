package edu.wctc.tjd.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

@SessionScoped

public class AuthorService implements Serializable {
    
    @Inject
   private AuthorDaoStrategy dao;
    
    public AuthorService(){
        
    }
   
   public void deleteAuthorById(String authorId) throws ClassNotFoundException, SQLException{
       dao.deleteAuthorById((Integer.parseInt(authorId)));
   }
   
   public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
       return dao.getAuthorList();
       
   }
   
   public void saveOrUpdateAuthor(String authorId, String authorName) throws ClassNotFoundException, SQLException{
        Integer id = null;
        if (authorId == null || authorId.isEmpty()) {
            id = null;
        } else {
            id = Integer.parseInt(authorId);
        }
        dao.saveAuthor(id, authorName);
    }
   
   public Author getAuthorById(String authorId) throws ClassNotFoundException, SQLException {
        return dao.getAuthorById(Integer.parseInt(authorId));
    }

    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
   
   
   
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
    }
    
}


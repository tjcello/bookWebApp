/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.tjd.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;


/**
 *
 * @author tjcel
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;

    public boolean deleteAuthorById(Integer authorId) throws ClassNotFoundException, SQLException;
    public void initDao(String driver, String url, String user, String pswd);

    boolean saveAuthor(Integer id, String authorName) throws ClassNotFoundException, SQLException;
    public Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException;
    
    public abstract void initDao(DataSource ds) throws Exception;
   

}

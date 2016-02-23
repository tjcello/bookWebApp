/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.tjd.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;

/**
 *
 * @author tjcel
 */
@Alternative
@SessionScoped
public class MockAuthorDao implements AuthorDaoStrategy, Serializable {

    private DBStrategy db;
    Date date = new Date();

    List<Author> authorList = new ArrayList<Author>();

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        authorList.add(new Author(1, "Your Mama", date));
        authorList.add(new Author(2, "Your Daddy", date));
        authorList.add(new Author(3, "Your Sister", date));
        return authorList;
    }
    
    @Override
    public int deleteAuthorById(Object id){
        return 1;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    

   

   

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }
    
    

}


package edu.wctc.tjd.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author tjcel
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable {
    @Inject
    private DBStrategy db;
    
    //private DBStrategy db = new MySqlDBStrategy();
    private String driver;
    private String url;
    private String user;
    private String pswd ;
    
    public AuthorDao(){
        
    }
    
      @Override
    public void initDao(String driver, String url, String user, String pswd) {
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPswd(pswd);
    }
    
   
    
   
    
    @Override
    public boolean deleteAuthorById(Integer authorId) throws ClassNotFoundException, SQLException{
        db.openConnection(driver, url, user, pswd);
        
        int result = db.deleteRecordbyPrimaryKey("author", "author_id", authorId);
         if(result == 0) {
            return false;
        } else {
            return true;
        }
    }
    
    @Override
    public Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException{
        db.openConnection(driver, url, user, pswd);
        
        Map<String,Object> rawRec = db.findById("author", "author_id", authorId);
        Author author = new Author();
        author.setAuthorId((Integer)rawRec.get("author_id"));
        author.setAuthorName(rawRec.get("author_name").toString());
        author.setDateAdded((Date)rawRec.get("date_added"));
        
        return author;
    }
    
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException{
        db.openConnection(driver, url, user, pswd);
        
        List<Map<String, Object>> rawData = db.findAllRecords("author", 0);
        List<Author> authors = new ArrayList<>();
        
        for(Map rec : rawData) {
            Author author = new Author();
            Integer id = new Integer(rec.get("author_id").toString());
            author.setAuthorId(id);
            String name = rec.get("author_name") == null ? "" : rec.get("author_name").toString();
            author.setAuthorName(name);
            Date date = rec.get("date_added") == null ? null : (Date)rec.get("date_added");
            author.setDateAdded(date);
            authors.add(author);
        }
        
        db.closeConnection();
        return authors;
    }
    
    
    
      @Override
    public boolean saveAuthor(Integer authorId, String authorName) throws ClassNotFoundException, SQLException {
        db.openConnection(driver, url, user, pswd);
        
        boolean result = false;
        
        if(authorId == null || authorId.equals(0)) {
            // must be a new record
            result = db.insertRecord("author", Arrays.asList("author_name","date_added"), 
                                      Arrays.asList(authorName,new Date()));
        } else {
            // must be an update of an existing record
            int recsUpdated = db.updateRecordById("author", Arrays.asList("author_name"), 
                                       Arrays.asList(authorName),
                                       "author_id", authorId);
            if(recsUpdated > 0) {
                result = true;
            }
        }
        return result;
    }
    
    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }
    
    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
    
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorDaoStrategy dao = new AuthorDao();
        List<Author> authors = dao.getAuthorList();
        System.out.println(authors);
    }

    
    
    

    

    

    

  
    

    
}

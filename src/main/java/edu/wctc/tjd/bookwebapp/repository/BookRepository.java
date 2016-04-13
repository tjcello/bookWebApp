package edu.wctc.tjd.bookwebapp.repository;

import  edu.wctc.tjd.bookwebapp.model.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author jlombardo
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
}

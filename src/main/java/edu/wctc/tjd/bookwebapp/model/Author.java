/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.tjd.bookwebapp.model;

import java.util.Date;
import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class Author {

    private int authorId;
    private String authorName;
    private Date dateAdded;

    public Author() {
    }

    public Author(int authorId) {
        this.authorId = authorId;
    }

    public Author(int authorId, String authorName, Date dateAdded) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.dateAdded = dateAdded;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.authorId;
        hash = 17 * hash + Objects.hashCode(this.authorName);
        hash = 17 * hash + Objects.hashCode(this.dateAdded);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Author other = (Author) obj;
        if (this.authorId != other.authorId) {
            return false;
        }
        if (!Objects.equals(this.authorName, other.authorName)) {
            return false;
        }
        if (!Objects.equals(this.dateAdded, other.dateAdded)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Author{" + "authorId=" + authorId + ", authorName=" + authorName + ", dateAdded=" + dateAdded + '}';
    }

}

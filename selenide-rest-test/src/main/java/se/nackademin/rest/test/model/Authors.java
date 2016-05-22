package se.nackademin.rest.test.model;

import java.util.List;

public class Authors {
    private List <Author> author;

    /**
     * @return the author
     */
    public List <Author> getAuthorList() {
        return author;
    }

    /**
     * @param book the author to set
     */
    public void setAuthorList(List <Author> author) {
        this.author = author;
    }  
}

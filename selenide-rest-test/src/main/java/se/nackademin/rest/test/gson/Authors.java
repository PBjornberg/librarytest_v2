package se.nackademin.rest.test.gson;

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
     * @param author the author to set
     */
    public void setAuthorList(List <Author> author) {
        this.author = author;
    }  
}

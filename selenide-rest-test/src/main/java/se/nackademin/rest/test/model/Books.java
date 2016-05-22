package se.nackademin.rest.test.model;

import java.util.List;

public class Books {
    private List <Book> book;

    /**
     * @return the book
     */
    public List <Book> getBookList() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBookList(List <Book> book) {
        this.book = book;
    }  
}

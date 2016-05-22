package se.nackademin.rest.test.model;

public class SingleBook {
    private Book book;
    
    public SingleBook(Book book){
        this.book = book;   
    }

    /**
     * @return the book
     */
    public Book getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(Book book) {
        this.book = book;
    }
}

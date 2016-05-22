package se.nackademin.rest.test.model;

public class SingleAuthor {
    private Author author;
    
    public SingleAuthor(Author author){
        this.author=author;
    }

    /**
     * @return the author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }
}

package se.nackademin.rest.test.model;

public class Book {
    
    private Object author;
    
    private Integer id;
    private String description;
    private String isbn;
    private Integer nbrPages;   
    private String publicationDate;    
    private String title;
    private Integer totalNbrCopies;      

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param descrition the description to set
     */
    public void setDescription(String descrition) {
        this.description = descrition;
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the nbOfPage
     */
    public Integer getNbOfPage() {
        return nbrPages;
    }

    /**
     * @param nbOfPage the nbOfPage to set
     */
    public void setNbOfPage(Integer nbOfPage) {
        this.nbrPages = nbOfPage;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author
     */
    public Object getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(Object author) {
        this.author = author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getTotalNbrCopies() {
        return totalNbrCopies;
    }

    public void setTotalNbrCopies(Integer totalNbrCopies) {
        this.totalNbrCopies = totalNbrCopies;
    }
            
    
}

package se.nackademin.rest.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.util.Random;
import java.util.UUID;
import se.nackademin.rest.test.model.Author;
import se.nackademin.rest.test.model.Book;
import se.nackademin.rest.test.model.SingleAuthor;
import se.nackademin.rest.test.model.SingleBook;
import static com.jayway.restassured.RestAssured.given;

/**
 * This class contains boilerplate code for REST tests in RestAssuredTest.java
 * @author Peter Björnberg
 */
public class BookOperations {
    //private static final String BASE_URL = "http://localhost:8080/librarytest/rest/";
    private static final String BASE_URL = "http://localhost:8080/librarytest-rest/";
    
    
    private Book book;
    private Author author;

    public Response createRandomAuthor(){     
        author = new Author();
        author.setBio(UUID.randomUUID().toString());  
        author.setCountry(UUID.randomUUID().toString());
        author.setFirstName(UUID.randomUUID().toString());  
        author.setLastName(UUID.randomUUID().toString());
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        String resourceName = "authors";
        return given().contentType(ContentType.JSON).body(singleAuthor).log().all().post(BASE_URL + resourceName);       
    }
    
    public Response getAuthor(Integer id){
        String resourceName = "authors/" + id.intValue();
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + resourceName);
    }     
    
     public Response getAllAuthors(){
        String resourceName = "authors";
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + resourceName); 
    } 
    
    public Response updateAuthor(Author author){
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        String resourceName = "authors";
        return given().contentType(ContentType.JSON).body(singleAuthor).log().all().put(BASE_URL + resourceName);       
    }    

    public Response deleleAuthor(int authorId){
        String deleteResourceName = BASE_URL+"authors/"+authorId;      
        return given().contentType(ContentType.JSON).log().all().delete(deleteResourceName);
    }    

  public Response createRandomBook(){
        
        book = new Book();
        book.setDescription(UUID.randomUUID().toString());
        book.setIsbn(UUID.randomUUID().toString().substring(0, 19));
        book.setNbOfPage(new Random().nextInt(400)+20);
        book.setPublicationDate("2010-01-01");
        book.setTitle(UUID.randomUUID().toString());
        book.setAuthor(author);
        book.setTotalNbrCopies(new Random().nextInt(10));
        
        SingleBook singleBook = new SingleBook(book);
        
        String resourceName = "books";
        return given().contentType(ContentType.JSON).body(singleBook).log().all().post(BASE_URL + resourceName);       
    }
    
    public Response getBook(int bookId){
        String resourceName = "books/"+bookId;
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + resourceName);
    }
    
    public Response getAllBooks(){
        String resourceName = "books";
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + resourceName); 
    } 

    public Response getAllBooksByAuthor(int authorId){
        String resourceName = "books/byauthor/"+authorId;
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + resourceName); 
    } 
   
    public Response updateBook(Book book){
        
        SingleBook singleBook = new SingleBook(book);
        
        String resourceName = "books";
        return given().contentType(ContentType.JSON).body(singleBook).log().all().put(BASE_URL + resourceName);
    }
    
    public Response addAuthorToBook(int bookId, Author author){
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        String resourceName = "books/"+bookId+"/authors";
        return given().contentType(ContentType.JSON).body(singleAuthor).log().all().post(BASE_URL + resourceName);
    }
    
    public Response deleleBook(int id){
        String deleteResourceName = BASE_URL+"books/"+id;  
        return given().contentType(ContentType.JSON).log().all().delete(deleteResourceName);
    }
    

    /**
     * @return the book
     */
    public Book getBook(){
        return book;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }
}

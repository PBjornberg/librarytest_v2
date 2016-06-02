package se.nackademin.rest.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import static com.jayway.restassured.RestAssured.given;
import java.util.Random;
import java.util.UUID;
import se.nackademin.rest.test.gson.Author;
import se.nackademin.rest.test.gson.Book;
import se.nackademin.rest.test.gson.Loan;
import se.nackademin.rest.test.gson.SingleAuthor;
import se.nackademin.rest.test.gson.SingleBook;
import se.nackademin.rest.test.gson.SingleLoan;
import se.nackademin.rest.test.gson.User;
import se.nackademin.rest.test.gson.SingleUser;

/**
 * This class contains boilerplate code for REST tests in RestAssuredTest.java
 * @author Peter Björnberg
 */
public class RestOperations {
    private static final String BASE_URL = "http://localhost:8080/librarytest-rest/";
    private static final String RESOURCE_USERS = "users/";
    private static final String RESOURCE_AUTHORS = "authors/";
    private static final String RESOURCE_BY_AUTHOR = "byauthor/";    
    private static final String RESOURCE_BOOKS = "books/";   
    private static final String RESOURCE_LOANS = "loans/";
    
    private User user;
    private Book book;
    private Author author;

    
     /**
     * Creates and persists a User with random data
     * @param role Either "LIBRARIAN" or "LOANER""
     * @return 
     */
    public Response createRandomUser(String role){     
        user = new User();
        user.setDisplayName(UUID.randomUUID().toString());
        user.setPassword(UUID.randomUUID().toString());        
        user.setFirstName(UUID.randomUUID().toString());
        user.setLastName(UUID.randomUUID().toString());  
        user.setPhone(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setRole(role);
        
        SingleUser singleUser = new SingleUser(user);
        
        return given().contentType(ContentType.JSON).body(singleUser).log().all().post(BASE_URL + RESOURCE_USERS);       
    }
    
    public Response getUser(Integer id){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_USERS + id);
    }  

    public Response getAllUsers(){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_USERS);
    }
    
    public Response updateUser(User user){
        
        SingleUser singleUser = new SingleUser(user);
        
        return given().contentType(ContentType.JSON).body(singleUser).log().all().put(BASE_URL + RESOURCE_USERS);
    }  
    
    public Response deleleUser(int userId){
        return given().contentType(ContentType.JSON).log().all().delete(BASE_URL+ RESOURCE_USERS +userId);
    }      
    
    public Response createRandomAuthor(){     
        author = new Author();
        author.setBio(UUID.randomUUID().toString());  
        author.setCountry(UUID.randomUUID().toString());
        author.setFirstName(UUID.randomUUID().toString());  
        author.setLastName(UUID.randomUUID().toString());
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        return given().contentType(ContentType.JSON).body(singleAuthor).log().all().post(BASE_URL + RESOURCE_AUTHORS);       
    }    
    
    public Response getAuthor(Integer id){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_AUTHORS + id);
    }     
    
     public Response getAllAuthors(){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_AUTHORS); 
    } 
    
    public Response updateAuthor(Author author){
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        return given().contentType(ContentType.JSON).body(singleAuthor).log().all().put(BASE_URL + RESOURCE_AUTHORS);       
    }    

    public Response deleleAuthor(int authorId){
        return given().contentType(ContentType.JSON).log().all().delete(BASE_URL + RESOURCE_AUTHORS + authorId);
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
        
        return given().contentType(ContentType.JSON).body(singleBook).log().all().post(BASE_URL + RESOURCE_BOOKS);       
    }
    
    public Response getBook(int bookId){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_BOOKS + bookId);
    }
    
    public Response getAllBooks(){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_BOOKS); 
    } 

    public Response getAllBooksByAuthor(int authorId){
        String resourceName = RESOURCE_BOOKS + RESOURCE_BY_AUTHOR + authorId;
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + resourceName); 
    } 
   
    public Response updateBook(Book book){
        
        SingleBook singleBook = new SingleBook(book);
        
        return given().contentType(ContentType.JSON).body(singleBook).log().all().put(BASE_URL + RESOURCE_BOOKS);
    }
    
    public Response addAuthorToBook(int bookId, Author author){
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        String resourceName = RESOURCE_BOOKS + bookId + "/" + RESOURCE_AUTHORS;
        return given().contentType(ContentType.JSON).body(singleAuthor).log().all().post(BASE_URL + resourceName);
    }
    
    public Response deleleBook(int id){
        return given().contentType(ContentType.JSON).log().all().delete(BASE_URL + RESOURCE_BOOKS +id);
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
    
    public Response createLoan(Book book, User user, String dateBorrowed, String dateDue){
        
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setDateBorrowed(dateBorrowed);
        loan.setDateDue(dateDue);
        SingleLoan singleLoan = new SingleLoan(loan);
        
        return given().contentType(ContentType.JSON).body(singleLoan).log().all().post(BASE_URL + RESOURCE_LOANS);       
    }
    
    public Response getAllLoans(){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_LOANS);
    }
    
    public Response getLoan(int loanId){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_LOANS + loanId);
    }    
    
    public Response deleleLoan(int id){
        return given().contentType(ContentType.JSON).log().all().delete(BASE_URL + RESOURCE_LOANS +id);
    }    
}

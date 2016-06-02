package se.nackademin.rest.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
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
import static com.jayway.restassured.RestAssured.given;

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
    private static final String RESOURCE_OFBOOK = "ofbook/";
    
    private Book book;
    
     /**
     * Creates and persists a User with random data
     * @param role Either "LIBRARIAN" or "LOANER""
     * @return Status code from http response
     */
    public static int createRandomUser(String role){     
        User user = new User();
        user.setDisplayName(UUID.randomUUID().toString());
        user.setPassword(UUID.randomUUID().toString());        
        user.setFirstName(UUID.randomUUID().toString());
        user.setLastName(UUID.randomUUID().toString());  
        user.setPhone(UUID.randomUUID().toString());
        user.setEmail(UUID.randomUUID().toString());
        user.setRole(role);
        
        SingleUser singleUser = new SingleUser(user);
        
        Response response = given().contentType(ContentType.JSON).body(singleUser).log().all().post(BASE_URL + RESOURCE_USERS);       
        return response.getStatusCode();
    }
    
    public static Response getUser(Integer id){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_USERS + id);
    }  

    public static Response getAllUsers(){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_USERS);
    }
    
    public static Response updateUser(User user){
        
        SingleUser singleUser = new SingleUser(user);
        
        return given().contentType(ContentType.JSON).body(singleUser).log().all().put(BASE_URL + RESOURCE_USERS);
    }  
    
    public static Response deleleUser(int userId){
        return given().contentType(ContentType.JSON).log().all().delete(BASE_URL+ RESOURCE_USERS +userId);
    }      
    
    /**
     * Creates an Author with random data
     * @return Status code from http response
     */
    public static int createRandomAuthor(){     
        Author author = new Author();
        author.setBio(UUID.randomUUID().toString());  
        author.setCountry(UUID.randomUUID().toString());
        author.setFirstName(UUID.randomUUID().toString());  
        author.setLastName(UUID.randomUUID().toString());
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).log().all().post(BASE_URL + RESOURCE_AUTHORS);
        return response.getStatusCode();
    }    
    
    public static Response getAuthor(Integer id){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_AUTHORS + id);
    }     
    
     public static Response getAllAuthors(){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_AUTHORS); 
    } 
    
    /**
     * Updates Author with given data
     * @param author
     * @return Status code from http response
     */
    public static int updateAuthor(Author author){
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).log().all().put(BASE_URL + RESOURCE_AUTHORS); 
        return response.getStatusCode();
    }    

    public static Response deleleAuthor(int authorId){
        return given().contentType(ContentType.JSON).log().all().delete(BASE_URL + RESOURCE_AUTHORS + authorId);
    }    
    
    /**
     * Creates a book with random data
     * 
     * @param author
     * @param totalNbrCopies Total number of copies of this book in Library* 
     * @return status code from http response
     */
    public int createRandomBook(Author author, int totalNbrCopies){
        
        book = new Book();
        book.setDescription(UUID.randomUUID().toString());
        book.setIsbn(UUID.randomUUID().toString().substring(0, 19));
        book.setNbOfPage(new Random().nextInt(400)+20);
        book.setPublicationDate("2010-01-01");
        book.setTitle(UUID.randomUUID().toString());
        book.setAuthor(author);
        book.setTotalNbrCopies(totalNbrCopies);
        
        SingleBook singleBook = new SingleBook(book);
        
        Response response = given().contentType(ContentType.JSON).body(singleBook).log().all().post(BASE_URL + RESOURCE_BOOKS); 
        return response.getStatusCode();
    }
    
    /**
     * @return the book
     */
    public Book getBook(){
        return book;
    }
    
    public static Response getBook(int bookId){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_BOOKS + bookId);
    }
    
    public static Response getAllBooks(){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_BOOKS); 
    } 

    public static Response getAllBooksByAuthor(int authorId){
        String resourceName = RESOURCE_BOOKS + RESOURCE_BY_AUTHOR + authorId;
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + resourceName); 
    } 
   
    /**
     * Updates Book with given data
     * @param book
     * @return Status code from http response
     */
    public static int updateBook(Book book){
        
        SingleBook singleBook = new SingleBook(book);
        
        Response response = given().contentType(ContentType.JSON).body(singleBook).log().all().put(BASE_URL + RESOURCE_BOOKS);
        return response.getStatusCode();
    }
    
    /**
     * Adds Author to Book
     * @param bookId
     * @param author
     * @return Status code from http response
     */
    public static int addAuthorToBook(int bookId, Author author){
        
        SingleAuthor singleAuthor = new SingleAuthor(author);
        
        String resourceName = RESOURCE_BOOKS + bookId + "/" + RESOURCE_AUTHORS;
        Response response = given().contentType(ContentType.JSON).body(singleAuthor).log().all().post(BASE_URL + resourceName);
        return response.getStatusCode();
    }
    
    /**
     * Deletes given Book
     * @param id
     * @return Status code from http response
     */
    public static int deleleBook(int id){
        Response response = given().contentType(ContentType.JSON).log().all().delete(BASE_URL + RESOURCE_BOOKS +id);
        return response.getStatusCode();
    }
    
    /**
     * Creates Loan for given Book and User
     * @param book
     * @param user
     * @param dateBorrowed
     * @param dateDue
     * @return Status code from http response
     */
    public static int createLoan(Book book, User user, String dateBorrowed, String dateDue){
        
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setDateBorrowed(dateBorrowed);
        loan.setDateDue(dateDue);
        SingleLoan singleLoan = new SingleLoan(loan);
        
        Response response = given().contentType(ContentType.JSON).body(singleLoan).log().all().post(BASE_URL + RESOURCE_LOANS); 
        return response.getStatusCode();
    }
    
    public static Response getLoansOfBook(int id){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_LOANS + RESOURCE_OFBOOK + id);
    }
    
    public static Response getLoan(int loanId){
        return given().accept(ContentType.JSON).log().all().get(BASE_URL + RESOURCE_LOANS + loanId);
    }    
    
    /**
     * Deletes Loan with given id
     * @param id
     * @return Status code from http response
     */
    public static int deleleLoan(int id){
        Response response = given().contentType(ContentType.JSON).log().all().delete(BASE_URL + RESOURCE_LOANS +id);
        return response.getStatusCode();
    }    
}

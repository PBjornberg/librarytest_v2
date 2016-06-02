package se.nackademin.librarytest;

import org.junit.Test;
import com.jayway.restassured.response.Response;
import java.util.List;
import se.nackademin.rest.test.gson.Book;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import se.nackademin.rest.test.RestOperations;
import se.nackademin.rest.test.gson.Author;
import se.nackademin.rest.test.gson.Authors;
import se.nackademin.rest.test.gson.Books;
import se.nackademin.rest.test.gson.User;
import se.nackademin.rest.test.gson.Users;
import static org.junit.Assert.assertEquals;
import se.nackademin.rest.test.gson.Loan;
import se.nackademin.rest.test.gson.Loans;


/**
 * REST tests for Library application
 * 
 * This class also contains some utility-methods at the very bottom
 * 
 * Prerequisite:
 * test.war should be deployed and run on a URL corresponding to BASE_URL in BookOperations.java
 * 
 * @author Peter Björnberg
 */
public class RestAssuredTest {

    public RestAssuredTest() {
    }
    
    private static final String LIBRARIAN_TYPE = "LIBRARIAN";
    private static final String LOANER_TYPE = "LOANER";
    

    @Test
    public void testFetchUser() {               

        int statusCode;
        
        statusCode = createUser(LIBRARIAN_TYPE);
        assertEquals("Status code should be 201", 201, statusCode);        
        User user = getLastUserFromDb();
        
        // Fetch the new User from database
        RestOperations ro = new RestOperations();
        Response getResponse = ro.getUser(user.getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode()); 
        User fetchedUser = getResponse.jsonPath().getObject("user", User.class);

        assertEquals(user.getId(),fetchedUser.getId());
    }      
    
    
    @Test
    public void testUpdateUser() {               
          
        int statusCode;
        
        statusCode = createUser(LIBRARIAN_TYPE);
        assertEquals("Status code should be 201", 201, statusCode);        
        User user = getLastUserFromDb();
        
        final String NEW_FIRST_NAME = "New first name";
        user.setFirstName(NEW_FIRST_NAME);

        RestOperations ro1 = new RestOperations();
        Response putResponse = ro1.updateUser(user);
        assertEquals("Status code should be 200", 200, putResponse.statusCode()); 
        
        // Verify that the user is updated in database
        RestOperations ro2 = new RestOperations();
        Response getResponse = ro2.getUser(user.getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode()); 
        User fetchedUser = getResponse.jsonPath().getObject("user", User.class);

        // Verify that displayname in unchanged, wheres first name is modified
        assertEquals(user.getDisplayName(),fetchedUser.getDisplayName());
        assertEquals(NEW_FIRST_NAME, fetchedUser.getFirstName());
    }   
    
    @Test
    public void testDeleteUser() {      
        
        int statusCode;
        
        statusCode = createUser(LIBRARIAN_TYPE);
        assertEquals("Status code should be 201", 201, statusCode);        
        User user = getLastUserFromDb();
        
        // Remove the created User from database
        RestOperations ro1 = new RestOperations();
        Response deleteResponse = ro1.deleleUser(user.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());            
    
        // Verify that the user is removed from database
        RestOperations ro2 = new RestOperations();
        Response getResponse = ro2.getUser(user.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode()); 
    }      
    
    @Test
    public void testReadNonExistingAuthor() {   
        RestOperations ro = new RestOperations();    
        Response getResponse = ro.getAuthor(0);
        assertEquals("Status code should be 404", 404, getResponse.statusCode()); 
    }
    
    @Test
    public void testCreateAndDeleteAuthor() {
        
        int statusCode;
        
        // Create a random Author
        statusCode = createAuthor();
        assertEquals("Status code should be 201", 201, statusCode);        
        
        // Remove the new Author from database
        Author authorToBeDeleted = getLastAuthorFromDb();
        RestOperations ro1 = new RestOperations();
        Response deleteResponse = ro1.deleleAuthor(authorToBeDeleted.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());            
    
        // Verify that the author is removed from database
        RestOperations bo2 = new RestOperations();
        Response getResponse = bo2.getAuthor(authorToBeDeleted.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());    
    }
    
    @Test
    public void testUpdateAuthor() {
        
        int statusCode;
        
         // Create a random Author       
        statusCode = createAuthor();
        assertEquals("Status code should be 201", 201, statusCode);         
        
        // Get id of the new author from database
        Author lastAuthor = getLastAuthorFromDb();
        Integer id = lastAuthor.getId();
        
        // Update last author with a different first name
        String newAuthorName = UUID.randomUUID().toString();
        lastAuthor.setFirstName(newAuthorName);
        RestOperations ro1 = new RestOperations();
        Response putResponse = ro1.updateAuthor(lastAuthor);      
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        // Fetch the updated author
        RestOperations bo2 = new RestOperations();    
        Response getResponse = bo2.getAuthor(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());        
        
        // Verify that the authors's name has been updated in the database      
        Author author = getResponse.jsonPath().getObject("author", Author.class);        
        assertEquals(newAuthorName, author.getFirstName());
    }

    @Test
    public void testCreateNewBook() {
      
        // Create a new book, attached to the last existing author       
        RestOperations ro = new RestOperations();    
        Author lastAuthor = getLastAuthorFromDb();
        ro.setAuthor(lastAuthor);
        
        Response postResponse2 = ro.createRandomBook();
        assertEquals("Status code should be 201", 201, postResponse2.statusCode());
        // Get all data for the new Book
        String newDescription = ro.getBook().getDescription();
        String newIsbn = ro.getBook().getIsbn();
        Integer newNbOfPage = ro.getBook().getNbOfPage();        
        String newTitle = ro.getBook().getTitle();

        Book newBook = getLastBookFromDb();

        // Verify that the book fetched from database contains expected data
        assertEquals(newDescription, newBook.getDescription());        
        assertEquals(newIsbn, newBook.getIsbn());        
        assertEquals(newNbOfPage, newBook.getNbOfPage());        
        assertEquals(newTitle, newBook.getTitle());
        
        // Verify that the author has expected id
        Map authorMap = (Map)newBook.getAuthor();   
        assertEquals((int)lastAuthor.getId(),Math.round((Double)authorMap.get("id")));        
    }

    @Test
    public void testReadNonExistingBook() {
        RestOperations bo = new RestOperations();
        Response getResponse = bo.getBook(0);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());        
    }
    
    @Test
    public void testReadAllBooksByAuthor() {
        
        int statusCode;
        
        // Create and fetch a new Author
        statusCode = createAuthor();
        assertEquals("Status code should be 201", 201, statusCode);         
        
        Author newAuthor = getLastAuthorFromDb();
        
        // Create first book, attached to the created author       
        RestOperations ro1 = new RestOperations();    
        ro1.setAuthor(newAuthor);       
        Response postResponse1 = ro1.createRandomBook();        
        assertEquals("Status code should be 201", 201, postResponse1.statusCode());
        Integer idBook1 = getLastBookFromDb().getId();       

        // Create second book, attached to the created author       
        RestOperations ro2 = new RestOperations();    
        ro2.setAuthor(newAuthor);
        Response postResponse2 = ro2.createRandomBook();
        assertEquals("Status code should be 201", 201, postResponse2.statusCode()); 
        Integer idBook2 = getLastBookFromDb().getId();
        
        RestOperations bo3 = new RestOperations();
        Response getResponse = bo3.getAllBooksByAuthor(newAuthor.getId()); 
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        
        // Pick the last book in the list
        Books books = getResponse.jsonPath().getObject("books", Books.class);
        assertEquals("List of Books should contain 2 items",2, books.getBookList().size());
        assertEquals(idBook1, books.getBookList().get(0).getId());
        assertEquals(idBook2, books.getBookList().get(1).getId());         
    }
    
    @Test
    public void testUpdateBook() {

        int statusCode;
        
        Book updatedBook = getLastBookFromDb();
        
        // Save book.id locally
        Integer id = updatedBook.getId();
        
        // Update the last book with different data
        String newDescription = UUID.randomUUID().toString();
        String newIsbn = UUID.randomUUID().toString().substring(0, 19);
        Integer newNbOfPage = new Random().nextInt(400)+20;                
        String newTitle = UUID.randomUUID().toString();        
        updatedBook.setDescription(newDescription);
        updatedBook.setIsbn(newIsbn);
        updatedBook.setNbOfPage(newNbOfPage);
        updatedBook.setTitle(newTitle);
        
        // Create a random Author
        statusCode = createAuthor();
        assertEquals("Status code should be 201", 201, statusCode); 
                
        // Get the new author from database
        Author newAuthor = getLastAuthorFromDb(); 
        updatedBook.setAuthor(newAuthor);

        RestOperations bo3 = new RestOperations();
        Response putResponse = bo3.updateBook(updatedBook);      
        assertEquals("Status code should be 200", 200, putResponse.statusCode());        
        
        // Fetch the updated book
        RestOperations bo4 = new RestOperations();        
        Response getResponse = bo4.getBook(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode()); 

        // Verify that the book's data has been updated    
        Book book = getResponse.jsonPath().getObject("book", Book.class);           
        assertEquals(newDescription, updatedBook.getDescription());        
        assertEquals(newIsbn, updatedBook.getIsbn());        
        assertEquals(newNbOfPage, updatedBook.getNbOfPage());        
        assertEquals(newTitle, updatedBook.getTitle());
        
        // Verify that the author has expected id
        // When there is only one Author, Gson will parse it into a Map in the Book object.
        Map authorMap = (Map)book.getAuthor();
        assertEquals((int)newAuthor.getId(),Math.round((Double)authorMap.get("id")));       
    }
    
    @Test
    public void testAddAuthorForBook() {
        
        int statusCode;
        
        // Get last book
        Book updatedBook = getLastBookFromDb();
        Integer bookId = updatedBook.getId();
        
        // Create first Author
        statusCode = createAuthor();       
        assertEquals("Status code should be 201", 201, statusCode);
        
        // Get the new author from database
        Author author1 = getLastAuthorFromDb();
 
        // Replace any previous Author(s) with the new Author
        updatedBook.setAuthor(author1);
        RestOperations bo1 = new RestOperations();
        Response putResponse = bo1.updateBook(updatedBook);      
        assertEquals("Status code should be 200", 200, putResponse.statusCode()); 
        
        // Create second Author        
        statusCode = createAuthor();
        assertEquals("Status code should be 201", 201, statusCode);
        
        // Get the new author from database
        Author author2 = getLastAuthorFromDb();
        Response postresponse2 = new RestOperations().addAuthorToBook(updatedBook.getId(), author2); 
        assertEquals("Status code should be 200", 200, postresponse2.statusCode());  
 
        // Fetch the updated book
        RestOperations bo2 = new RestOperations();        
        Response getResponse = bo2.getBook(bookId);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());            

        // Verify that the book has two different Authors attached
        // If there are more than one Author, Gson will parse them into a List of Map:s in the Book object.
        Book book = getResponse.jsonPath().getObject("book", Book.class);   
        List authorList = (List)book.getAuthor();
        assertEquals("List of Authors should contain 2 items",2, authorList.size());
        assertEquals((int)author1.getId(),Math.round((Double)((Map)authorList.get(0)).get("id"))); 
        assertEquals((int)author2.getId(),Math.round((Double)((Map)authorList.get(1)).get("id"))); 
    }
    
    
    @Test
    public void testDeleteBook() {
        
        // Remove last book from database
        Book bookToBeDeleted = getLastBookFromDb();
        RestOperations ro1 = new RestOperations();
        Response deleteResponse = ro1.deleleBook(bookToBeDeleted.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());            
    
        // Verify that the book is removed from database
        RestOperations ro2 = new RestOperations();
        Response getResponse = ro2.getBook(bookToBeDeleted.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());         
    }
    
    @Test
    public void testCreateAndDeleteLoan() {
      
        // Create a new laon, attached to the last existing book/user      
        RestOperations ro = new RestOperations();    
        Book book = getLastBookFromDb();
        User user = getLastUserFromDb();
        final String DATE_BORROWED = "2016-02-24";
        final String DATE_DUE = "2016-03-20";        
        
        Response postResponse = ro.createLoan(book, user, DATE_BORROWED, DATE_DUE);
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
        
        Loan loan = getLastLoanFromDb();
        
        assertEquals(book.getId(), loan.getBook().getId());
        assertEquals(user.getId(), loan.getUser().getId());
        assertEquals(DATE_BORROWED, loan.getDateBorrowed());
        assertEquals(DATE_DUE, loan.getDateDue());
        
        ro.deleleLoan(loan.getId());
        
        RestOperations ro2 = new RestOperations();
        Response getResponse = ro2.getLoan(loan.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());        
        
    }    
 
     /**
     * Utility method for creating User populated with random data 
     * @return Status code from http response
     */
    private int createUser(String role) {
        RestOperations ro = new RestOperations();
        Response postResponse = ro.createRandomUser(role);
        return postResponse.statusCode();
    }
    
     /**
     * Utility method for creating Author populated with random data 
     * @return Status code from http response
     */
    private int createAuthor() {
        RestOperations ro = new RestOperations();
        Response postResponse = ro.createRandomAuthor();
        return postResponse.statusCode();
    }
    
     /**
     * Utility method for fetching the last added User
     * @return The last User in the database 
     */
    private User getLastUserFromDb() {
        // Fetch all users (in order to get the last one)
        RestOperations ro = new RestOperations();
        Response getResponse = ro.getAllUsers();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        Users users = getResponse.jsonPath().getObject("users", Users.class);
        // Pick the last author from the list of users
        return users.getUserList().get(users.getUserList().size()-1);
    }
    
    /**
     * Utility method for fetching the last added Author
     * @return The last Author in the database 
     */
    private Author getLastAuthorFromDb() {
        // Fetch all authors (in order to get the last one)
        RestOperations ro = new RestOperations();
        Response getResponse = ro.getAllAuthors();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        Authors authors = getResponse.jsonPath().getObject("authors", Authors.class);
        // Pick the last author from the list of authors
        return authors.getAuthorList().get(authors.getAuthorList().size()-1);
    }
 
    /**
     * Utility method for fetching the last added Book
     * @return The last Book in the database 
     */    
    private Book getLastBookFromDb() {
        // Read all books from database
        RestOperations ro = new RestOperations();
        Response getResponse = ro.getAllBooks();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        // Pick the last book in the list
        Books books = getResponse.jsonPath().getObject("books", Books.class);
        return books.getBookList().get(books.getBookList().size()-1);
    }
    
     /**
     * Utility method for fetching the last added Loan
     * @return The last Loan in the database 
     */    
    private Loan getLastLoanFromDb() {
        // Read all loans from database
        RestOperations ro = new RestOperations();
        Response getResponse = ro.getAllLoans();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        // Pick the last book in the list
        Loans loans = getResponse.jsonPath().prettyPeek().getObject("loans", Loans.class);
        return loans.getLoanList().get(loans.getLoanList().size()-1);
    }
}
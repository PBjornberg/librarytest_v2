package se.nackademin.librarytest;

import com.google.gson.internal.LinkedTreeMap;
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
import se.nackademin.rest.test.gson.Loan;
import se.nackademin.rest.test.gson.Loans;
import static org.junit.Assert.assertEquals;


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
    
    private static final String ROLE_LIBRARIAN = "LIBRARIAN";
    private static final String ROLE_LOANER = "LOANER";
    

    @Test
    public void testFetchUser() {               

        int statusCode;
        
        statusCode = RestOperations.createRandomUser(ROLE_LIBRARIAN);
        assertEquals("Status code should be 201", 201, statusCode);        
        
        // Fetch the new User from database
        User user = fetchLastUser();
        Response getResponse = RestOperations.getUser(user.getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode()); 
        User fetchedUser = getResponse.jsonPath().getObject("user", User.class);

        assertEquals(user.getId(),fetchedUser.getId());
    }      
    
    
    @Test
    public void testUpdateUser() {               
          
        int statusCode;
        
        statusCode = RestOperations.createRandomUser(ROLE_LIBRARIAN);
        assertEquals("Status code should be 201", 201, statusCode);        
        User user = fetchLastUser();
        
        final String NEW_FIRST_NAME = "New first name";
        user.setFirstName(NEW_FIRST_NAME);

        Response putResponse = RestOperations.updateUser(user);
        assertEquals("Status code should be 200", 200, putResponse.statusCode()); 
        
        // Verify that the user is updated in database
        Response getResponse = RestOperations.getUser(user.getId());
        assertEquals("Status code should be 200", 200, getResponse.statusCode()); 
        User fetchedUser = getResponse.jsonPath().getObject("user", User.class);

        // Verify that displayname in unchanged, wheres first name is modified
        assertEquals(user.getDisplayName(),fetchedUser.getDisplayName());
        assertEquals(NEW_FIRST_NAME, fetchedUser.getFirstName());
    }   
    
    @Test
    public void testDeleteUser() {      
        
        int statusCode;
        
        statusCode = RestOperations.createRandomUser(ROLE_LIBRARIAN);
        assertEquals("Status code should be 201", 201, statusCode);        
        User user = fetchLastUser();
        
        // Remove the created User from database
        Response deleteResponse = RestOperations.deleleUser(user.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());            
    
        // Verify that the user is removed from database
        Response getResponse = RestOperations.getUser(user.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode()); 
    }      
    
    @Test
    public void testReadNonExistingAuthor() {   
        Response getResponse = RestOperations.getAuthor(0);
        assertEquals("Status code should be 404", 404, getResponse.statusCode()); 
    }
    
    @Test
    public void testCreateAndDeleteAuthor() {
        
        int statusCode;
        
        // Create a random Author
        statusCode = RestOperations.createRandomAuthor();
        assertEquals("Status code should be 201", 201, statusCode);        
        
        // Remove the new Author from database
        Author authorToBeDeleted = fetchLastAuthor();
        Response deleteResponse = RestOperations.deleleAuthor(authorToBeDeleted.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());            
    
        // Verify that the author is removed from database
        Response getResponse = RestOperations.getAuthor(authorToBeDeleted.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());    
    }
    
    @Test
    public void testUpdateAuthor() {
        
        int statusCode;
        
         // Create a random Author       
        statusCode = RestOperations.createRandomAuthor();
        assertEquals("Status code should be 201", 201, statusCode);         
        
        // Get id of the new author from database
        Author author = fetchLastAuthor();
        Integer id = author.getId();
        
        // Update last author with a different first name
        String newAuthorName = UUID.randomUUID().toString();
        author.setFirstName(newAuthorName);
        statusCode = RestOperations.updateAuthor(author);      
        assertEquals("Status code should be 200", 200, statusCode);
        
        // Fetch the updated author
        Response getResponse = RestOperations.getAuthor(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());        
        
        // Verify that the authors's name has been updated in the database      
        Author fetchedAuthor = getResponse.jsonPath().getObject("author", Author.class);        
        assertEquals(newAuthorName, fetchedAuthor.getFirstName());
    }

    @Test
    public void testCreateNewBook() {
      
        // Create a new book, attached to the last existing author       
        Author author = fetchLastAuthor();
        
        RestOperations ro = new RestOperations();        
        int statusCode = ro.createRandomBook(author, 2);
        assertEquals("Status code should be 201", 201, statusCode);
        // Get all data for the new Book
        String newDescription = ro.getBook().getDescription();
        String newIsbn = ro.getBook().getIsbn();
        Integer newNbOfPage = ro.getBook().getNbOfPage();        
        String newTitle = ro.getBook().getTitle();

        Book newBook = fetchLastBook();

        // Verify that the book fetched from database contains expected data
        assertEquals(newDescription, newBook.getDescription());        
        assertEquals(newIsbn, newBook.getIsbn());        
        assertEquals(newNbOfPage, newBook.getNbOfPage());        
        assertEquals(newTitle, newBook.getTitle());
        
        // Verify that the author has expected id
        Map authorMap = (Map)newBook.getAuthor();   
        assertEquals((int)author.getId(), Math.round((Double)authorMap.get("id")));        
    }

    @Test
    public void testReadNonExistingBook() {
        Response getResponse = RestOperations.getBook(0);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());        
    }
    
    @Test
    public void testReadAllBooksByAuthor() {
        
        int statusCode;
        
        // Create and fetch a new Author
        statusCode = RestOperations.createRandomAuthor();
        assertEquals("Status code should be 201", 201, statusCode);         
        
        Author newAuthor = fetchLastAuthor();
        
        // Create first book, attached to the created author       
        RestOperations ro1 = new RestOperations();    
        statusCode = ro1.createRandomBook(newAuthor, 2);        
        assertEquals("Status code should be 201", 201, statusCode);
        Integer idBook1 = fetchLastBook().getId();       

        // Create second book, attached to the created author       
        RestOperations ro2 = new RestOperations();    
        statusCode = ro2.createRandomBook(newAuthor, 1);
        assertEquals("Status code should be 201", 201, statusCode); 
        Integer idBook2 = fetchLastBook().getId();
        
        Response getResponse = RestOperations.getAllBooksByAuthor(newAuthor.getId()); 
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
        
        Book updatedBook = fetchLastBook();
        
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
        statusCode = RestOperations.createRandomAuthor();
        assertEquals("Status code should be 201", 201, statusCode); 
                
        // Get the new author from database
        Author newAuthor = fetchLastAuthor(); 
        updatedBook.setAuthor(newAuthor);

        statusCode = RestOperations.updateBook(updatedBook);      
        assertEquals("Status code should be 200", 200, statusCode);        
        
        // Fetch the updated book
        Response getResponse = RestOperations.getBook(id);
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
        Book updatedBook = fetchLastBook();
        Integer bookId = updatedBook.getId();
        
        // Create first Author
        statusCode = RestOperations.createRandomAuthor();       
        assertEquals("Status code should be 201", 201, statusCode);
        
        // Get the new author from database
        Author author1 = fetchLastAuthor();
 
        // Replace any previous Author(s) with the new Author
        updatedBook.setAuthor(author1);
        statusCode = RestOperations.updateBook(updatedBook);      
        assertEquals("Status code should be 200", 200, statusCode); 
        
        // Create second Author        
        statusCode = RestOperations.createRandomAuthor();
        assertEquals("Status code should be 201", 201, statusCode);
        
        // Get the new author from database
        Author author2 = fetchLastAuthor();
        statusCode = RestOperations.addAuthorToBook(updatedBook.getId(), author2); 
        assertEquals("Status code should be 200", 200, statusCode);  
 
        // Fetch the updated book
        Response getResponse = RestOperations.getBook(bookId);
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
        Book bookToBeDeleted = fetchLastBook();
        int statusCode = RestOperations.deleleBook(bookToBeDeleted.getId());
        assertEquals("Status code should be 204", 204, statusCode);            
    
        // Verify that the book is removed from database
        Response getResponse = RestOperations.getBook(bookToBeDeleted.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());         
    }
    
    @Test
    public void testCreateAndDeleteLoan() {
    
        // Create a new book, (number of copies in library = 1)     
        RestOperations ro1 = new RestOperations();    
        int statusCode = ro1.createRandomBook(null,1);
        assertEquals("Status code should be 201", 201, statusCode);
        Book book = fetchLastBook();
        
        // Create a new user
        statusCode = RestOperations.createRandomUser(ROLE_LOANER);
        assertEquals(201, statusCode);
        User user = fetchLastUser();

        // Create a new Loan with the new book and the new user      
        final String DATE_BORROWED_1 = "2016-02-24";
        final String DATE_DUE_1 = "2016-03-20";        
        statusCode = RestOperations.createLoan(book, user, DATE_BORROWED_1, DATE_DUE_1);
        assertEquals("Status code should be 201", 201, statusCode);
        
        Loan loan = fetchLastLoanOfBook(book.getId());
        
        assertEquals(DATE_BORROWED_1, loan.getDateBorrowed());
        assertEquals(DATE_DUE_1, loan.getDateDue());
                
        // Create a second user
        statusCode = RestOperations.createRandomUser(ROLE_LOANER);
        assertEquals(201, statusCode);
        User user2 = fetchLastUser();
        
        // Try to create another Loan with the same book and the second user  
        final String DATE_BORROWED_2 = "2016-03-25";
        final String DATE_DUE_2 = "2016-04-21";        
        statusCode = RestOperations.createLoan(book, user2, DATE_BORROWED_2, DATE_DUE_2);
        assertEquals("Status code should be 409", 409, statusCode);
        
        loan = fetchLastLoanOfBook(book.getId());
        statusCode = RestOperations.deleleLoan(loan.getId());
        assertEquals("Status code should be 204", 204, statusCode);
        
        Response getResponse = RestOperations.getLoan(loan.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());        
        
    }    
    
     /**
     * Utility method for fetching the last added User
     * @return The last User in the database 
     */
    private User fetchLastUser() {
        // Fetch all users (in order to get the last one)
        Response getResponse = RestOperations.getAllUsers();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        Users users = getResponse.jsonPath().getObject("users", Users.class);
        // Pick the last author from the list of users
        return users.getUserList().get(users.getUserList().size()-1);
    }
    
    /**
     * Utility method for fetching the last added Author
     * @return The last Author in the database 
     */
    private Author fetchLastAuthor() {
        // Fetch all authors (in order to get the last one)
        Response getResponse = RestOperations.getAllAuthors();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        Authors authors = getResponse.jsonPath().getObject("authors", Authors.class);
        // Pick the last author from the list of authors
        return authors.getAuthorList().get(authors.getAuthorList().size()-1);
    }
 
    /**
     * Utility method for fetching the last added Book
     * @return The last Book in the database 
     */    
    private Book fetchLastBook() {
        // Read all books from database
        Response getResponse = RestOperations.getAllBooks();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        // Pick the last book in the list
        Books books = getResponse.jsonPath().getObject("books", Books.class);
        return books.getBookList().get(books.getBookList().size()-1);
    }
    
     /**
     * Utility method for fetching the last Loan for given User
     * @return The last Loan in the database 
     */    
    private Loan fetchLastLoanOfBook(int id) {
        // Read all loans from database
        Response getResponse = RestOperations.getLoansOfBook(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode());

        Loans loans = getResponse.jsonPath().prettyPeek().getObject("loans", Loans.class);
        
        Loan loan = null;
        Map loanMap = null;
        
        // Gson deserialisation results in either
        //  - a LinkedTreeMAp
        //  - a List of LinkedTreeMap:s
        if (loans.getLoan() instanceof LinkedTreeMap){
            loanMap = (Map)loans.getLoan();

        } else if (loans.getLoan() instanceof List) {
            List loanList = (List)loans.getLoan();
            loanMap = (Map)loanList.get(loanList.size()-1);
        }
        loan = new Loan();
        loan.setId(((Double)loanMap.get("id")).intValue());
        loan.setDateBorrowed((String)loanMap.get("dateBorrowed"));
        loan.setDateDue((String)loanMap.get("dateDue")); 
        // Book and User objects omitted for simplicity
        
        return loan;
    }
}
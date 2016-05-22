package se.nackademin.librarytest;

import org.junit.Test;
import com.jayway.restassured.response.Response;
import java.util.List;
import se.nackademin.rest.test.model.Book;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import se.nackademin.rest.test.BookOperations;
import se.nackademin.rest.test.model.Author;
import se.nackademin.rest.test.model.Authors;
import se.nackademin.rest.test.model.Books;
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
    
    @Test
    public void testReadNonExistingAuthor() {   
        BookOperations bo = new BookOperations();    
        Response getResponse = bo.getAuthor(0);
        assertEquals("Status code should be 404", 404, getResponse.statusCode()); 
    }
    
    @Test
    public void testCreateAndDeleteAuthor() {

        // Create a random Author
        createAuthor();
        
        // Remove the new Author from database
        Author authorToBeDeleted = getLastAuthorFromDb();
        BookOperations bo2 = new BookOperations();
        Response deleteResponse = bo2.deleleAuthor(authorToBeDeleted.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());            
    
        // Verify that the author is removed from database
        BookOperations bo3 = new BookOperations();
        Response getResponse = bo3.getAuthor(authorToBeDeleted.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());    
    }
    
    @Test
    public void testUpdateAuthor() {
        
         // Create a random Author       
        createAuthor();
        
        // Get id of the new author from database
        Author lastAuthor = getLastAuthorFromDb();
        Integer id = lastAuthor.getId();
        
        // Update last author with a different first name
        String newAuthorName = UUID.randomUUID().toString();
        lastAuthor.setFirstName(newAuthorName);
        BookOperations bo3 = new BookOperations();
        Response putResponse = bo3.updateAuthor(lastAuthor);      
        assertEquals("Status code should be 200", 200, putResponse.statusCode());
        
        // Fetch the updated author
        BookOperations bo4 = new BookOperations();    
        Response getResponse2 = bo4.getAuthor(id);
        assertEquals("Status code should be 200", 200, getResponse2.statusCode());        
        
        // Verify that the authors's name has been updated in the database      
        Author author = getResponse2.jsonPath().getObject("author", Author.class);        
        assertEquals(newAuthorName, author.getFirstName());
    }

    @Test
    public void testCreateNewBook() {
      
        // Create a new book, attached to the last existing author       
        BookOperations bo1 = new BookOperations();    
        Author lastAuthor = getLastAuthorFromDb();
        bo1.setAuthor(lastAuthor);
        
        Response postResponse2 = bo1.createRandomBook();
        assertEquals("Status code should be 201", 201, postResponse2.statusCode());
        // Get all data for the new Book
        String newDescription = bo1.getBook().getDescription();
        String newIsbn = bo1.getBook().getIsbn();
        Integer newNbOfPage = bo1.getBook().getNbOfPage();        
        String newTitle = bo1.getBook().getTitle();

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
        BookOperations bo = new BookOperations();
        Response getResponse = bo.getBook(0);
        assertEquals("Status code should be 404", 404, getResponse.statusCode());        
    }
    
    @Test
    public void testReadAllBooksByAuthor() {
        // Create and fetch a new Author
        createAuthor();
        Author newAuthor = getLastAuthorFromDb();
        
        // Create first book, attached to the created author       
        BookOperations bo1 = new BookOperations();    
        bo1.setAuthor(newAuthor);       
        Response postResponse1 = bo1.createRandomBook();        
        assertEquals("Status code should be 201", 201, postResponse1.statusCode());
        Integer idBook1 = getLastBookFromDb().getId();       

        // Create second book, attached to the created author       
        BookOperations bo2 = new BookOperations();    
        bo2.setAuthor(newAuthor);
        Response postResponse2 = bo2.createRandomBook();
        assertEquals("Status code should be 201", 201, postResponse2.statusCode()); 
        Integer idBook2 = getLastBookFromDb().getId();
        
        BookOperations bo3 = new BookOperations();
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
        createAuthor();       
        // Get the new author from database
        Author newAuthor = getLastAuthorFromDb(); 
        updatedBook.setAuthor(newAuthor);

        BookOperations bo3 = new BookOperations();
        Response putResponse = bo3.updateBook(updatedBook);      
        assertEquals("Status code should be 200", 200, putResponse.statusCode());        
        
        // Fetch the updated book
        BookOperations bo4 = new BookOperations();        
        Response getResponse = bo4.getBook(id);
        assertEquals("Status code should be 200", 200, getResponse.statusCode()); 

        // Verify that the book's data has been updated    
        Book book = getResponse.jsonPath().getObject("book", Book.class);           
        assertEquals(newDescription, updatedBook.getDescription());        
        assertEquals(newIsbn, updatedBook.getIsbn());        
        assertEquals(newNbOfPage, updatedBook.getNbOfPage());        
        assertEquals(newTitle, updatedBook.getTitle());
        
        //Verify that the author has expected id
        //When there is only one Author, Gson will parse it into a Map in the Book object.
        Map authorMap = (Map)book.getAuthor();
        assertEquals((int)newAuthor.getId(),Math.round((Double)authorMap.get("id")));       
    }
    
    @Test
    public void testAddAuthorForBook() {
        // Get last book
        Book updatedBook = getLastBookFromDb();
        Integer bookId = updatedBook.getId();
        
        // Create first Author
        createAuthor();       
        // Get the new author from database
        Author author1 = getLastAuthorFromDb();
 
        // Replace any previous Author(s) with the new Author
        updatedBook.setAuthor(author1);
        BookOperations bo1 = new BookOperations();
        Response putResponse = bo1.updateBook(updatedBook);      
        assertEquals("Status code should be 200", 200, putResponse.statusCode()); 
        
        // Create second Author        
        createAuthor();
        // Get the new author from database
        Author author2 = getLastAuthorFromDb();
        Response postresponse2 = new BookOperations().addAuthorToBook(updatedBook.getId(), author2); 
        assertEquals("Status code should be 200", 200, postresponse2.statusCode());  
 
        // Fetch the updated book
        BookOperations bo2 = new BookOperations();        
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
        BookOperations bo1 = new BookOperations();
        Response deleteResponse = bo1.deleleBook(bookToBeDeleted.getId());
        assertEquals("Status code should be 204", 204, deleteResponse.statusCode());            
    
        // Verify that the book is removed from database
        BookOperations bo2 = new BookOperations();
        Response getResponse = bo2.getBook(bookToBeDeleted.getId());
        assertEquals("Status code should be 404", 404, getResponse.statusCode());         
    }
    
    
     /**
     * Utility method for creating Author populated with random data 
     */
    private void createAuthor() {
        BookOperations bo1 = new BookOperations();
        Response postResponse = bo1.createRandomAuthor();
        assertEquals("Status code should be 201", 201, postResponse.statusCode());
    }
    
    /**
     * Utility method for fetching the last added Author
     * @return The last Author in the database 
     */
    private Author getLastAuthorFromDb() {
        // Fetch all authors (in order to get the last one)
        BookOperations bo = new BookOperations();
        Response getResponse = bo.getAllAuthors();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        Authors authors = getResponse.jsonPath().getObject("authors", Authors.class);
        // Pick the last author from the list of authors
        Author lastAuthor = authors.getAuthorList().get(authors.getAuthorList().size()-1);
        return lastAuthor;
    }
 
    /**
     * Utility method for fetching the last added Book
     * @return The last Book in the database 
     */    
    private Book getLastBookFromDb() {
        // Read all books from database
        BookOperations bo = new BookOperations();
        Response getResponse = bo.getAllBooks();
        assertEquals("Status code should be 200", 200, getResponse.statusCode());
        // Pick the last book in the list
        Books books = getResponse.jsonPath().getObject("books", Books.class);
        Book newBook = books.getBookList().get(books.getBookList().size()-1);
        return newBook;
    }     
}
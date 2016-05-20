package se.nackademin.librarytest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import static org.junit.Assert.assertEquals;


import org.junit.Test;
import se.nackademin.librarytest.helpers.AuthorHelper;
import se.nackademin.librarytest.helpers.BookHelper;

import se.nackademin.librarytest.helpers.UserHelper;
import se.nackademin.librarytest.model.Author;
import se.nackademin.librarytest.model.User;
import se.nackademin.librarytest.pages.BookPage;
import se.nackademin.librarytest.pages.MenuPage;
import se.nackademin.librarytest.pages.MyProfilePage;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.page;
import static org.junit.Assert.assertEquals;
import static com.codeborne.selenide.Selenide.page;

public class SelenideTest extends TestBase {

    public SelenideTest() {
    }
    
    /*
    @Test
    public void testUsingTable() {
        page(MenuPage.class).navigateToBrowseBooks();
        BrowseBooksPage browseBooksPage = page(BrowseBooksPage.class);
        //browseBooksPage.setTitleField("G");
        browseBooksPage.clickSearchBooksButton();
        Table table = new Table($(".v-grid-tablewrapper"));
        System.out.println(table.getColumnCount());
        System.out.println(table.getRowCount());
        System.out.println(table.getCellValue(0, 0));
        System.out.println(table.getCellValue(1, 1));
        table.searchAndClick("American Gods", 0);
        sleep(2000);
    }*/
    
    
    /**
     * Skapa en ny författare
     */
    @Test
    public void testLoginAndCreateAuthor(){

        String firstName = "Ernest";
        String lastName = "Hemingway";
        String biography = "Ernest Miller Hemingway (July 21, 1899 – July 2, 1961) "
                + "was an American novelist, short story writer, and journalist. "
                + "His economical and understated style had a strong influence on 20th-century fiction, "
                + "while his life of adventure and his public image influenced later generations.";
        String country = "USA";

        // Log in as admin user and create new author
        UserHelper.logInAsUser("admin", "1234567890");        
        AuthorHelper.createNewAuthor(firstName, lastName, country, biography);

        // Verify fetched author
        Author author = AuthorHelper.fetchAuthor("Hemingway");
        assertEquals("Authors name should be first and laast names concatenated", firstName +" "+ lastName, author.getName());
        assertEquals(country, author.getCountry());
        assertEquals(biography, author.getBiography());
    }

    /**
     * Ändra e-mailadress
     */
    @Test
    public void testCreateUserAndUpdateEmail(){

        final String uuid = UUID.randomUUID().toString();
        final String oldEmail = "abc.def@mailserver.com";
        final String newEmail = "fornman.efternamn@mailserver.com";
        User user;
        
        // Create user
        UserHelper.createNewUser(uuid, uuid, "firstname", "lastname", "010 - 12345678", oldEmail);
        
        // Verify email is correctly set
        user = UserHelper.fetchUser(uuid, uuid);        
        assertEquals(oldEmail, user.getEmail());
        
        // Update user's email
        UserHelper.updateLoggedInUser(null, null, null, null, newEmail);

        // Verify email has changed
        user = UserHelper.fetchUser(uuid, uuid);
        assertEquals(newEmail, user.getEmail());        
    }
    
    /**
     * Ändra publiceringsdatum
     */
    @Test
    public void testUpdateBookDatePublished(){
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String todaysDate = df.format(new Date());
        final String originalDatePublished = "1990-05-01";
        final String bookTitle = "Good Omens";         
        
        // Log in as admin user
        UserHelper.logInAsUser("admin", "1234567890");
        
        // We dont know for sure what "date published" the book has
        // Therefore, we must change the value twice
        BookHelper.editBook(bookTitle, null, null, null, null, null, todaysDate);         
        assertEquals(todaysDate, BookHelper.fetchBookPage(bookTitle).getDatePublished());

        BookHelper.editBook(bookTitle, null, null, null, null, null, originalDatePublished);
        assertEquals(originalDatePublished, BookHelper.fetchBookPage(bookTitle).getDatePublished());
    }
    
    /**
    * Låna en bok
    */
    @Test
    public void testCreateUserAndBorrowBook(){

        final String uuid = UUID.randomUUID().toString();
        UserHelper.createNewUser(uuid, uuid, "firstname", "lastname", "010 - 12345678", "mail.address@server.com");
        UserHelper.logInAsUser(uuid, uuid);

        final String bookTitle = "Coraline";
        BookPage bookPage;
        
        // Save the number of available books
        bookPage = BookHelper.fetchBookPage(bookTitle);
        int originalNbrOfCopiesAvailable = Integer.parseInt(bookPage.getNbrOfCopiesAvailable()); 
        
        // Borrow book
        BookHelper.borrowBook(bookTitle);

        // Verify that the number of available boos is decremeted by one
        // For some reason, we need to wait a moment
        sleep(1000);
        assertEquals("Number of copies availabe is not decremented", originalNbrOfCopiesAvailable - 1, Integer.parseInt(bookPage.getNbrOfCopiesAvailable()));
        
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToMyProfile();        
        MyProfilePage myProfilePage = page(MyProfilePage.class); 
        myProfilePage.clickFirstResultTitle();   
        assertEquals(bookTitle, bookPage.getTitle());
        
        // Return book
        BookHelper.returnBook(bookTitle);
        
        // Verify that the number of available boos is restored to original value
        // For some reason, we need to wait a moment        
        sleep(1000);
        assertEquals("Number of copies availabe is wrong", originalNbrOfCopiesAvailable, Integer.parseInt(bookPage.getNbrOfCopiesAvailable()));
        
    }
    
}

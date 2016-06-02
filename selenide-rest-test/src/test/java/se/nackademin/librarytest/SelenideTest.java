package se.nackademin.librarytest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


import org.junit.Test;
import se.nackademin.selenide.helpers.AuthorHelper;
import se.nackademin.selenide.helpers.BookHelper;
import se.nackademin.selenide.helpers.UserHelper;
import se.nackademin.selenide.model.Author;
import se.nackademin.selenide.model.User;
import se.nackademin.selenide.pages.BookViewPage;
import se.nackademin.selenide.pages.MenuPage;
import se.nackademin.selenide.pages.MyProfilePage;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static com.codeborne.selenide.Selenide.page;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import se.nackademin.selenide.pages.UserFormPage;

public class SelenideTest extends SelenideTestBase {

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
        UserHelper.logInAsUser(ADMIN_USER, ADMIN_PWD);        
        AuthorHelper.createNewAuthor(firstName, lastName, country, biography);

        // Verify fetched author
        Author author = AuthorHelper.fetchAuthor("Hemingway");
        assertEquals("Authors name should be first and laast names concatenated", firstName +" "+ lastName, author.getName());
        assertEquals(country, author.getCountry());
        assertEquals(biography, author.getBiography());
    }

    
    /**
     * Skapa användare, logga in - logga ut
     */
    @Test
    public void testSignInAndSignOut(){
        
        // Sign in
        UserHelper.logInAsUser(ADMIN_USER, ADMIN_PWD);
        
        // Verify there is a link to the profile page
        MenuPage menuPage = page(MenuPage.class); 
        
        menuPage.waitForProfileLinkToShow();
        assertTrue("Link to profile page is not visible",menuPage.isMyProfileLinkVisibel());

        // Sign out        
        menuPage.clickSignOut();
        // Verify there is NO link to the profile page        
        assertFalse("Link to profile page should not be visible",menuPage.isMyProfileLinkVisibel());
        
      
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
        UserHelper.createNewUser(uuid, uuid, "firstname", "lastname", "010 - 12345678", oldEmail, false);
        
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
     * Skapa ny bok
     */
    @Test
    public void testAddBook(){
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        final String datePublished = df.format(new Date());
        final String title = UUID.randomUUID().toString();        
        final String description = "Lorem ipsum dolor sit amet, sit te tation putent.";  
        final String expectedAuthor = "Terry Pratchett";
        
        // Log in as admin user
        UserHelper.logInAsUser(ADMIN_USER, ADMIN_PWD);
        
        // Crate a nw book
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddBook();
        BookHelper.createBook(title, description, null, null, null, datePublished); 

        BookViewPage bp = BookHelper.fetchBookPage(title);
        
        assertEquals(title, bp.getTitle());
        assertEquals(description, bp.getDescription());   
        assertEquals(expectedAuthor, bp.getAuthor());
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
        UserHelper.logInAsUser(ADMIN_USER, ADMIN_PWD);
        
        // We dont know for sure what "date published" the book has
        // Therefore, we must change the value twice
        BookHelper.editBook(bookTitle, null, null, null, null, null, todaysDate);         
        assertEquals(todaysDate, BookHelper.fetchBookPage(bookTitle).getDatePublished());

        BookHelper.editBook(bookTitle, null, null, null, null, null, originalDatePublished);
        assertEquals(originalDatePublished, BookHelper.fetchBookPage(bookTitle).getDatePublished());
    }
    
    /**
    * Skapa användare och låna en bok
    */
    @Test
    public void testCreateUserAndBorrowBook(){

        final String uuid = UUID.randomUUID().toString();
        UserHelper.createNewUser(uuid, uuid, "firstname", "lastname", "010 - 12345678", "mail.address@server.com", false);
        UserHelper.logInAsUser(uuid, uuid);

        final String bookTitle = "Coraline";
        BookViewPage bookPage;
        
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
        bookPage.waitForPageToLoad();
        assertEquals(bookTitle, bookPage.getTitle());
        
        // Return book
        BookHelper.returnBook(bookTitle);
        
        // Verify that the number of available boos is restored to original value
        // For some reason, we need to wait a moment        
        sleep(1000);
        assertEquals("Number of copies availabe is wrong", originalNbrOfCopiesAvailable, Integer.parseInt(bookPage.getNbrOfCopiesAvailable()));
        
    }
    
    /**
    * Skapa librarian-användare
    */
    @Test
    public void testCreateLibrarianUser(){

        // Log in as admin user
        UserHelper.logInAsUser(ADMIN_USER, ADMIN_PWD);
        final String uuid = UUID.randomUUID().toString();
        UserHelper.createNewUser(uuid, uuid, "firstname", "lastname", "010 - 12345678", "admin@server.com", true);
        UserHelper.logInAsUser(uuid, uuid);
        
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToMyProfile();    
        
        MyProfilePage myProfilePage = page(MyProfilePage.class);
        myProfilePage.clickDeleteUserButton();
        myProfilePage.clickConfirmDeleteUserOkButton();
        
    }
    
    /**
    * Skapa användare med redan upptaget "Display Name"
    */
    @Test
    public void testCreateUserWithDisplayNameAlreadyTaken(){

        // Try to create user with display name "admin"
        UserFormPage page = UserHelper.createNewUser(ADMIN_USER, ADMIN_PWD, "firstname", "lastname", "010 - 12345678", "admin@server.com", false);
        assertThat(page.getMessage(), containsString("display name already exists"));
        
    }
    
}

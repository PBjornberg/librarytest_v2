/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.helpers;


import se.nackademin.selenide.pages.BookViewPage;
import se.nackademin.selenide.pages.BooksBrowsePage;
import se.nackademin.selenide.pages.BookFormPage;
import se.nackademin.selenide.pages.MenuPage;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * @author testautomatisering
 */
public class BookHelper {

    /**
     * This method returns a BookPage (instead of a Book transfer object)
     * The use of BookPage allows code reuse, which simplifies BookHelper class
     * 
     * @param searchQuery
     * @return 
     */
    public static BookViewPage fetchBookPage(String searchQuery) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToBrowseBooks();
        sleep(2000);
        BooksBrowsePage browseBooksPage = page(BooksBrowsePage.class);
        sleep(2000);
        browseBooksPage.setTitleField(searchQuery);
        browseBooksPage.clickSearchBooksButton();
        browseBooksPage.clickFirstResultTitle();

        BookViewPage bookPage = page(BookViewPage.class);
        return bookPage;
    }


    /**
     * Creates a new book. Author will be Terry Pratchett (the only author beginning with the letter "T")
     * 
     * @param title The new title for this book
     * @param description
     * @param numberOfPages
     * @param isbn
     * @param nbrInInventory
     * @param datePublished
     */
    public static void createBook(String title,
            String description, 
            String numberOfPages, 
            String isbn,
            String nbrInInventory,
            String datePublished) {
        
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddBook();        
        BookFormPage addBookPage = page(BookFormPage.class);
        
        addBookPage.setTitle(title);
        addBookPage.setDescription(description);       
        addBookPage.setDatePublished(datePublished);
        addBookPage.setNumberInInventory(nbrInInventory);
        
        // Add first author with a name beginning with the letter "T"
        addBookPage.sendKeysToAvailabelAuthorsList("T");
        addBookPage.clickAddAuthorButton();

        // Other properties omitted for simplicity
        
        addBookPage.clickAddBookButton();        
    }


    /**
     * Updates book with given title. 
     * Parameters with non-null values will be updated in database
     * 
     * @param searchTitle The book title to search for
     * @param newTitle The new title for this book
     * @param description
     * @param numberOfPages
     * @param isbn
     * @param numberInInventory
     * @param datePublished
     */
    public static void editBook(String searchTitle,
            String newTitle,
            String description, 
            String numberOfPages, 
            String isbn,
            String numberInInventory,
            String datePublished) {
        
        BookViewPage bookViewPage = fetchBookPage(searchTitle);        
        bookViewPage.clickEditBookButton();
        
        BookFormPage editBookPage = page(BookFormPage.class);
        
        if (newTitle!=null) { editBookPage.setTitle(newTitle); }
        if (description!=null) { editBookPage.setDescription(description); }        
        if (datePublished!=null) { editBookPage.setDatePublished(datePublished); } 
        // Other properties omitted for simplicity
        
        editBookPage.clickSaveBookButton();        
    }
    
    public static void borrowBook(String searchTitle) {
        
        BookViewPage bookViewPage = fetchBookPage(searchTitle);
        bookViewPage.clickBorrowBookButton();
        bookViewPage.clickConfirmDialogOKButton();
    }
    
    public static void returnBook(String searchTitle) {
        
        BookViewPage bookViewPage = fetchBookPage(searchTitle);
        bookViewPage.clickReturnBookButton();
        bookViewPage.clickConfirmDialogOKButton();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.librarytest.helpers;

import static com.codeborne.selenide.Selenide.page;
import se.nackademin.librarytest.model.Author;
import se.nackademin.librarytest.pages.AddAuthorPage;
import se.nackademin.librarytest.pages.AuthorPage;
import se.nackademin.librarytest.pages.BrowseAuthorsPage;
import se.nackademin.librarytest.pages.MenuPage;

/**
 *
 * @author administrator
 */
public class AuthorHelper {
    public static void createNewAuthor(String firstName, String lastName, String country, String biography) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddAuthor();

        AddAuthorPage addAuthorPage = page(AddAuthorPage.class);
        addAuthorPage.setFirstNameField(firstName);
        addAuthorPage.setLasttNameField(lastName);
        addAuthorPage.setCountryField(country);
        addAuthorPage.setBibliographyField(biography);
        addAuthorPage.clickAddAuthorButton();
    }    
    
    public static Author fetchAuthor(String searchQuery) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToBrowseAuthors();
        BrowseAuthorsPage browseAuthorsPage = page(BrowseAuthorsPage.class);
        browseAuthorsPage.setNameField(searchQuery);
        browseAuthorsPage.clickSearchAuthorsButton();
        browseAuthorsPage.clickFirstResultTitle();

        AuthorPage authorPage = page(AuthorPage.class);
        Author author = new Author();
        author.setName(authorPage.getName());
        author.setCountry(authorPage.getCountry());
        author.setBiography(authorPage.getBiography());
        return author;
    }    
}

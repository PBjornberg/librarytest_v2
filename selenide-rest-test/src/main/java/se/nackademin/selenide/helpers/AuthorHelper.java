/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.helpers;

import static com.codeborne.selenide.Selenide.page;
import se.nackademin.selenide.model.Author;
import se.nackademin.selenide.pages.AuthorAddPage;
import se.nackademin.selenide.pages.AuthorPage;
import se.nackademin.selenide.pages.AuthorsBrowsePage;
import se.nackademin.selenide.pages.MenuPage;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.page;

/**
 *
 * @author administrator
 */
public class AuthorHelper {
    public static void createNewAuthor(String firstName, String lastName, String country, String biography) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddAuthor();

        AuthorAddPage addAuthorPage = page(AuthorAddPage.class);
        addAuthorPage.setFirstNameField(firstName);
        addAuthorPage.setLasttNameField(lastName);
        addAuthorPage.setCountryField(country);
        addAuthorPage.setBibliographyField(biography);
        addAuthorPage.clickAddAuthorButton();
    }    
    
    public static Author fetchAuthor(String searchQuery) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToBrowseAuthors();
        AuthorsBrowsePage browseAuthorsPage = page(AuthorsBrowsePage.class);
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

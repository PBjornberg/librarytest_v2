/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.helpers;

import se.nackademin.selenide.pages.AuthorFormPage;
import se.nackademin.selenide.pages.AuthorViewPage;
import se.nackademin.selenide.pages.AuthorsBrowsePage;
import se.nackademin.selenide.pages.MenuPage;
import static com.codeborne.selenide.Selenide.page;

/**
 *
 * @author administrator
 */
public class AuthorHelper {
    public static void createAuthor(String firstName, String lastName, String country, String biography) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddAuthor();

        AuthorFormPage authorFormPage = page(AuthorFormPage.class);
        authorFormPage.setFirstNameField(firstName);
        authorFormPage.setLasttNameField(lastName);
        authorFormPage.setCountryField(country);
        authorFormPage.setBibliographyField(biography);
        authorFormPage.clickAddAuthorButton();
    }
    
    public static void editAuthor(String firstName, String lastName, String country, String biography) {

        AuthorFormPage authorFormPage = page(AuthorFormPage.class);
        authorFormPage.setFirstNameField(firstName);
        authorFormPage.setLasttNameField(lastName);
        authorFormPage.setCountryField(country);
        authorFormPage.setBibliographyField(biography);
        authorFormPage.clickSaveAuthorButton();
    }  
    
    
    public static AuthorViewPage fetchAuthor(String searchQuery) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToBrowseAuthors();
        AuthorsBrowsePage browseAuthorsPage = page(AuthorsBrowsePage.class);
        browseAuthorsPage.setNameField(searchQuery);
        browseAuthorsPage.clickSearchAuthorsButton();
        browseAuthorsPage.clickFirstResultTitle();

        return page(AuthorViewPage.class);
    }    
}

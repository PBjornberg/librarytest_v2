/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import java.util.logging.Logger;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author testautomatisering
 */
public class MenuPage extends PageBase {

    private static final Logger LOG = Logger.getLogger(MenuPage.class.getName());

    @FindBy(css = "#side-menu-link-browse-books")
    private SelenideElement browseBooks;
    @FindBy(css = "#side-menu-link-browse-authors")
    private SelenideElement browseAuthors;    
    @FindBy(css = "#side-menu-link-add-book")
    private SelenideElement addBook;    
    @FindBy(css = "#side-menu-link-add-author")
    private SelenideElement addAuthor;     
    @FindBy(css = "#side-menu-link-add-user")
    private SelenideElement addUser;
    @FindBy(css = "#side-menu-link-sign-in")
    private SelenideElement signIn;
    @FindBy(css = "#side-menu-link-sign-out")
    private SelenideElement signOut;    
    @FindBy(css = "#side-menu-link-my-profile")
    private SelenideElement myProfile;    
    




    public void navigateToBrowseBooks() {
        clickButton("browse books", browseBooks);
    }

    public void navigateToBrowseAuthors(){
        clickButton("browse author", browseAuthors);
    }    

    public void navigateToAddBook(){
        clickButton("add book", addBook);
    }
    
    public void navigateToAddAuthor(){
        clickButton("add author", addAuthor);
    }
    
    public void navigateToAddUser() {
        clickButton("add user", addUser);
    }

    public void navigateToSignIn() {
        clickButton("sign in", signIn);
    }

    public void clickSignOut(){
        clickButton("sign out", signOut);
    }    
    
    public void navigateToMyProfile() {
        clickButton("my profile", myProfile);
    }
    
    /**
     * Checks whether ProfileLink is visible or not
     * @return flag signaling the visibility of Profile Link in menu
     */
    public boolean isMyProfileLinkVisibel() {
        return myProfile.isDisplayed();
    }
    
    /**
     * Waits up to 10 seconds for Profile Link to become visible 
     */
    public void waitForProfileLinkToShow(){
        WebDriverWait wait;
        wait = new WebDriverWait(getWebDriver(), 10);
        WebElement element;
        element=wait.until(ExpectedConditions.elementToBeClickable(myProfile));
    }
}

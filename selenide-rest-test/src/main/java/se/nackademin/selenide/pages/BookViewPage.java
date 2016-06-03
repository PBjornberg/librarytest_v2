/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author testautomatisering
 */
public class BookViewPage extends MenuPage {

    @FindBy(css = "#gwt-uid-3")
    private SelenideElement titleField;
    @FindBy(css = "#gwt-uid-5")
    private SelenideElement authorField;
    @FindBy(css = "#gwt-uid-7")
    private SelenideElement descriptionField;
    @FindBy(css = "#gwt-uid-11")
    private SelenideElement datePublishedField;  
    @FindBy(css = "#gwt-uid-13")
    private SelenideElement nbrOfCopiesAvailableField;  
    
    // The leftmost button is prefixed either "borrow" or "return" depending on the books status.
    @FindBy(css = "#borrow-book-button")
    private SelenideElement borrowBookButton;  
    @FindBy(css = "#return-book-button")
    private SelenideElement returnBookButton; 
    
    @FindBy(css = "#edit-book-button")
    private SelenideElement editBookButton;
    @FindBy(css = "#confirmdialog-ok-button")
    private SelenideElement confirmDialogOKButton;  
    

    public String getTitle() {       
        return titleField.getText();
    }

    public String getAuthor() {
        return authorField.getText();
    }

    public String getDescription() {
        return descriptionField.getText();
    }
    
    public String getDatePublished() {
        return datePublishedField.getText();
    }

    public String getNbrOfCopiesAvailable() {
        return nbrOfCopiesAvailableField.getText();
    }    
    
    public void clickBorrowBookButton() {
        clickButton("borrow book button", borrowBookButton);
    } 
    public boolean isBorrowBookButtonDisplayed(){
        return borrowBookButton.isDisplayed();
    }
    
    public void clickReturnBookButton() {
        clickButton("return book button", returnBookButton);
    } 
    
    public void clickConfirmDialogOKButton() {
        clickButton("confirm dialog OK button", confirmDialogOKButton);
    }    
    
    public void clickEditBookButton() {
        clickButton("edit book button", editBookButton);
    }

    public void waitForPageToLoad() {
        WebElement element = new WebDriverWait(getWebDriver(), 30).until(ExpectedConditions.elementToBeClickable(nbrOfCopiesAvailableField)); 
    }
}

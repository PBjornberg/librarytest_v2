/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author testautomatisering
 */
public class UserFormPage extends MenuPage {

    @FindBy(css = "#gwt-uid-3")
    private SelenideElement userNameField;
    @FindBy(css = "#gwt-uid-5")
    private SelenideElement passwordField;
    @FindBy(css = "#gwt-uid-7")
    private SelenideElement firstNameField;
    @FindBy(css = "#gwt-uid-9")
    private SelenideElement lastNameField;
    @FindBy(css = "#gwt-uid-11")
    private SelenideElement phoneNbrField;
    @FindBy(css = "#gwt-uid-13")
    private SelenideElement emailField; 
    //@FindBy(css = "#gwt-uid-16")
    @FindBy(id = "gwt-uid-16")  
    private SelenideElement librarianRadioButton;    
  
    // The button at the bottom is prefixed either "add" or "save" depending on wich URL this Page is mapped against.
    @FindBy(css = "#add-user-button")
    private SelenideElement addUserButton;
    @FindBy(css = "#save-user-button")
    private SelenideElement saveUserButton;
    
    @FindBy(css = ".v-label-undef-w")
    private SelenideElement messageField;    
    
            
    

    public String getUserName(){
        return userNameField.getText();
    }
    
    public void setUsername(String username) {
        setTextFieldValue("user name field", username, userNameField);
    }

    public String getPassword(){
        return passwordField.getText();
    }
    
    public void setPassword(String password) {
        setTextFieldValue("password field", password, passwordField);
    }
    
    public String getFirstName(){
        return firstNameField.getText();
    }
        
    public void setFirstName(String firstName) {
        setTextFieldValue("firstname field", firstName, firstNameField);
    }
    
    public String getLastName(){
        return lastNameField.getText();
    }
        
    public void setLastName(String lastName) {
        setTextFieldValue("lastname field", lastName, lastNameField);
    }
    
    public String getPhoneNbr(){
        return phoneNbrField.getText();
    }
    public void setPhoneNbr(String phoneNbr) {
        setTextFieldValue("phoneNbr field", phoneNbr, phoneNbrField);
    }
    
    public String getEmail(){
        return emailField.getText();
    }
    
    public void setEmail(String email) {
        setTextFieldValue("email field", email, emailField);
    }

    public String getMessage(){
        return messageField.getText();
    }
    
    public void clickLibrarianRadioButton() {
        
        clickButton("librarian role radio button", librarianRadioButton);
        WebElement element = new WebDriverWait(getWebDriver(), 30).until(ExpectedConditions.elementToBeClickable(librarianRadioButton));
        element.sendKeys(Keys.SPACE);
    }
    
    public void clickAddUserButton() {
        clickButton("add user button", addUserButton);
    }
    
    public void clickSaveUserButton() {
        clickButton("save user button", saveUserButton);
    }    
}

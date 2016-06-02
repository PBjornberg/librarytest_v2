/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author administrator
 */
public class BookFormPage extends PageBase {
    
    @FindBy(css = "#gwt-uid-3")
    private SelenideElement titleField;
    @FindBy(css = ".v-select-twincol-options")
    private SelenideElement availableAuthorsList;
    @FindBy(css = ".v-select-twincol-options > option:nth-child(1)")
    private SelenideElement firstAuthorInAvailableList;
    @FindBy(css = ".v-select-twincol-selections > option:nth-child(1)")
    private SelenideElement firstAuthorInSelectedList; 
    @FindBy(css = ".v-select-twincol-buttons > div:nth-child(1)")
    private SelenideElement addAuthorButton;
    @FindBy(css = ".v-select-twincol-buttons > div:nth-child(3)")
    private SelenideElement removeAuthorButton; 
    @FindBy(css = "#gwt-uid-9")
    private SelenideElement descriptionField;
    @FindBy(css = "#gwt-uid-11")
    private SelenideElement numberOfPagesField;
    @FindBy(css = "#gwt-uid-13")
    private SelenideElement isbnField; 
    @FindBy(css = "#gwt-uid-5")
    private SelenideElement nbrInInventoryField;     
    @FindBy(css = "#gwt-uid-7")
    private SelenideElement datePublishedField; 
    
    // The button at the bottom is prefixed either "save" or "add" depending on wich URL this Page is mapped against    
    @FindBy(css = "#save-book-button")            
    private SelenideElement saveBookButton;    
    @FindBy(css = "#add-book-button")            
    private SelenideElement addBookButton;
    
    
    public String getTitle(){
        return titleField.getText();
    }
    
    public void setTitle(String title) {
        setTextFieldValue("title field", title, titleField);
    }
    
    public String getDescription(){
        return descriptionField.getText();
    }
    
    public void setDescription(String description) {
        setTextFieldValue("description field", description, descriptionField);
    }
    
    public String getDatePublished(){
        return datePublishedField.getText();
    }
    
    public void setDatePublished(String datePublished) {
        setTextFieldValue("date published field", datePublished, datePublishedField);
    }
    
    public void sendKeysToAvailabelAuthorsList(String keysToSend){
        availableAuthorsList.sendKeys(keysToSend);        
    }
    
    public void clickFirstAuthorInAvailableList(){
        clickButton("click first author in available list", firstAuthorInAvailableList);
    }
    
    public void clickFirstAuthorInSelectedList(){
        clickButton("click first author in selected list", firstAuthorInSelectedList);
    }

    public void clickAddAuthorButton() {
        clickButton("add author button", addAuthorButton);
    }
    
    public void clickRemoveAuthorButton() {
        clickButton("remove author button", removeAuthorButton);
    }     

    public void clickSaveBookButton() {
        clickButton("save book button", saveBookButton);
    } 
    
    public void clickAddBookButton() {
        clickButton("add book button", addBookButton);
    } 
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.librarytest.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author administrator
 */
public class EditBookPage extends PageBase {
    
    @FindBy(css = "#gwt-uid-3")
    private SelenideElement titleField;
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
    @FindBy(css = "#save-book-button")            
    private SelenideElement saveBookButton;   
    
    
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
    
    public void clickSaveBookButton() {
        clickButton("save book button", saveBookButton);
    } 
}

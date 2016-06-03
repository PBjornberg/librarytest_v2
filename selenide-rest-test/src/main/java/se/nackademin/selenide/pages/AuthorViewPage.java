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
public class AuthorViewPage extends PageBase{
    @FindBy(css = "#gwt-uid-3")
    private SelenideElement nameField;
    @FindBy(css = "#gwt-uid-5")
    private SelenideElement countryField; 
    @FindBy(css = "#gwt-uid-7")
    private SelenideElement biographyField;
    @FindBy(css = "#edit-author-button")
    private SelenideElement editAuthorButton;
    @FindBy(css = "#delete-author-button")
    private SelenideElement deleteAuthorButton;


    public String getName() {
        return nameField.getText();
    }

    public String getCountry() {
        return countryField.getText();
    }

    public String getBiography() {
        return biographyField.getText();
    }
    
    public void clickEditAuthorButton() {
        clickButton("edit author button", editAuthorButton);
    }
    
    public void clickDeleteAuthorButton() {
        clickButton("delete author button", deleteAuthorButton);
    } 
    
}

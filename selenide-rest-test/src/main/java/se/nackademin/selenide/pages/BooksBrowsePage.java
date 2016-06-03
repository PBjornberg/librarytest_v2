/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.pages;

import static com.codeborne.selenide.Selenide.sleep;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author testautomatisering
 */
public class BooksBrowsePage extends MenuPage {
    @FindBy(css = "#gwt-uid-3")
    private SelenideElement titleField;
    @FindBy(css = "#search-books-button")
    private SelenideElement searchBooksButton;
    @FindBy(css = "td.v-grid-cell:nth-child(1) > a:nth-child(1)")
    private SelenideElement firstResultTitle;

    public void clickFirstResultTitle() {
        firstResultTitle.click();
    }

    public void setTitleField(String title) {
        sleep(2000);
        setTextFieldValue("title field", title, titleField);
    }

    public void clickSearchBooksButton() {
        searchBooksButton.click();
    }
}

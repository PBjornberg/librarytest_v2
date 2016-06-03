/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.librarytest;

import com.codeborne.selenide.Configuration;

import org.junit.After;
import org.junit.Before;
import static com.codeborne.selenide.Selenide.open;
import static se.nackademin.librarytest.Constants.SELENIDE_BASE_URL;
import static se.nackademin.librarytest.Constants.TIMEOUT_VALUE;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author testautomatisering
 */
public class SelenideTestBase {
    
    @Before
    public void setup() {
        open(SELENIDE_BASE_URL);
        Configuration.timeout = TIMEOUT_VALUE;
    }

    @After
    public void teardown() {

    }
}

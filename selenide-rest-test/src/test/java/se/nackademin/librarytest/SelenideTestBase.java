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

/**
 * @author testautomatisering
 */
public class SelenideTestBase {
    
    protected static final String ADMIN_USER = "admin";
    protected static final String ADMIN_PWD = "1234567890";
    
    @Before
    public void setup() {
        open("http://localhost:8080/librarytest");
        Configuration.timeout = 6000;
    }

    @After
    public void teardown() {

    }
}

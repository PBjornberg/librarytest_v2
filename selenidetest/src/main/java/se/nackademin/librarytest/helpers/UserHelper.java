/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.librarytest.helpers;


import se.nackademin.librarytest.model.User;
import se.nackademin.librarytest.pages.AddOrEditUserPage;
import se.nackademin.librarytest.pages.MenuPage;
import se.nackademin.librarytest.pages.SignInPage;
import se.nackademin.librarytest.pages.MyProfilePage;
import static com.codeborne.selenide.Selenide.page;

/**
 * @author testautomatisering
 */
public class UserHelper {
    public static void createNewUser(String username, 
            String password, 
            String firstName, 
            String lastName, 
            String phoneNbr, 
            String email) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddUser();

        AddOrEditUserPage addUserPage = page(AddOrEditUserPage.class);
        addUserPage.setUsername(username);
        addUserPage.setPassword(password);
        addUserPage.setFirstName(firstName);
        addUserPage.setLastName(lastName);
        addUserPage.setPhoneNbr(phoneNbr);
        addUserPage.setEmail(email);
        addUserPage.clickAddUserButton();
    }
    
    /**
     * Updates signed in user. 
     * Parameters with non-null values will be updated in database.
     * 
     * @param password
     * @param firstName
     * @param lastName
     * @param phoneNbr
     * @param email 
     */
    public static void updateLoggedInUser(String password,
            String firstName,
            String lastName,
            String phoneNbr,
            String email) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToMyProfile();
        
        MyProfilePage myProfilePage = page(MyProfilePage.class);
        myProfilePage.clickEditUserButton();
        
        AddOrEditUserPage editUserPage = page(AddOrEditUserPage.class);

        if (password!=null) { editUserPage.setPassword(password); }
        if (firstName!=null) { editUserPage.setFirstName(firstName); }        
        if (lastName!=null) { editUserPage.setLastName(lastName); } 
        if (phoneNbr!=null) { editUserPage.setPhoneNbr(phoneNbr); } 
        if (email!=null) { editUserPage.setEmail(email); }        
        editUserPage.clickSaveUserButton();
    }

    public static void logInAsUser(String username, String password) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToSignIn();
        SignInPage signInPage = page(SignInPage.class);
        signInPage.setUsername(username);
        signInPage.setPassword(password);
        signInPage.clickLogIn();
    }
    
    public static User fetchUser(String username, String password) {
        logInAsUser(username, password);
        
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToMyProfile();

        MyProfilePage myProfilePage = page(MyProfilePage.class);

        User user = new User();
        user.setUserName(myProfilePage.getUserName());
        user.setFirstName(myProfilePage.getFirstName());
        user.setLastName(myProfilePage.getLastName());
        user.setPhoneNbr(myProfilePage.getPhoneNbr());
        user.setEmail(myProfilePage.getEmail());
        return user;
    }    
}

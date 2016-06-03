/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenide.helpers;


import se.nackademin.selenide.model.User;
import se.nackademin.selenide.pages.UserFormPage;
import se.nackademin.selenide.pages.MenuPage;
import se.nackademin.selenide.pages.UserSignInPage;
import se.nackademin.selenide.pages.MyProfilePage;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.page;

/**
 * @author testautomatisering
 */
public class UserHelper {
    
    /**
     * Creates a new User
     * 
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param phoneNbr
     * @param email
     * @param librarianRole true = LIBRARIAN, otherwise LOANER
     * @return UserFormPage
     */
    public static UserFormPage createNewUser(String username, 
            String password, 
            String firstName, 
            String lastName, 
            String phoneNbr, 
            String email,
            boolean librarianRole) {
        
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddUser();

        UserFormPage userFormPage = page(UserFormPage.class);
        userFormPage.setUsername(username);
        userFormPage.setPassword(password);
        userFormPage.setFirstName(firstName);
        userFormPage.setLastName(lastName);
        userFormPage.setPhoneNbr(phoneNbr);
        userFormPage.setEmail(email);
        if (librarianRole) {
                userFormPage.clickLibrarianRadioButton();
            }
        userFormPage.clickAddUserButton();
        return userFormPage;
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
        
        UserFormPage editUserPage = page(UserFormPage.class);

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
        UserSignInPage signInPage = page(UserSignInPage.class);
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
    
    /**
     * Deletes the logged in user. 
     * Logged in user must have role "LIBRARIAN", otherwise delete button will not be visible
     * 
     * @param username
     * @param password 
     */
    public static void deleteUser(String username, String password){
        logInAsUser(username, password);
        MyProfilePage myProfilePage = page(MyProfilePage.class);
        myProfilePage.clickDeleteUserButton();
    }
}

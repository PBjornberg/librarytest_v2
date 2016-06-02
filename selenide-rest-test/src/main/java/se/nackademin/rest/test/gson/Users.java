package se.nackademin.rest.test.gson;

import java.util.List;

public class Users {
    private List <User> user;

    /**
     * @return the user
     */
    public List <User> getUserList() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUserList(List <User> user) {
        this.user = user;
    }  
}

package se.nackademin.rest.test.gson;

public class SingleUser {
    private User user;
    
    public SingleUser(User user){
        this.user=user;
    }

    /**
     * @return the author
     */
    public User getUser() {
        return user;
    }

    /**
     * @param author the author to set
     */
    public void setUser(User user) {
        this.user = user;
    }
}

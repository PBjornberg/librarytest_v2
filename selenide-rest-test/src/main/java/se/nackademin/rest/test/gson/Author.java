package se.nackademin.rest.test.gson;

public class Author {
    private Integer id;
    private String bio;
    private String country;      
    private String firstName;
    private String lastName;    

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    /**
     * @return the First name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the First name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
        /**
     * @return the Last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param firstName the Last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

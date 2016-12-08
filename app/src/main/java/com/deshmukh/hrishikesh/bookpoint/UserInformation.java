package com.deshmukh.hrishikesh.bookpoint;

/**
 * Created by hrishikesh on 12/7/16.
 */

public class UserInformation {

    public String mFirstName;
    public String LastName;
    public String PhoneNumber;
    public String email;
    public String Password;
    public String Location;


    public UserInformation(){

    }

    public UserInformation(String firstName, String lastName, String phoneNumber, String email, String password, String location) {
        mFirstName = firstName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
        this.email = email;
        Password = password;
        Location = location;
    }
}

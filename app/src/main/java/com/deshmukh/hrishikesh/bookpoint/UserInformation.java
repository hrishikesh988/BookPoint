package com.deshmukh.hrishikesh.bookpoint;

/**
 * Created by Atharva on 12/7/16.
 *
 * this is a class to store information about a user
 * This class store following information about user
 *  - First name
 *  - Last Name
 *  - Phone nuber
 *  - Email
 *  - Password
 *  - Location
 */

public class UserInformation {

    public String mFirstName;
    public String LastName;
    public String PhoneNumber;
    public String email;
    public String Password;
    public String Location;

    //default constructor
    public UserInformation(){

    }
    //Fully classified constructor to initialize user details
    public UserInformation(String firstName, String lastName, String phoneNumber, String email, String password, String location) {
        mFirstName = firstName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
        this.email = email;
        Password = password;
        Location = location;
    }
}

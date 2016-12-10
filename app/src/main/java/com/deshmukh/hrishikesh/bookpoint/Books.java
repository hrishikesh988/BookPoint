package com.deshmukh.hrishikesh.bookpoint;

/**
 * Created by hrishikesh on 12/8/16.
 * This is book class which is a model for book on sell.
 * This class creates a instance of book which has properties: Title, Price, Book's publisher, its condition and UID of a seller
 */

public class Books {

    /**
     *  Title - Saves the title of a book on sale
     * Price - Saves the price of a book on sale
     * Publisher - Saves the publisher of a book on sale
     * Condition - Saves the condition of a book on sale: {Like a new, In good condition, used}
     * UID - saves Unique id of a seller
     */
    public String Title;
    public String Price;
    public String Publisher;
    public String Condition;
    public String UID;


    // Default constructor
    public Books() {

    }

    //Fully classified constructor to save details of a book on sale
    public Books(String title, String price, String publisher, String condition, String uid) {
        Title = title;
        Price = price;
        Publisher = publisher;
        Condition = condition;
        UID = uid;
    }
}


package com.deshmukh.hrishikesh.bookpoint;

/**
 * Created by hrishikesh on 12/8/16.
 */

public class BookList {

    private String title;
    private String price;
    private String publisher;
    private String condition;
    private String sold_by;
    private String ships_from;

    public BookList(String title, String price, String publisher, String condition, String sold_by, String ships_from) {
        this.title = title;
        this.price = price;
        this.publisher = publisher;
        this.condition = condition;
        this.sold_by = sold_by;
        this.ships_from = ships_from;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCondition() {
        return condition;
    }

    public String getSold_by() {
        return sold_by;
    }

    public String getShips_from() {
        return ships_from;
    }
}

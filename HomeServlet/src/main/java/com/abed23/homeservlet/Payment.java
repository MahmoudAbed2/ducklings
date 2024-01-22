package com.abed23.homeservlet;

// Payment.java

import java.io.Serializable;

public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String title;
    private String date;
    private String description;
    private String category;
    private double amount;

    public Payment(int id, String title, String date, String description, String category, double amount) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

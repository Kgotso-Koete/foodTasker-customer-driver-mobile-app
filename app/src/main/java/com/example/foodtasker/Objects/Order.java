// ALL UPDATES COMPLETED
package com.example.foodtasker.Objects;

public class Order {
    private String id;
    private String restaurantName;
    private String customerName;
    private String customerAddress;
    private String customerImage;

    public Order(String id, String restaurantName, String customerName, String customerAddress, String customerImage) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerImage = customerImage;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    @Override
    public String toString() {
        return restaurantName;
    }
}
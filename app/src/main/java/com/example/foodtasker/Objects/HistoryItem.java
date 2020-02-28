// ALL UPDATES COMPLETED
package com.example.foodtasker.Objects;

public class HistoryItem {
    private String id;
    private String mealName;
    private String mealQuantity;
    private String mealSubTotal;

    public HistoryItem(String id, String mealName, String mealQuantity, String mealSubTotal) {
        this.id = id;
        this.mealName = mealName;
        this.mealQuantity = mealQuantity;
        this.mealSubTotal = mealSubTotal;
    }

    public String getId() {
        return id;
    }

    public String getMealName() {
        return mealName;
    }

    public String getMealQuantity() {
        return mealQuantity;
    }

    public String getMealSubTotal() {
        return mealSubTotal;
    }

    @Override
    public String toString() {
        return mealName;
    }

}